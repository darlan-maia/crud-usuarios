package darlan.maia.adapters.in.controller.swagger;

import darlan.maia.adapters.in.controller.dto.UsuarioResponseDTO;
import darlan.maia.adapters.out.exceptions.response.ApiErroResponse;
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
}
