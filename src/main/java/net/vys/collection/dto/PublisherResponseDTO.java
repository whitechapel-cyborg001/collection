package net.vys.collection.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Publisher response data transfer object")
public class PublisherResponseDTO {
    @Schema(description = "Publisher ID", example = "1")
    private Long id;
    @Schema(description = "Publisher name", example = "DC Comics")
    private String name;


    public PublisherResponseDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
