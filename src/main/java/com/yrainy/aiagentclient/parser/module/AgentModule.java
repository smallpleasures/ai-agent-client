package com.yrainy.aiagentclient.parser.module;

/**
 * 节点公共属性
 *
 * @author zhanglun
 * @since 2026-02-28
 */
public interface AgentModule {

    /**
     * 获取节点类型
     *
     * @return
     */
    String getModuleType();

    /**
     * 获取节点名称
     *
     * @return
     */
    String getModuleName();

    String getModuleLogo();

    /**
     * 获取运行时长
     *
     * @return
     */
    Double getRunningTime();

    String getId();

    String getNodeId();
}
