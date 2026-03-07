package net.vys.collection.controllers;

import org.springframework.web.bind.annotation.RestController;

//import net.vys.collection.entities.Publisher;
import net.vys.collection.dto.PublisherDTO;
import net.vys.collection.dto.PublisherResponseDTO;
import net.vys.collection.services.PublisherServiceManager;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {
    private final PublisherServiceManager serviceManager;

    public PublisherController(PublisherServiceManager serviceManager) {
        this.serviceManager = serviceManager;
}

    @GetMapping
    public List<PublisherResponseDTO> findAll() {
        return serviceManager.findAll();
    }

    @PostMapping
    public PublisherResponseDTO save(@Valid @RequestBody PublisherDTO publisherDTO) {
        return this.serviceManager.save(publisherDTO);
    }
    
}
