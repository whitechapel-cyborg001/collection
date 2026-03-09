package net.vys.collection.controllers;

import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
//import net.vys.collection.entities.Comic;

import net.vys.collection.services.ComicServiceManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import io.swagger.v3.oas.annotations.Operation;

import net.vys.collection.dto.ComicDTO;
import net.vys.collection.dto.ComicResponseDTO;


@RestController
@RequestMapping("/api/comics")
public class ComicController {
    private final ComicServiceManager serviceManager;
    public ComicController(ComicServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    @Operation(summary = "Gets all Comics", description = "Returns a list of Comics. Author, Serie and Publisher are returned as IDs")
    @GetMapping
    @Transactional(readOnly = true)
    public List<ComicResponseDTO> findAllComics() {
        return this.serviceManager.findAll();
    }

    @Operation(summary = "Creates a new Comic", description = "Returns the created Comic. Author, Serie and Publisher are returned as IDs")
    @PostMapping
    @Transactional
    public ComicResponseDTO saveComic(@Valid @RequestBody ComicDTO comic) {
        return this.serviceManager.save(comic);
    }
    
    @Operation(summary = "Gets Comic by ID", description = "Returns a single Comic. Author, Serie and Publisher are returned as IDs")
    @GetMapping("{id}")
    @Transactional(readOnly = true)
    public ComicResponseDTO findById(@PathVariable Long id) {
        return this.serviceManager.findById(id);
    }
}
