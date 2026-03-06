package net.vys.collection.services;

import net.vys.collection.entities.Serie;
import net.vys.collection.repositories.SerieRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SerieServiceManager implements SerieService {

    @Autowired
    private SerieRepository repository;

    @Override
    public List<Serie> findAll() {
        return (List<Serie>) this.repository.findAll();
    }

    @Override
    public Serie findById(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public Serie save(Serie serie) {
        return this.repository.save(serie);
    }
    
}
