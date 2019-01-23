package com.zhs.exception;

import lombok.Data;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2019/1/17 09:23
 * @package: com.zhs.exception
 * @description:
 */
@Data
public class ZhsException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public ZhsException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ZhsException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public ZhsException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public ZhsException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

}
