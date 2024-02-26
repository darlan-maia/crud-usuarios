package darlan.maia.adapters.out.exception.handler;

import darlan.maia.adapters.out.exception.BusinessException;
import darlan.maia.adapters.out.exception.response.ApiErroResponse;
import darlan.maia.adapters.out.exception.response.ValidationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class HandlerOfExceptions {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErroResponse> handlerBusinessException(final BusinessException exception) {

        final List<ValidationResponse> validations = exception.getValidations().stream().map(
                valid -> ValidationResponse.builder()
                        .campo(valid[0])
                        .descricao(valid[1])
                        .build()
        ).toList();

        final ApiErroResponse response = ApiErroResponse.builder()
                .mensagem(exception.getMessage())
                .mensagemDetalhada(exception.getCause() != null ? exception.getCause().getMessage() : null)
                .httpStatus(exception.getHttpStatus())
                .validations(validations)
                .build();

        return ResponseEntity.status(exception.getHttpStatus()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErroResponse> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {

        final List<ValidationResponse> validations = exception
                .getFieldErrors()
                .stream()
                .map(
                        fieldError -> ValidationResponse.builder()
                                .campo(fieldError.getField())
                                .descricao(fieldError.getDefaultMessage())
                                .build()
                )
                .toList();

        final ApiErroResponse response = ApiErroResponse.builder()
                .mensagem("Há erros de validação em alguns campos")
                .mensagemDetalhada(exception.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .validations(validations)
                .build();

        return ResponseEntity.badRequest().body(response);
    }
}
