package main;

import main.model.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    /** Provides handling for exceptions throughout this service. */

    @ExceptionHandler({EntityNotFoundException.class, IllegalArgumentException.class})
    public final ResponseEntity<ApiError> handleException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        if (ex instanceof EntityNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            EntityNotFoundException unfe = (EntityNotFoundException) ex;
            return handleEntityNotFoundException(unfe, headers, status, request);
        } else if (ex instanceof IllegalArgumentException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            IllegalArgumentException cnae = (IllegalArgumentException) ex;
            return handleIllegalArgumentException(cnae, headers, status, request);
        } else {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(ex, null, headers, status, request);
        }
    }

    /** Customize the response for UserNotFoundException. */
    protected ResponseEntity<ApiError> handleEntityNotFoundException(EntityNotFoundException ex, HttpHeaders headers,
                                                                     HttpStatus status, WebRequest request) {
        String error = ex.getMessage();
        return handleExceptionInternal(ex, new ApiError(status, error), headers, status, request);
    }

    /** Customize the response for ContentNotAllowedException. */
    protected ResponseEntity<ApiError> handleIllegalArgumentException(IllegalArgumentException ex, HttpHeaders headers,
                                                                      HttpStatus status, WebRequest request) {
        String message = ex.getMessage();
        return handleExceptionInternal(ex, new ApiError(status, message), headers, status, request);
    }

    /** A single place to customize the response body of all Exception types. */
    protected ResponseEntity<ApiError> handleExceptionInternal(Exception ex, ApiError body, HttpHeaders headers,
                                                               HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(body, headers, status);
    }
}
