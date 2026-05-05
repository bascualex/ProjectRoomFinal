package basfer.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "permiso")
public class Permiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPermiso")
    private Integer idPermiso;

    @Column(name = "nombre", length = 128, nullable = false)
    private String nombre;

    @ManyToMany(mappedBy = "permisos", fetch = FetchType.LAZY)
    private List<Rol> roles;

    @ManyToMany(mappedBy = "permisos", fetch = FetchType.LAZY)
    private List<Integrante> integrantes;

    public Permiso() {}

    public Permiso(String nombre, Usuario usuario) {
        this.nombre = nombre;
    }

    public Integer getId() { return idPermiso; }
    public void setId(Integer idPermiso) { this.idPermiso = idPermiso; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<Rol> getRoles() { return roles; }
    public void setRoles(List<Rol> roles) { this.roles = roles; }

    public List<Integrante> getIntegrantes() { return integrantes; }
    public void setIntegrantes(List<Integrante> integrantes) { this.integrantes = integrantes; }

    @Override
    public String toString() {
        return "Permiso: " + idPermiso + " - " + nombre;
    }
}
