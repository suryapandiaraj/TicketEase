package BookMyShow.Transformers;

import BookMyShow.Models.Show;
import BookMyShow.RequestDtos.AddShowRequest;

public class ShowTransformers {

    public static Show convertAddRequestToEntity(AddShowRequest addShowRequest){

        Show showObj = Show.builder().showDate(addShowRequest.getShowDate())
                .showTime(addShowRequest.getShowTime())
                .build();

        return showObj;
    }
}
