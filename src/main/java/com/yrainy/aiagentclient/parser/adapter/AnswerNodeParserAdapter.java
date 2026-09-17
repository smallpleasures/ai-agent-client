package com.yrainy.aiagentclient.parser.adapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.yrainy.aiagentclient.parser.module.AnswerNodeModule;
import lombok.AllArgsConstructor;

import java.util.Objects;

/**
 * 指定回复节点解析器
 *
 * @author zhanglun
 * @since 2026-02-28
 */
@AllArgsConstructor
public class AnswerNodeParserAdapter implements ModuleParserAdapter<AnswerNodeModule> {

    @Override
    public boolean supports(String moduleType) {
        return Objects.equals("answerNode", moduleType);
    }

    /**
     * 解析节点
     *
     * @param node 节点
     * @return 经过解析器解析后的数据
     */
    @Override
    public AnswerNodeModule parse(JsonNode node) {
        AnswerNodeModule answerNodeModule = new AnswerNodeModule();
        answerNodeModule.setModuleType(node.path("moduleType").asText());
        answerNodeModule.setModuleName(node.path("moduleName").asText());
        answerNodeModule.setTextOutput(node.path("textOutput").asText());
        return answerNodeModule;
    }

}
