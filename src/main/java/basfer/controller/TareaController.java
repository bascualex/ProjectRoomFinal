package basfer.controller;

import basfer.model.Integrante;
import basfer.model.Tarea;
import basfer.model.Usuario;
import basfer.repository.TareaRepository;
import basfer.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TareaController {

    private final TareaRepository tareaRepository;
    private final UsuarioService usuarioService;

    public TareaController(TareaRepository tareaRepository, UsuarioService usuarioService) {
        this.tareaRepository = tareaRepository;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/tareas")
    public String misTareas(HttpSession session, Model model) {
        Usuario usuario = usuarioService.getSession(session);
        if (usuario == null) return "redirect:/login";

        // Recopilar todas las tareas asignadas al usuario (como integrante en cualquier proyecto)
        List<Tarea> tareas = new ArrayList<>();
        for (Integrante integrante : usuario.getIntegrantes()) {
            if (!integrante.getProyecto().getArchivado()) {
                tareas.addAll(integrante.getTareas());
            }
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("tareas", tareas);
        return "tareas";
    }

    @PostMapping("/tarea/eliminar/{id}")
    public String eliminarTarea(@PathVariable Integer id, HttpSession session,
                                @RequestParam(required = false) Integer proyectoId) {
        Usuario usuario = usuarioService.getSession(session);
        if (usuario == null) return "redirect:/login";

        tareaRepository.deleteById(id);

        if (proyectoId != null) return "redirect:/proyecto/" + proyectoId;
        return "redirect:/tareas";
    }
}
