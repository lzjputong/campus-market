package com.luozhijian.campusmarket;

public class BusinessException extends RuntimeException {

    private final int code;

    public BusinessException(int code, String message) {
        super(message);        // 提示信息交给父类 RuntimeException
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}