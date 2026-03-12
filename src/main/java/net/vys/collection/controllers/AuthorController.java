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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import net.vys.collection.dto.AuthorDTO;
import net.vys.collection.dto.AuthorResponseDTO;
import net.vys.collection.dto.ErrorResponse;
import net.vys.collection.services.AuthorServiceManager;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@RestController
@RequestMapping("/api/authors")
@Tag(name = "Authors", description = "Operaciones sobre autores")
public class AuthorController {

    private final AuthorServiceManager serviceManager;

    public AuthorController(AuthorServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    // -----------------------------------------------------------------------
    // GET /api/authors
    // -----------------------------------------------------------------------
    @Operation(summary = "Obtiene todos los autores", description = "Retorna la lista completa de autores")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<Page<AuthorResponseDTO>> findAll(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(serviceManager.findAll(pageable));
    }

    // -----------------------------------------------------------------------
    // POST /api/authors
    // -----------------------------------------------------------------------
    @Operation(summary = "Crea un nuevo autor", description = "Retorna el autor creado")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Autor creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    @Transactional
    public ResponseEntity<AuthorResponseDTO> save(@Valid @RequestBody AuthorDTO authorDTO) {
        AuthorResponseDTO created = serviceManager.save(authorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // -----------------------------------------------------------------------
    // GET /api/authors/{id}
    // -----------------------------------------------------------------------
    @Operation(summary = "Obtiene un autor por ID", description = "Retorna un único autor")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Autor encontrado"),
        @ApiResponse(responseCode = "400", description = "ID con formato inválido",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Autor no encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<AuthorResponseDTO> findById(@PathVariable Long id) {
        AuthorResponseDTO author = serviceManager.findById(id);
        return ResponseEntity.ok(author);
    }

    // -----------------------------------------------------------------------
    // PUT /api/authors/{id}
    // -----------------------------------------------------------------------
    @Operation(summary = "Actualiza un Author", description = "Retorna el Author actualizado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Autor actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Autor no encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<AuthorResponseDTO> update(@PathVariable Long id, @Valid @RequestBody AuthorDTO author) {
        return ResponseEntity.ok(serviceManager.update(id, author));
    }

    // -----------------------------------------------------------------------
    // DELETE /api/authors/{id}
    //  devuelve 204 No Content — es el estándar REST: operación exitosa pero sin cuerpo de respuesta. Por eso ResponseEntity<Void>
    // -----------------------------------------------------------------------
    @Operation(summary = "Elimina un Author", description = "No retorna contenido")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Autor eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Autor no encontrado",
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