package com.yrainy.aiagentclient.parser.module;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 插件节点
 *
 * @author zhanglun
 * @since 2026-02-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PluginModule extends AbstractAgentModule {

    // 可能是任何类型
    private Object pluginOutput;

    private List<AgentModule> pluginDetail;

    public void addPluginDetail(AgentModule agentModule) {
        Optional.ofNullable(pluginDetail).orElseGet(() ->
                this.setPluginDetail(new ArrayList<>()).getPluginDetail()).add(agentModule);
    }
}
