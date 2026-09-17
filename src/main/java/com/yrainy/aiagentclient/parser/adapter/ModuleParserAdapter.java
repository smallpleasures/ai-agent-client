package com.yrainy.aiagentclient.parser.adapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.yrainy.aiagentclient.parser.module.AgentModule;

/**
 * 统一解析接口
 *
 * @author zhanglun
 * @since 2026-02-28
 */
public interface ModuleParserAdapter<T extends AgentModule> {

    /**
     * 解析器是否支持该节点
     *
     * @param moduleType 节点类型
     * @return 解析器是否支持该节点
     */
    boolean supports(String moduleType);

    /**
     * 解析节点
     *
     * @param node 节点
     * @return 经过解析器解析后的数据
     */
    T parse(JsonNode node);
}
