package darlan.maia.adapters.in.controller;

import darlan.maia.adapters.out.exception.BusinessException;
import darlan.maia.adapters.out.exception.handler.HandlerOfExceptions;
import darlan.maia.domain.model.Telefone;
import darlan.maia.domain.model.TipoTelefone;
import darlan.maia.domain.model.Usuario;
import darlan.maia.domain.port.in.UsuarioInputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

class UsuarioControllerTest {

    @Mock
    private UsuarioInputPort service;

    @InjectMocks
    private UsuarioController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new HandlerOfExceptions()).build();
    }

    @Test
    @DisplayName("Sucesso - GET/api/v1/{username}")
    void test_deveBuscarUsuarioComSucesso() throws Exception {

        final Usuario dto = Usuario.builder()
                .username("joao")
                .email("joao@email.com")
                .firstName("João")
                .lastName("Silva")
                .telefones(List.of(new Telefone("(71)999999999", TipoTelefone.CELULAR)))
                .build();

        Mockito.when(service.findByUsername(Mockito.anyString())).thenReturn(dto);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/joao"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("joao"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.full_name").value("João Silva"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("joao@email.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.telefones").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.telefones[0].numero").value("(71)999999999"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.telefones[0].tipo").value("CELULAR"));

    }

    @Test
    @DisplayName("Fracasso - GET/api/v1/{username}")
    void deveRetornarDTOdeErroAoBuscaPorUsername() throws Exception {

        final BusinessException exception = BusinessException.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message("Não foi possível encontrar usuário com username joao")
                .build();

        Mockito.when(service.findByUsername(Mockito.anyString())).thenThrow(exception);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/joao"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.http_status").value("NOT_FOUND"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem").value("Não foi possível encontrar usuário com username joao"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem_detalhada").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.validations").isEmpty());
    }

    @Test
    @DisplayName("Sucesso - POST/api/v1")
    void deveSalvarUsuarioComSucesso() throws Exception {

        final String requestBody = """
                {
                    "username" : "joao123",
                    "password" : "12345678",
                    "first_name" : "João",
                    "last_name" : "Silva",
                    "email" : "joao@email.com",
                    "telefones" : [
                        {
                            "numero" : "71999999999",
                            "tipo" : "CELULAR"
                        }
                    ]
                }
                """;

        final Usuario usuario = Usuario.builder()
                .username("joao123")
                .password("12345678")
                .firstName("João")
                .lastName("Silva")
                .email("joao@email.com")
                .telefones(List.of(Telefone.builder().numero("71999999999").tipo(TipoTelefone.CELULAR).build()))
                .build();

        Mockito.when(service.save(Mockito.any(Usuario.class))).thenReturn(usuario);

        final MockHttpServletRequestBuilder toBePerformed = MockMvcRequestBuilders
                .post("/api/v1")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(toBePerformed)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("joao123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.full_name").value("João Silva"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("joao@email.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.telefones").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.telefones[0].numero").value("71999999999"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.telefones[0].tipo").value("CELULAR"));
    }

    @Test
    @DisplayName("Fracasso - Erro de Validação")
    void deveExibirErroDeValidacao() throws Exception {

        final String requestBody = """
                {
                    "password" : "123456",
                    "first_name" : "João",
                    "last_name" : "Silva",
                    "email" : "joao@email.com",
                    "telefones" : [
                        {
                            "numero" : "71999999999",
                            "tipo" : "CELULAR"
                        }
                    ]
                }
                """;

        final MockHttpServletRequestBuilder toBePerformed = MockMvcRequestBuilders
                .post("/api/v1")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(toBePerformed)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.http_status").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem").value("Há erros de validação em alguns campos"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validations").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.validations[0].campo").value("username"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validations[0].descricao").value("Campo obrigatório"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validations[1].campo").value("password"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validations[1].descricao").value("Campo deve conter, no mínimo, oito caracteres"));
    }

    @Test
    @DisplayName("Sucesso - PUT/api/v1/{username}")
    void deveAtualizarUsuarioComSucesso() throws Exception {

        final String requestBody = """
                {
                    "password" : "nova_senha",
                    "first_name" : "João",
                    "last_name" : "Silva",
                    "email" : "joao_silva@email.com",
                    "telefones" : []
                }
                """;

        final Usuario updated = Usuario.builder()
                .id(1L)
                .username("joao123")
                .password("encoded")
                .firstName("João")
                .lastName("Silva")
                .email("joao_silva@email.com")
                .telefones(new ArrayList<>())
                .build();

        final MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/api/v1/joao123")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON);

        Mockito.when(service.update(Mockito.anyString(), Mockito.any())).thenReturn(updated);

        mockMvc
                .perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("joao123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.full_name").value("João Silva"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("joao_silva@email.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.telefone").doesNotExist());
    }

    @Test
    @DisplayName("Falha na validação de campos")
    void deveFalharAoPassarCamposInvalidos() throws Exception {

        final String requestBody = """
                {
                    "password" : "nova_senha",
                    "first_name" : "João",
                    "last_name" : "Silva",
                    "email" : "joao_silva",
                    "telefones" : []
                }
                """;

        final MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/api/v1/joao123")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc
                .perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.http_status").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem").value("Há erros de validação em alguns campos"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validations").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.validations[0].campo").value("email"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validations[0].descricao").value("E-mail inválido"));
    }

    @Test
    @DisplayName("Deve excluir usuário com sucesso")
    void deveExcluirUsuarioComSucesso() throws Exception {

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/joao");

        mockMvc
                .perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
    }

}