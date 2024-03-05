package darlan.maia.service;

import darlan.maia.exception.BusinessException;
import darlan.maia.model.dto.TelefoneDTO;
import darlan.maia.model.dto.UsuarioRequestDTO;
import darlan.maia.model.dto.UsuarioResponseDTO;
import darlan.maia.model.dto.UsuarioUpdateRequestDTO;
import darlan.maia.model.entity.Usuario;
import darlan.maia.model.mapper.UsuarioMapper;
import darlan.maia.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    public UsuarioResponseDTO buscaPorUsername(final String username) {

        final Supplier<BusinessException> supplier = () -> BusinessException.builder()
                .message("Usuário com username %s não encontrado".formatted(username))
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();

        final Usuario usuario = repository.findByUsername(username).orElseThrow(supplier);

        return UsuarioMapper.toDTO(usuario);
    }

    public UsuarioResponseDTO salvar(final UsuarioRequestDTO request) {

        final BusinessException exception = BusinessException.builder()
                .message("Usuário com username %s já existe".formatted(request.getUsername()))
                .httpStatus(HttpStatus.CONFLICT)
                .build();

        final Optional<Usuario> optional = repository.findByUsername(request.getUsername());

        if (optional.isPresent()) throw exception;

        final String encoded = encoder.encode(request.getPassword());
        request.setPassword(encoded);

        final Usuario saved = repository.save(UsuarioMapper.toEntity(request));

        return UsuarioMapper.toDTO(saved);
    }

    public UsuarioResponseDTO update(final String username, final UsuarioUpdateRequestDTO request) {

        final BusinessException exception = BusinessException.builder()
                .message("Não existe usuário com username %s".formatted(username))
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();

        final Usuario usuario = repository.findByUsername(username).orElseThrow(() -> exception);

        usuario.setPassword(request.getPassword());
        usuario.setFirstName(request.getFirstName());
        usuario.setLastName(request.getLastName());
        usuario.setEmail(request.getEmail());
        usuario.setTelefones(request.getTelefones().stream().map(TelefoneDTO::toString).toList());

        final Usuario saved = repository.save(usuario);

        return UsuarioMapper.toDTO(saved);
    }

    public void delete(final String username) {

        final BusinessException exception = BusinessException.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message("Usuario com username %s não foi encontrado".formatted(username))
                .build();

        final Usuario usuario = repository.findByUsername(username).orElseThrow(() -> exception);

        repository.delete(usuario);
    }
}
