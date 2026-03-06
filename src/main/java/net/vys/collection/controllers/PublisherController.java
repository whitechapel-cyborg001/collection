package net.vys.collection.controllers;

import org.springframework.web.bind.annotation.RestController;

import net.vys.collection.entities.Publisher;
import net.vys.collection.services.PublisherServiceManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {
        @Autowired
        private PublisherServiceManager serviceManager;

        @GetMapping
        public List<Publisher> findAll() {
            return this.serviceManager.findAll();
        }

        @PostMapping
        public Publisher save(@RequestBody Publisher publisher) {
            return this.serviceManager.save(publisher);
        }
    
}
