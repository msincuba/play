package com.msincuba.play.exceptions;

/**
 *
 * @author mlungisi
 */
public class InvalidPasswordException extends RuntimeException {

    /**
     * Creates a new instance of <code>InvalidPasswordException</code> without
     * detail message.
     */
    public InvalidPasswordException() {
    }

    /**
     * Constructs an instance of <code>InvalidPasswordException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidPasswordException(String msg) {
        super(msg);
    }
}
