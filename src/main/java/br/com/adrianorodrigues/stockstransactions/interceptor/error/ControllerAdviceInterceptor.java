package br.com.adrianorodrigues.stockstransactions.interceptor.error;

import br.com.adrianorodrigues.stockstransactions.exceptions.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdviceInterceptor
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    protected ResponseEntity<ErrorDto> handleExceptions(
            BaseException ex, WebRequest request) {
        return new ResponseEntity<>(ex.toErrorDto(), ex.getStatusCode());
    }
}