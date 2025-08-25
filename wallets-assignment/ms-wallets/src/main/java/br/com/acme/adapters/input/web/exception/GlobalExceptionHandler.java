package br.com.acme.adapters.input.web.exception;

import br.com.acme.adapters.input.web.exception.response.ErrorResponse;
import br.com.acme.usecases.exception.BusinessException;
import br.com.acme.usecases.exception.WalletNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WalletNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleWalletNotFoundException(MethodArgumentNotValidException ex) {
        List<String> messages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        return ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error("Wallet not exists.")
                .code("VALIDATION_001")
                .messages(messages)
                .timestamp(LocalDateTime.now().withNano(0))
                .build();
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> messages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Validation Error")
                .code("VALIDATION_001")
                .messages(messages)
                .timestamp(LocalDateTime.now().withNano(0))
                .build();
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // 422
    public ErrorResponse handleBusinessException(BusinessException ex) {
        return ErrorResponse.builder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .error("Business Error")
                .code("BUSINESS_001")
                .messages(List.of(ex.getMessage()))
                .timestamp(LocalDateTime.now().withNano(0))
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneralException(Exception ex) {
        return ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .code("GENERIC_001")
                .messages(List.of(ex.getMessage()))
                .timestamp(LocalDateTime.now().withNano(0))
                .build();
    }
}
