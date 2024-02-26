package darlan.maia.adapters.in.controller.mapper;

import darlan.maia.adapters.in.controller.dto.TelefoneDTO;
import darlan.maia.adapters.in.controller.dto.UsuarioRequestDTO;
import darlan.maia.adapters.in.controller.dto.UsuarioResponseDTO;
import darlan.maia.domain.model.Telefone;
import darlan.maia.domain.model.Usuario;

public final class UsuarioControllerMapper {

    private UsuarioControllerMapper() { }

    private static TelefoneDTO toDTO(Telefone telefone) {

        if (telefone == null) return null;

        return TelefoneDTO.builder()
                .numero(telefone.getNumero())
                .tipo(telefone.getTipo())
                .build();
    }

    public static UsuarioResponseDTO toDTO(Usuario usuario) {

        if (usuario == null) return null;

        return UsuarioResponseDTO.builder()
                .username(usuario.getUsername())
                .fullName("%s %s".formatted(usuario.getFirstName(), usuario.getLastName()))
                .email(usuario.getEmail())
                .telefones(usuario.getTelefones().stream().map(UsuarioControllerMapper::toDTO).toList())
                .build();
    }

    public static Telefone toDomain(TelefoneDTO telefone) {

        if (telefone == null) return null;

        return Telefone.builder()
                .numero(telefone.getNumero())
                .tipo(telefone.getTipo())
                .build();
    }

    public static Usuario toDomain(UsuarioRequestDTO request) {

        if (request == null) return null;

        return Usuario.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .telefones(request.getTelefones().stream().map(UsuarioControllerMapper::toDomain).toList())
                .build();
    }
}
