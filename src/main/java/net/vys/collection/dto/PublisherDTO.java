package net.vys.collection.dto;

import jakarta.validation.constraints.NotBlank;

public class PublisherDTO {
    Long id;
    @NotBlank(message = "Publisher name cannot be empty")
    private String name;

    public PublisherDTO() {
    }

    public PublisherDTO(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
