package messageboard.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MessageControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiExceptionResponse> handleNotFoundException(NotFoundException e) {

        ApiExceptionResponse response = new ApiExceptionResponse(HttpStatus.NOT_FOUND, e.getMessage());

        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }

    @ExceptionHandler(EmptyMessageEditException.class)
    public ResponseEntity<ApiExceptionResponse> handleEmptyMessageEditException(EmptyMessageEditException e) {

        ApiExceptionResponse response = new ApiExceptionResponse(HttpStatus.NOT_FOUND, e.getMessage());

        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }
}
