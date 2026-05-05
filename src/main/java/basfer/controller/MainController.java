package basfer.controller;

import basfer.model.Integrante;
import basfer.model.Proyecto;
import basfer.model.Tarea;
import basfer.model.Usuario;
import basfer.repository.ProyectoRepository;
import basfer.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    private final UsuarioService usuarioService;
    private final ProyectoRepository proyectoRepository;

    public MainController(ProyectoRepository proyectoRepository, UsuarioService usuarioService) {
        this.proyectoRepository = proyectoRepository;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        Usuario usuario = usuarioService.getSession(session);
        if (usuario == null) return "redirect:/login";

        List<Proyecto> proyectos = new ArrayList<>();
        long tareasPendientes = 0;

        for (Integrante integrante : usuario.getIntegrantes()) {
            if (!integrante.getProyecto().getArchivado()) {
                proyectos.add(integrante.getProyecto());
                // Contar tareas del usuario que NO están completadas (estado id != 4)
                for (Tarea tarea : integrante.getTareas()) {
                    if (tarea.getEstado() == null || tarea.getEstado().getId() != 4) {
                        tareasPendientes++;
                    }
                }
            }
        }

        // Presupuesto global: suma gasto / suma presupuesto
        long totalPresupuesto = proyectos.stream()
                .filter(p -> p.getPresupuesto() != null)
                .mapToLong(Proyecto::getPresupuesto).sum();
        long totalGasto = proyectos.stream()
                .flatMap(p -> p.getQuatrimestres().stream())
                .mapToLong(q -> q.getGasto()).sum();
        int porcentajePresupuesto = totalPresupuesto > 0
                ? (int) (totalGasto * 100 / totalPresupuesto) : 0;

        model.addAttribute("usuario", usuario);
        model.addAttribute("proyectos", proyectos);
        model.addAttribute("totalProyectos", proyectos.size());
        model.addAttribute("tareasPendientes", tareasPendientes);
        model.addAttribute("porcentajePresupuesto", porcentajePresupuesto);

        return "index";
    }

    @GetMapping("/archivados")
    public String archivados(HttpSession session, Model model) {
        Usuario usuario = usuarioService.getSession(session);
        if (usuario == null) return "redirect:/login";

        List<Proyecto> archivados = new ArrayList<>();
        for (Integrante integrante : usuario.getIntegrantes()) {
            if (integrante.getProyecto().getArchivado()) {
                archivados.add(integrante.getProyecto());
            }
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("proyectos", archivados);
        return "archivados";
    }
}
