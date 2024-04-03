package com.andjela.diplomski.dto.email;

import com.sendgrid.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class EmailSendResponseDto {
    private String emailSentTo;
    private Response response;
    private boolean error;
    private String message;
    private String emailProvider;

    public EmailSendResponseDto(Response response, String emailSentTo, String emailProvider) {
        this.emailSentTo = emailSentTo;
        this.response = response;
        this.emailProvider = emailProvider;
        this.error = response.getStatusCode() < 200 || response.getStatusCode() > 300;
    }

    public EmailSendResponseDto(boolean error, String emailSentTo, String emailProvider) {
        this.emailSentTo = emailSentTo;
        this.emailProvider = emailProvider;
        this.error = error;
    }

    @Override
    public String toString() {
        if(response == null) {
            return null;
        }
        return "EmailSendResponse{StatusCode:" + response.getStatusCode() + "}";
    }
}
