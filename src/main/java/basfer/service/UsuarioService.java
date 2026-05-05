package basfer.service;

import basfer.model.Usuario;
import basfer.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario getSession(HttpSession session) {
        Usuario usuarioSesion = (Usuario) session.getAttribute("usuario");

        if (usuarioSesion == null){
            return null;
        }

        Usuario usuario = usuarioRepository.findById(usuarioSesion.getEmail()).orElse(null);

        return usuario;
    }

}