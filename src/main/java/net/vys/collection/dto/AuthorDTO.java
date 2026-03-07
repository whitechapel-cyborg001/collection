package net.vys.collection.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthorDTO {
    @NotBlank(message = "Author name cannot be empty")
    @Size(max = 100, message = "Author name too long")
    private String name;

    public AuthorDTO() {
    }

    public AuthorDTO(String name) {

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
