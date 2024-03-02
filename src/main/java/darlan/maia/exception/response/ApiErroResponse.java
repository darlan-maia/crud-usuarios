package darlan.maia.exception.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiErroResponse {
    private LocalDateTime timestamp;
    private HttpStatus httpStatus;
    private String mensagem;
    private List<Validation> validations;
}
