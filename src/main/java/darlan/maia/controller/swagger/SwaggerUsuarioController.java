package darlan.maia.controller.swagger;

import darlan.maia.exception.response.ApiErroResponse;
import darlan.maia.model.dto.UsuarioResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Controlador de Usuários", description = "Contém todos os endpoints relacionados aos usuários")
public interface SwaggerUsuarioController {

    @Operation(
            summary = "Busca um usuário por username",
            description = """
                    Dado um username, faz consulta no banco, retorna um usuário caso ele exista,
                    ou exibe mensagem de erro.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(
                            schema = @Schema(implementation = ApiErroResponse.class),
                            examples = @ExampleObject(
                                    """
                                    {
                                        "timestamp" : "2024-03-01 19:04:00.000",
                                        "http_status" : "NOT_FOUND",
                                        "mensagem" : "Usuário com username joao123 não foi encontrado",
                                        "validations" : []
                                    }
                                    """
                            )
                    )
            )
    })
    ResponseEntity<UsuarioResponseDTO> buscarPorUsername(String username);
}
