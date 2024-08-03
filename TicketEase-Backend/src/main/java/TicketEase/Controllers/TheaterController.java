package TicketEase.Controllers;

import TicketEase.Enums.City;
import TicketEase.RequestDtos.AddTheaterRequest;
import TicketEase.Service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("theater")
public class TheaterController {
    @Autowired
    private TheaterService theaterService;

    @PostMapping("/addTheater")
    public ResponseEntity addTheater(@RequestBody AddTheaterRequest addTheaterRequest){
        String result = theaterService.addTheater(addTheaterRequest);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/showTheaterByLocation")
    public ResponseEntity showTheaterByLocation(@RequestParam("location") City location)
    {
        List<String> result = theaterService.showTheaterByLocation(location);
        return new ResponseEntity<>(result,HttpStatus.FOUND);
    }

}