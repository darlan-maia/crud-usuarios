package darlan.maia.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {

    @Schema(example = "joao123")
    private String username;

    @Schema(example = "João Silva")
    private String fullName;

    @Schema(example = "joao@email.com")
    private String email;


    private List<TelefoneDTO> telefones;
}
