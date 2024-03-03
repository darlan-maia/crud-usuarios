package darlan.maia.service;

import darlan.maia.exception.BusinessException;
import darlan.maia.model.dto.UsuarioRequestDTO;
import darlan.maia.model.dto.UsuarioResponseDTO;
import darlan.maia.model.entity.Usuario;
import darlan.maia.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UsuarioService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve buscar usuário com sucesso")
    void deveBuscarUsuarioComSucesso() {

        final Usuario usuario = Usuario.builder()
                .username("joao")
                .password("encoded")
                .firstName("João")
                .lastName("Silva")
                .email("joao@email.com")
                .build();

        Mockito.when(repository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(usuario));

        final UsuarioResponseDTO dto = service.buscaPorUsername("joao");

        Assertions.assertEquals("joao", dto.getUsername());
        Assertions.assertEquals("João Silva", dto.getFullName());
        Assertions.assertEquals("joao@email.com", dto.getEmail());
        Assertions.assertEquals(new ArrayList<>(), dto.getTelefones());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar buscar usuário")
    void deveLancarExcecaoAoTentarBuscarUsuario() {

        Mockito.when(repository.findByUsername(Mockito.anyString())).thenReturn(Optional.empty());

        try {
            service.buscaPorUsername("joao");
            Assertions.fail("Uma exceção deveria ser lançada aqui");
        } catch (final Exception e) {
            Assertions.assertTrue(e instanceof BusinessException);

            final BusinessException exception = (BusinessException) e;
            Assertions.assertEquals("Usuário com username joao não encontrado", exception.getMessage());
            Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        }
    }

    @Test
    @DisplayName("Deve salvar usuário com sucesso")
    void deveSalvarUsuarioComSucesso() {

        final UsuarioRequestDTO request = UsuarioRequestDTO.builder()
                .username("joao")
                .password("123")
                .firstName("João")
                .lastName("Silva")
                .build();

        final Usuario usuario = Usuario.builder()
                .username("joao")
                .firstName("João")
                .lastName("Silva")
                .build();

        Mockito.when(repository.findByUsername(Mockito.anyString())).thenReturn(Optional.empty());

        Mockito.when(repository.save(Mockito.any())).thenReturn(usuario);

        Mockito.when(encoder.encode(Mockito.anyString())).thenReturn("<encoded>");

        final UsuarioResponseDTO dto = service.salvar(request);

        Assertions.assertEquals("joao", dto.getUsername());
        Assertions.assertEquals("João Silva", dto.getFullName());

        final ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);

        Mockito.verify(repository).save(captor.capture());

        final Usuario value = captor.getValue();

        Assertions.assertEquals("<encoded>", value.getPassword());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar salvar um usuário")
    void deveLancarExcecaoAoTentarSalvarUsuario() {

        Mockito.when(repository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(new Usuario()));

        final UsuarioRequestDTO request = UsuarioRequestDTO.builder().username("joao").build();

        try {
            service.salvar(request);
            Assertions.fail("Uma exceção deveria ser lançada aqui");
        } catch (final Exception e) {
            Assertions.assertTrue(e instanceof BusinessException);

            final BusinessException exception = (BusinessException) e;
            Assertions.assertEquals("Usuário com username joao já existe", exception.getMessage());
            Assertions.assertEquals(HttpStatus.CONFLICT, exception.getHttpStatus());
        }
    }
}