package com.yrainy.aiagentclient.parser.module;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 指定回复节点
 *
 * @author zhanglun
 * @since 2026-02-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class AnswerNodeModule extends AbstractAgentModule {

    private String textOutput;
}
