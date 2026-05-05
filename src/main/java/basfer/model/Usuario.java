package basfer.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @Column(name = "email", length = 128)
    private String email;

    @Column(name = "nombre", length = 128, nullable = false)
    private String nombre;

    @Column(name = "apellido", length = 128, nullable = false)
    private String apellido;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "foto", length = 255)
    private String foto = "uploads/default.jpg";

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Integrante> integrantes;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Rol> roles;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Etiqueta> etiquetas;

    public Usuario() {}

    public Usuario(String email, String nombre, String apellido, String password, String foto) {
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.password = password;
        this.foto = foto;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }

    public List<Integrante> getIntegrantes() { return integrantes; }
    public void setIntegrantes(List<Integrante> integrantes) { this.integrantes = integrantes; }

    @Override
    public String toString() {
        return "Usuario: " + email + " - " + nombre + " " + apellido;
    }
}
