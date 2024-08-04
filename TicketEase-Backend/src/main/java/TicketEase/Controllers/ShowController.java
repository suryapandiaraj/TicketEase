package TicketEase.Controllers;

import TicketEase.Enums.City;
import TicketEase.RequestDtos.AddShowRequest;
import TicketEase.RequestDtos.AddShowSeatsRequest;
import TicketEase.RequestDtos.GetShowByDateDTO;
import TicketEase.Service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("show")
public class ShowController {

    @Autowired
    private ShowService showService;

    @PostMapping("/addShow")
    public String addShow(@RequestBody AddShowRequest addShowRequest){
        try {
            String result = showService.addShow(addShowRequest);
            return result;
        }
        catch (Exception e)
        {
            return e.getMessage();
        }

    }

    @PostMapping("/createShowSeats")
    public String enableShowSeats(@RequestBody AddShowSeatsRequest addShowSeatsRequest) {

        String result = showService.createShowSeats(addShowSeatsRequest);
        return result;
    }

    @GetMapping("/getShowByDate")
    public ResponseEntity getShowByDate(@RequestParam("date")LocalDate date, @RequestParam("movieName")String movieName, @RequestParam("city")City city)
    {
        List<GetShowByDateDTO> result = showService.getShowByDate(date,movieName,city);
        if(result.size()==0)
            return new ResponseEntity<>("Show not found", HttpStatus.FOUND);
        else return new ResponseEntity<>(result, HttpStatus.FOUND);
    }

    @GetMapping("/getBookedSeat")
    public List<String> getBookedSeat(@RequestParam("showId") int showId, @RequestParam("theaterId") int theaterId)
    {
        return showService.getBookedSeat(showId,theaterId);
    }

    @GetMapping("/getUnbookedSeat")
    public List<String> getUnbookedSeat(@RequestParam("showId") int showId, @RequestParam("theaterId") int theaterId)
    {
        return showService.getUnbookedSeat(showId,theaterId);
    }



}
