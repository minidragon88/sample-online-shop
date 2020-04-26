package com.phu.onlineshop.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class APIResponse<T>
{
    private int status;
    private String message;
    private T results;

    public APIResponse(final int status, final String message, final T results)
    {
        this.status = status;
        this.message = message;
        this.results = results;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(final int status)
    {
        this.status = status;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(final String message)
    {
        this.message = message;
    }

    public T getResults()
    {
        return results;
    }

    public void setResults(final T results)
    {
        this.results = results;
    }

    public ResponseEntity<APIResponse<T>> toResponseEntity()
    {
        return new ResponseEntity<APIResponse<T>>(this, HttpStatus.valueOf(status));
    }
}
