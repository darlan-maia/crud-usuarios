package darlan.maia.adapters.in.controller.swagger;

import darlan.maia.adapters.in.controller.dto.UsuarioRequestDTO;
import darlan.maia.adapters.in.controller.dto.UsuarioResponseDTO;
import darlan.maia.adapters.out.exception.response.ApiErroResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface SwaggerUsuarioController {

    @Operation(
            summary = "Busca um usuário por username",
            description = "Obtém todas as informações de um usuário que corresponde ao username especificado"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Busca realizada com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Falha ao buscar usuário",
                    content = @Content(
                            schema = @Schema(implementation = ApiErroResponse.class),
                            examples = @ExampleObject(
                                    """
                                    {
                                        "http_status" : "NOT_FOUND",
                                        "mensagem" : "Não foi possível encontrar usuário cujo username é joao123",
                                        "mensagem_detalhada" : null,
                                        "validations" : null
                                    }
                                    """
                            )
                    )
            )

    })
    ResponseEntity<UsuarioResponseDTO> findByUsername(@Parameter(in = ParameterIn.PATH) String username);

    @Operation(
            summary = "Persiste um usuário no banco de dados",
            description = "Salva um novo usuário."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Busca realizada com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados do corpo de requisição inconsistentes",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = ApiErroResponse.class),
                                    examples = @ExampleObject(
                                            """
                                            {
                                                "http_status" : "BAD_REQUEST",
                                                "mensagem" : "Dados do corpo da requisição são inválidos",
                                                "mensagem_detalhada" : "<detalhes>",
                                                "validations" : [
                                                    {
                                                        "campo" : "username",
                                                        "mensagem" : "O campo deve conter, no mínimo, três caracteres"
                                                    }
                                                ]
                                            }
                                            """
                                    )
                            )
                    }
            )}
    )
    ResponseEntity<UsuarioResponseDTO> save(UsuarioRequestDTO request);
}
