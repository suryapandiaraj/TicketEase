package BookMyShow.Repository;

import BookMyShow.Models.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TheaterRepository extends JpaRepository<Theater,Integer> {

    @Query(value = "SELECT name FROM theater WHERE city = :location",nativeQuery = true)
    public List<String> showTheaterByLocation(
            @Param("location") String location
    );

}
