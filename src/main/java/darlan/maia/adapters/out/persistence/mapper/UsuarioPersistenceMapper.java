package darlan.maia.adapters.out.persistence.mapper;

import darlan.maia.adapters.out.persistence.entity.UsuarioEntity;
import darlan.maia.domain.model.Telefone;
import darlan.maia.domain.model.TipoTelefone;
import darlan.maia.domain.model.Usuario;

import java.util.List;

public final class UsuarioPersistenceMapper {

    private UsuarioPersistenceMapper() { }

    public static Usuario toDomain(UsuarioEntity entity) {

        if (entity == null) return null;

        final List<Telefone> telefones =
                entity.getTelefones().stream()
                .map(string -> {
                    final String[] split = string.split("#");
                    return Telefone.builder().numero(split[0]).tipo(TipoTelefone.valueOf(split[1])).build();
                })
                .toList();

        return Usuario.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .telefones(telefones)
                .build();
    }
}
