package com.mytaxi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Driver is not online.")
public class DriverIsNotOnlineException extends Exception
{
    public DriverIsNotOnlineException(String message)
    {
        super(message);
    }
}
