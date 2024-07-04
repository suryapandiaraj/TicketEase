package BookMyShow.Service;

import BookMyShow.CustomException.ShowAlreadyExistException;
import BookMyShow.Enums.City;
import BookMyShow.Enums.SeatType;
import BookMyShow.Models.Movie;
import BookMyShow.Models.Show;
import BookMyShow.Models.ShowSeat;
import BookMyShow.Models.Theater;
import BookMyShow.Models.TheaterSeat;
import BookMyShow.Repository.MovieRepository;
import BookMyShow.Repository.ShowRepository;
import BookMyShow.Repository.ShowSeatRespository;
import BookMyShow.Repository.TheaterRepository;
import BookMyShow.RequestDtos.AddShowRequest;
import BookMyShow.RequestDtos.AddShowSeatsRequest;
import BookMyShow.RequestDtos.GetShowByDateDTO;
import BookMyShow.Transformers.ShowTransformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShowService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ShowSeatRespository showSeatRespository;

    public String addShow(AddShowRequest addShowRequest) throws ShowAlreadyExistException{

        //Goal is to set the attributes of the Show Entity and save it to db.

        Show show = ShowTransformers.convertAddRequestToEntity(addShowRequest);
        Show findshow =showRepository.findShowByDateAndTime(show.getShowDate(),show.getShowTime(),addShowRequest.getTheaterId());
        if(findshow!=null){
            throw new ShowAlreadyExistException("THE SHOW ALREADY AVAILABLE FOR THE DATE "+show.getShowDate()+" TIME "+show.getShowTime() );
        }
        Movie movie = movieRepository.findMovieByMovieName(addShowRequest.getMovieName());

        Optional<Theater> optionalTheater = theaterRepository.findById(addShowRequest.getTheaterId());
        Theater theater = optionalTheater.get();

        //Setting the FK values
        show.setMovie(movie);
        show.setTheater(theater);

        //Setting the bidirectional mapping
        theater.getShowList().add(show);
        movie.getShowList().add(show);

        show = showRepository.save(show);

        return "Show has been saved to the DB with showId "+show.getShowId();

    }

    public String createShowSeats(AddShowSeatsRequest showSeatsRequest){

        //I need to create the show Seats and save to the DB.

        Show show = showRepository.findById(showSeatsRequest.getShowId()).get();
        Theater theater = show.getTheater();
        List<TheaterSeat> theaterSeatList = theater.getTheaterSeatList();

        List<ShowSeat> showSeatList = new ArrayList<>();


        for(TheaterSeat theaterSeat:theaterSeatList) {

            ShowSeat showSeat = ShowSeat.builder()
                    .seatNo(theaterSeat.getSeatNo())
                    .seatType(theaterSeat.getSeatType())
                    .isAvailable(true)
                    .isFoodAttached(false)
                    .show(show)
                    .build();

            if(theaterSeat.getSeatType().equals(SeatType.CLASSIC)){
                showSeat.setCost(showSeatsRequest.getPriceOfClassicSeats());
            }
            else{
                showSeat.setCost(showSeatsRequest.getPriceOfPremiumSeats());
            }

            showSeatList.add(showSeat);
        }

        show.setShowSeatList(showSeatList);

        //Either save parent or save child

        //child is alot of seats (you need to save that list)

        showRepository.save(show);
        return "The show seats have been added";

    }

    public List<GetShowByDateDTO> getShowByDate(LocalDate date,String movieName,City city)
    {
        List<Show> showList = showRepository.getShowByDate(date);
        List<GetShowByDateDTO> result = new ArrayList<>();
        for(Show show : showList)
        {
            if(show.getMovie().getMovieName().equals(movieName) && show.getTheater().getCity().equals(city))
            {
                GetShowByDateDTO getShowByDateDTO = GetShowByDateDTO.builder()
                        .date(date)
                        .theaterName(show.getTheater().getName())
                        .time(show.getShowTime())
                        .movieName(movieName)
                        .city(city)
                        .address(show.getTheater().getAddress())
                        .build();
                result.add(getShowByDateDTO);
            }
        }
        return result;
    }

    public List<String> getBookedSeat(int showId, int theaterId)
    {
        Theater theater = theaterRepository.findById(theaterId).get();
        Show show = showRepository.findById(showId).get();

        List<String> result = new ArrayList<>();
        List<ShowSeat> showSeatList = show.getShowSeatList();

        for (ShowSeat showSeat : showSeatList)
        {
            if(!showSeat.isAvailable())
            {
                String s = showSeat.getSeatNo()+" "+showSeat.getSeatType();
                result.add(s);
            }
        }
        return result;
    }

    public List<String> getUnbookedSeat(int showId, int theaterId)
    {
        Theater theater = theaterRepository.findById(theaterId).get();
        Show show = showRepository.findById(showId).get();

        List<String> result = new ArrayList<>();
        List<ShowSeat> showSeatList = show.getShowSeatList();

        for (ShowSeat showSeat : showSeatList)
        {
            if(showSeat.isAvailable())
            {
                String s = showSeat.getSeatNo()+" "+showSeat.getSeatType();
                result.add(s);
            }
        }
        return result;
    }

}
