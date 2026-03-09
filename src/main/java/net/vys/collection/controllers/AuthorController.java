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
import org.springframework.web.bind.annotation.PathVariable;

import net.vys.collection.dto.AuthorDTO;
import net .vys.collection.dto.AuthorResponseDTO;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    @Autowired
    private AuthorServiceManager serviceManager;

    @GetMapping
    @Transactional(readOnly = true)
    public List<AuthorResponseDTO> findAll() {
        return this.serviceManager.findAll();
    }

    @PostMapping
    @Transactional
    public AuthorResponseDTO save(@Valid @RequestBody AuthorDTO authorDTO) {
        return this.serviceManager.save(authorDTO);
    }
    
    @GetMapping("{id}")
    @Transactional(readOnly = true)
    public AuthorResponseDTO findById(@PathVariable Long id) {
        return this.serviceManager.findById(id);
    }
}

