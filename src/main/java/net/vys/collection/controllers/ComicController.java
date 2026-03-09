package net.vys.collection.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import net.vys.collection.dto.ComicDTO;
import net.vys.collection.dto.ComicResponseDTO;
import net.vys.collection.dto.ErrorResponse;
import net.vys.collection.services.ComicServiceManager;

@RestController
@RequestMapping("/api/comics")
@Tag(name = "Comics", description = "Operaciones sobre cómics")
public class ComicController {

    private final ComicServiceManager serviceManager;

    public ComicController(ComicServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    // -----------------------------------------------------------------------
    // GET /api/comics
    // -----------------------------------------------------------------------
    @Operation(summary = "Obtiene todos los cómics",
               description = "Retorna la lista completa de cómics. Author, Serie y Publisher se retornan como IDs")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<ComicResponseDTO>> findAllComics() {
        List<ComicResponseDTO> comics = serviceManager.findAll();
        return ResponseEntity.ok(comics);
    }

    // -----------------------------------------------------------------------
    // POST /api/comics
    // -----------------------------------------------------------------------
    @Operation(summary = "Crea un nuevo cómic",
               description = "Retorna el cómic creado. Author, Serie y Publisher se retornan como IDs")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cómic creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    @Transactional
    public ResponseEntity<ComicResponseDTO> saveComic(@Valid @RequestBody ComicDTO comic) {
        ComicResponseDTO created = serviceManager.save(comic);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // -----------------------------------------------------------------------
    // GET /api/comics/{id}
    // -----------------------------------------------------------------------
    @Operation(summary = "Obtiene un cómic por ID",
               description = "Retorna un único cómic. Author, Serie y Publisher se retornan como IDs")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cómic encontrado"),
        @ApiResponse(responseCode = "400", description = "ID con formato inválido",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Cómic no encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<ComicResponseDTO> findById(@PathVariable Long id) {
        ComicResponseDTO comic = serviceManager.findById(id);
        return ResponseEntity.ok(comic);
    }
}