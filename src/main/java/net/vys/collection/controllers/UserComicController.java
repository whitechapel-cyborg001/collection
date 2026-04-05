package net.vys.collection.controllers;
 
import net.vys.collection.dto.AddToCollectionRequest;
import net.vys.collection.dto.ErrorResponse;
import net.vys.collection.dto.UserComicResponseDTO;
import net.vys.collection.services.UserComicService;
 
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;

@RestController
@RequestMapping("/api/collection")
@Tag(name = "Collection", description = "Gestión de la colección personal del usuario autenticado")
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
   
    @Operation(summary = "Obtiene la colección del usuario", description = "Retorna todos los cómics de la colección del usuario autenticado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Colección obtenida correctamente"),
        @ApiResponse(responseCode = "401", description = "No autenticado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<UserComicResponseDTO>> getCollection(
            @AuthenticationPrincipal UserDetails userDetails) {
 
        return ResponseEntity.ok(userComicService.getCollection(userDetails.getUsername()));
    }
 
    @Operation(summary = "Añade un cómic a la colección", description = "Añade el cómic indicado a la colección del usuario autenticado con el estado especificado (OWNED o WANTED)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cómic añadido correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "401", description = "No autenticado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Cómic no encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "409", description = "El cómic ya está en la colección",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{comicId}")
    public ResponseEntity<UserComicResponseDTO> addToCollection(
            @PathVariable Long comicId,
            @Valid @RequestBody AddToCollectionRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
 
        return ResponseEntity.ok(userComicService.addToCollection(comicId, userDetails.getUsername(), request.getStatus()));
    }
 
    @Operation(summary = "Elimina un cómic de la colección", description = "Elimina el cómic indicado de la colección del usuario autenticado")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Cómic eliminado correctamente"),
        @ApiResponse(responseCode = "401", description = "No autenticado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Cómic no encontrado en la colección",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{comicId}")
    public ResponseEntity<Void> removeFromCollection(
            @PathVariable Long comicId,
            @AuthenticationPrincipal UserDetails userDetails) {
 
        userComicService.removeFromCollection(comicId, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}