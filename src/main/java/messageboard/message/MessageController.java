package messageboard.message;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


// https://petstore.swagger.io/
// https://github.com/swagger-api/swagger-petstore/blob/master/src/main/resources/openapi.yaml
// https://www.baeldung.com/spring-rest-openapi-documentation


public interface MessageController {


    @Operation(summary = "Get all message")
    ResponseEntity<List<Message>> all();


    @Operation(summary = "Get a message by it's id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the message"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid id supplied",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Message not found",
                    content = @Content()
            ),
    })
    @Parameters(value = {
            @Parameter(
                    name = "id",
                    description = "id of message to return",
                    example = "69"

            )
    })
    ResponseEntity<Message> getOne(@PathVariable Long id);


    @Operation(summary = "Add new message to the board")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Created the message"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input",
                    content = @Content()
            ),
    })
    ResponseEntity<Message> createMessage(@Valid @RequestBody MessageDto messageDto);


    @Operation(summary = "Edit existing message")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Edited the message"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid body supplied",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Message not found",
                    content = @Content()
            ),
    })
    ResponseEntity<Message> editMessage(@Valid @RequestBody MessageEditDto messageEditDto);


    @Operation(summary = "Delete a pet")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Deleted the message"
            ),
    })
    @Parameters(value = {
            @Parameter(
                    name = "id",
                    description = "id of message to delete",
                    example = "69"
            )
    })
    ResponseEntity<Void> deleteMessage(@PathVariable Long id);


}
