package com.laurel.eth.trace;

public class TransformException extends Exception {

    public TransformException(String message) {
        super(message);
    }

    public TransformException(String message, Throwable cause) {
        super(message, cause);
    }
}
