package com.yrainy.aiagentclient.client.impl;

import com.yrainy.aiagentclient.entity.AiTaskPO;
import com.yrainy.aiagentclient.entity.dto.OpenAiResponseParseDTO;
import com.yrainy.aiagentclient.client.AbstractOpenAiAgentClientSupport;
import com.yrainy.aiagentclient.client.AbstractParseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 业务智能体客户端
 *
 * @author zhanglun
 * @since 2026-09-17
 */
@Service
@Slf4j
public class BizAiAgentClient extends AbstractOpenAiAgentClientSupport<AbstractParseResult.ShareParseResult> {

    @Override
    protected AbstractParseResult.ShareParseResult afterParse(OpenAiResponseParseDTO parseDTO, AiTaskPO aiTaskPO) {
        String output = parseDTO.getOutput();
        log.info("业务处理");
        return AbstractParseResult.ShareParseResult.defaultParseResult();
    }
}
