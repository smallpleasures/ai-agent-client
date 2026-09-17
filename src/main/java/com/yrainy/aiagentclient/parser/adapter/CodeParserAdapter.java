package com.yrainy.aiagentclient.parser.adapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.yrainy.aiagentclient.parser.module.CodeModule;
import lombok.AllArgsConstructor;

import java.util.Objects;

/**
 * 代码运行节点解析器
 *
 * @author zhanglun
 * @since 2026-02-28
 */
@AllArgsConstructor
public class CodeParserAdapter implements ModuleParserAdapter<CodeModule> {
    /**
     * 解析器是否支持该节点
     *
     * @param moduleType 节点类型
     * @return 解析器是否支持该节点
     */
    @Override
    public boolean supports(String moduleType) {
        return Objects.equals("code", moduleType);
    }

    /**
     * 解析节点
     *
     * @param node 节点
     * @return 经过解析器解析后的数据
     */
    @Override
    public CodeModule parse(JsonNode node) {
        return new CodeModule();
    }
}
