package net.vys.collection.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import net.vys.collection.dto.SerieDTO;
import net.vys.collection.dto.SerieResponseDTO;
import net.vys.collection.services.SerieServiceManager;

import java.util.List;
//import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/series")
public class SerieController {
    @Autowired
    private SerieServiceManager serviceManager;
    
    @GetMapping
    @Transactional(readOnly = true)
    public List<SerieResponseDTO> findAll() {
        return this.serviceManager.findAll();
    }

    @PostMapping
    @Transactional
    public SerieResponseDTO save(@RequestBody SerieDTO serie) {
        return this.serviceManager.save(serie);
    }

    @GetMapping("{id}")
    @Transactional(readOnly = true)
    public SerieResponseDTO findById(@PathVariable Long id) {
        return this.serviceManager.findById(id);
    }
}
