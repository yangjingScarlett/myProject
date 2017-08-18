package com.yang.myProject.api;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * @author Yangjing
 */
public class CimsResult implements Serializable {
    private int status;
    private String message = "";
    private String messageCode = "";
    private String error = "";
    private Object data;

    public CimsResult() {
    }

    private CimsResult(int status, Object data) {
        this.status = status;
        this.data = data;
    }

    private CimsResult(int status, String error) {
        this.status = status;
        this.error = error;
        this.data = null;
    }

    public CimsResult(int status, String message, String messageCode, Object data) {
        this.status = status;
        this.message = message;
        this.messageCode = messageCode;
        this.data = data;
    }

    public static CimsResult OK(Object data) {
        return new CimsResult(HttpStatus.OK.value(), data);
    }

    public static CimsResult OK(Object data, String message, String messageCode) {
        return new CimsResult(HttpStatus.OK.value(), message, messageCode, data);
    }

    public static CimsResult INTERNAL_ERROR(String error) {
        return new CimsResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), error);
    }

    public static CimsResult CLIENT_ERROR(String error) {
        return new CimsResult(HttpStatus.BAD_REQUEST.value(), error);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
