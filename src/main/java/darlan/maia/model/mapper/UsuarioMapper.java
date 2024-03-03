package darlan.maia.model.mapper;

import darlan.maia.model.dto.TelefoneDTO;
import darlan.maia.model.dto.TipoTelefone;
import darlan.maia.model.dto.UsuarioRequestDTO;
import darlan.maia.model.dto.UsuarioResponseDTO;
import darlan.maia.model.entity.Usuario;

import java.util.ArrayList;
import java.util.List;

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

        final List<TelefoneDTO> telefones = (usuario.getTelefones() == null)
                ? new ArrayList<>()
                : usuario.getTelefones().stream().map(UsuarioMapper::toDTO).toList();

        return UsuarioResponseDTO.builder()
                .username(usuario.getUsername())
                .fullName("%s %s".formatted(usuario.getFirstName(), usuario.getLastName()))
                .email(usuario.getEmail())
                .telefones(telefones)
                .build();
    }

    public static Usuario toEntity(final UsuarioRequestDTO request) {

        if (request == null) return null;

        final List<String> telefones = (request.getTelefones() == null)
                ? new ArrayList<>()
                : request.getTelefones().stream().map(TelefoneDTO::toString).toList();

        return Usuario.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .telefones(telefones)
                .build();
    }
}
