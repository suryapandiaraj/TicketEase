package BookMyShow.Service;

import BookMyShow.Models.Movie;
import BookMyShow.Repository.MovieRepository;
import BookMyShow.RequestDtos.AddMovieRequest;
import BookMyShow.Transformers.MovieTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public String addMovie(AddMovieRequest addMovieRequest){

        Movie movie = MovieTransformer.convertAddMovieReqToMovie(addMovieRequest);

        movieRepository.save(movie);

        return "Movie has been added to the DB successfully";

    }


}
