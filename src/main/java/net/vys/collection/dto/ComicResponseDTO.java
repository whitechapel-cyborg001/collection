package net.vys.collection.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema (description = "Comic response data transfer object")
public class ComicResponseDTO {
    @Schema(description = "Comic ID", example = "1")
    private Long id;
    @Schema(description = "Comic title", example = "The Dark Knight Returns")
    private String title;
    @Schema(description = "List of Author responses", example = "[{\"id\": 1, \"name\": \"Tyrion IV\"}, {...}]")
    private List<AuthorResponseDTO> authors;
    @Schema(description = "Publisher response", example = "{\"id\": 1, \"name\": \"DC Comics\"}")
    private PublisherResponseDTO publisher;
    @Schema(description = "Publication year", example = "2020")
    private Integer publicationYear;
    @Schema(description = "Notes about the comic", example = "First appearance of the character")
    private String notes;
    @Schema(description = "Issue number", example = "1")
    private Integer issue;
    @Schema(description = "Serie response", example = "{\"id\": 1, \"name\": \"Batman\"}")
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
