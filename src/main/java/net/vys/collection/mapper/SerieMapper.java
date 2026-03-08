package net.vys.collection.mapper;

import org.mapstruct.Mapper;

import net.vys.collection.dto.SerieResponseDTO;
import net.vys.collection.entities.Serie;
import net.vys.collection.dto.SerieDTO;

@Mapper(componentModel = "spring")
public interface SerieMapper {
    SerieResponseDTO toSerieResponseDTO(Serie serie);
    SerieDTO toSerieDTO(Serie serie);
    Serie toSerie(SerieDTO serieDTO);

      // Conversión Serie -> ID
    default Long map(Serie serie) {
        return serie != null ? serie.getId() : null;
    }

    // Conversión ID -> Serie
    default Serie map(Long id) {
        if (id == null) {
            return null;
        }
        Serie serie = new Serie();
        serie.setId(id);
        return serie;
    }
}
