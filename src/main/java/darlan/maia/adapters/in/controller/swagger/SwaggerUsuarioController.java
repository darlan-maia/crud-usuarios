package darlan.maia.adapters.in.controller.swagger;

import darlan.maia.adapters.in.controller.dto.UsuarioRequestDTO;
import darlan.maia.adapters.in.controller.dto.UsuarioResponseDTO;
import darlan.maia.adapters.in.controller.dto.UsuarioUpdateRequestDTO;
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

    @Operation(
            summary = "Atualiza usuário conforme informações passadas",
            description = "Atualiza campos de um usuário específico"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Username informado não cadastrado",
                    content = @Content(
                            schema = @Schema(implementation = ApiErroResponse.class),
                            examples = @ExampleObject(
                                    """
                                    {
                                        "http_status" : "BAD_REQUEST",
                                        "mensagem" : "Não existem usuários com o username informado",
                                        "mensagem_detalhada" : null,
                                        "validations" : []
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Campos informados no corpo da requisição são inválidos",
                    content = @Content(
                            schema = @Schema(implementation = ApiErroResponse.class),
                            examples = @ExampleObject(
                                    """
                                    {
                                        "http_status" : "BAD_REQUEST",
                                        "mensagem" : "Campos passados no corpo da requisição são inválidos",
                                        "mensagem_detalhada" : "<mensagem detalhada>",
                                        "validations" : [
                                            {
                                                "campo" : "password",
                                                "descricao" : "Este campo deve ter, no mínimo, oito caracteres"
                                            }
                                        ]
                                    }
                                    """
                            )
                    )
            )
    })
    ResponseEntity<UsuarioResponseDTO> update(
            @Parameter(in = ParameterIn.PATH) String username,
            UsuarioUpdateRequestDTO request
    );

    @Operation(
            summary = "Remove um usuário",
            description = "Exclui um usuário correspondente ao username informado"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Usuário removido com sucesso",
                    content = @Content(schema = @Schema())
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErroResponse.class))
            )
    })
    ResponseEntity<Void> delete(@Parameter(in = ParameterIn.PATH) String username);
}
