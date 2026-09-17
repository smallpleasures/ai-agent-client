package com.yrainy.aiagentclient.parser.module;

import lombok.Getter;
import lombok.Setter;

/**
 * 抽象节点结构
 *
 * @author zhanglun
 * @since 2026-02-28
 */
@Getter
@Setter
public abstract class AbstractAgentModule implements AgentModule {

    private String moduleType;
    private String moduleName;
    private String moduleLogo;
    private Double runningTime;
    private String id;
    private String nodeId;
}
