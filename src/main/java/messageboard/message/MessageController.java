package messageboard.message;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/messages")
    ResponseEntity<List<Message>> all() {
        List<Message> messages = messageService.getAllMessage();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @PostMapping("/messages")
    ResponseEntity<Message> createMessage(@Valid @RequestBody MessageDto messageDto) {
        Message savedMessage = messageService.createMessage(messageDto);
        return new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
    }

    @PatchMapping("/messages")
    ResponseEntity<Message> editMessage(@Valid @RequestBody MessageEditDto messageEditDto) {
        Message editedMessage = messageService.editMessage(messageEditDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(editedMessage);
    }

    @DeleteMapping("/messages/{id}")
    ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
