package com.itheima.controller.utils;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


public class Response extends AbstractDto {
    private static final long serialVersionUID = 1L;
    protected String flag;
    protected String errorCode;
    protected String errorInfo;

    public Response() {
    }

    public String getFlag() {
        return this.flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorInfo() {
        return this.errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String toString() {
        return "Response{flag='" + this.flag + '\'' + ", errorCode='" + this.errorCode + '\'' + ", errorInfo='" + this.errorInfo + '\'' + '}';
    }

    public static Response buildFailure(String errCode, String errMessage) {
        Response response = new Response();
        response.setFlag("F");
        response.setErrorCode(errCode);
        response.setErrorInfo(errMessage);
        return response;
    }

    public static Response buildSuccess() {
        Response response = new Response();
        response.setFlag("T");
        return response;
    }
}
