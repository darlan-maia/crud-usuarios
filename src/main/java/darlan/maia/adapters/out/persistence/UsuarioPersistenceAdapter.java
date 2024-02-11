package darlan.maia.adapters.out.persistence;

import darlan.maia.adapters.out.exceptions.BusinessException;
import darlan.maia.adapters.out.persistence.entity.UsuarioEntity;
import darlan.maia.adapters.out.persistence.mapper.UsuarioPersistenceMapper;
import darlan.maia.adapters.out.persistence.repository.UsuarioRepository;
import darlan.maia.domain.model.Usuario;
import darlan.maia.domain.port.out.UsuarioOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioPersistenceAdapter implements UsuarioOutputPort {

    private final UsuarioRepository repository;

    @Override
    public Usuario findByUsername(String username) {
        final UsuarioEntity entity = repository
                .findByUsername(username)
                .orElseThrow(() -> BusinessException.builder()
                        .message("Usuário com username %s não foi encontrado".formatted(username))
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .build()
                );
        return UsuarioPersistenceMapper.toDomain(entity);
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
