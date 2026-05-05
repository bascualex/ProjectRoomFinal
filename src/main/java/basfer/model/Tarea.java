package basfer.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tarea")
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTarea")
    private Integer idTarea;

    @Column(name = "nombre", length = 128, nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "idEtiqueta")
    private Etiqueta etiqueta;

    @ManyToOne
    @JoinColumn(name = "idProyecto")
    private Proyecto proyecto;

    @ManyToOne
    @JoinColumn(name = "idPrioridad")
    private Prioridad prioridad;

    @ManyToOne
    @JoinColumn(name = "idEstado")
    private Estado estado;

    @ManyToOne
    @JoinColumn(name = "idIntegrante")
    private Integrante integrante;

    public Tarea() {}

    public Tarea(String nombre, Etiqueta etiqueta, Proyecto proyecto, Prioridad prioridad, Integrante integrante) {
        this.nombre = nombre;
        this.etiqueta = etiqueta;
        this.proyecto = proyecto;
        this.prioridad = prioridad;
        this.integrante = integrante;
    }

    public Integer getId() { return idTarea; }
    public void setId(Integer idTarea) { this.idTarea = idTarea; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Etiqueta getEtiqueta() { return etiqueta; }
    public void setEtiqueta(Etiqueta etiqueta) { this.etiqueta = etiqueta; }

    public Proyecto getProyecto() { return proyecto; }
    public void setProyecto(Proyecto proyecto) { this.proyecto = proyecto; }

    public Prioridad getPrioridad() { return prioridad; }
    public void setPrioridad(Prioridad prioridad) { this.prioridad = prioridad; }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    public Integrante getIntegrante() { return integrante; }
    public void setIntegrante(Integrante integrante) { this.integrante = integrante; }

    @Override
    public String toString() {
        return "Tarea: " + idTarea + " - " + nombre + " Proyecto: " + proyecto.getNombre() + " Prioridad: " + prioridad.getNombre();
    }
}
