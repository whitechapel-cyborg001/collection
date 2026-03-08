package net.vys.collection.dto;

public class SerieResponseDTO {
    private final Long id;
    private final String name;
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
