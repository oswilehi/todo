package oscar.project.todo.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity handleException(NoSuchElementException e) {
        ResponseEntity responseEntity = new ResponseEntity<>("Element not found", HttpStatus.BAD_REQUEST);
        log.error("Exception... {}", (Object) e.getStackTrace());
        return responseEntity;
    }
}
