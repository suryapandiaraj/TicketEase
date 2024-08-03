package TicketEase.Service;

import TicketEase.Models.Movie;
import TicketEase.Models.Show;
import TicketEase.Models.ShowSeat;
import TicketEase.Models.Theater;
import TicketEase.Models.Ticket;
import TicketEase.Models.User;
import TicketEase.Repository.MovieRepository;
import TicketEase.Repository.ShowRepository;
import TicketEase.Repository.TheaterRepository;
import TicketEase.Repository.TicketRepository;
import TicketEase.Repository.UserRepository;
import TicketEase.RequestDtos.BookTicketRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public String bookTicket(BookTicketRequest bookTicketRequest){

        Show show = findRightShow(bookTicketRequest);
        //My steps are :
        List<ShowSeat> showSeatList = show.getShowSeatList();
        //Whatever are the requested seats : mark them as not available in show seats

        int totalPrice = 0;
        for(ShowSeat showSeat:showSeatList) {

            if(bookTicketRequest.getRequestedSeatNos().contains(showSeat.getSeatNo())) {
                if(!showSeat.isAvailable())
                    return "ShowSeat already booked by someone";
                showSeat.setAvailable(false);
                totalPrice = totalPrice + showSeat.getCost();
            }
        }

        User user = userRepository.findById(bookTicketRequest.getUserId()).get();

        Ticket ticket = Ticket.builder()
                .movieName(show.getMovie().getMovieName())
                .theaterAddress(show.getTheater().getAddress())
                .showDate(show.getShowDate())
                .showTime(show.getShowTime())
                .bookedSeats(bookTicketRequest.getRequestedSeatNos().toString())
                .user(user)
                .show(show)
                .totalPrice(totalPrice)
                .build();

        show.getTicketList().add(ticket);
        user.getTicketList().add(ticket);
        ticketRepository.save(ticket);

        SimpleMailMessage mailMessage=new SimpleMailMessage();
        String body ="Hi "+ticket.getUser().getName()+" Your Ticket Conform for Movie "+ticket.getMovieName()+" "+"Seats NO's= "+ticket.getBookedSeats()+" Total Booked Seats : "+bookTicketRequest.getRequestedSeatNos().size();
        mailMessage.setFrom("TicketEase@gmail.com");
        mailMessage.setTo(ticket.getUser().getEmailId());
        mailMessage.setSubject("Book My Show Ticket Book Successful");
        mailMessage.setText(body);
        mailSender.send(mailMessage);

        return "Ticket has been booked";

        //Calculate total Price
        //We also need to add it to list of booked tickets against user
    }

    private Show findRightShow(BookTicketRequest bookTicketRequest){

        Movie movie = movieRepository.findMovieByMovieName(bookTicketRequest.getMovieName());
        Theater theater = theaterRepository.findById(bookTicketRequest.getTheaterId()).get();

        Show show = showRepository.findShowByShowDateAndShowTimeAndMovieAndTheater(bookTicketRequest.getShowDate()
                                                                        ,bookTicketRequest.getShowTime(),
                                                                        movie,theater);


        return show;
    }

    public String cancleTicket(BookTicketRequest bookTicketRequest,int ticketId)
    {
        Optional<Ticket> optionalTicket = ticketRepository.findById(ticketId);
        if(!optionalTicket.isPresent()) return "Invalid Ticket";
        Show show = findRightShow(bookTicketRequest);
        List<ShowSeat> showSeatList = show.getShowSeatList();


        int totalPrice = 0;
        for(ShowSeat showSeat:showSeatList) {

            if(bookTicketRequest.getRequestedSeatNos().contains(showSeat.getSeatNo())) {
                showSeat.setAvailable(true);
                totalPrice = totalPrice + showSeat.getCost();
            }
        }

        Ticket ticket = optionalTicket.get();

        User user = userRepository.findById(bookTicketRequest.getUserId()).get();
        ticketRepository.deleteById(ticketId);
        show.getTicketList().remove(ticket);
        user.getTicketList().remove(ticket);


        showRepository.save(show);

        return "Ticket canceled Successful and Amount Refunded "+totalPrice;
    }

}
