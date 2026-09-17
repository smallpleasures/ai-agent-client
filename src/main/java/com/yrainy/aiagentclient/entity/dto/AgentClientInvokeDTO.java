package com.yrainy.aiagentclient.entity.dto;

import com.yrainy.aiagentclient.client.AbstractParseResult;
import com.yrainy.aiagentclient.parser.module.AgentModule;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * FastGPT调用返回结果
 *
 * @author zhanglun
 * @since 2026-02-28
 */
@Data
@Accessors(chain = true)
public class AgentClientInvokeDTO<T extends AbstractParseResult> {

    /**
     * 原始数据
     */
    private String response;

    private OpenAiResponseParseDTO openAiResponseParseDTO;

    /**
     * 节点解析结果
     */
    private List<AgentModule> agentModuleList;

    /**
     * afterParse之后的值
     */
    private T parseResult;

}
