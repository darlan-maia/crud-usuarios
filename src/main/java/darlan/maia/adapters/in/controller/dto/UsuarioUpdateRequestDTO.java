package darlan.maia.adapters.in.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Email;
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
public class UsuarioUpdateRequestDTO {

    @NotBlank(message = "Campo obrigatório")
    @Size(min = 8, message = "Campo deve conter, no mínimo, oito caracteres")
    private String password;

    @NotBlank(message = "Campo obrigatório")
    private String firstName;

    @NotBlank(message = "Campo obrigatório")
    private String lastName;

    @Email(message = "E-mail inválido")
    @NotBlank(message = "Campo obrigatório")
    private String email;

    private List<TelefoneDTO> telefones;
}
