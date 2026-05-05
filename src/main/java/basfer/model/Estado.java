package basfer.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "estado")
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEstado")
    private Integer idEstado;

    @Column(name = "nombre", length = 128, nullable = false)
    private String nombre;

    @Column(name = "color", length = 128, nullable = false)
    private String color;

    @OneToMany(mappedBy = "estado", fetch = FetchType.LAZY)
    private List<Tarea> tareas;

    public Estado() {}

    public Estado(String nombre, String color) {
        this.nombre = nombre;
        this.color = color;
    }

    public Integer getId() { return idEstado; }
    public void setId(Integer idEstado) { this.idEstado = idEstado; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public List<Tarea> getTareas() { return tareas; }
    public void setTareas(List<Tarea> tareas) { this.tareas = tareas; }

    @Override
    public String toString() {
        return "Estado: " + idEstado + " - " + nombre + " (" + color + ")";
    }
}
