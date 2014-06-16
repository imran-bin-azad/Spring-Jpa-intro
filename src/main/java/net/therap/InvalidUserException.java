package net.therap;

/**
 * Created with IntelliJ IDEA.
 * User: imran.azad
 * Date: 5/19/14
 * Time: 12:12 PM
 */
public class InvalidUserException extends Exception {
    public InvalidUserException() {
        super();
    }
    public InvalidUserException(String message) {
        super(message);
    }
}
