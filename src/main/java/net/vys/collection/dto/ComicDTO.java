package net.vys.collection.dto;
import java.util.List;

public class ComicDTO {

    private String title;
    private List<Long> authorsIDs;
    private Long publisherID;
    private Integer publicationYear;
    private String notes;
    private Integer issueNumber;
    private Long serieId; // ID of the associated Serie

    public ComicDTO() {}

    public ComicDTO(String title, List<Long> authorsIDs, Long publisherID, Integer publicationYear, String notes, Integer issueNumber, Long serieId) {
        this.title = title;
        this.authorsIDs = authorsIDs;
        this.publisherID = publisherID;
        this.publicationYear = publicationYear;
        this.notes = notes;
        this.issueNumber = issueNumber;
        this.serieId = serieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Long> getAuthorsIDs() {
        return authorsIDs;
    }
    public void setAuthorsIDs(List<Long> authorsIDs) {
        this.authorsIDs = authorsIDs;
    }

    public Long getPublisherID() {
        return publisherID;
    }
    public void setPublisherID(Long publisherID) {
        this.publisherID = publisherID;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }
    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getIssueNumber() {
        return issueNumber;
    }
    public void setIssueNumber(Integer issueNumber) {
        this.issueNumber = issueNumber;
    }

    public Long getSerieId() {
        return serieId;
    }
    public void setSerieId(Long serieId) {
        this.serieId = serieId;
    }

    

}
