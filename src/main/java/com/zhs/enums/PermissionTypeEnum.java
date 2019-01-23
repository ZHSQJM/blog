package com.zhs.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2018/12/28 09:37
 * @package: com.zhs.enums
 * @description:
 */
@Getter
@AllArgsConstructor
public enum PermissionTypeEnum {

    /**
     * 1-菜单
     */
    MENU(1, "菜单"),
    /**
     * 2-按钮
     */
    BUTTON(2, "按钮"),

    /**
     *  3-API
     */
    API(3, "API"),

    /**
     * 外链
     */
    OUTCHAIN(4, "外链");

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
