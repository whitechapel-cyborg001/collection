package net.vys.collection.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.vys.collection.entities.Comic;

import net.vys.collection.services.ComicServiceManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/comics")
public class ComicController {
    @Autowired
    private ComicServiceManager serviceManager;

    @GetMapping
    public List<Comic> findAllComics() {
        return this.serviceManager.findAll();
    }

    @PostMapping
    public Comic saveComic(@RequestBody Comic comic) {
        return this.serviceManager.save(comic);
    }
    
}
