package basfer.controller;

import basfer.model.Integrante;
import basfer.model.Proyecto;
import basfer.model.Usuario;
import basfer.repository.ProyectoRepository;
import basfer.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IntegranteController {
    private final UsuarioService usuarioService;

    public IntegranteController(UsuarioService usuarioService) {this.usuarioService = usuarioService;}

    @GetMapping("/integrantes")
    public String integrantes(HttpSession session, Model model) {

        Usuario usuario = usuarioService.getSession(session);

        if (usuario == null) {
            return "redirect:/login";
        }

        List<Proyecto> proyectos = new ArrayList<>();

        for (Integrante integrante : usuario.getIntegrantes()) {
            if (!integrante.getProyecto().getArchivado()) {
                proyectos.add(integrante.getProyecto());
            }
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("proyectos", proyectos);
        return "integrantes";
    }
}
