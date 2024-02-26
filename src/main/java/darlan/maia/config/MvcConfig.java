package darlan.maia.config;

import darlan.maia.domain.port.out.PasswordEncoderOutputPort;
import darlan.maia.domain.port.out.UsuarioOutputPort;
import darlan.maia.domain.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class MvcConfig {

    @Bean
    public UsuarioService usuarioService(
            final UsuarioOutputPort usuarioOutputPort,
            final PasswordEncoderOutputPort encoder) {
        return new UsuarioService(usuarioOutputPort, encoder);
    }

    @Bean(name = "bcrypt")
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
