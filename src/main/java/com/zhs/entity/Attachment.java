package com.zhs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.Date;


/**
 * decription:
 * @param 
 * @return: 
 * @auther: zhs
 * @date: 2019/1/21 10:43
 * @remarks:    
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attachment {

    @Id
    private Integer id;

    /**
     * 图片的名字
     */
    private String picturename;

    /**
     * 图片路径
     */
    private String picturepath;
    /**
     * 略缩图
     */
    private String picturesmallpath;
    /**
     * 图片类型
     */
    private String picturetype;
    /**
     * 上传时间
     */
    private Date picturecreatedate;
    /**
     * 文件大小
     */
    private String picturesize;
    /**
     * 后缀
     */
    private String picturesuffix;
    /**
     * 尺寸
     */
    private String picturewh;


}