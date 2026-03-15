package net.vys.collection.repositories;

import net.vys.collection.entities.UserComic;
import net.vys.collection.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserComicRepository extends JpaRepository<UserComic, Long> {

    // devuelve toda la colección de un usuario. Lo usará el endpoint GET /api/collection
    List<UserComic> findByUser(User user);

    // busca una entrada concreta de usuario + cómic. Lo usaremos para eliminar
    Optional<UserComic> findByUserAndComicId(User user, Long comicId);

    // comprueba si el cómic ya está en la colección antes de añadirlo — evita duplicados a nivel de servicio,
    // antes de que lo rechace la BD
    boolean existsByUserAndComicId(User user, Long comicId);
}