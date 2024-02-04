package messageboard;

import messageboard.exceptions.EmptyMessageEditException;
import messageboard.exceptions.NotFoundException;
import messageboard.message.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// https://github.com/Pio-Trek/Spring-Rest-API-Unit-Test/blob/master/src/test/java/com/sundaydevblog/springrestapitest/service/MemberServiceTest.java
// https://github.com/imtiaz-rahi/junit5-mockito-example/blob/master/src/test/java/com/github/imtiazrahi/junit5mock/AddressServiceTest.java
// https://github.com/springframeworkguru/testing-junit5-mockito/blob/master/src/test/java/guru/springframework/sfgpetclinic/services/map/OwnerMapServiceTest.java

@SpringBootTest
public class MessageServiceTests {

    @MockBean
    private MessageRepository messageRepository;

    @Autowired
    private MessageService messageService;


    private Message m1;
    private MessageDto m1Dto;
    final private Long id = 69L;


    @BeforeEach
    public void setUp() {
        this.m1 = new Message("text", "a author", ZonedDateTime.now());
        this.m1Dto = new MessageDto("a author", "text");
    }


    @Test
    void shouldGetAllMessages() {
        when(messageRepository.findAll()).thenReturn(List.of(m1));

        List<Message> messages = messageService.getAllMessage();

        assertThat(messages).hasSize(1);
    }


    @Test
    void shouldCreateMessage() {
        when(messageRepository.save(any(Message.class))).thenReturn(m1);

        Message result = messageService.createMessage(m1Dto);


        assertThat(result).isEqualTo(m1);
        verify(messageRepository, times(1)).save(any(Message.class));
    }


    @Test
    void shouldRemoveMessage() {
        messageService.deleteMessage(id);

        verify(messageRepository, times(1)).deleteById(id);
    }


    @Test
    void shouldEditMessage() {
        MessageEditDto messageEditDto = new MessageEditDto(id.toString(), "Updated Author", "Updated Text");

        when(messageRepository.findById(id)).thenReturn(Optional.of(m1));

        Message result = messageService.editMessage(messageEditDto);

        assertThat(result.getAuthor()).isEqualTo("Updated Author");
        assertThat(result.getText()).isEqualTo("Updated Text");
        assertThat(result.getUpdated()).isNotNull();

        verify(messageRepository, times(1)).save(any(Message.class));
    }


    @Test
    void shouldThrowWithEmptyEdit() {
        MessageEditDto messageEditDto = new MessageEditDto(String.valueOf(id), null, null);

        when(messageRepository.findById(id)).thenReturn(Optional.of(m1));

        EmptyMessageEditException exception = assertThrows(EmptyMessageEditException.class,
                () -> messageService.editMessage(messageEditDto)
        );

        assertThat(exception.getMessage()).isEqualTo("no text or author was provided");
    }


    @Test
    void shouldThrowWithNotFoundMessageId() {
        MessageEditDto messageEditDto = new MessageEditDto(String.valueOf(id), "a author", "text");

        when(messageRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> messageService.editMessage(messageEditDto)
        );

        assertThat(exception.getMessage()).isEqualTo("message not found. id: " + id);
    }

}
