package net.vys.collection.services;

import org.springframework.stereotype.Service;

import net.vys.collection.entities.Author;
import net.vys.collection.entities.Comic;
import net.vys.collection.entities.Publisher;
import net.vys.collection.entities.Serie;

import net.vys.collection.dto.AuthorResponseDTO;
import net.vys.collection.dto.ComicDTO;
import net.vys.collection.dto.ComicResponseDTO;
import net.vys.collection.dto.PublisherResponseDTO;
import net.vys.collection.dto.SerieDTO;
import net.vys.collection.dto.SerieResponseDTO;
import net.vys.collection.repositories.AuthorRepository;
import net.vys.collection.repositories.ComicRepository;
import net.vys.collection.repositories.PublisherRepository;
import net.vys.collection.repositories.SerieRepository;
import net.vys.collection.mapper.ComicMapper;

import java.util.List;
import java.util.stream.StreamSupport;


@Service
public class ComicServiceManager implements ComicService {

    private final ComicRepository comicRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final SerieRepository serieRepository;
    private final ComicMapper mapper;


    public ComicServiceManager(
            ComicRepository comicRepository,
            AuthorRepository authorRepository,
            PublisherRepository publisherRepository,
            SerieRepository serieRepository,
            ComicMapper mapper) {

        this.comicRepository = comicRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.serieRepository = serieRepository;
        this.mapper = mapper;
    }

    @Override
    public List<ComicResponseDTO> findAll() {
        return comicRepository.findAll()
            .stream()
            .map(mapper::toComicResponseDTO)
            .toList();
    }

    @Override
    public ComicResponseDTO findById(Long id) {
        Comic comic = comicRepository.findById(id).orElse(null);

        if (comic == null) {
            return null;
        }

        return mapper.toComicResponseDTO(comic);
    }

    @Override
    public ComicResponseDTO save(ComicDTO comicDTO) {
        /*System.out.println("publisherId: " + comicDTO.getPublisher()); // VERIFICACION DE LLEGADA DE DTO
        System.out.println("serieId: "     + comicDTO.getSerie());
        System.out.println("authorIds: "   + comicDTO.getAuthors()); */
        Comic comic = new Comic();
        comic = mapper.toComic(comicDTO);
/*
        // Resolver las relaciones manualmente desde los repositorios           // INNECESARIO CON MAPPER
        Publisher publisher = publisherRepository.findById(comicDTO.getPublisher())
                .orElseThrow(() -> new RuntimeException("Publisher not found"));
        comic.setPublisher(publisher);

        Serie serie = serieRepository.findById(comicDTO.getSerie())
                .orElseThrow(() -> new RuntimeException("Serie not found"));
        comic.setSerie(serie);

        List<Author> authors = authorRepository.findAllById(comicDTO.getAuthors());
        comic.setAuthors(authors);*/

        Comic saved = comicRepository.save(comic);
        return mapper.toComicResponseDTO(saved);
    }
}
