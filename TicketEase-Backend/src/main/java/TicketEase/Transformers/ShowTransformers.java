package TicketEase.Transformers;

import TicketEase.Models.Show;
import TicketEase.RequestDtos.AddShowRequest;

public class ShowTransformers {

    public static Show convertAddRequestToEntity(AddShowRequest addShowRequest){

        Show showObj = Show.builder().showDate(addShowRequest.getShowDate())
                .showTime(addShowRequest.getShowTime())
                .build();

        return showObj;
    }
}
