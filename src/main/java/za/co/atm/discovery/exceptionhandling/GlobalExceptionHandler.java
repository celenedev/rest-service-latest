package za.co.atm.discovery.exceptionhandling;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ATMException.class)
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public @ResponseBody ErrorResponse handleException(ATMException ex) {
        return new ErrorResponse(HttpStatus.I_AM_A_TEAPOT.value(), ex.getMessage());
    }
}
