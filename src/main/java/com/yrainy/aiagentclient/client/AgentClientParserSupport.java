package com.yrainy.aiagentclient.client;

import com.yrainy.aiagentclient.entity.AiTaskPO;
import com.yrainy.aiagentclient.entity.dto.OpenAiResponseParseDTO;
import com.yrainy.aiagentclient.enums.AgentPlatformEnum;
import org.springframework.http.ResponseEntity;

/**
 * 智能体客户端响应解析器
 *
 * @author zhanglun
 * @since 2026-09-13
 */
public abstract class AgentClientParserSupport<T extends AbstractParseResult> {

    /**
     * 解析响应，封装为OpenAiResponseParseDTO
     *
     * @param response
     * @param agentPlatformEnum
     * @return
     * @throws Exception
     */
    protected abstract OpenAiResponseParseDTO parse(ResponseEntity<String> response, AgentPlatformEnum agentPlatformEnum) throws Exception;

    /**
     * 解析响应后置处理
     *
     * @param parseDTO
     * @param aiTaskPO
     * @return
     */
    protected abstract T afterParse(OpenAiResponseParseDTO parseDTO, AiTaskPO aiTaskPO);
}
