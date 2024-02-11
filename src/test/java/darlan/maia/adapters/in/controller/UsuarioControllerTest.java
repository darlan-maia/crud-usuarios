package darlan.maia.adapters.in.controller;

import darlan.maia.adapters.out.exceptions.BusinessException;
import darlan.maia.adapters.out.exceptions.handler.HandlerOfExceptions;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.http_status").value("NOT_FOUND"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem").value("Não foi possível encontrar usuário com username joao"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem_detalhada").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.validations").isEmpty());
    }
}