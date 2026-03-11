package net.vys.collection.controllers;

import java.util.List;

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
import net.vys.collection.dto.PublisherDTO;
import net.vys.collection.dto.PublisherResponseDTO;
import net.vys.collection.services.PublisherServiceManager;

@RestController
@RequestMapping("/api/publishers")
@Tag(name = "Publishers", description = "Operaciones sobre editoriales")
public class PublisherController {

    private final PublisherServiceManager serviceManager;

    public PublisherController(PublisherServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    // -----------------------------------------------------------------------
    // GET /api/publishers
    // -----------------------------------------------------------------------
    @Operation(summary = "Obtiene todos los publishers", description = "Retorna la lista completa de editoriales")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<PublisherResponseDTO>> findAll() {
        return ResponseEntity.ok(serviceManager.findAll());
    }

    // -----------------------------------------------------------------------
    // POST /api/publishers
    // -----------------------------------------------------------------------
    @Operation(summary = "Crea un nuevo publisher", description = "Retorna la editorial creada")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Editorial creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    @Transactional
    public ResponseEntity<PublisherResponseDTO> save(@Valid @RequestBody PublisherDTO publisherDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceManager.save(publisherDTO));
    }

    // -----------------------------------------------------------------------
    // GET /api/publishers/{id}
    // -----------------------------------------------------------------------
    @Operation(summary = "Obtiene un publisher por ID", description = "Retorna una única editorial")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Editorial encontrada"),
        @ApiResponse(responseCode = "400", description = "ID con formato inválido",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Editorial no encontrada",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<PublisherResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceManager.findById(id));
    }

    // -----------------------------------------------------------------------
    // PUT /api/publishers/{id}
    // -----------------------------------------------------------------------
    @Operation(summary = "Actualiza una editorial", description = "Retorna la editorial actualizada")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Editorial actualizada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Editorial no encontrada",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<PublisherResponseDTO> update(@PathVariable Long id, @Valid @RequestBody PublisherDTO publisher) {
        return ResponseEntity.ok(serviceManager.update(id, publisher));
    }

    // -----------------------------------------------------------------------
    // DELETE /api/publishers/{id}
    //  devuelve 204 No Content — es el estándar REST: operación exitosa pero sin cuerpo de respuesta. Por eso ResponseEntity<Void>
    // -----------------------------------------------------------------------
    @Operation(summary = "Elimina una editorial", description = "No retorna contenido")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Editorial eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Editorial no encontrada",
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