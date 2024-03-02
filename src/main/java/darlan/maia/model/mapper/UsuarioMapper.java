package darlan.maia.model.mapper;

import darlan.maia.model.dto.TelefoneDTO;
import darlan.maia.model.dto.TipoTelefone;
import darlan.maia.model.dto.UsuarioResponseDTO;
import darlan.maia.model.entity.Usuario;

public class UsuarioMapper {

    private UsuarioMapper() { }

    private static TelefoneDTO toDTO(final String telefone) {

        if (telefone == null) return null;

        final String[] split = telefone.split("#");

        return TelefoneDTO.builder()
                .numero(split[0])
                .tipo(TipoTelefone.valueOf(split[1])).build();
    }

    public static UsuarioResponseDTO toDTO(final Usuario usuario) {

        if (usuario == null) return null;

        return UsuarioResponseDTO.builder()
                .username(usuario.getUsername())
                .fullName("%s %s".formatted(usuario.getFirstName(), usuario.getLastName()))
                .email(usuario.getEmail())
                .telefones(usuario.getTelefones().stream().map(UsuarioMapper::toDTO).toList())
                .build();
    }
}
