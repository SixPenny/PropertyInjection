package com.dylan.propertyinjection.exception;

public class PropertyInjectionException extends RuntimeException {
    public PropertyInjectionException(String msg) {
        super(msg);
    }

    public PropertyInjectionException(String msg, Throwable e) {
        super(msg, e);
    }
}
