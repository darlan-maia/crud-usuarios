package darlan.maia.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private List<Telefone> telefones;
}
