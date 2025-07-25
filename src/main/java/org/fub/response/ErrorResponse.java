package org.fub.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter
@Builder
public class ErrorResponse {

    private HttpStatus statusCode;

    private String message;

    private Date timeStamp;
}
