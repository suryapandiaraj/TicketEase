package TicketEase.RequestDtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddShowSeatsRequest {

    private Integer showId;

    private Integer priceOfClassicSeats;

    private Integer priceOfPremiumSeats;
}
