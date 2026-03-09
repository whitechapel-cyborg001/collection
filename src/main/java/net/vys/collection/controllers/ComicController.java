package net.vys.collection.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import net.vys.collection.entities.Comic;

import net.vys.collection.services.ComicServiceManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import net.vys.collection.dto.ComicDTO;
import net.vys.collection.dto.ComicResponseDTO;


@RestController
@RequestMapping("/api/comics")
public class ComicController {
    @Autowired
    private ComicServiceManager serviceManager;

    @GetMapping
    @Transactional(readOnly = true)
    public List<ComicResponseDTO> findAllComics() {
        return this.serviceManager.findAll();
    }

    @PostMapping
    @Transactional
    public ComicResponseDTO saveComic(@Valid @RequestBody ComicDTO comic) {
        return this.serviceManager.save(comic);
    }
    
    @GetMapping("{id}")
    @Transactional(readOnly = true)
    public ComicResponseDTO findById(@PathVariable Long id) {
        return this.serviceManager.findById(id);
    }
}
