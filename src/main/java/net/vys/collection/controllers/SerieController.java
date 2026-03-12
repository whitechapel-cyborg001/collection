package net.vys.collection.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import net.vys.collection.dto.ErrorResponse;
import net.vys.collection.dto.SerieDTO;
import net.vys.collection.dto.SerieResponseDTO;
import net.vys.collection.services.SerieServiceManager;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/series")
@Tag(name = "Series", description = "Operaciones sobre series")
public class SerieController {

    private final SerieServiceManager serviceManager;

    public SerieController(SerieServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    // -----------------------------------------------------------------------
    // GET /api/series
    // -----------------------------------------------------------------------
    @Operation(summary = "Obtiene todas las series", description = "Retorna la lista completa de series")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<Page<SerieResponseDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(serviceManager.findAll(pageable));
    }

    // -----------------------------------------------------------------------
    // GET /api/series/{id}
    // -----------------------------------------------------------------------
    @Operation(summary = "Obtiene una serie por ID", description = "Retorna una única serie")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Serie encontrada"),
        @ApiResponse(responseCode = "400", description = "ID con formato inválido",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Serie no encontrada",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<SerieResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceManager.findById(id));
    }

    // -----------------------------------------------------------------------
    // POST /api/series
    // -----------------------------------------------------------------------
    @Operation(summary = "Crea una nueva serie", description = "Retorna la serie creada")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Serie creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    @Transactional
    public ResponseEntity<SerieResponseDTO> save(@Valid @RequestBody SerieDTO serie) {
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceManager.save(serie));
    }

    // -----------------------------------------------------------------------
    // PUT /api/series/{id}
    // -----------------------------------------------------------------------
    @Operation(summary = "Actualiza una serie", description = "Retorna la serie actualizada")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Serie actualizada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Serie no encontrada",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<SerieResponseDTO> update(@PathVariable Long id, @Valid @RequestBody SerieDTO serie) {
        return ResponseEntity.ok(serviceManager.update(id, serie));
    }

    // -----------------------------------------------------------------------
    // DELETE /api/series/{id}
    //  devuelve 204 No Content — es el estándar REST: operación exitosa pero sin cuerpo de respuesta. Por eso ResponseEntity<Void>
    // -----------------------------------------------------------------------
    @Operation(summary = "Elimina una serie", description = "No retorna contenido")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Serie eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Serie no encontrada",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        serviceManager.delete(id);
        return ResponseEntity.noContent().build();
    }
}