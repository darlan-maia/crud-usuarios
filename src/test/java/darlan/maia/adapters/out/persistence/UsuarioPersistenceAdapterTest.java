package darlan.maia.adapters.out.persistence;

import darlan.maia.adapters.out.exception.BusinessException;
import darlan.maia.adapters.out.persistence.entity.UsuarioEntity;
import darlan.maia.adapters.out.persistence.repository.UsuarioRepository;
import darlan.maia.domain.model.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Optional;

class UsuarioPersistenceAdapterTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioPersistenceAdapter adapter;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Sucesso - Busca por username")
    void deveBuscarUsuarioComSucesso() {

        Mockito.when(repository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(new UsuarioEntity()));

        final Usuario expected = Usuario.builder().telefones(new ArrayList<>()).build();

        final Usuario actual = adapter.findByUsername("joao");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar por username")
    void deveLancarExcecaoAoBuscarPorUsername() {

        Mockito.when(repository.findByUsername(Mockito.anyString())).thenReturn(Optional.empty());

        try {
            adapter.findByUsername("joao");
            Assertions.fail("Uma exceção deveria ser lançada aqui");
        } catch (Exception e) {
            Assertions.assertTrue(e instanceof BusinessException);

            final BusinessException exception = (BusinessException) e;
            Assertions.assertEquals("Usuário com username joao não foi encontrado", exception.getMessage());
            Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        }

    }

    @Test
    @DisplayName("Deve Salvar usuário com sucesso")
    void deveSalvarUsuarioComSucesso() {

        Mockito.when(repository.existsByUsername(Mockito.anyString())).thenReturn(false);
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
        Mockito.when(repository.save(Mockito.any())).thenReturn(new UsuarioEntity());

        final Usuario expected = Usuario.builder().telefones(new ArrayList<>()).build();

        final Usuario actual = adapter.save(new Usuario());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Deve Lançar Exceção com Erros de Validação")
    void deveLancarExcecaoComErrosDeValidacao() {

        Mockito.when(repository.existsByUsername(Mockito.anyString())).thenReturn(true);
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);

        try {
            adapter.save(Usuario.builder().username("joao").email("joao@email.com").build());
            Assertions.fail("Uma exceção deveria ser lançada aqui");
        } catch (Exception e) {

            Assertions.assertTrue(e instanceof BusinessException);

            final BusinessException exception = (BusinessException) e;
            Assertions.assertEquals("Problemas na integridade dos dados", exception.getMessage());
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
            Assertions.assertEquals(2, exception.getValidations().size());
            Assertions.assertEquals("email", exception.getValidations().get(0)[0]);
            Assertions.assertEquals("Email joao@email.com já está sendo utilizado", exception.getValidations().get(0)[1]);
            Assertions.assertEquals("username", exception.getValidations().get(1)[0]);
            Assertions.assertEquals("Username joao já está sendo utilizado", exception.getValidations().get(1)[1]);
        }
    }

    @Test
    @DisplayName("Deve Atualizar Usuário com Sucesso")
    void deveAtualizarUsuarioComSucesso() {

        Mockito.when(repository.existsByUsername(Mockito.anyString())).thenReturn(true);
        Mockito.when(repository.save(Mockito.any())).thenReturn(UsuarioEntity.builder().id(1L).username("joao").build());

        final Usuario updated = adapter.update("joao", new Usuario());

        Assertions.assertEquals(1L, updated.getId());
        Assertions.assertEquals("joao", updated.getUsername());
    }

    @Test
    @DisplayName("Deve Lançar Exceção ao tentar atualizar")
    void deveLancarExcecaoAoTentarAtualizar() {

        Mockito.when(repository.existsByUsername(Mockito.anyString())).thenReturn(false);

        try {
            adapter.update("joao", new Usuario());
            Assertions.fail("Uma exceção deveria ser lançada aqui");
        } catch (Exception e) {

            Assertions.assertTrue(e instanceof BusinessException);

            final BusinessException exception = (BusinessException) e;
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
            Assertions.assertEquals("Não existe usuário com username informado", exception.getMessage());
        }
    }

    @Test
    @DisplayName("Deve Excluir usuário com sucesso")
    void deveExcluirUsuarioComSucesso() {

        final UsuarioEntity entity = UsuarioEntity.builder().username("joao").build();

        Mockito.when(repository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(entity));

        Mockito.doNothing().when(repository).delete(Mockito.any());

        final ArgumentCaptor<UsuarioEntity> captor = ArgumentCaptor.forClass(UsuarioEntity.class);

        adapter.delete("joao");

        Mockito.verify(repository).delete(captor.capture());

        final UsuarioEntity value = captor.getValue();

        Assertions.assertEquals("joao", value.getUsername());
    }

    @Test
    @DisplayName("Deve Lançar Exceção ao tentar excluir usuário")
    void deveLancarExcecaoAoTentarExcluirUsuario() {

        Mockito.when(repository.findByUsername(Mockito.anyString())).thenReturn(Optional.empty());

        try {
            adapter.delete("joao");
            Assertions.fail("Uma exceção deveria ser lançada aqui");
        } catch (final Exception e) {

            Assertions.assertTrue(e instanceof BusinessException);

            final BusinessException exception = (BusinessException) e;
            Assertions.assertEquals("Usuário com username joao não foi encontrado", exception.getMessage());
            Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        }
    }
}