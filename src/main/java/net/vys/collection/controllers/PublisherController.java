package net.vys.collection.controllers;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
//import net.vys.collection.entities.Publisher;
import net.vys.collection.dto.PublisherDTO;
import net.vys.collection.dto.PublisherResponseDTO;
import net.vys.collection.services.PublisherServiceManager;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {
    private final PublisherServiceManager serviceManager;

    public PublisherController(PublisherServiceManager serviceManager) {
        this.serviceManager = serviceManager;
}

    @Operation(summary = "Gets all Publishers", description = "Returns a list of Publishers")
    @GetMapping
    @Transactional(readOnly = true)
    public List<PublisherResponseDTO> findAll() {
        return serviceManager.findAll();
    }

    @Operation(summary = "Creates a new Publisher", description = "Returns the created Publisher")
    @PostMapping
    @Transactional
    public PublisherResponseDTO save(@Valid @RequestBody PublisherDTO publisherDTO) {
        return this.serviceManager.save(publisherDTO);
    }

    @Operation(summary = "Gets Publisher by ID", description = "Returns a single Publisher")
    @GetMapping("{id}")
    @Transactional(readOnly = true)
    public PublisherResponseDTO findById(@PathVariable Long id) {
        return this.serviceManager.findById(id);
    }
}
