package messageboard.message;

import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private ZonedDateTime created;

    @Column(nullable = false)
    private ZonedDateTime updated;

    @Column(nullable = false)
    private String author;

    public Message() {
    }

    public Message(String text, ZonedDateTime created, String author) {
        this.text = text;
        this.created = created;
        this.updated = created;
        this.author = author;
    }

    @Override
    public String toString() {
        return "Message{id=%d, text=%s, author=%s, created=%s, updated=%s"
                .formatted(this.id, this.text, this.author, this.created, this.updated);
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public ZonedDateTime getUpdated() {
        return updated;
    }

    public String getAuthor() {
        return author;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
