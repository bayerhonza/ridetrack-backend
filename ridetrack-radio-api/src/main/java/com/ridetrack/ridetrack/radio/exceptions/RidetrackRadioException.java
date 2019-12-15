package com.ridetrack.ridetrack.radio.exceptions;

public class RidetrackRadioException extends Exception {

    public RidetrackRadioException(String msg) {
        super(msg);
    }

    public RidetrackRadioException(String msg, Throwable th) {
        super(msg, th);
    }

    public RidetrackRadioException(Throwable th) {
        super(th);
    }
}
