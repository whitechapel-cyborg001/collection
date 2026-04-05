package net.vys.collection.controllers;

import net.vys.collection.dto.UserComicResponseDTO;
import net.vys.collection.entities.UserComic;
import net.vys.collection.services.UserComicService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collection")
public class UserComicController {

    private final UserComicService userComicService;

    public UserComicController(UserComicService userComicService) {
        this.userComicService = userComicService;
    }

    /* *****************************************************************
        @AuthenticationPrincipal

        En lugar de leer el token JWT manualmente, Spring Security ya procesó el token por ti
        — gracias al JwtFilter que tienes montado. @AuthenticationPrincipal UserDetails te
        da directamente el usuario autenticado. De ahí sacas el username y se lo pasas al service.
    *********************************************************************/
    @PostMapping("/{comicId}")
    public ResponseEntity<UserComicResponseDTO> addToCollection(
            @PathVariable Long comicId,
            @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(userComicService.addToCollection(comicId, userDetails.getUsername()));
    }

    @DeleteMapping("/{comicId}")
    public ResponseEntity<Void> removeFromCollection(
            @PathVariable Long comicId,
            @AuthenticationPrincipal UserDetails userDetails) {

        userComicService.removeFromCollection(comicId, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserComicResponseDTO>> getCollection(
            @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(userComicService.getCollection(userDetails.getUsername()));
    }
}