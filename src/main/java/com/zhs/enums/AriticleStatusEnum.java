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
public enum AriticleStatusEnum {
    /**
     * 1-发布
     */
    ORIGINAL(1, "发布"),
    /**
     * 2-草稿
     */
    TRANSLATE(2, "草稿"),

    /**
     *  3-删除
     */
    REPRINT(3, "删除");


    /**
     * 类型
     */
    private Integer type;

    /**
     * 信息
     */
    private String typeMsg;


    public static String getTypeMsg(Integer type) {
        for (AriticleStatusEnum typeEnum : AriticleStatusEnum.values()) {
            if (typeEnum.getType().equals( type)) {
                return typeEnum.getTypeMsg();
            }
        }
        return null;
    }
}