package com.epam.edumanagementsystem.util.exceptions;

public class ObjectIsNull extends RuntimeException {

    String msg ="Please, fill the required fields";
    public ObjectIsNull() {
    }

    public ObjectIsNull(String message) {
      this.msg=message;
    }

    public ObjectIsNull(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectIsNull(Throwable cause) {
        super(cause);
    }
}
