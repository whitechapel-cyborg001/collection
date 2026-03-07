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

import java.util.List;
import java.util.stream.StreamSupport;


@Service
public class ComicServiceManager implements ComicService {

    private final ComicRepository comicRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final SerieRepository serieRepository;

    public ComicServiceManager(
            ComicRepository comicRepository,
            AuthorRepository authorRepository,
            PublisherRepository publisherRepository,
            SerieRepository serieRepository) {

        this.comicRepository = comicRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.serieRepository = serieRepository;
    }

    @Override
    public List<ComicResponseDTO> findAll() {

        return comicRepository.findAll()
            .stream()
            .map(c -> new ComicResponseDTO(
                c.getId(),
                c.getTitle(),
                c.getAuthors()
                    .stream()
                    .map(a -> new AuthorResponseDTO(a.getId(), a.getName()))
                    .toList(),
                c.getPublisher() != null
                    ? new PublisherResponseDTO(
                        c.getPublisher().getId(),
                        c.getPublisher().getName()
                    )
                    : null,
                c.getPublicationYear(),
                c.getNotes(),
                c.getIssue(),
                c.getSerie() != null
                    ? new SerieResponseDTO(
                        c.getSerie().getId(),
                        c.getSerie().getName(),
                        c.getSerie().getIssues()
                    )
                    : null
            ))
            .toList();
    }

    @Override
    public ComicResponseDTO findById(Long id) {

        return comicRepository.findById(id)
            .map(c -> new ComicResponseDTO(
                c.getId(),
                c.getTitle(),
                c.getAuthors()
                    .stream()
                    .map(a -> new AuthorResponseDTO(a.getId(), a.getName()))
                    .toList(),
                c.getPublisher() != null
                    ? new PublisherResponseDTO(
                        c.getPublisher().getId(),
                        c.getPublisher().getName()
                    )
                    : null,
                c.getPublicationYear(),
                c.getNotes(),
                c.getIssue(),
                c.getSerie() != null
                    ? new SerieResponseDTO(
                        c.getSerie().getId(),
                        c.getSerie().getName(),
                        c.getSerie().getIssues()
                    )
                    : null
            ))
            .orElse(null);
    }

    @Override
    public ComicResponseDTO save(ComicDTO comicDTO) {

        Comic comic = new Comic();

        comic.setTitle(comicDTO.getTitle());
        comic.setNotes(comicDTO.getNotes());
        comic.setIssue(comicDTO.getIssueNumber());
        comic.setPublicationYear(comicDTO.getPublicationYear());

        Publisher publisher = publisherRepository
            .findById(comicDTO.getPublisherID())
            .orElse(null);

        Serie serie = serieRepository
            .findById(comicDTO.getSerieId())
            .orElse(null);

        comic.setPublisher(publisher);
        comic.setSerie(serie);

        List<Author> authors = StreamSupport
            .stream(authorRepository.findAllById(comicDTO.getAuthorsIDs()).spliterator(), false)
            .toList();

        comic.setAuthors(authors);

        Comic saved = comicRepository.save(comic);

        return new ComicResponseDTO(
            saved.getId(),
            saved.getTitle(),
            saved.getAuthors()
                .stream()
                .map(a -> new AuthorResponseDTO(a.getId(), a.getName()))
                .toList(),
            saved.getPublisher() != null
                ? new PublisherResponseDTO(
                    saved.getPublisher().getId(),
                    saved.getPublisher().getName()
                )
                : null,
            saved.getPublicationYear(),
            saved.getNotes(),
            saved.getIssue(),
            saved.getSerie() != null
                ? new SerieResponseDTO(
                    saved.getSerie().getId(),
                    saved.getSerie().getName(),
                    saved.getSerie().getIssues()
                )
                : null
        );
    }

}
