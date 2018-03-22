package com.msincuba.play.exceptions;

/**
 *
 * @author mlungisi
 */
public final class EmailNotFoundException extends RuntimeException {

    private final String email;
    
    /**
     * Creates a new instance of <code>EmailNotFoundException</code>
     * without detail message.
     * @param email email
     */
    public EmailNotFoundException(final String email) {
        super(String.format("Email {%s} not found.", email));
        this.email = email;
    }

    /**
     * Constructs an instance of <code>EmailNotFoundException</code> with
     * the specified detail message.
     *
     * @param email email
     * @param msg the detail message.
     */
    public EmailNotFoundException(final String email, final String msg) {
        super(msg + ". Email supplied was: {" + email + "}");
        this.email = email;
    }
    
    public String getEmail() {
        return email;
    }
}
