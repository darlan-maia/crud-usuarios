package darlan.maia.service;

import darlan.maia.exception.BusinessException;
import darlan.maia.model.dto.UsuarioResponseDTO;
import darlan.maia.model.entity.Usuario;
import darlan.maia.model.mapper.UsuarioMapper;
import darlan.maia.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public UsuarioResponseDTO buscaPorUsername(final String username) {

        final Supplier<BusinessException> supplier = () -> BusinessException.builder()
                .message("Usuário com username %s não encontrado".formatted(username))
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();

        final Usuario usuario = repository.findByUsername(username).orElseThrow(supplier);

        return UsuarioMapper.toDTO(usuario);
    }
}
