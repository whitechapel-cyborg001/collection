package net.vys.collection.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Publisher data transfer object")
public class PublisherDTO {
    //@Schema(description = "Publisher ID", example = "1")
    //Long id;
    @NotBlank(message = "Publisher name cannot be empty")
    @Schema(description = "Publisher name", example = "DC Comics")
    private String name;

    public PublisherDTO() {
    }

    public PublisherDTO(String name) {
        this.name = name;
    }

    /*public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }*/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
