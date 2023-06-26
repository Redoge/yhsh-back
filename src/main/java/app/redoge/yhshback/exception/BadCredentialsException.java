package app.redoge.yhshback.exception;

public class BadCredentialsException extends Exception{
    public BadCredentialsException() {
        super();
    }
    public BadCredentialsException(String username) {
        super(String.format("User %s dont have credentials!!!", username));
    }
    public BadCredentialsException(Long id) {
        super(String.format("User with id %s dont have credentials!!!", id));
    }
}
