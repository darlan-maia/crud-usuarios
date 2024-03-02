package darlan.maia.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TelefoneDTO {

    @Schema(example = "71999999999")
    private String numero;

    @Schema(example = "CELULAR")
    private TipoTelefone tipo;
}
