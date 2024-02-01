package messageboard.message;

import messageboard.exceptions.EmptyMessageEditException;
import messageboard.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getAllMessage() {
        return messageRepository.findAll();
    }

    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }

    public Message createMessage(MessageDto messageDto) {
        ZonedDateTime now = ZonedDateTime.now();

        Message message = new Message(
                messageDto.getAuthor(),
                messageDto.getText(),
                now
        );

        return messageRepository.save(message);
    }

    public Message editMessage(MessageEditDto messageEditDto) {
        Long id = Long.parseLong(messageEditDto.getId());
        String newAuthor = messageEditDto.getAuthor();
        String newText = messageEditDto.getText();

        if (newAuthor == null && newText == null) {
            throw new EmptyMessageEditException("no text or author was provided");
        }

        Optional<Message> messageOptional = messageRepository.findById(id);

        if (messageOptional.isEmpty()) {
            throw new NotFoundException("message not found. id: " + id);
        }

        Message message = messageOptional.get();

        if (newAuthor != null) {
            message.setAuthor(newAuthor);
        }

        if (newText != null) {
            message.setText(newText);
        }

        message.setUpdated(ZonedDateTime.now());

        messageRepository.save(message);

        return message;
    }
}
