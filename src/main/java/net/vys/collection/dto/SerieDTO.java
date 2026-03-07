package net.vys.collection.dto;

import jakarta.validation.constraints.NotBlank;

public class SerieDTO {
    @NotBlank(message = "Serie name cannot be empty")
    private String name;
    private Integer issues;

    public SerieDTO() {}

    public SerieDTO(String name, Integer issues) {
        this.name = name;
        this.issues = issues;
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
