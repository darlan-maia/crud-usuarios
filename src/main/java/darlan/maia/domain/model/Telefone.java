package darlan.maia.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Telefone {
    private String numero;
    private TipoTelefone tipo;

    @Override
    public String toString() {
        return "%s#%s".formatted(numero, tipo.name());
    }
}
