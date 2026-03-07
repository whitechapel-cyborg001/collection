package net.vys.collection.dto;

public class AuthorResponseDTO {
    private Long id;
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
