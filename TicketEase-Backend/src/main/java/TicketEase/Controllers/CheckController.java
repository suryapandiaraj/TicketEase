package TicketEase.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class CheckController {
    @GetMapping("/check")
    String check() {
        return "Check OK.";
    }
}