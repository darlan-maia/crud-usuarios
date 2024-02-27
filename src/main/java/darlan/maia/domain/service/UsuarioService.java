package darlan.maia.domain.service;

import darlan.maia.domain.model.Usuario;
import darlan.maia.domain.port.in.UsuarioInputPort;
import darlan.maia.domain.port.out.PasswordEncoderOutputPort;
import darlan.maia.domain.port.out.UsuarioOutputPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UsuarioService implements UsuarioInputPort {

    private final UsuarioOutputPort adapter;

    private final PasswordEncoderOutputPort encoder;

    @Override
    public Usuario findByUsername(final String username) {
        return adapter.findByUsername(username);
    }

    @Override
    public Usuario save(final Usuario usuario) {
        final String encoded = encoder.encode(usuario.getPassword());
        usuario.setPassword(encoded);
        return adapter.save(usuario);
    }

    @Override
    public Usuario update(final String username, final Usuario usuario) {

        final String encoded = encoder.encode(usuario.getPassword());

        usuario.setUsername(username);
        usuario.setPassword(encoded);

        return adapter.update(username, usuario);
    }

    @Override
    public void delete(final String username) {

    }
}
