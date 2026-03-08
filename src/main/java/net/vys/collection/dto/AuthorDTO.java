package net.vys.collection.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthorDTO {
    Long id;
    @NotBlank(message = "Author name cannot be empty")
    @Size(max = 100, message = "Author name too long")
    private String name;

    public AuthorDTO() {
    }

    public AuthorDTO(Long id, String name) {
        this.id = id;
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
