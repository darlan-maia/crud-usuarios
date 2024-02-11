package darlan.maia.domain.service;

import darlan.maia.domain.model.Usuario;
import darlan.maia.domain.port.in.UsuarioInputPort;
import darlan.maia.domain.port.out.UsuarioOutputPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UsuarioService implements UsuarioInputPort {

    private final UsuarioOutputPort adapter;

    @Override
    public Usuario findByUsername(String username) {
        return adapter.findByUsername(username);
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
