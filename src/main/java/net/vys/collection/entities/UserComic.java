package net.vys.collection.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "user_comics",
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "comic_id"}))
       /*uniqueConstraints
        Restricción a nivel de base de datos. Aunque en el service controlemos la lógica, esto añade una segunda
        capa de seguridad directamente en MySQL. Si por algún bug intentas insertar el mismo user_id + comic_id
        dos veces, la BD lo rechaza antes de que ocurra el problema. */
public class UserComic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* fetch = FetchType.LAZY
    Cuando cargas un UserComic de la base de datos, JPA podría automáticamente cargar también el User completo y 
    el Comic completo que tiene dentro. Eso son queries extra que quizás no necesitas.
    LAZY le dice: "no cargues el User ni el Comic hasta que yo los pida explícitamente." Es 
    la opción eficiente por defecto. El contrario es EAGER — carga todo siempre, aunque no lo uses. 
    */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comic_id", nullable = false)
    private Comic comic;

    /* @Enumerated(EnumType.STRING)
    Le dice a JPA cómo guardar el enum en la base de datos. Sin esta anotación lo guardaría como número (0, 1) 
    — frágil, porque si cambias el orden del enum, los datos se corrompen. Con STRING guarda literalmente
    "OWNED" o "WANTED" — legible y seguro. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CollectionStatus status = CollectionStatus.OWNED;

    public enum CollectionStatus {
        OWNED, WANTED
    }

    // Getters y setters
    public Long getId() { return id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Comic getComic() { return comic; }
    public void setComic(Comic comic) { this.comic = comic; }
    public CollectionStatus getStatus() { return status; }
    public void setStatus(CollectionStatus status) { this.status = status; }
}