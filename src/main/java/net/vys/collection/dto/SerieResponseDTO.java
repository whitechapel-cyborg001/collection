package net.vys.collection.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "An Serie Response data transfer object")
public class SerieResponseDTO {
    @Schema(description = "Serie ID", example = "1")
    private final Long id;
    @Schema(description = "Serie name", example = "Batman")
    private final String name;
    @Schema(description = "Number of issues", example = "10")
    private final Integer issues;

    public SerieResponseDTO(Long id, String name, Integer issues) {
        this.id = id;
        this.name = name;
        this.issues = issues;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getIssues() {
        return issues;
    }
}
