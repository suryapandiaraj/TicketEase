package BookMyShow.CustomException;

public class ShowAlreadyExistException extends Exception{
    public ShowAlreadyExistException(String message)
    {
        super(message);
    }
}
