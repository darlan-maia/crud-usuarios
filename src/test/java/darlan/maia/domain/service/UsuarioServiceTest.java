package darlan.maia.domain.service;

import darlan.maia.domain.model.Usuario;
import darlan.maia.domain.port.out.PasswordEncoderOutputPort;
import darlan.maia.domain.port.out.UsuarioOutputPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioOutputPort persistenceAdapter;

    @Mock
    private PasswordEncoderOutputPort encoderAdapter;

    @InjectMocks
    private UsuarioService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Buscar por username")
    void deveBuscarPorUsernameComSucesso() {

        Mockito.when(persistenceAdapter.findByUsername(Mockito.anyString())).thenReturn(new Usuario());

        final Usuario usuario = service.findByUsername("joao");

        Assertions.assertEquals(new Usuario(), usuario);
    }

    @Test
    @DisplayName("Sucesso - Salvar Usuário")
    void deveSalvarUsuarioComSucesso() {

        final Usuario usuario = Usuario.builder()
                .username("joao123")
                .password("123456")
                .build();

        final ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);

        Mockito.when(encoderAdapter.encode(Mockito.anyString())).thenReturn("XYZ");

        Mockito.when(persistenceAdapter.save(Mockito.any())).thenReturn(usuario);

        final Usuario saved = service.save(usuario);

        Mockito.verify(persistenceAdapter).save(captor.capture());

        final Usuario value = captor.getValue();

        Assertions.assertEquals("XYZ", value.getPassword());
        Assertions.assertEquals(usuario, saved);
    }

}