package com.yrainy.aiagentclient.client;

import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yrainy.aiagentclient.entity.AiAgentPO;
import com.yrainy.aiagentclient.entity.AiTaskPO;
import com.yrainy.aiagentclient.entity.dto.*;
import com.yrainy.aiagentclient.enums.AgentPlatformEnum;
import com.yrainy.aiagentclient.parser.module.AgentModule;
import com.yrainy.aiagentclient.parser.module.AnswerNodeModule;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * 抽象HiAgent客户端
 *
 * @author zhanglun
 * @since 2026-09-13
 */
@Slf4j
public abstract class AbstractHiAgentClient<T extends AbstractParseResult> extends AgentClientParserSupport<T> implements IAgentClient<T> {

    private ObjectMapper objectMapper = SpringUtil.getBean(ObjectMapper.class);
    private RestTemplate restTemplate = SpringUtil.getBean(RestTemplate.class);

    private static final String HEADER_APIKEY = "Apikey";

    @Override
    protected OpenAiResponseParseDTO parse(ResponseEntity<String> response, AgentPlatformEnum agentPlatformEnum) throws Exception {
        List<AgentModule> agentModuleList = new ArrayList<>();
        OpenAiResponseParseDTO openAiResponseParseDTO = new OpenAiResponseParseDTO();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());

        HiAgentResponse choices = objectMapper.convertValue(jsonNode, HiAgentResponse.class);
        AnswerNodeModule answerNodeModule = new AnswerNodeModule();
        answerNodeModule.setModuleType("end");
        String output = objectMapper.readValue(choices.getOutput(), OutputContract.class).getOutput();
        answerNodeModule.setTextOutput(output);
        agentModuleList.add(answerNodeModule);
        return openAiResponseParseDTO.setAgentModuleList(agentModuleList);
    }

    /**
     * 约定HiAgent智能体end节点参数为output
     */
    @Data
    private static class OutputContract {
        private String output;
    }

    @Override
    public AiAgentPO getAiAgent(AiTaskPO aiTaskPO) {
        // 查询数据库
        return new AiAgentPO()
                .setUrl("")
                .setToken("")
                .setAgentPlatform(AgentPlatformEnum.HI_AGENT.getCode())
                ;
    }

    @Override
    public AgentClientInvokeDTO<T> invoke(AiTaskPO aiTaskPO, List<OpenAiRequestContent> content) {
        HiAgentRequest hiAgentRequest = convertToHiAgentRequest(content);
        String agentPlatform = getAiAgent(aiTaskPO).getAgentPlatform();
        AgentPlatformEnum agentPlatformEnum = AgentPlatformEnum.ofCodeOrElseThrowsException(agentPlatform);
        // 转换为HiAgent请求体
        ResponseEntity<String> response = executeHttpInvoke(aiTaskPO, hiAgentRequest, agentPlatformEnum);
        // 解析响应
        OpenAiResponseParseDTO parseDTO = null;
        try {
            parseDTO = parse(response, agentPlatformEnum);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 解析之后，业务逻辑处理
        T parseResult = afterParse(parseDTO, aiTaskPO);

        return new AgentClientInvokeDTO<T>()
                .setResponse(response.getBody())
                .setOpenAiResponseParseDTO(parseDTO)
                .setParseResult(parseResult)
                .setAgentModuleList(parseDTO.getAgentModuleList());
    }

    private ResponseEntity<String> executeHttpInvoke(AiTaskPO aiTaskPO, HiAgentRequest hiAgentRequest, AgentPlatformEnum agentPlatformEnum) {

        AiAgentPO aiAgent = getAiAgent(aiTaskPO);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set(HEADER_APIKEY, aiAgent.getToken());

        HttpEntity<HiAgentRequest> request = new HttpEntity<>(hiAgentRequest, httpHeaders);
        long st = System.currentTimeMillis();
        ResponseEntity<String> response = restTemplate.exchange(
                aiAgent.getUrl(),
                HttpMethod.POST,
                request,
                String.class);
        long et = System.currentTimeMillis();
        log.info("【{}】调用耗时{}ms", agentPlatformEnum.getName(), et - st);
        log.debug("【{}】调用返回结果：{}", agentPlatformEnum.getName(), response.getBody());
        return response;
    }

    /**
     * 转换为HiAgent请求
     *
     * @param content
     * @return
     */
    private HiAgentRequest convertToHiAgentRequest(List<OpenAiRequestContent> content) {

        HiAgentRequest hiAgentRequest = new HiAgentRequest();
        hiAgentRequest.setUserId("system");
        StringJoiner text = new StringJoiner("。");
        List<HiAgentRequest.VariableContract.ImageUrl> imageUrls = new ArrayList<>();
        for (OpenAiRequestContent openAiRequestContent : content) {
            if (openAiRequestContent instanceof OpenAiRequestContent.OpenAiTextContent) {
                text.add(((OpenAiRequestContent.OpenAiTextContent) openAiRequestContent).getText());
            } else if (openAiRequestContent instanceof OpenAiRequestContent.OpenAiImageContent) {
                OpenAiRequestContent.OpenAiImageContent.ImageUrl imageUrl = ((OpenAiRequestContent.OpenAiImageContent) openAiRequestContent).getImageUrl();
                imageUrls.add(new HiAgentRequest.VariableContract.ImageUrl(imageUrl.getUrl()));
            }
        }
        hiAgentRequest.setVariableContract(new HiAgentRequest.VariableContract(text.toString(), imageUrls));
        return hiAgentRequest;
    }
}
