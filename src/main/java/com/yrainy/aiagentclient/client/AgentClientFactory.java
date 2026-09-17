package com.yrainy.aiagentclient.client;

import cn.hutool.extra.spring.SpringUtil;
import com.yrainy.aiagentclient.enums.TaskTypeEnum;
import com.yrainy.aiagentclient.client.impl.BizAiAgentClient;
import com.yrainy.aiagentclient.client.impl.BusinessAiAgentClient;

/**
 * 智能体客户端工厂
 *
 * @author zhanglun
 * @since 2026-02-28
 */
public class AgentClientFactory {

    private static final AgentClientFactory INSTANCE = new AgentClientFactory();

    private AgentClientFactory() {
    }

    public static AgentClientFactory getInstance() {
        return INSTANCE;
    }

    /**
     * 获取智能体客户端
     *
     * @param taskTypeEnum 材料
     * @return 智能体客户端
     */
    public IAgentClient<AbstractParseResult.ShareParseResult> getClient(TaskTypeEnum taskTypeEnum) {
        switch (taskTypeEnum) {
            case BIZ:
                return SpringUtil.getBean(BizAiAgentClient.class);
            case BUSINESS:
                return SpringUtil.getBean(BusinessAiAgentClient.class);
            default: throw new IllegalStateException("类型不匹配");
        }
    }
}
