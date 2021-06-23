package dbproject.exception;

public class WrongCredentialsException extends RuntimeException{
    public WrongCredentialsException(String message){
        super(message);
    }
}
