package darlan.maia.adapters.out.persistence.repository;

import darlan.maia.adapters.out.persistence.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
}
