package com.yrainy.aiagentclient.client;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yrainy.aiagentclient.entity.AiAgentPO;
import com.yrainy.aiagentclient.entity.AiTaskPO;
import com.yrainy.aiagentclient.entity.dto.*;
import com.yrainy.aiagentclient.enums.AgentPlatformEnum;
import com.yrainy.aiagentclient.parser.ModuleParserFactory;
import com.yrainy.aiagentclient.parser.module.AgentModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 抽象支持OpenAI协议的Agent客户端
 *
 * @author zhanglun
 * @since 2026-02-28
 */
@Slf4j
public abstract class AbstractOpenAiAgentClientSupport<T extends AbstractParseResult>
        extends AgentClientParserSupport<T> implements IAgentClient<T> {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected ModuleParserFactory moduleParserFactory;

    @Override
    public AgentClientInvokeDTO<T> invoke(AiTaskPO aiTaskPO, List<OpenAiRequestContent> content) {
        AiAgentPO aiAgentPO = getAiAgent(aiTaskPO);
        AgentPlatformEnum agentPlatformEnum = AgentPlatformEnum.ofCodeOrElseThrowsException(aiAgentPO.getAgentPlatform());
        if (agentPlatformEnum.supportOpenAiApiProtocol()) {
            return invokeSupportOpenAiApiProtocolAgent(aiTaskPO, content, agentPlatformEnum);
        }
        switch (agentPlatformEnum) {
            case HI_AGENT:
                return HiAgentClientAdapter.adapt(aiAgentPO, this).invoke(aiTaskPO, content);
            default:
                throw new IllegalArgumentException(String.format("暂无%s的适配", agentPlatformEnum.getName()));
        }

    }

    /**
     * 调用支持OpenAI API协议的智能体
     *
     * @param aiTaskPO
     * @param content
     * @param agentPlatform
     * @return
     */
    private AgentClientInvokeDTO<T> invokeSupportOpenAiApiProtocolAgent(AiTaskPO aiTaskPO, List<OpenAiRequestContent> content, AgentPlatformEnum agentPlatform) {
        ResponseEntity<String> response = executeHttpInvoke(aiTaskPO, content, agentPlatform);
        // 解析响应
        OpenAiResponseParseDTO parseDTO = null;
        try {
            parseDTO = parse(response, agentPlatform);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 解析之后，数据落表
        T parseResult = afterParse(parseDTO, aiTaskPO);

        return new AgentClientInvokeDTO<T>()
                .setResponse(response.getBody())
                .setOpenAiResponseParseDTO(parseDTO)
                .setParseResult(parseResult)
                .setAgentModuleList(parseDTO.getAgentModuleList());
    }

    protected String getLogPrefix(AiTaskPO aiTaskPO) {
        return StrUtil.format("AI任务编号：{}------------", aiTaskPO.getAiTaskId());
    }


    @Override
    public AiAgentPO getAiAgent(AiTaskPO aiTaskPO) {
        // 查询数据库
        log.info("{}获取智能体", getLogPrefix(aiTaskPO));
        return new AiAgentPO()
                .setUrl("https://api.fastgpt.in/api/v1/chat/completions")
                .setToken("fastgpt-d6UFKZgP36O5GEvclKWMrW1OyxQVZMCrHSwpqWWrNqsoJM8jZI5iHPo0uWDszhkUf")
                .setAgentPlatform("")
                ;
    }

    protected ResponseEntity<String> executeHttpInvoke(AiTaskPO aiTaskPO, List<OpenAiRequestContent> content, AgentPlatformEnum agentPlatform) {

        AiAgentPO aiAgentPO = getAiAgent(aiTaskPO);
        OpenAiRequest<OpenAiRequestContent> openAiRequest = new OpenAiRequest<>();
        OpenAiRequest.Message<OpenAiRequestContent> message = new OpenAiRequest.Message<>();
        message.setContent(content);
        openAiRequest.setMessages(Collections.singletonList(message));
        openAiRequest.setAppId(aiAgentPO.getAgentId());
        openAiRequest.setChatId(RandomStringUtils.randomAlphanumeric(24));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + aiAgentPO.getToken());

        HttpEntity<OpenAiRequest<OpenAiRequestContent>> request = new HttpEntity<>(openAiRequest, httpHeaders);
        long st = System.currentTimeMillis();
        ResponseEntity<String> response = restTemplate.exchange(
                aiAgentPO.getUrl(),
                HttpMethod.POST,
                request,
                String.class);
        long et = System.currentTimeMillis();
        log.info("【{}】调用耗时{}ms", agentPlatform.getName(), et - st);
        log.debug("【{}】调用返回结果：{}", agentPlatform.getName(), response.getBody());
        return response;
    }

    protected OpenAiResponseParseDTO parse(ResponseEntity<String> response, AgentPlatformEnum agentPlatformEnum) throws Exception {
        List<AgentModule> agentModuleList = new ArrayList<>();
        OpenAiResponseParseDTO openAiResponseParseDTO = new OpenAiResponseParseDTO();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        OpenAiResponse openAiResponse = objectMapper.convertValue(jsonNode, OpenAiResponse.class);

        for (JsonNode responseData : jsonNode.path("responseData")) {
            // 开始解析节点
            moduleParserFactory.getParser(responseData.path("moduleType").asText())
                    .ifPresent(parse -> agentModuleList.add(parse.parse(responseData)));
        }
        return openAiResponseParseDTO.setAgentModuleList(agentModuleList);
    };

    protected abstract T afterParse(OpenAiResponseParseDTO parseDTO, AiTaskPO aiTaskPO);
}
