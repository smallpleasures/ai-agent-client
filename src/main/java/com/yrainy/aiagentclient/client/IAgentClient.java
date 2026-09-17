package com.yrainy.aiagentclient.client;

import com.yrainy.aiagentclient.entity.AiAgentPO;
import com.yrainy.aiagentclient.entity.AiTaskPO;
import com.yrainy.aiagentclient.entity.dto.AgentClientInvokeDTO;
import com.yrainy.aiagentclient.entity.dto.OpenAiRequestContent;

import java.util.List;

/**
 * 统一定义智能体客户端接口
 *
 * @author zhanglun
 * @since 2026-02-28
 */
public interface IAgentClient<T extends AbstractParseResult> {

    /**
     * 获取智能体对象
     * @param aiTaskPO AI任务
     *
     * @return 智能体对象
     */
    AiAgentPO getAiAgent(AiTaskPO aiTaskPO);

    /**
     * 执行智能体
     *
     * @param aiTaskPO AI任务
     * @param content 请求内容
     * @return 智能体返回结果
     */
    AgentClientInvokeDTO<T> invoke(AiTaskPO aiTaskPO, List<OpenAiRequestContent> content);


}
