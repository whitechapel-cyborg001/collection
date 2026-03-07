package net.vys.collection.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import net.vys.collection.entities.Publisher;



public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    

}
