package net.vys.collection.services;

import org.springframework.beans.factory.annotation.Autowired;
import net.vys.collection.repositories.ComicRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.StreamSupport;

import net.vys.collection.entities.Author;
import net.vys.collection.entities.Comic;
import net.vys.collection.dto.ComicDTO;
import net.vys.collection.repositories.PublisherRepository;
import net.vys.collection.repositories.SerieRepository;
import net.vys.collection.repositories.AuthorRepository;


@Service
public class ComicServiceManager implements ComicService {

    @Autowired
    private ComicRepository comicRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private SerieRepository serieRepository;

    @Override
    public List<Comic> findAll() {
        return (List<Comic>) this.comicRepository.findAll();
    }

    @Override
    public Comic findById(Long id) {
        return this.comicRepository.findById(id).orElse(null);
    }

    /* @Override
    public Comic save(Comic comic) {
        return this.comicRepository.save(comic);
    }   */

    @Override
    public Comic save(ComicDTO dto) {

        Comic comic = new Comic();

        comic.setTitle(dto.getTitle());
        comic.setNotes(dto.getNotes());
        comic.setIssue(dto.getIssueNumber());
        comic.setPublicationYear(dto.getPublicationYear());

        comic.setPublisher(
            publisherRepository.findById(dto.getPublisherID()).orElse(null)
        );

        comic.setSerie(
            serieRepository.findById(dto.getSerieId()).orElse(null)
        );

        List<Author> authors =
            StreamSupport
            .stream(authorRepository.findAllById(dto.getAuthorsIDs()).spliterator(), false)
            .toList();

        comic.setAuthors(authors);

        return comicRepository.save(comic);
    }

    @Override
    public List<Comic> findByAuthor(Long authorId) {

    // TODO : findByAuthor
        return null;
    }


}
