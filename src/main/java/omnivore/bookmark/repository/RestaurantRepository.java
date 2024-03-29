package omnivore.bookmark.repository;

import omnivore.bookmark.entity.Restaurant;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@EnableScan
@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, String> {
}
