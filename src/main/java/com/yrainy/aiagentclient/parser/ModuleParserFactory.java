package com.yrainy.aiagentclient.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yrainy.aiagentclient.parser.adapter.*;
import com.yrainy.aiagentclient.parser.module.AgentModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 节点解析工厂
 *
 * @author zhanglun
 * @since 2026-02-28
 */
@Component
@Slf4j
public class ModuleParserFactory implements InitializingBean {

    @Autowired
    private ObjectMapper objectMapper;

    private List<ModuleParserAdapter<? extends AgentModule>> moduleParserList = new ArrayList<>();


    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("开始注册Agent解析器----------------------------------------------------------");
        moduleParserList.add(new ChatNodeParserAdapter(objectMapper, this));
        moduleParserList.add(new CodeParserAdapter());
        moduleParserList.add(new PluginParserAdapter(objectMapper, this));
        moduleParserList.add(new AnswerNodeParserAdapter());
        log.info("完成注册Agent解析器----------------------------------------------------------");

    }

    /**
     * 获取节点解析器
     *
     * @param moduleType 节点类型
     * @return Optional<ModuleParserAdapter<? extends AgentModule>>
     */
    public Optional<ModuleParserAdapter<? extends AgentModule>> getParser(String moduleType) {
        Optional<ModuleParserAdapter<? extends AgentModule>> parser = moduleParserList.stream()
                .filter(moduleParserAdapter -> moduleParserAdapter.supports(moduleType)).findFirst();
        return parser;
    }
}
