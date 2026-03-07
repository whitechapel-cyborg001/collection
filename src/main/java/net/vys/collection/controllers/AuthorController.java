package net.vys.collection.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import net.vys.collection.entities.Author;

import net.vys.collection.services.AuthorServiceManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import net.vys.collection.dto.AuthorDTO;
import net .vys.collection.dto.AuthorResponseDTO;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    @Autowired
    private AuthorServiceManager serviceManager;

    @GetMapping
    public List<AuthorResponseDTO> findAll() {
        return this.serviceManager.findAll();
    }

    @PostMapping
    public AuthorResponseDTO save(@Valid @RequestBody AuthorDTO authorDTO) {
        return this.serviceManager.save(authorDTO);
    }
    
}

