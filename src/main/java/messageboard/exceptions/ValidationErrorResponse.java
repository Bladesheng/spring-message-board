package messageboard.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class ValidationErrorResponse {
    private HttpStatus status;
    private Map<String, String> message;


    public ValidationErrorResponse(HttpStatus status, Map<String, String> message) {
        this.status = status;
        this.message = message;
    }


    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Map<String, String> getMessage() {
        return message;
    }

    public void setMessage(Map<String, String> message) {
        this.message = message;
    }
}
