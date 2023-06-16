package app.redoge.yhshback.exception;

public class NotFoundException extends Exception {
    public NotFoundException() {
        super();
    }

    public NotFoundException(String objName, long id) {
        super(String.format("%s with id %d - not found!!!", objName, id));
    }
}
