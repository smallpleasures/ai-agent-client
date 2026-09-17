package com.yrainy.aiagentclient.client;

import com.yrainy.aiagentclient.entity.AiAgentPO;
import com.yrainy.aiagentclient.entity.AiTaskPO;
import com.yrainy.aiagentclient.entity.dto.AgentClientInvokeDTO;
import com.yrainy.aiagentclient.entity.dto.OpenAiRequestContent;
import com.yrainy.aiagentclient.entity.dto.OpenAiResponseParseDTO;

import java.util.List;

/**
 * HiAgent客户端适配器
 *
 * @author zhanglun
 * @since 2026-09-13
 */
public class HiAgentClientAdapter<T extends AbstractParseResult> implements IAgentClient<T> {

    private AiAgentPO aiAgentPO;

    private IAgentClient<T> delegateAgent;

    public HiAgentClientAdapter(AiAgentPO aiAgentPO, IAgentClient<T> agentClient) {
        this.aiAgentPO = aiAgentPO;
        this.delegateAgent = agentClient;
    }


    @Override
    public AiAgentPO getAiAgent(AiTaskPO aiTaskPO) {
        return aiAgentPO;
    }

    /**
     * 执行HiAgent智能体
     *
     * @param aiTaskPO AI任务
     * @param content 请求内容
     * @return
     */
    @Override
    public AgentClientInvokeDTO<T> invoke(AiTaskPO aiTaskPO, List<OpenAiRequestContent> content) {
        // 委托HiAgent执行
        return delegateAgent.invoke(aiTaskPO, content);
    }

    /**
     * 适配HiAgent智能体
     *
     * @param aiAgentPO
     * @param agentClientParserSupport
     * @return
     * @param <U>
     */
    public static <U extends AbstractParseResult> HiAgentClientAdapter<U> adapt(AiAgentPO aiAgentPO, AgentClientParserSupport<U> agentClientParserSupport) {
        return new HiAgentClientAdapter<>(aiAgentPO, new AbstractHiAgentClient<U>() {

            @Override
            public AiAgentPO getAiAgent(AiTaskPO aiTaskPO) {
                return aiAgentPO;
            }

            // 让原Agent客户端做解析之后的业务逻辑处理，避免写多套
            // 这里用AgentClientParserSupport类型，而不用AbstractOpenAiAgentClientSupport，因为只做一件事，那就是要它的afterParse能力，满足开闭原则
            @Override
            protected U afterParse(OpenAiResponseParseDTO parseDTO, AiTaskPO aiTaskPO) {
                return agentClientParserSupport.afterParse(parseDTO, aiTaskPO);
            }
        });
    }
}
