package net.vys.collection.dto;

import jakarta.validation.constraints.NotBlank;

public class SerieDTO {
    Long id;
    @NotBlank(message = "Serie name cannot be empty")
    private String name;
    private Integer issues;

    public SerieDTO() {}

    public SerieDTO(Long id, String name, Integer issues) {
        this.id = id;
        this.name = name;
        this.issues = issues;
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

    public Integer getIssues() {
        return issues;
    }

    public void setIssues(Integer issues) {
        this.issues = issues;
    }
}
