package net.vys.collection.services;

import net.vys.collection.dto.ComicDTO;
import net.vys.collection.dto.ComicResponseDTO;
import net.vys.collection.entities.Author;
import net.vys.collection.entities.Comic;
import net.vys.collection.entities.Publisher;
import net.vys.collection.entities.Serie;
import net.vys.collection.exceptions.AuthorNotFoundException;
import net.vys.collection.exceptions.ComicNotFoundException;
import net.vys.collection.exceptions.PublisherNotFoundException;
import net.vys.collection.exceptions.SerieNotFoundException;
import net.vys.collection.mapper.ComicMapper;
import net.vys.collection.repositories.AuthorRepository;
import net.vys.collection.repositories.ComicRepository;
import net.vys.collection.repositories.PublisherRepository;
import net.vys.collection.repositories.SerieRepository;

import java.util.List;
import org.springframework.stereotype.Service;


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
        // Lanza ComicNotFoundException (404) si no existe
        return comicRepository.findById(id)
                .map(mapper::toComicResponseDTO)
                .orElseThrow(() -> new ComicNotFoundException(id));
    }

    @Override
    public ComicResponseDTO save(ComicDTO comicDTO) {
        // Validar que las relaciones existen ANTES de guardar
        // Si algún ID no existe, lanza 404 con un mensaje claro en lugar de fallar silenciosamente
        Publisher publisher = publisherRepository.findById(comicDTO.getPublisher())
                .orElseThrow(() -> new PublisherNotFoundException(comicDTO.getPublisher()));

        Serie serie = serieRepository.findById(comicDTO.getSerie())
                .orElseThrow(() -> new SerieNotFoundException(comicDTO.getSerie()));

        List<Author> authors = authorRepository.findAllById(comicDTO.getAuthors());
        if (authors.size() != comicDTO.getAuthors().size()) {
            // Al menos uno de los authorIds enviados no existe
            List<Long> foundIds = authors.stream().map(Author::getId).toList();
            List<Long> missingIds = comicDTO.getAuthors().stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();
            throw new AuthorNotFoundException(missingIds.get(0));
        }

        // Mapper construye el Comic
        Comic comic = mapper.toComic(comicDTO);
        comic.setPublisher(publisher);
        comic.setSerie(serie);
        comic.setAuthors(authors);

        return mapper.toComicResponseDTO(comicRepository.save(comic));
    }

    @Override
    public ComicResponseDTO update(Long id, ComicDTO comicDTO) {
        // Verificar que el cómic existe
        Comic existing = comicRepository.findById(id)
                .orElseThrow(() -> new ComicNotFoundException(id));
        // Validar relaciones (similar a save)
        Publisher publisher = publisherRepository.findById(comicDTO.getPublisher())
                .orElseThrow(() -> new PublisherNotFoundException(comicDTO.getPublisher()));
        Serie serie = serieRepository.findById(comicDTO.getSerie())
                .orElseThrow(() -> new SerieNotFoundException(comicDTO.getSerie()));
        List<Author> authors = authorRepository.findAllById(comicDTO.getAuthors());
        if (authors.size() != comicDTO.getAuthors().size()) {
            List<Long> foundIds = authors.stream().map(Author::getId).toList();
            List<Long> missingIds = comicDTO.getAuthors().stream()
                    .filter(aid -> !foundIds.contains(aid))
                    .toList();
            throw new AuthorNotFoundException(missingIds.get(0));
        }
        // Actualizar campos
        existing.setTitle(comicDTO.getTitle());
        existing.setPublisher(publisher);
        existing.setSerie(serie);
        existing.setAuthors(authors);
        existing.setIssue(comicDTO.getIssue());
        existing.setPublicationYear(comicDTO.getPublicationYear());
        existing.setNotes(comicDTO.getNotes());
        return mapper.toComicResponseDTO(comicRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!comicRepository.existsById(id)) {
            throw new ComicNotFoundException(id);
        }
        comicRepository.deleteById(id);
    }
}
