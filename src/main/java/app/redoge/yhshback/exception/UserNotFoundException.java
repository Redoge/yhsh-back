package app.redoge.yhshback.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super();
    }
    public UserNotFoundException(long id) {
        super("User with id " + id + " not found!!!");
    }
    public UserNotFoundException(String username) {
        super(String.format("User with username %s not found!!!", username));
    }
}
