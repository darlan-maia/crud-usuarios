package darlan.maia.adapters.out.persistence;

import darlan.maia.adapters.out.persistence.repository.UsuarioRepository;
import darlan.maia.domain.model.Usuario;
import darlan.maia.domain.port.out.UsuarioOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioPersistenceAdapter implements UsuarioOutputPort {

    private final UsuarioRepository repository;

    @Override
    public Usuario findByUsername(String username) {
        return null;
    }

    @Override
    public Usuario save(Usuario usuario) {
        return null;
    }

    @Override
    public Usuario update(String username, Usuario usuario) {
        return null;
    }

    @Override
    public void delete(String username) {

    }
}
