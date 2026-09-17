package com.yrainy.aiagentclient.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.http.codec.json.Jackson2JsonDecoder;

import java.util.List;

/**
 * HiAgent请求体
 *
 * @author zhanglun
 * @since 2026-09-17
 */
@Data
public class HiAgentRequest {

    private static ObjectMapper jackson = new Jackson2JsonDecoder().getObjectMapper();

    @JsonProperty(value = "InputData")
    @Setter(value = AccessLevel.NONE)
    private String inputData;

    @JsonProperty(value = "UserId")
    private String userId;

    @JsonProperty(value = "NoDebug")
    private boolean noDebug = true;

    @JsonIgnore
    private VariableContract variableContract;

    @SneakyThrows
    public String getInputData() {
        return jackson.writeValueAsString(this.variableContract);
    }

    public String toString() {
        try {
            return jackson.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    /**
     * 约定HiAgent智能体输入变量
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VariableContract {

        private String text;

        private List<ImageUrl> imageUrl;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ImageUrl {
            @JsonProperty(value = "url")
            private String url;
        }
    }
}
