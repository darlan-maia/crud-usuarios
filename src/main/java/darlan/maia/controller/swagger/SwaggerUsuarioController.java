package darlan.maia.controller.swagger;

import darlan.maia.exception.response.ApiErroResponse;
import darlan.maia.model.dto.UsuarioRequestDTO;
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
                    description = "Busca realizada com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário com username informado não foi encontrado",
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

    @Operation(
            summary = "Salva um usuário no banco de dados",
            description = "Salva um usuário no banco de dados conforme informações especificados no corpo da requisição"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Novo usuário foi salvo com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Quando já existe um usuário com o username informado",
                    content = @Content(
                            schema = @Schema(implementation = ApiErroResponse.class),
                            examples = @ExampleObject(
                                    """
                                            {
                                                "timestamp" : "2024-03-02 20:14:00.000",
                                                "http_status" : "BAD_REQUEST",
                                                "mensagem" : "Novo usuário não pôde ser cadastrado"
                                                "validations" : [
                                                    {
                                                        "campo" : "username",
                                                        "descricao" : "Já existe um usuário com o username informado"
                                                    }
                                                ]
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Dados inseridos no corpo da requisição são inválidos",
                    content = @Content(
                            schema = @Schema(implementation = ApiErroResponse.class),
                            examples = @ExampleObject(
                                    """
                                    {
                                        "timestamp" : "2024-03-02 20:14:00.000",
                                        "http_status" : "BAD_REQUEST",
                                        "mensagem" : "Valor de algum campo do corpo da requisição é inválido"
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
    ResponseEntity<UsuarioResponseDTO> salvar(UsuarioRequestDTO response);
}
