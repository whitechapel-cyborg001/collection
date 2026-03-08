package net.vys.collection.dto;

import java.util.List;

public class ComicResponseDTO {
    private Long id;
    private String title;
    private List<AuthorResponseDTO> authors;
    private PublisherResponseDTO publisher;
    private Integer publicationYear;
    private String notes;
    private Integer issue;
    private SerieResponseDTO serie;

    public ComicResponseDTO(Long id, String title, List<AuthorResponseDTO> authors, PublisherResponseDTO publisher, Integer publicationYear, String notes, Integer issue, SerieResponseDTO serie) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.notes = notes;
        this.issue = issue;
        this.serie = serie;
    }

    public Long getId() {return id;}
    public String getTitle() {return title;}
    public List<AuthorResponseDTO> getAuthors() {return authors;}
    public PublisherResponseDTO getPublisher() {return publisher;}
    public Integer getPublicationYear() {return publicationYear;}
    public String getNotes() {return notes;}
    public Integer getIssue() {return issue;}
    public SerieResponseDTO getSerie() {return serie;} 
}
