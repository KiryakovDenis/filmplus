package ru.jabka.filmplus.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.jabka.filmplus.model.ApiError;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequestException(BadRequestException e) {
        log.error("ExceptionController#BadRequestException", e);
        return ResponseEntity.badRequest()
                .body(new ApiError(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception e) {
        log.error("ExceptionController#Exception", e);
        return ResponseEntity.internalServerError()
                .body(new ApiError(e.getMessage()));
    }

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<ApiError> handleDataBaseException(DataBaseException e) {
        log.error("ExceptionController#DataBaseException", e);
        return ResponseEntity.badRequest()
                .body(new ApiError(e.getMessage()));
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<ApiError> handleNoDataFoundException(NoDataFoundException e) {
        log.error("ExceptionController#NoDataFoundException", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(e.getMessage()));
    }
}