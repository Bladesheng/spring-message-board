package messageboard.message;

import jakarta.validation.constraints.*;

public class MessageEditDto {
    @NotNull
    @Min(value = 0L, message = "id must be positive number")
    private String id;

    @Size(min = 2, message = "author should have at least 2 characters")
    private String author;

    @Size(min = 2, message = "text should have at least 2 characters")
    private String text;

    public MessageEditDto() {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
