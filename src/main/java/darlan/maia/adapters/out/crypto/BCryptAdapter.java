package darlan.maia.adapters.out.crypto;

import darlan.maia.domain.port.out.PasswordEncoderOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BCryptAdapter implements PasswordEncoderOutputPort {

    private final PasswordEncoder encoder;

    public String encode(final String password) {
        return encoder.encode(password);
    }
}
