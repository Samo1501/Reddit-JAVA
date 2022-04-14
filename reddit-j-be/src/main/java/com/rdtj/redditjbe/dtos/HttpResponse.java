package com.rdtj.redditjbe.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@NoArgsConstructor
@Getter @Setter
public class HttpResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy hh:mm:ss", timezone = "Europe/Prague")
    private Date timestamp;
    private int statusCode;
    private HttpStatus httpStatus;
    private String reason;
    private String message;

    public HttpResponse(int statusCode, HttpStatus httpStatus, String reason, String message) {
        timestamp = new Date();
        this.statusCode = statusCode;
        this.httpStatus = httpStatus;
        this.reason = reason;
        this.message = message;
    }
}
