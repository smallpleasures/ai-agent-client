package com.yrainy.aiagentclient.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * 智能体平台
 *
 * @author zhanglun
 * @since 2026-09-13
 */
@Getter
@AllArgsConstructor
public enum AgentPlatformEnum {

    FAST_GPT("FastGPT", "FastGPT平台"),
    HI_AGENT("HiAgent", "火山引擎HiAgent平台"),
    ;

    private String code;
    private String name;

    public static AgentPlatformEnum ofCodeOrElseThrowsException(String code) {
        return Stream.of(AgentPlatformEnum.values()).filter(e -> Objects.equals(e.getCode(), code))
                .findFirst().orElseThrow(IllegalArgumentException::new);
    }

    public static AgentPlatformEnum ofCode(String code) {
        for (AgentPlatformEnum value : AgentPlatformEnum.values()) {
            if (Objects.equals(value.getCode(), code)) {
                return value;
            }
        }
        return null;
    }

    /**
     * 是否支持OpenAI的API协议
     *
     * @return
     */
    public boolean supportOpenAiApiProtocol() {
        switch (this) {
            case FAST_GPT:
                return true;
            case HI_AGENT:
                return false;
            default:
                return false;
        }
    }
}
