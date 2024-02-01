package messageboard.message;

import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

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
}
