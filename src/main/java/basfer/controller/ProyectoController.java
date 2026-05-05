package basfer.controller;

import basfer.model.*;
import basfer.repository.*;
import basfer.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProyectoController {

    private final UsuarioService usuarioService;
    private final IntegranteRepository integranteRepository;
    private final ProyectoRepository proyectoRepository;
    private final EstadoRepository estadoRepository;
    private final EtiquetaRepository etiquetaRepository;
    private final PrioridadRepository prioridadRepository;
    private final TareaRepository tareaRepository;
    private final RolRepository rolRepository;
    private final QuatrimistreRepository quatrimistreRepository;

    public ProyectoController(ProyectoRepository proyectoRepository,
                               IntegranteRepository integranteRepository,
                               EstadoRepository estadoRepository,
                               UsuarioService usuarioService,
                               EtiquetaRepository etiquetaRepository,
                               PrioridadRepository prioridadRepository,
                               TareaRepository tareaRepository,
                               RolRepository rolRepository,
                               QuatrimistreRepository quatrimistreRepository) {
        this.proyectoRepository = proyectoRepository;
        this.integranteRepository = integranteRepository;
        this.estadoRepository = estadoRepository;
        this.usuarioService = usuarioService;
        this.etiquetaRepository = etiquetaRepository;
        this.prioridadRepository = prioridadRepository;
        this.tareaRepository = tareaRepository;
        this.rolRepository = rolRepository;
        this.quatrimistreRepository = quatrimistreRepository;
    }

    @GetMapping("/proyecto/{id}")
    public String proyecto(@PathVariable Integer id, Model model, HttpSession session) {
        Usuario usuario = usuarioService.getSession(session);
        if (usuario == null) return "redirect:/login";

        Proyecto proyecto = proyectoRepository.findById(id).orElse(null);
        if (proyecto == null) return "redirect:/";

        List<Estado> estados = estadoRepository.findAll();

        Map<Integer, List<Tarea>> tareasPorEstado = new HashMap<>();
        for (Estado estado : estados) {
            tareasPorEstado.put(estado.getId(),
                    proyecto.getTareas().stream()
                            .filter(t -> t.getEstado() != null && t.getEstado().getId().equals(estado.getId()))
                            .collect(java.util.stream.Collectors.toList()));
        }

        int totalGasto = proyecto.getQuatrimestres().stream().mapToInt(Quatrimestre::getGasto).sum();
        int porcentajeGasto = proyecto.getPresupuesto() != null && proyecto.getPresupuesto() > 0
                ? totalGasto * 100 / proyecto.getPresupuesto() : 0;

        model.addAttribute("proyecto", proyecto);
        model.addAttribute("estados", estados);
        model.addAttribute("tareasPorEstado", tareasPorEstado);
        model.addAttribute("totalGasto", totalGasto);
        model.addAttribute("porcentajeGasto", porcentajeGasto);
        model.addAttribute("restante", proyecto.getPresupuesto() != null ? proyecto.getPresupuesto() - totalGasto : 0);
        model.addAttribute("etiquetas", etiquetaRepository.findAll());
        model.addAttribute("prioridades", prioridadRepository.findAll());
        model.addAttribute("usuario", usuario);

        return "proyecto";
    }

    @PostMapping("/proyecto/{id}/tarea")
    public String crearTarea(@PathVariable Integer id,
                             @RequestParam String nombre,
                             @RequestParam(required = false) Integer idEtiqueta,
                             @RequestParam(required = false) Integer idPrioridad,
                             @RequestParam Integer idEstado,
                             @RequestParam(required = false) Integer idIntegrante,
                             HttpSession session) {
        Usuario usuario = usuarioService.getSession(session);
        if (usuario == null) return "redirect:/login";

        Proyecto proyecto = proyectoRepository.findById(id).orElse(null);
        if (proyecto == null) return "redirect:/";

        Tarea tarea = new Tarea();
        tarea.setNombre(nombre);
        tarea.setProyecto(proyecto);

        if (idEtiqueta != null)
            tarea.setEtiqueta(etiquetaRepository.findById(idEtiqueta).orElse(null));
        if (idPrioridad != null)
            tarea.setPrioridad(prioridadRepository.findById(idPrioridad).orElse(null));
        if (idEstado != null)
            tarea.setEstado(estadoRepository.findById(idEstado).orElse(null));
        if (idIntegrante != null)
            tarea.setIntegrante(integranteRepository.findById(idIntegrante).orElse(null));

        tareaRepository.save(tarea);
        return "redirect:/proyecto/" + id;
    }

    @PostMapping("/proyectos/crear")
    public String crearProyecto(@ModelAttribute Proyecto proyecto,
                                @RequestParam(required = false, name = "porcentajes") List<Integer> porcentajes,
                                HttpSession session) {
        Usuario usuario = usuarioService.getSession(session);
        if (usuario == null) return "redirect:/login";

        if (porcentajes != null && !porcentajes.isEmpty()) {
            int suma = porcentajes.stream().mapToInt(Integer::intValue).sum();
            if (suma != 100) {
                return "redirect:/?errorCuatrimestres=" + (suma > 100
                        ? "La suma supera el 100% en " + (suma - 100) + "%"
                        : "Faltan " + (100 - suma) + "% por distribuir");
            }
        }

        proyecto.setArchivado(false);
        proyectoRepository.save(proyecto);

        // Crear quatrimestres con gasto inicial = 0
        if (porcentajes != null && !porcentajes.isEmpty()) {
            for (Integer porcentaje : porcentajes) {
                Quatrimestre q = new Quatrimestre(porcentaje, 0, proyecto);
                quatrimistreRepository.save(q);
            }
        }

        // Asignar al creador como integrante con el primer rol Admin disponible
        List<Rol> roles = rolRepository.findAll();
        Rol rolAdmin = roles.stream()
                .filter(r -> r.getNombre().equalsIgnoreCase("Admin"))
                .findFirst()
                .orElse(roles.isEmpty() ? null : roles.get(0));

        Integrante integrante = new Integrante(usuario, rolAdmin);
        integrante.setProyecto(proyecto);
        integranteRepository.save(integrante);

        return "redirect:/";
    }

    @PostMapping("/proyectos/archivar/{id}")
    public String archivarProyecto(@PathVariable Integer id, HttpSession session) {
        Usuario usuario = usuarioService.getSession(session);
        if (usuario == null) return "redirect:/login";

        Proyecto proyecto = proyectoRepository.findById(id).orElse(null);
        if (proyecto != null) {
            proyecto.setArchivado(true);
            proyectoRepository.save(proyecto);
        }
        return "redirect:/";
    }

    @PostMapping("/proyectos/restaurar/{id}")
    public String restaurarProyecto(@PathVariable Integer id, HttpSession session) {
        Usuario usuario = usuarioService.getSession(session);
        if (usuario == null) return "redirect:/login";

        Proyecto proyecto = proyectoRepository.findById(id).orElse(null);
        if (proyecto != null) {
            proyecto.setArchivado(false);
            proyectoRepository.save(proyecto);
        }
        return "redirect:/archivados";
    }
}
