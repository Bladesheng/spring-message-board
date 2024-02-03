package messageboard.message;

import jakarta.validation.Valid;
import messageboard.exceptions.ValidationAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {
    private final MessageService messageService;
    private static final Logger logger = LoggerFactory.getLogger(ValidationAdvice.class);

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

        logger.info("Created new message: {}", savedMessage);

        return new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
    }

    @PatchMapping("/messages")
    ResponseEntity<Message> editMessage(@Valid @RequestBody MessageEditDto messageEditDto) {
        Message editedMessage = messageService.editMessage(messageEditDto);

        logger.info("Edited a message: {}", editedMessage);

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
