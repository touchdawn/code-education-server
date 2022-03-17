package com.itheima.controller.utils;
public class ApiResult extends Response {
    private String sign;
    private Object data;

    public ApiResult() {
    }

    private ApiResult(String flag, String errorCode, String errorInfo, String sign, Object data) {
        this.flag = flag;
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
        this.sign = sign;
        this.data = data;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getSign() {
        return this.sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public static ApiResult T() {
        return newInstance("T", "", "", (Object)null);
    }

    public static ApiResult T(Object data) {
        return newInstance("T", "", "", data);
    }

    public static ApiResult T(String sign, Object data) {
        return newInstance("T", "", "", sign, data);
    }

    public static ApiResult F() {
        return newInstance("F", "", "", (Object)null);
    }

    public static ApiResult F(String errorCode, String errorInfo) {
        return newInstance("F", errorCode, errorInfo, (Object)null);
    }

    public static ApiResult F(ErrorCodeI errorCodeI, String errorInfo) {
        return newInstance("F", errorCodeI.getErrorCode(), errorInfo, (Object)null);
    }

    public static ApiResult F(ErrorCodeI errorCodeI) {
        return newInstance("F", errorCodeI.getErrorCode(), errorCodeI.getErrorInfo(), (Object)null);
    }

    public static ApiResult newInstance(String flag, ErrorCodeI errorCodeI) {
        return newInstance(flag, errorCodeI.getErrorCode(), errorCodeI.getErrorInfo(), (Object)null);
    }

    public static ApiResult newInstance(String flag, String errorCode, String errorInfo) {
        return newInstance(flag, errorCode, errorInfo, (Object)null);
    }

    public static ApiResult newInstance(String flag, String errorCode, String errorInfo, Object data) {
        ApiResult apiResult = new ApiResult(flag, errorCode, errorInfo, "", data);
        return apiResult;
    }

    public static ApiResult newInstance(String flag, String errorCode, String errorInfo, String sign, Object data) {
        ApiResult apiResult = new ApiResult(flag, errorCode, errorInfo, sign, data);
        return apiResult;
    }
}
