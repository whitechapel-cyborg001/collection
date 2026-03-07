package net.vys.collection.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.vys.collection.dto.SerieDTO;
import net.vys.collection.dto.SerieResponseDTO;
import net.vys.collection.services.SerieServiceManager;

import java.util.List;

@RestController
@RequestMapping("/api/series")
public class SerieController {
    @Autowired
    private SerieServiceManager serviceManager;
    
    @GetMapping
    public List<SerieResponseDTO> findAll() {
        return this.serviceManager.findAll();
    }

    @PostMapping
    public SerieResponseDTO save(@RequestBody SerieDTO serie) {
        return this.serviceManager.save(serie);
    }

}
