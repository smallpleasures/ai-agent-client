package com.yrainy.aiagentclient.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 请求内容
 *
 * @author zhanglun
 * @since 2026-02-28
 */
public interface OpenAiRequestContent {

    /**
     * 类型，文本|图片|文件链接
     *
     * @return
     */
    String getType();

    @Data
    @Accessors(chain = true)
    class OpenAiImageContent implements OpenAiRequestContent {

        private String type = "image_url";
        @JsonProperty("image_url")
        private ImageUrl imageUrl;

        @Data
        @Accessors(chain = true)
        public static class ImageUrl {
            private String url;
        }
    }

    @Data
    @Accessors(chain = true)
    class OpenAiTextContent implements OpenAiRequestContent {

        private String type = "text";
        private String text;
    }
}
