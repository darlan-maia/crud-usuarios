package darlan.maia.config;

import darlan.maia.domain.port.out.UsuarioOutputPort;
import darlan.maia.domain.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MvcConfig {

    @Bean
    public UsuarioService usuarioService(UsuarioOutputPort usuarioOutputPort) {
        return new UsuarioService(usuarioOutputPort);
    }

}
