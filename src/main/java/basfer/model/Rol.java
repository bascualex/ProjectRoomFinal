package basfer.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRol")
    private Integer idRol;

    @Column(name = "nombre", length = 128, nullable = false)
    private String nombre;

    @Column(name = "color", length = 128, nullable = false)
    private String color;

    @ManyToOne
    @JoinColumn(name = "creador")
    private Usuario usuario;

    @OneToMany(mappedBy = "rol", fetch = FetchType.LAZY)
    private List<Integrante> integrantes;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "detalle_rol_permiso",
        joinColumns = @JoinColumn(name = "idRol"),
        inverseJoinColumns = @JoinColumn(name = "idPermiso")
    )
    private List<Permiso> permisos;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<Etiqueta> etiquetas;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<Proyecto> proyectos;

    public Rol() {}

    public Rol(String nombre, String color, Usuario usuario) {
        this.nombre = nombre;
        this.color = color;
        this.usuario = usuario;
    }

    public Integer getId() { return idRol; }
    public void setId(Integer idRol) { this.idRol = idRol; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public List<Integrante> getIntegrantes() { return integrantes; }
    public void setIntegrantes(List<Integrante> integrantes) { this.integrantes = integrantes; }

    public List<Permiso> getPermisos() { return permisos; }
    public void setPermisos(List<Permiso> permisos) { this.permisos = permisos; }

    public List<Etiqueta> getEtiquetas() { return etiquetas; }
    public void setEtiquetas(List<Etiqueta> etiquetas) { this.etiquetas = etiquetas; }

    public List<Proyecto> getProyectos() { return proyectos; }
    public void setProyectos(List<Proyecto> proyectos) { this.proyectos = proyectos; }

    @Override
    public String toString() {
        return "Rol: " + idRol + " - " + nombre + " (" + color + ")";
    }
}
