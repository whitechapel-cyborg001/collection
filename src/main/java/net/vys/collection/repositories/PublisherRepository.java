package net.vys.collection.repositories;
import org.springframework.data.repository.CrudRepository;
import net.vys.collection.entities.Publisher;



public interface PublisherRepository extends CrudRepository<Publisher, Long> {
    

}
