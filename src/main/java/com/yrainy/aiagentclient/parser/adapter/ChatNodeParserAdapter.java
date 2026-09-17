package com.yrainy.aiagentclient.parser.adapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yrainy.aiagentclient.parser.ModuleParserFactory;
import com.yrainy.aiagentclient.parser.module.ChatNodeModule;
import lombok.AllArgsConstructor;

import java.util.Objects;

/**
 * 对话 节点解析器
 *
 * @author zhanglun
 * @since 2026-02-28
 */
@AllArgsConstructor
public class ChatNodeParserAdapter implements ModuleParserAdapter<ChatNodeModule> {

    private ObjectMapper objectMapper;
    private ModuleParserFactory moduleParserFactory;

    /**
     * 解析器是否支持该节点
     *
     * @param moduleType 节点类型
     * @return 解析器是否支持该节点
     */
    @Override
    public boolean supports(String moduleType) {
        return Objects.equals("chatNode", moduleType);
    }

    /**
     * 解析节点
     *
     * @param node 节点
     * @return 经过解析器解析后的数据
     */
    @Override
    public ChatNodeModule parse(JsonNode node) {
        ChatNodeModule chatNodeModule = new ChatNodeModule();
        chatNodeModule.setModuleType(node.path("moduleType").asText());
        return chatNodeModule;
    }
}
