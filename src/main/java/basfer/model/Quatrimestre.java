package basfer.model;

import jakarta.persistence.*;

@Entity
@Table(name = "quatrimestre")
public class Quatrimestre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idQuatrimestre")
    private Integer idQuatrimestre;

    @Column(name = "porcentaje", nullable = false)
    private Integer porcentaje;

    @Column(name = "gasto", nullable = false)
    private Integer gasto;

    @ManyToOne
    @JoinColumn(name = "idProyecto")
    private Proyecto proyecto;

    public Quatrimestre() {}

    public Quatrimestre(Integer porcentaje, Integer gasto, Proyecto proyecto) {
        this.porcentaje = porcentaje;
        this.gasto = gasto;
        this.proyecto = proyecto;
    }

    public Integer getId() { return idQuatrimestre; }
    public void setId(Integer idQuatrimestre) { this.idQuatrimestre = idQuatrimestre; }

    public Integer getPorcentaje() { return porcentaje; }
    public void setPorcentaje(Integer porcentaje) { this.porcentaje = porcentaje; }

    public Integer getGasto() { return gasto; }
    public void setGasto(Integer gasto) { this.gasto = gasto; }

    public Proyecto getProyecto() { return proyecto; }
    public void setProyecto(Proyecto proyecto) { this.proyecto = proyecto; }

    @Override
    public String toString() {
        return "Quatrimestre: " + idQuatrimestre + " - Porcentaje: " + porcentaje + "% Gasto: " + gasto;
    }
}
