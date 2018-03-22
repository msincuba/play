package com.msincuba.play.exceptions;

/**
 *
 * @author mlungisi
 */
public final class EmailAlreadyUsedException extends RuntimeException {

    private final String email;
    
    /**
     * Creates a new instance of <code>EmailAlreadyUsedException</code>
     * without detail message.
     * @param email email
     */
    public EmailAlreadyUsedException(final String email) {
        super(String.format("Email {%s} already used.", email));
        this.email = email;
    }

    /**
     * Constructs an instance of <code>EmailAlreadyUsedException</code> with
     * the specified detail message.
     *
     * @param email email
     * @param msg the detail message.
     */
    public EmailAlreadyUsedException(final String email, final String msg) {
        super(msg + ". Email supplied was: {" + email + "}");
        this.email = email;
    }
    
    public String getEmail() {
        return email;
    }
}
