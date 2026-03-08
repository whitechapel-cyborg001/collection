package net.vys.collection.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.util.List;
import net.vys.collection.entities.Author;
import net.vys.collection.entities.Publisher;
import net.vys.collection.entities.Serie;

@Entity
@Table(name = "comics")
public class Comic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToMany
    private List<Author> authors;
    @ManyToOne
    private Publisher publisher;
    private Integer publicationYear;
    private String notes;
    private Integer issue;
    @ManyToOne
    private Serie serie;

    // Constructors, getters, and setters

    public Comic() {
    }

    public Comic(String title, List<Author> authors, Publisher publisher, Integer publicationYear, Integer issue, Serie serie) {
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.issue = issue;
        this.serie = serie;
    }

    public Long getId() { return id; }
    public void setId(Long id) {this.id = id;}
    public String getTitle() {return title; }
    public void setTitle(String title) { this.title = title;}
    public List<Author> getAuthors() {return authors;}
    public void setAuthors(List<Author> authors) {this.authors = authors;}
    public Publisher getPublisher() {return publisher;}
    public void setPublisher(Publisher publisher) {this.publisher = publisher;}
    public Integer getPublicationYear() {return publicationYear;}
    public void setPublicationYear(Integer publicationYear) {this.publicationYear = publicationYear;}
    public String getNotes() {return notes;}  
    public void setNotes(String notes) {this.notes = notes;}
    public Integer getIssue() {return issue;}
    public void setIssue(Integer issue) {this.issue = issue;}
    public Serie getSerie() {return serie;}
    public void setSerie(Serie serie) {this.serie = serie;}
}