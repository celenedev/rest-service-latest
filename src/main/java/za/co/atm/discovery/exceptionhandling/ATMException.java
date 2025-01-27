package za.co.atm.discovery.exceptionhandling;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ATMException extends RuntimeException {
    private final String message;

    public ATMException(String msg) {
        super(msg);
        this.message = msg;
    }
}
