package app.redoge.yhshback.exception;

public class TrainingNotFoundException extends Exception{
    public TrainingNotFoundException() {
        super();
    }
    public TrainingNotFoundException(long id) {
        super("Training with id " + id + " not found!!!");
    }
}
