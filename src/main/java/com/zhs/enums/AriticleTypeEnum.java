package com.zhs.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2018/12/18 17:21
 * @package: com.zhs.enums
 * @description: 文章枚举类型  原创 转载  翻译
 */

@Getter
@AllArgsConstructor
public enum AriticleTypeEnum {
    /**
     * 1-原创
     */
    ORIGINAL(1, "原创"),
    /**
     * 2-翻译
     */
    TRANSLATE(2, "翻译"),

    /**
     *  3-转载
     */
    REPRINT(3, "转载");


    /**
     * 类型
     */
    private Integer type;

    /**
     * 信息
     */
    private String typeMsg;


    public static String getTypeMsg(Integer type) {
        for (AriticleTypeEnum typeEnum : AriticleTypeEnum.values()) {
            if (typeEnum.getType().equals( type)) {
                return typeEnum.getTypeMsg();
            }
        }
        return null;
    }
}