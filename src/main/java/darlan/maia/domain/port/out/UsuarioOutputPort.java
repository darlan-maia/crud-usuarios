package darlan.maia.domain.port.out;

import darlan.maia.domain.model.Usuario;

public interface UsuarioOutputPort {

    Usuario findByUsername(String username);

    Usuario save(Usuario usuario);

    Usuario update(String username, Usuario usuario);

    void delete(String username);
}
