package com.yrainy.aiagentclient.parser.module;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 代码运行节点
 *
 * @author zhanglun
 * @since 2026-02-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CodeModule extends AbstractAgentModule {

    private CustomInput customInputs;
    private String codeLog;
    private Object customOutputs;

    @Data
    @Accessors(chain = true)
    public static class CustomInput {
        private String data;
    }
}
