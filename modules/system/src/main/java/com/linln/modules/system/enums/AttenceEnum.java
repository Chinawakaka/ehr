package com.linln.modules.system.enums;

import lombok.Data;
import lombok.Getter;

/**
 * @Author liangjunhao
 * @Description
 * @Date 2020-04-01
 */
@Getter
public enum AttenceEnum {

    ONATTENCE((byte) 1, "上班卡"),
    OFFATTENCE((byte) 2, "下班卡"),
    ADDATTENCE((byte) 3, "加班卡"),
    EXCEPTATTENCE((byte) 4, "打卡异常")
    ;

    private Byte code;

    private String message;

    AttenceEnum(Byte code, String message) {
        this.code = code;
        this.message = message;
    }

}
