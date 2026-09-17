package com.yrainy.aiagentclient.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 材料类型枚举
 *
 * @author zhanglun
 * @since 2026-02-28
 */
@Getter
@AllArgsConstructor
public enum TaskTypeEnum {

    BIZ("BIZ", "业务BIZ"),
    BUSINESS("BUSINESS", "业务BUSINESS"),
    ;

    private final String code;
    private final String name;
}
