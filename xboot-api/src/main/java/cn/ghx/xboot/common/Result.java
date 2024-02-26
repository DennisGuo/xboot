package cn.ghx.xboot.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * common 返回结果
 */
@Data
@NoArgsConstructor
public class Result<T> implements Serializable {

    private Integer status = CODE_OK;
    private String error;
    private boolean success;
    private String message;

    private T data;

    public Result(T data) {
        this.success = true;
        this.data = data;
    }

    public Result(String message) {
        this.success = false;
        this.message = message;
    }

    public Result(String message, Integer status) {
        this.success = false;
        this.message = message;
        this.status = status;
    }

    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Result(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public Result(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public Result(boolean success, String message, Integer status, T data) {
        this.success = success;
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(true, data);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(true, message, data);
    }


    public static <T> Result<T> fail(String message, Integer code) {
        if (code == null) {
            code = CODE_FAIL;
        }
        return new Result<>(message, code);
    }

    public static <T> Result<T> fail(String message, Integer code, T data) {
        if (code == null) {
            code = CODE_FAIL;
        }
        return new Result<>(false, message, code, data);
    }


    // 各种状态码
    /**
     * 正常返回
     */
    public static final Integer CODE_OK = 200;
    /**
     * 失败返回
     */
    public static final Integer CODE_FAIL = 500;
    /**
     * 请求参数验证失败
     */
    public static final Integer CODE_VALID_ERROR = 30001;
    /**
     * 应用判定错误
     */
    public static final Integer CODE_APP_ERROR = 30002;
    /**
     * 数据错误
     */
    public static final Integer CODE_DATA_ERROR = 30003;
}
