package com.model;

/**
 * Describes the result of any operation including data return, errors, and comments regarding the operation.
 * 
 * @author Matt Carey
 */

public class OperationResult<T> {
    public final boolean success;
    public final T result;
    public final Exception error;
    public final String message;

    /**
     * Constructs a new successful operation result, with the return value of result.
     * @param result
     */
    public OperationResult(T result) {
        this.success = true;
        this.result = result;
        this.error = null;
        this.message = null;
    }
    /**
     * Constructs a new unsuccessful operation result specifying a message and exception
     * @param message Developer generated note
     * @param error Java generated error
     */
    public OperationResult(String message, Exception error) {
        this.success = false;
        this.result = null;
        this.error = error;
        this.message = message;
    }
    /**
     * Constructs a new unsuccessful operation result specifying a message
     * @param message Developer generated note
     */
    public OperationResult(String message) {
        this.success = false;
        this.result = null;
        this.error = null;
        this.message = message;
    }

    /**
     * Should only be used in OperationResult<Void> so that result is guaranteed for successful operations.
     * @param result
     */
    public OperationResult(boolean result) {
        this.success = result;
        this.result = null;
        this.error = null;
        this.message = null;
    }

    @Override
    public String toString() {
        return "Operation Result {"
            + "\n\tsuccess=" + success
            + ",\n\tresult=" + result
            + ",\n\terror=" + (error != null ? error.toString() : "null")
            + (error != null ? ",\n\ttrace="+error.fillInStackTrace().toString() : "")
            + ",\n\tmessage='" + message + '\''
            + "\n}";
    }
}
