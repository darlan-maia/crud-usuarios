package darlan.maia.adapters.out.exceptions.handler;

import darlan.maia.adapters.out.exceptions.BusinessException;
import darlan.maia.adapters.out.exceptions.response.ApiErroResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerOfExceptions {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErroResponse> handlerBusinessException(final BusinessException exception) {

        final ApiErroResponse response = ApiErroResponse.builder()
                .mensagem(exception.getMessage())
                .mensagemDetalhada(exception.getCause() != null ? exception.getCause().getMessage() : null)
                .httpStatus(exception.getHttpStatus())
                .build();

        return ResponseEntity.status(exception.getHttpStatus()).body(response);
    }
}
