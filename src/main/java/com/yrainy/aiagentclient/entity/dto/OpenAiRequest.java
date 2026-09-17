package com.yrainy.aiagentclient.entity.dto;

import lombok.Data;

import java.util.List;

/**
 * FastGPT请求
 *
 * @author zhanglun
 * @since 2026-02-28
 */
@Data
public class OpenAiRequest<T extends OpenAiRequestContent> {
    private String appId;
    private String chatId;
    private Boolean detail = Boolean.TRUE;
    private Boolean stream = Boolean.FALSE;
    private List<Message<T>> messages;

    @Data
    public static class Message<T> {
        private String role = "user";
        private List<T> content;
    }
}
