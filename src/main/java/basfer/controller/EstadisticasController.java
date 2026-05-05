package basfer.controller;

import basfer.model.Integrante;
import basfer.model.Proyecto;
import basfer.model.Quatrimestre;
import basfer.model.Usuario;
import basfer.repository.ProyectoRepository;
import basfer.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EstadisticasController {

    private final UsuarioService usuarioService;
    private final ProyectoRepository proyectoRepository;

    public EstadisticasController(UsuarioService usuarioService, ProyectoRepository proyectoRepository) {
        this.usuarioService = usuarioService;
        this.proyectoRepository = proyectoRepository;
    }

    @GetMapping("/estadisticas")
    public String estadisticas(@RequestParam(required = false) Integer proyectoId,
                               HttpSession session, Model model) {

        Usuario usuario = usuarioService.getSession(session);
        if (usuario == null) return "redirect:/login";

        // Proyectos activos del usuario
        List<Proyecto> proyectos = new ArrayList<>();
        for (Integrante integrante : usuario.getIntegrantes()) {
            if (!integrante.getProyecto().getArchivado()) {
                proyectos.add(integrante.getProyecto());
            }
        }

        // Proyecto seleccionado (por defecto el primero)
        Proyecto proyectoSeleccionado = null;
        if (proyectoId != null) {
            proyectoSeleccionado = proyectoRepository.findById(proyectoId).orElse(null);
        }
        if (proyectoSeleccionado == null && !proyectos.isEmpty()) {
            proyectoSeleccionado = proyectos.get(0);
        }

        // Calcular datos por cuatrimestre del proyecto seleccionado
        // Para cada quatrimestre: porcentaje planificado vs % del presupuesto realmente gastado
        List<Map<String, Object>> cuatrimestresData = new ArrayList<>();
        int totalGastoProyecto = 0;

        if (proyectoSeleccionado != null && proyectoSeleccionado.getPresupuesto() != null
                && proyectoSeleccionado.getPresupuesto() > 0) {

            List<Quatrimestre> quatrimestres = proyectoSeleccionado.getQuatrimestres();
            int presupuesto = proyectoSeleccionado.getPresupuesto();

            for (int i = 0; i < quatrimestres.size(); i++) {
                Quatrimestre q = quatrimestres.get(i);
                totalGastoProyecto += q.getGasto();

                // % que este quatrimestre representa del presupuesto total realmente gastado
                int porcentajeGastoReal = presupuesto > 0
                        ? (q.getGasto() * 100 / presupuesto) : 0;

                Map<String, Object> data = new LinkedHashMap<>();
                data.put("numero", i + 1);
                data.put("porcentajePlanificado", q.getPorcentaje()); // % planificado del presupuesto
                data.put("porcentajeGastoReal", porcentajeGastoReal); // % real gastado del presupuesto
                data.put("gastoReal", q.getGasto());
                data.put("presupuestoPlanificado", presupuesto * q.getPorcentaje() / 100);
                data.put("diferencia", porcentajeGastoReal - q.getPorcentaje()); // positivo = sobrepresupuesto
                cuatrimestresData.add(data);
            }
        }

        int porcentajeTotalGastado = proyectoSeleccionado != null
                && proyectoSeleccionado.getPresupuesto() != null
                && proyectoSeleccionado.getPresupuesto() > 0
                ? totalGastoProyecto * 100 / proyectoSeleccionado.getPresupuesto() : 0;

        model.addAttribute("usuario", usuario);
        model.addAttribute("proyectos", proyectos);
        model.addAttribute("proyectoSeleccionado", proyectoSeleccionado);
        model.addAttribute("cuatrimestresData", cuatrimestresData);
        model.addAttribute("totalGasto", totalGastoProyecto);
        model.addAttribute("porcentajeTotalGastado", porcentajeTotalGastado);

        return "estadisticas";
    }
}
