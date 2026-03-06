package net.vys.collection.services;

import net.vys.collection.entities.Serie;

import java.util.List;

public interface SerieService {
    List<Serie> findAll();
    Serie findById(Long id);
    Serie save(Serie serie);
}
