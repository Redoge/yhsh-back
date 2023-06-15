package app.redoge.yhshback.exception;

public class UserIsExistException extends Exception {
    public UserIsExistException() {
        super();
    }
    public UserIsExistException(long id) {
        super("User with id " + id + " is exist!!!");
    }
}
