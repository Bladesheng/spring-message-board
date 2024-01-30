package messageboard.message;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MessageController {
    private final MessageRepository repository;

    public MessageController(MessageRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/messages")
    ResponseEntity<List<Message>> all() {
        List<Message> messages = repository.findAll();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @PostMapping("/messages")
    ResponseEntity<Message> createMessage(@Valid @RequestBody MessageDto messageDto) {
        ZonedDateTime now = ZonedDateTime.now();

        Message message = new Message();
        message.setAuthor(messageDto.getAuthor());
        message.setText(messageDto.getText());
        message.setCreated(now);
        message.setUpdated(now);

        Message savedMessage = repository.save(message);

        return new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
    }

    @DeleteMapping("/messages/{id}")
    ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
