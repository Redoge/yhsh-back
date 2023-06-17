package app.redoge.yhshback.exception;

public class UserIsExistException extends Exception {
    public UserIsExistException() {
        super();
    }
    public UserIsExistException(long id) {
        super("User with id " + id + " is exist!!!");
    }
    public UserIsExistException(String username, String email) {
        super(String.format("User with username %s or email %s is exist!!!", username, email));
    }
}
