package net.vys.collection.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

import jakarta.persistence.Id;

@Entity
@Table(name = "users")  // "user" es palabra reservada en SQL, mejor "users"
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;   // Único — lo usaremos para identificar al usuario

    @Column(nullable = false)
    private String password;   // Aquí irá el hash BCrypt, nunca texto plano

    @Column(nullable = false)
    private String role;       // Por ejemplo: "ROLE_USER" o "ROLE_ADMIN"

    // Constructores
    public User() {}

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}