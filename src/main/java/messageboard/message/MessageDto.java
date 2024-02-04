package messageboard.message;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class MessageDto {
    @NotEmpty
    @Size(min = 2, message = "author should have at least 2 characters")
    private String author;

    @NotEmpty
    @Size(min = 2, message = "text should have at least 2 characters")
    private String text;

    public MessageDto() {
    }

    public MessageDto(String author, String text) {
        this.author = author;
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
