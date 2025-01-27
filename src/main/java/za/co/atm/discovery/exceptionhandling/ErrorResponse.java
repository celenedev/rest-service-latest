package za.co.atm.discovery.exceptionhandling;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    private int statusCode;
    private String message;

    public ErrorResponse(int statusCode, String message){
        super();
        this.statusCode = statusCode;
        this.message = message;
    }
}
