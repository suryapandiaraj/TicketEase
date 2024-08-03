package TicketEase.CustomException;

public class ShowAlreadyExistException extends Exception{
    public ShowAlreadyExistException(String message)
    {
        super(message);
    }
}
