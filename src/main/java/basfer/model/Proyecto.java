package basfer.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "proyecto")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProyecto")
    private Integer idProyecto;

    @Column(name = "nombre", length = 128, nullable = false)
    private String nombre;

    @Column(name = "color", length = 128, nullable = false)
    private String color;

    @Column(name = "tipo", length = 128, nullable = false)
    private String tipo;

    @Column(name = "fecha_inicial", nullable = false)
    private LocalDate fechaInicial;

    @Column(name = "fecha_final", nullable = false)
    private LocalDate fechaFinal;

    @Column(name = "presupuesto")
    private Integer presupuesto;

    @Column(name = "archivado")
    private Boolean archivado;

    @ManyToOne
    @JoinColumn(name = "idEtiqueta")
    private Etiqueta etiqueta;

    @OneToMany(mappedBy = "proyecto", fetch = FetchType.LAZY)
    private List<Quatrimestre> quatrimestres;

    @OneToMany(mappedBy = "proyecto", fetch = FetchType.LAZY)
    private List<Tarea> tareas;

    @OneToMany(mappedBy = "proyecto", fetch = FetchType.LAZY)
    private List<Integrante> integrantes;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "detalle_proyecto_rol",
        joinColumns = @JoinColumn(name = "idProyecto"),
        inverseJoinColumns = @JoinColumn(name = "idRol")
    )
    private List<Rol> roles;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "detalle_proyecto_etiqueta",
        joinColumns = @JoinColumn(name = "idProyecto"),
        inverseJoinColumns = @JoinColumn(name = "idEtiqueta")
    )
    private List<Etiqueta> etiquetas;

    public Proyecto() {}

    public Proyecto(String nombre, String color, String tipo, LocalDate fechaInicial, LocalDate fechaFinal) {
        this.nombre = nombre;
        this.color = color;
        this.tipo = tipo;
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
    }

    public Integer getId() { return idProyecto; }
    public void setId(Integer idProyecto) { this.idProyecto = idProyecto; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public LocalDate getFechaInicial() { return fechaInicial; }
    public void setFechaInicial(LocalDate fechaInicial) { this.fechaInicial = fechaInicial; }

    public LocalDate getFechaFinal() { return fechaFinal; }
    public void setFechaFinal(LocalDate fechaFinal) { this.fechaFinal = fechaFinal; }

    public Integer getPresupuesto() { return presupuesto; }
    public void setPresupuesto(Integer presupuesto) { this.presupuesto = presupuesto; }

    public Boolean getArchivado() { return archivado; }
    public void setArchivado(Boolean archivado) { this.archivado = archivado; }

    public Etiqueta getEtiqueta() { return etiqueta; }
    public void setEtiqueta(Etiqueta etiqueta) { this.etiqueta = etiqueta; }

    public List<Quatrimestre> getQuatrimestres() { return quatrimestres; }
    public void setQuatrimestres(List<Quatrimestre> quatrimestres) { this.quatrimestres = quatrimestres; }

    public List<Tarea> getTareas() { return tareas; }
    public void setTareas(List<Tarea> tareas) { this.tareas = tareas; }

    public List<Integrante> getIntegrantes() { return integrantes; }
    public void setIntegrantes(List<Integrante> integrantes) { this.integrantes = integrantes; }

    public List<Rol> getRoles() { return roles; }
    public void setRoles(List<Rol> roles) { this.roles = roles; }

    public List<Etiqueta> getEtiquetas() { return etiquetas; }
    public void setEtiquetas(List<Etiqueta> etiquetas) { this.etiquetas = etiquetas; }

    @Override
    public String toString() {
        return "Proyecto: " + idProyecto + " - " + nombre + " (" + color + ")";
    }
}
