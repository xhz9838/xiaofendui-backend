package cn.edu.sdbi.utils;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 返回结果的通用封装
 * @author suiyue
 * @date 2020/9/28
 */
@Data
@Accessors(chain = true)
@Builder
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int status;

    private String msg;

    private T data;

    public static Result ok(String msg, Object data) {
        return Result.builder()
                .status(ResultCode.OK)
                .msg(msg)
                .data(data)
                .build();
    }

    public static Result ok(int code, String msg, Object data) {
        return Result.builder()
                .status(code)
                .msg(msg)
                .data(data)
                .build();
    }

    public static Result error(String msg) {
        return Result.builder()
                .status(ResultCode.ERROR)
                .msg(msg)
                .build();
    }

    public static Result error(int code, String msg) {
        return Result.builder()
                .status(code)
                .msg(msg)
                .build();
    }
}
