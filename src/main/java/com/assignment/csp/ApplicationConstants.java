package com.assignment.csp;

/**
 * Application constants class.
 *
 * @author Prachi Das
 */
public final class ApplicationConstants {
    /**
     * <em>SUCCESSFUL</em> label constant
     */
    public static final String SUCCESSFUL = "SUCCESSFUL";
    /**
     * <em>DUPLICATE_REFERENCE_INCORRECT_END_BALANCE</em> label constant
     */
    public static final String DUPLICATE_REFERENCE_INCORRECT_END_BALANCE = "DUPLICATE_REFERENCE_INCORRECT_END_BALANCE";
    /**
     * <em>DUPLICATE_REFERENCE</em> label constant
     */
    public static final String DUPLICATE_REFERENCE = "DUPLICATE_REFERENCE";
    /**
     * <em>INCORRECT_END_BALANCE</em> label constant
     */
    public static final String INCORRECT_END_BALANCE = "INCORRECT_END_BALANCE";

    private ApplicationConstants() throws InstantiationException {
        throw new InstantiationException("Cannot instantiate this class");
    }
}
