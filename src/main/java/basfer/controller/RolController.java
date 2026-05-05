package basfer.controller;

import basfer.model.Etiqueta;
import basfer.model.Permiso;
import basfer.model.Rol;
import basfer.model.Usuario;
import basfer.repository.EtiquetaRepository;
import basfer.repository.PermisoRepository;
import basfer.repository.RolRepository;
import basfer.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RolController {

    private final RolRepository rolRepository;
    private final EtiquetaRepository etiquetaRepository;
    private final PermisoRepository permisoRepository;
    private final UsuarioService usuarioService;

    public RolController(RolRepository rolRepository,
                         EtiquetaRepository etiquetaRepository,
                         PermisoRepository permisoRepository,
                         UsuarioService usuarioService) {
        this.rolRepository = rolRepository;
        this.etiquetaRepository = etiquetaRepository;
        this.permisoRepository = permisoRepository;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/roles_permisos")
    public String rolesPermisos(@RequestParam(required = false, defaultValue = "roles") String seccion,
                                HttpSession session, Model model) {
        Usuario usuario = usuarioService.getSession(session);
        if (usuario == null) return "redirect:/login";

        List<Rol> roles = rolRepository.findAll();
        List<Etiqueta> etiquetas = etiquetaRepository.findAll();
        List<Permiso> permisos = permisoRepository.findAll();

        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", roles);
        model.addAttribute("etiquetas", etiquetas);
        model.addAttribute("permisos", permisos);
        model.addAttribute("seccion", seccion);

        return "roles_permisos";
    }

    @PostMapping("/roles/crear")
    public String crearRol(@RequestParam String nombre,
                           @RequestParam String color,
                           @RequestParam(required = false) List<Integer> idPermisos,
                           @RequestParam(required = false) List<Integer> idEtiquetas,
                           HttpSession session) {

        Usuario usuario = usuarioService.getSession(session);
        if (usuario == null) return "redirect:/login";

        Rol rol = new Rol(nombre, color, usuario);
        rolRepository.save(rol);

        if (idPermisos != null)
            rol.setPermisos(permisoRepository.findAllById(idPermisos));
        if (idEtiquetas != null)
            rol.setEtiquetas(etiquetaRepository.findAllById(idEtiquetas));

        rolRepository.save(rol);
        return "redirect:/roles_permisos?seccion=roles";
    }

    @PostMapping("/etiquetas/crear")
    public String crearEtiqueta(@RequestParam String nombre,
                                @RequestParam String color,
                                HttpSession session) {
        Usuario usuario = usuarioService.getSession(session);
        if (usuario == null) return "redirect:/login";

        Etiqueta etiqueta = new Etiqueta(nombre, color, usuario);
        etiquetaRepository.save(etiqueta);
        return "redirect:/roles_permisos?seccion=etiquetas";
    }
}
