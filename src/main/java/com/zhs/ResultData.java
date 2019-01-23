package com.zhs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.ObjectOutputStream;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2018/12/3 16:46
 * @package: com.zhs
 * @description: 用于统一的Restful Api的返回结果
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultData {

    /**
     * 响应码
     */
    private String code;

    /**
     * 响应信息
     */
    private  String message;

    /**
     * 响应数据
     */
    private Object data;

    /**
     * decription: 失败的结果
     * @param :[msg]
     * @return: com.zhs.ResultData
     * @auther: zhs
     * @date: 2018/12/3 17:13
     * @remarks:    
     */
    public static  ResultData ofFail(String msg){ return  new ResultData("500",msg,""); }

    public static  ResultData ofFail(String msg,String code){ return  new ResultData(code,msg,""); }

    public static  ResultData ofSuccess(String msg,Object obj){ return  new ResultData("200",msg,obj); }

    public static  ResultData ofSuccess(String msg){ return  new ResultData("200",msg,""); }

}
