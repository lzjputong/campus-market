package com.luozhijian.campusmarket;

public class Result<T> {

    private int code;         // 业务状态码：0 = 成功
    private String message;   // 提示信息
    private T data;           // 真正的数据（泛型）

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /** 成功，带数据 */
    public static <T> Result<T> ok(T data) {
        return new Result<>(0, "success", data);
    }

    /** 失败，带错误码和提示 */
    public static <T> Result<T> fail(int code, String message) {
        return new Result<>(code, message, null);
    }

    // Jackson 序列化成 JSON 时需要 getter，必须有
    public int getCode()       { return code; }
    public String getMessage() { return message; }
    public T getData()         { return data; }
}