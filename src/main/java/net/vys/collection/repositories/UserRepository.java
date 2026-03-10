package net.vys.collection.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import net.vys.collection.entities.User;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring genera la query automáticamente por el nombre del método
    // Equivale a: SELECT * FROM users WHERE username = ?
    Optional<User> findByUsername(String username);
}