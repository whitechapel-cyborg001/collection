package net.vys.collection.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import net.vys.collection.dto.ComicDTO;
import net.vys.collection.dto.ComicResponseDTO;
import net.vys.collection.entities.Comic;

@Mapper(componentModel = "spring", uses = { AuthorMapper.class, PublisherMapper.class, SerieMapper.class })
public interface ComicMapper {
    @Mapping(target = "authors", source = "authors")
    @Mapping(target = "publisher", source = "publisher")
    @Mapping(target = "serie", source = "serie")
    ComicResponseDTO toComicResponseDTO(Comic comic);
    @Mapping(target = "authors", source = "authors")
    @Mapping(target = "publisher", source = "publisher")
    @Mapping(target = "serie", source = "serie")
    Comic toComic(ComicDTO comicDTO);
    @Mapping(target = "authors", source = "authors")
    @Mapping(target = "publisher", source = "publisher")
    @Mapping(target = "serie", source = "serie")
    ComicDTO toComicDTO(Comic comic);
    /*
    @Mapping(target = "authors", source = "authors")
    @Mapping(target = "publisher", source = "publisher")
    @Mapping(target = "serie", source = "serie")
    ComicResponseDTO toComicResponseDTO(Comic comic);
    @Mapping(target = "authors", source = "authors", ignore = true)
    @Mapping(target = "publisher", source = "publisher", ignore = true)
    @Mapping(target = "serie", source = "serie", ignore = true)
    Comic toComic(ComicDTO comicDTO);
    @Mapping(target = "authors", source = "authors", ignore = true)
    @Mapping(target = "publisher", source = "publisher", ignore = true)
    @Mapping(target = "serie", source = "serie", ignore = true)
    ComicDTO toComicDTO(Comic comic);*/
}
