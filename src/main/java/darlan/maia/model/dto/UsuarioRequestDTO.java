package darlan.maia.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UsuarioRequestDTO {

    @NotBlank
    @Size(min = 3)
    @Schema(example = "joao123")
    private String username;

    @NotBlank
    @Size(min = 8)
    @Schema(example = "12345678")
    private String password;

    @NotBlank
    @Schema(example = "João")
    private String firstName;

    @NotBlank
    @Schema(example = "Silva")
    private String lastName;

    private List<TelefoneDTO> telefones;
}
