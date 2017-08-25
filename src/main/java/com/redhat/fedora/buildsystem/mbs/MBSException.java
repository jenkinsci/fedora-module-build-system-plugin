package com.redhat.fedora.buildsystem.mbs;

/**
 * Created by shebert on 23/08/17.
 */
public class MBSException extends RuntimeException {
    public MBSException() {
        super();
    }

    public MBSException(String error) {
        super(error);
    }

    public MBSException(String message, Throwable cause) {
        super(message, cause);
    }

    public MBSException(Throwable cause) {
        super(cause);
    }

    protected MBSException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public MBSException(Exception e) {
        super(e);
    }
}
