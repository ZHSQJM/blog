package com.zhs.exception;

import com.zhs.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Created with IDEA
 * @author:周华生
 * @Date:2018/8/21 9:48
 * @描述: 全局异常捕获
 **/
@ControllerAdvice
@Slf4j
@RestController
public class GlobalExceptionHandler {


    /**
     *  //实体类上面的参数校验异常
     * @param e  异常
     * @return ResultData<XxxxDO>
     */

    @ExceptionHandler(BindException.class)
    public ResultData handleBindException(BindException e) {
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String message = error.getDefaultMessage();
        return  ResultData.ofFail(message);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResultData AuthenticationException(Exception e){
        return ResultData.ofFail("用户名或密码错误");
    }


   /* @ExceptionHandler(QiniuException.class)
    public ResultData QiniuException(Exception e){
        return ResultData.ofFail(MsgConst.DEFAULT_ERROR_SELECT_MESSAGE);
    }*/

    @ExceptionHandler(Exception.class)
    public ResultData AllException(Exception e){
        e.printStackTrace();
        return ResultData.ofFail("服务器异常，请联系管理员");
    }

}
