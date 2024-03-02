package darlan.maia.controller;

import darlan.maia.exception.BusinessException;
import darlan.maia.exception.handler.GlobalExceptionHandler;
import darlan.maia.model.dto.TelefoneDTO;
import darlan.maia.model.dto.TipoTelefone;
import darlan.maia.model.dto.UsuarioResponseDTO;
import darlan.maia.service.UsuarioService;
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
    private UsuarioService service;

    @InjectMocks
    private UsuarioController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    void deveBuscaUsuarioComSucesso() throws Exception {

        final TelefoneDTO telefone = TelefoneDTO.builder()
                .numero("71999999999")
                .tipo(TipoTelefone.CELULAR).build();

        final UsuarioResponseDTO response = UsuarioResponseDTO.builder()
                .username("joao")
                .fullName("João Silva")
                .email("joao@email.com")
                .telefones(List.of(telefone)).build();

        Mockito.when(service.buscaPorUsername(Mockito.anyString())).thenReturn(response);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/joao"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("joao"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.full_name").value("João Silva"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("joao@email.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.telefones").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.telefones[0].numero").value("71999999999"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.telefones[0].tipo").value("CELULAR"));
    }

    @Test
    @DisplayName("Deve falhar ao tentar buscar usuário")
    void deveFalharAoTentarBuscarUsuario() throws Exception {

        final BusinessException exception = BusinessException.builder()
                .message("Usuário com username joao não foi encontrado")
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();

        Mockito.when(service.buscaPorUsername(Mockito.anyString())).thenThrow(exception);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/joao"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.http_status").value("NOT_FOUND"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem").value("Usuário com username joao não foi encontrado"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validations").isArray());
    }

}