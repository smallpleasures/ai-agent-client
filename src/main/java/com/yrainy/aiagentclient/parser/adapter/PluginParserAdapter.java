package com.yrainy.aiagentclient.parser.adapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yrainy.aiagentclient.parser.ModuleParserFactory;
import com.yrainy.aiagentclient.parser.module.PluginModule;
import lombok.AllArgsConstructor;

import java.util.Objects;

/**
 * 插件节点解析器
 *
 * @author zhanglun
 * @since 2026-02-28
 */
@AllArgsConstructor
public class PluginParserAdapter implements ModuleParserAdapter<PluginModule> {

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
        return Objects.equals("pluginModule", moduleType);
    }

    /**
     * 解析节点
     *
     * @param node 节点
     * @return 经过解析器解析后的数据
     */
    @Override
    public PluginModule parse(JsonNode node) {
        PluginModule pluginModule = new PluginModule();
        for (JsonNode pluginDetail : node.path("pluginDetail")) {
            moduleParserFactory.getParser(pluginDetail.path("moduleType").asText())
                    .ifPresent(parser -> pluginModule.addPluginDetail(parser.parse(pluginDetail)));
        }
        return null;
    }
}
