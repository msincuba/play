package com.msincuba.play.exceptions;

/**
 *
 * @author mlungisi
 */
public final class UsernameAlreadyUsedException extends RuntimeException {

    private final String username;
    
    /**
     * Creates a new instance of <code>UsernameAlreadyUsedException</code>
     * without detail message.
     * @param username username
     */
    public UsernameAlreadyUsedException(final String username) {
        super(String.format("Username {%s} already used.", username));
        this.username = username;
    }

    /**
     * Constructs an instance of <code>UsernameAlreadyUsedException</code> with
     * the specified detail message.
     *
     * @param username username
     * @param msg the detail message.
     */
    public UsernameAlreadyUsedException(final String username, String msg) {
        super(msg + ". Username supplied was: {" + username + "}");
        this.username = username;
    }
    
    public String getUsername() {
        return username;
    }
}
