package net.vys.collection.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import net.vys.collection.dto.SerieDTO;
import net.vys.collection.dto.SerieResponseDTO;
import net.vys.collection.services.SerieServiceManager;

import java.util.List;
//import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/series")
public class SerieController {
    private final SerieServiceManager serviceManager;
    public SerieController(SerieServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    @Operation(summary = "Gets all Series", description = "Returns a list of Series")
    @GetMapping
    @Transactional(readOnly = true)
    public List<SerieResponseDTO> findAll() {
        return this.serviceManager.findAll();
    }

    @Operation(summary = "Creates a new Serie", description = "Returns the created Serie")
    @PostMapping
    @Transactional
    public SerieResponseDTO save(@Valid @RequestBody SerieDTO serie) {
        return this.serviceManager.save(serie);
    }

    @Operation(summary = "Gets Serie by ID", description = "Returns a single Serie")
    @GetMapping("{id}")
    @Transactional(readOnly = true)
    public SerieResponseDTO findById(@PathVariable Long id) {
        return this.serviceManager.findById(id);
    }
}
