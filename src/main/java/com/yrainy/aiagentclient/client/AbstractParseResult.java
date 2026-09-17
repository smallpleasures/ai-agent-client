package com.yrainy.aiagentclient.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *
 * @author zhanglun
 * @since 2026-09-13
 */
public abstract class AbstractParseResult {

    @EqualsAndHashCode(callSuper = true)
    @Data
    @Accessors(chain = true)
    public static class ShareParseResult extends AbstractParseResult {

        public static ShareParseResult defaultParseResult() {
            return new ShareParseResult();
        }
    }
}
