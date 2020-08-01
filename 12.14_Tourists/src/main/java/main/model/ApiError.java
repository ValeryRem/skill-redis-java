package main.model;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ApiError {

    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ApiError() {
    }

    public ApiError(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
//        errors = Collections.singletonList(error);
    }

    public ApiError(List<String> errors) {
        this.errors = errors;
    }
}
