package basfer.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "etiqueta")
public class Etiqueta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEtiqueta")
    private Integer idEtiqueta;

    @Column(name = "nombre", length = 128, nullable = false)
    private String nombre;

    @Column(name = "color", length = 128, nullable = false)
    private String color;

    @ManyToOne
    @JoinColumn(name = "creador")
    private Usuario usuario;

    @OneToMany(mappedBy = "etiqueta", fetch = FetchType.LAZY)
    private List<Tarea> tareas;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "detalle_etiqueta_rol",
        joinColumns = @JoinColumn(name = "idEtiqueta"),
        inverseJoinColumns = @JoinColumn(name = "idRol")
    )
    private List<Rol> roles;

    @ManyToMany(mappedBy = "etiquetas", fetch = FetchType.LAZY)
    private List<Integrante> integrantes;

    @ManyToMany(mappedBy = "etiquetas", fetch = FetchType.LAZY)
    private List<Proyecto> proyectos;

    public Etiqueta() {}

    public Etiqueta(String nombre, String color, Usuario usuario) {
        this.nombre = nombre;
        this.color = color;
        this.usuario = usuario;
    }

    public Integer getId() { return idEtiqueta; }
    public void setId(Integer idEtiqueta) { this.idEtiqueta = idEtiqueta; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public List<Tarea> getTareas() { return tareas; }
    public void setTareas(List<Tarea> tareas) { this.tareas = tareas; }

    public List<Rol> getRoles() { return roles; }
    public void setRoles(List<Rol> roles) { this.roles = roles; }

    public List<Integrante> getIntegrantes() { return integrantes; }
    public void setIntegrantes(List<Integrante> integrantes) { this.integrantes = integrantes; }

    public List<Proyecto> getProyectos() { return proyectos; }
    public void setProyectos(List<Proyecto> proyectos) { this.proyectos = proyectos; }

    @Override
    public String toString() {
        return "Etiqueta: " + idEtiqueta + " - " + nombre + " (" + color + ")";
    }
}
