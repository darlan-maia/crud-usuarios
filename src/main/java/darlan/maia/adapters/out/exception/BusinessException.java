package darlan.maia.adapters.out.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BusinessException extends RuntimeException {

    private final HttpStatus httpStatus;

    private final List<String[]> validations = new ArrayList<>();

    @Builder
    public BusinessException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public boolean hasValidationErrors() {
        return !validations.isEmpty();
    }

    public Campo addCampo(final String nomeCampo) {
        return new Campo(nomeCampo, this);
    }

    @RequiredArgsConstructor
    public static class Campo {

        private final String nome;

        private final BusinessException exception;

        public BusinessException withDescription(final String descricao) {
            exception.validations.add(new String[] {nome, descricao});
            return exception;
        }
    }
}
