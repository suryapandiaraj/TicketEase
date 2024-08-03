package TicketEase.Controllers;

import TicketEase.RequestDtos.AddUserRequest;
import TicketEase.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody AddUserRequest addUserRequest){

        String result = userService.addUser(addUserRequest);
        return new ResponseEntity(result, HttpStatus.OK);
    }


}
