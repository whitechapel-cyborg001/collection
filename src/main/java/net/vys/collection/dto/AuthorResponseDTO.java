package net.vys.collection.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Author response data transfer object")
public class AuthorResponseDTO {
    @Schema(description = "Author ID", example = "1")
    private Long id;
    @Schema(description = "Author name", example = "Tyrion IV")
    private String name;

    public AuthorResponseDTO(Long id, String name) {
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
