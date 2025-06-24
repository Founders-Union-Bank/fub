package org.fub;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;

public interface TestAPI {

    @Operation(description = "test setup check",summary = "Test method",tags = {"Test"})
    @ApiResponse(responseCode = "200",description = "Server is up and running",content = @Content(schema = @Schema))
    @GetMapping("/test")
    String setup();
}
