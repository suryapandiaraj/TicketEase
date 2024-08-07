package TicketEase.Controllers;

import TicketEase.RequestDtos.BookTicketRequest;
import TicketEase.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/bookTicket")
    private String bookTicket(@RequestBody BookTicketRequest bookTicketRequest) {

        String result  = ticketService.bookTicket(bookTicketRequest);
        return result;
    }

    @DeleteMapping("/cancelTicket")
    private String cancelTicket(@RequestBody BookTicketRequest bookTicketRequest, @RequestParam int ticketId) {
        
        return ticketService.cancelTicket(bookTicketRequest,ticketId);

    }

}
