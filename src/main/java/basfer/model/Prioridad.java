package basfer.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "prioridad")
public class Prioridad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPrioridad")
    private Integer idPrioridad;

    @Column(name = "nombre", length = 128, nullable = false)
    private String nombre;

    @Column(name = "color", length = 128, nullable = false)
    private String color;

    @OneToMany(mappedBy = "prioridad", fetch = FetchType.LAZY)
    private List<Tarea> tareas;

    public Prioridad() {}

    public Prioridad(String nombre, String color) {
        this.nombre = nombre;
        this.color = color;
    }

    public Integer getId() { return idPrioridad; }
    public void setId(Integer idPrioridad) { this.idPrioridad = idPrioridad; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public List<Tarea> getTareas() { return tareas; }
    public void setTareas(List<Tarea> tareas) { this.tareas = tareas; }

    @Override
    public String toString() {
        return "Prioridad: " + idPrioridad + " - " + nombre + " (" + color + ")";
    }
}
