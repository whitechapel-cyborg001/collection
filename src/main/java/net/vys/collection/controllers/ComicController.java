package net.vys.collection.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import net.vys.collection.entities.Comic;

import net.vys.collection.services.ComicServiceManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import net.vys.collection.dto.ComicDTO;
import net.vys.collection.dto.ComicResponseDTO;


@RestController
@RequestMapping("/api/comics")
public class ComicController {
    @Autowired
    private ComicServiceManager serviceManager;

    @GetMapping
    public List<ComicResponseDTO> findAllComics() {
        return this.serviceManager.findAll();
    }

    @PostMapping
    public ComicResponseDTO saveComic(@Valid @RequestBody ComicDTO comic) {
        return this.serviceManager.save(comic);
    }
    
}
