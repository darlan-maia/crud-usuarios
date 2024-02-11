package darlan.maia.adapters.in.controller.dto;

import darlan.maia.domain.model.TipoTelefone;
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

    @Schema(example = "(71)999999999")
    private String numero;

    @Schema(example = "CELULAR")
    private TipoTelefone tipo;
}
