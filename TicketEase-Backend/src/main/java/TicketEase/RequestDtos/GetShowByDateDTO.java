package TicketEase.RequestDtos;

import TicketEase.Enums.City;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetShowByDateDTO {
    LocalDate date;
    LocalTime time;
    String movieName;
    String theaterName;
    City city;
    String address;
}
