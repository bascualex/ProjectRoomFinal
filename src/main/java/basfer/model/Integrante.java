package basfer.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "integrante")
public class Integrante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idIntegrante")
    private Integer idIntegrante;

    @ManyToOne
    @JoinColumn(name = "email")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idRol")
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "idProyecto")
    private Proyecto proyecto;

    @OneToMany(mappedBy = "integrante", fetch = FetchType.LAZY)
    private List<Tarea> tareas;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "detalle_integrante_permiso",
        joinColumns = @JoinColumn(name = "idIntegrante"),
        inverseJoinColumns = @JoinColumn(name = "idPermiso")
    )
    private List<Permiso> permisos;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "detalle_integrante_etiqueta",
        joinColumns = @JoinColumn(name = "idIntegrante"),
        inverseJoinColumns = @JoinColumn(name = "idEtiqueta")
    )
    private List<Etiqueta> etiquetas;

    public Integrante() {}

    public Integrante(Usuario usuario, Rol rol) {
        this.usuario = usuario;
        this.rol = rol;
    }

    public Integer getId() { return idIntegrante; }
    public void setId(Integer idIntegrante) { this.idIntegrante = idIntegrante; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public Proyecto getProyecto() { return proyecto; }
    public void setProyecto(Proyecto proyecto) { this.proyecto = proyecto; }

    public List<Tarea> getTareas() { return tareas; }
    public void setTareas(List<Tarea> tareas) { this.tareas = tareas; }

    public List<Permiso> getPermisos() { return permisos; }
    public void setPermisos(List<Permiso> permisos) { this.permisos = permisos; }

    public List<Etiqueta> getEtiquetas() { return etiquetas; }
    public void setEtiquetas(List<Etiqueta> etiquetas) { this.etiquetas = etiquetas; }

    @Override
    public String toString() {
        return "Integrante: " + idIntegrante + " - " + usuario.getEmail() + " Rol: " + rol.getNombre();
    }
}
