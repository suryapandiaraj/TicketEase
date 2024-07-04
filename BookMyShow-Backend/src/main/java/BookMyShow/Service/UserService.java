package BookMyShow.Service;

import BookMyShow.Models.User;
import BookMyShow.Repository.UserRepository;
import BookMyShow.RequestDtos.AddUserRequest;
import BookMyShow.Transformers.UserTransformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String addUser(AddUserRequest addUserRequest) {
        
        User userObj = UserTransformers.convertAddUserReqToUserEntity(addUserRequest);
        userRepository.save(userObj);

        Collections.sort(new ArrayList<Integer>());
        return "User added successfully";
    }


}
