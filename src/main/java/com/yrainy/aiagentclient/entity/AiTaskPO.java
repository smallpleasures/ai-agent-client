package com.yrainy.aiagentclient.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * AI任务
 *
 * @author zhanglun
 * @since 2026-02-28
 */
@Data
@Accessors(chain = true)
public class AiTaskPO {

    /**
     * 任务ID
     */
    private String aiTaskId;

    /**
     * 父AI任务ID
     */
    private String parentAiTaskId;

    /**
     * 智能体编号
     */
    private String agentId;

    /**
     * 识别材料
     */
    private String taskType;

    /**
     * doc版本
     */
    private String docVersion;

    /**
     * 识别状态
     */
    private String status;
}
