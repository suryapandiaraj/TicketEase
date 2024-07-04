package BookMyShow.RequestDtos;

import BookMyShow.Enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class AddMovieRequest {

    private String movieName;
    private Double rating;
    private Genre genre;
    private LocalDate releaseDate;
}
