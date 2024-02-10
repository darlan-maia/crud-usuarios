package darlan.maia.domain.port.in;

import darlan.maia.domain.model.Usuario;

public interface UsuarioInputPort {

    Usuario findByUsername(String username);

    Usuario save(Usuario usuario);

    Usuario update(String username, Usuario usuario);

    void delete(String username);
}
