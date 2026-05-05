package basfer.controller;

import basfer.model.Usuario;
import basfer.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCrypt;


@Controller
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, HttpSession session, Model model) {

        // Si ya hay usuario en sesión, redirigir al inicio
        if (session.getAttribute("usuario") != null) {
            return "redirect:/";
        }

        if (error != null) {
            model.addAttribute("error", "Email o contraseña incorrectos");
        }

        return "login";
    }

    @PostMapping("/login")
    public String comprobarUsuario(@RequestParam String email, @RequestParam String password, HttpSession session) {

        Usuario usuario = usuarioRepository.findById(email).orElse(null);
        if (usuario != null && BCrypt.checkpw(password, usuario.getPassword())) {
            session.setAttribute("usuario", usuario);
            return "redirect:/";
        }

        return "redirect:/login?error";
    }

    @GetMapping("/register")
    public String register(@RequestParam(required = false) String error,
                           HttpSession session,
                           Model model) {


        if (session.getAttribute("usuario") != null) {
            return "redirect:/";
        }

        if (error != null) {
            model.addAttribute("error", "Ya existe un usuario con ese email");
        }

        model.addAttribute("usuario", new Usuario());
        return "register";
    }

    @PostMapping("/register")
    public String saveUsuario(@ModelAttribute Usuario usuario, HttpSession session) {

        if (usuarioRepository.findById(usuario.getEmail()).isPresent()) {
            return "redirect:/register?error";
        }

        usuario.setPassword(BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt()));

        session.setAttribute("usuario", usuario);

        usuarioRepository.save(usuario);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
