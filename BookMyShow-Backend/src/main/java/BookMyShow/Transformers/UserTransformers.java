package BookMyShow.Transformers;

import BookMyShow.Models.User;
import BookMyShow.RequestDtos.AddUserRequest;

public class UserTransformers {


    public static User convertAddUserReqToUserEntity(AddUserRequest addUserRequest){


        User userObj = User.builder()
                .age(addUserRequest.getAge())
                .emailId(addUserRequest.getEmailId())
                .mobNo(addUserRequest.getMobNo())
                .name(addUserRequest.getName())
                .build();


        return userObj;

    }


}
