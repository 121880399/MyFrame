package com.viking.myframe.http.exception;

/**
 * Created by 周正一 on 2017/5/8.
 */

public class HttpTrowable extends Throwable {
    private static final long serialVersionUID = -648750296039999792L;
    private String errorCode;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public HttpTrowable(String detailMessage, String errorCode) {
        super(detailMessage);
        this.errorCode=errorCode;
    }
}
