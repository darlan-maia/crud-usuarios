package darlan.maia.exception.handler;

import darlan.maia.exception.BusinessException;
import darlan.maia.exception.response.ApiErroResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErroResponse> handleBusinessException(final BusinessException exception) {

        final ApiErroResponse response = ApiErroResponse.builder()
                .timestamp(LocalDateTime.now())
                .mensagem(exception.getMessage())
                .httpStatus(exception.getHttpStatus())
                .build();

        return ResponseEntity.status(exception.getHttpStatus()).body(response);
    }

}
