package darlan.maia.adapters.out.persistence;

import darlan.maia.adapters.out.exception.BusinessException;
import darlan.maia.adapters.out.persistence.entity.UsuarioEntity;
import darlan.maia.adapters.out.persistence.mapper.UsuarioPersistenceMapper;
import darlan.maia.adapters.out.persistence.repository.UsuarioRepository;
import darlan.maia.domain.model.Usuario;
import darlan.maia.domain.port.out.UsuarioOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class UsuarioPersistenceAdapter implements UsuarioOutputPort {

    private final UsuarioRepository repository;

    @Override
    public Usuario findByUsername(final String username) {

        final Supplier<BusinessException> supplier = () -> BusinessException.builder()
                .message("Usuário com username %s não foi encontrado".formatted(username))
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();

        final UsuarioEntity entity = repository
                .findByUsername(username)
                .orElseThrow(supplier);

        return UsuarioPersistenceMapper.toDomain(entity);
    }

    @Override
    public Usuario save(final Usuario usuario) {

        BusinessException exception = BusinessException.builder()
                .message("Problemas na integridade dos dados")
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();

        if (repository.existsByEmail(usuario.getEmail()))  {
            exception = exception.addCampo("email").withDescription("Email %s já está sendo utilizado".formatted(usuario.getEmail()));
        }

        if (repository.existsByUsername(usuario.getUsername())) {
            exception = exception.addCampo("username").withDescription("Username %s já está sendo utilizado".formatted(usuario.getUsername()));
        }

        if(exception.hasValidationErrors()) throw exception;

        final UsuarioEntity entity = UsuarioPersistenceMapper.toEntity(usuario);
        final UsuarioEntity saved = repository.save(entity);
        return UsuarioPersistenceMapper.toDomain(saved);
    }

    @Override
    public Usuario update(final String username, final Usuario usuario) {

        if (!repository.existsByUsername(username)) {
            throw BusinessException.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("Não existe usuário com username informado")
                    .build();
        }

        final UsuarioEntity entity = UsuarioPersistenceMapper.toEntity(usuario);
        final UsuarioEntity updated = repository.save(entity);

        return UsuarioPersistenceMapper.toDomain(updated);
    }

    @Override
    public void delete(final String username) {

        final Supplier<BusinessException> supplier = () -> BusinessException.builder()
                .message("Usuário com username %s não foi encontrado".formatted(username))
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();

        final UsuarioEntity entity = repository.findByUsername(username).orElseThrow(supplier);

        repository.delete(entity);
    }
}
