package com.yrainy.aiagentclient.entity.dto;

import lombok.Data;

import java.util.List;

/**
 * FastGPT响应
 *
 * @author zhanglun
 * @since 2026-02-28
 */
@Data
public class OpenAiResponse {

    private List<Choice> choices;

    @Data
    public static class Choice {
        private String finishReason;
        private Integer index;
        private Message message;

        @Data
        public static class Message {
            private String role;
            private String content;
        }
    }
}
