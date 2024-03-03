package darlan.maia.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TelefoneDTO {

    @NotBlank
    @Schema(example = "71999999999")
    private String numero;

    @NotBlank
    @Schema(example = "CELULAR")
    private TipoTelefone tipo;

    @Override
    public String toString() {
        return "%s#%s".formatted(numero, tipo);
    }
}
