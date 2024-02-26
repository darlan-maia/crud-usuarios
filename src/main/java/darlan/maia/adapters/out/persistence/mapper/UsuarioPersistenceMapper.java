package darlan.maia.adapters.out.persistence.mapper;

import darlan.maia.adapters.out.persistence.entity.UsuarioEntity;
import darlan.maia.domain.model.Telefone;
import darlan.maia.domain.model.TipoTelefone;
import darlan.maia.domain.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public final class UsuarioPersistenceMapper {

    private UsuarioPersistenceMapper() { }

    private static List<Telefone> loadTelefones(UsuarioEntity entity) {
        if (entity.getTelefones() == null) return new ArrayList<>();
        return entity.getTelefones().stream().map(
                string -> {
                    final String[] split = string.split("#");
                    return Telefone.builder().numero(split[0]).tipo(TipoTelefone.valueOf(split[1])).build();
                }
        ).toList();
    }

    private static List<String> empacotarTelefones(Usuario usuario) {
        if (usuario.getTelefones() == null) return new ArrayList<>();
        return usuario.getTelefones().stream().map(telefone -> telefone.toString()).toList();
    }

    public static Usuario toDomain(final UsuarioEntity entity) {

        if (entity == null) return null;

        final List<Telefone> telefones = loadTelefones(entity);

        return Usuario.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .telefones(telefones)
                .build();
    }

    public static UsuarioEntity toEntity(final Usuario usuario) {

        if (usuario == null) return null;

        final List<String> telefones = empacotarTelefones(usuario);

        return UsuarioEntity.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .firstName(usuario.getFirstName())
                .lastName(usuario.getLastName())
                .email(usuario.getEmail())
                .telefones(telefones)
                .build();
    }
}
