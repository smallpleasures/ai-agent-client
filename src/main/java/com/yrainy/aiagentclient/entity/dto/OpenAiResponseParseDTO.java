package com.yrainy.aiagentclient.entity.dto;

import cn.hutool.core.collection.CollUtil;
import com.yrainy.aiagentclient.parser.module.AgentModule;
import com.yrainy.aiagentclient.parser.module.AnswerNodeModule;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * FastGPT响应解析
 *
 * @author zhanglun
 * @since 2026-02-28
 */
@Data
@Accessors(chain = true)
public class OpenAiResponseParseDTO {

    /**
     * GPT原始响应
     */
    private List<OpenAiResponse> response;

    /**
     * 节点解析结果
     */
    private List<AgentModule> agentModuleList;

    public String getOutput() {
        if (CollUtil.isNotEmpty(agentModuleList)) {
            AnswerNodeModule answerNodeModule = (AnswerNodeModule) agentModuleList.get(agentModuleList.size() -1);
            return answerNodeModule.getTextOutput();
        }
        return null;
    }
}
