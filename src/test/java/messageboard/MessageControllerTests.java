package messageboard;

import com.fasterxml.jackson.databind.ObjectMapper;
import messageboard.exceptions.EmptyMessageEditException;
import messageboard.exceptions.NotFoundException;
import messageboard.message.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// https://github.com/bezkoder/spring-boot-unit-test-rest-controller/blob/master/src/test/java/com/bezkoder/spring/test/TutorialControllerTests.java
// https://github.com/khoubyari/spring-boot-rest-example/blob/master/src/test/java/com/khoubyari/example/test/HotelControllerTest.java
// https://github.com/Pio-Trek/Spring-Rest-API-Unit-Test/blob/master/src/test/java/com/sundaydevblog/springrestapitest/controller/MemberControllerTest.java

@WebMvcTest(MessageController.class)
public class MessageControllerTests {

    @MockBean
    private MessageService messageService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void shouldFetchAllMessages() throws Exception {
        Message message = new Message("text", "a author", ZonedDateTime.now());

        when(messageService.getAllMessage()).thenReturn(List.of(message));

        mockMvc.perform(get("/messages")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$", hasSize(1)))
                .andDo(print());

    }


    @Test
    void shouldCreateMessage() throws Exception {
        MessageDto messageDto = new MessageDto("a author", "text");

        Message message = new Message("text", "a author", ZonedDateTime.now());
        when(messageService.createMessage(any(MessageDto.class))).thenReturn(message);

        mockMvc.perform(post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(messageDto))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.author").value("a author"))
                .andDo(print());

    }


    @Test
    void shouldValidateMissingAuthor() throws Exception {
        MessageDto messageDto = new MessageDto();
        messageDto.setText("text");

        mockMvc.perform(post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(messageDto))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message.author").value("must not be empty"))
                .andDo(print());

    }


    @Test
    void shouldValidateMissingText() throws Exception {
        MessageDto messageDto = new MessageDto();
        messageDto.setAuthor("a author");

        mockMvc.perform(post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(messageDto))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message.text").value("must not be empty"))
                .andDo(print());

    }


    @Test
    void shouldEditMessage() throws Exception {
        MessageEditDto messageEditDto = new MessageEditDto("1", "AUTHOR", "TEXT");

        Message message = new Message("TEXT", "AUTHOR", ZonedDateTime.now());
        when(messageService.editMessage(any(MessageEditDto.class))).thenReturn(message);

        mockMvc.perform(patch("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(messageEditDto))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value("AUTHOR"))
                .andExpect(jsonPath("$.text").value("TEXT"))
                .andDo(print());

    }


    @Test
    void shouldValidateMissingAuthorAndText() throws Exception {
        MessageEditDto messageEditDto = new MessageEditDto();
        messageEditDto.setId("1");

        when(messageService.editMessage(any(MessageEditDto.class))).thenThrow(
                new EmptyMessageEditException("no text or author was provided")
        );

        mockMvc.perform(patch("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(messageEditDto))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andDo(print());

    }


    @Test
    void shouldValidateExistingId() throws Exception {
        MessageEditDto messageEditDto = new MessageEditDto("69", "a author", "text");

        when(messageService.editMessage(any(MessageEditDto.class))).thenThrow(
                new NotFoundException("message not found. id: 69")
        );

        mockMvc.perform(patch("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(messageEditDto))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("message not found. id: 69"))
                .andDo(print());

    }


    @Test
    void shouldRemoveMessage() throws Exception {
        mockMvc.perform(delete("/messages/1"))
                .andExpect(status().isNoContent())
                .andDo(print());

    }
}
