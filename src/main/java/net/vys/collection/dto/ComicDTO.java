package net.vys.collection.dto;
import java.util.List;
import net.vys.collection.dto.AuthorResponseDTO;
import net.vys.collection.dto.PublisherResponseDTO;
import net.vys.collection.dto.SerieResponseDTO;
import jakarta.validation.constraints.NotBlank;

public class ComicDTO {
    Long id;
    @NotBlank(message = "Comic name cannot be empty")
    private String title;
    private List<Long> authors;// List ID of Authors
    private Long publisher; // ID of the associated Publisher
    private Integer publicationYear;
    private String notes;
    private Integer issue;
    private Long serie; // ID of the associated Serie

    public ComicDTO() {}

    public ComicDTO(Long id, String title, List<Long> authors, Long publisher, Integer publicationYear,
                    String notes, Integer issue, Long serie) {
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
    public void setId(Long id) {this.id = id;}
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public List<Long> getAuthors() {return authors;}
    public void setAuthors(List<Long> authors) {this.authors = authors;}
    public Long getPublisher() {return publisher;}
    public void setPublisher(Long publisher) {this.publisher = publisher;}
    public Integer getPublicationYear() {return publicationYear;}
    public void setPublicationYear(Integer publicationYear) {this.publicationYear = publicationYear;}
    public String getNotes() {return notes;}
    public void setNotes(String notes) {this.notes = notes;}
    public Integer getIssue() {return issue;}
    public void setIssue(Integer issue) {this.issue = issue;}
    public Long getSerie() {return serie;}
    public void setSerie(Long serie) {this.serie = serie;}
}
