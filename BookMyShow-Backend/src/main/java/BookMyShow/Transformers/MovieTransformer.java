package BookMyShow.Transformers;

import BookMyShow.Models.Movie;
import BookMyShow.RequestDtos.AddMovieRequest;

public class MovieTransformer {

    public static Movie convertAddMovieReqToMovie(AddMovieRequest addMovieRequest){

        Movie movie = Movie.builder()
                .movieName(addMovieRequest.getMovieName())
                .genre(addMovieRequest.getGenre())
                .releaseDate(addMovieRequest.getReleaseDate())
                .rating(addMovieRequest.getRating())
                .build();

        return movie;
    }
}
