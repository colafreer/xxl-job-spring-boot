package com.github.colafreer.xxl.job.spring.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author ren
 * @since 2023/12/19 00:04
 */
@Data
@Builder
public class XxlJobPageParam {
    @Builder.Default
    private Integer start = 0;
    @Builder.Default
    private Integer length = 10;
    @Builder.Default
    private Integer jobGroup = -1;
    @Builder.Default
    private int triggerStatus = 0;
    private String jobDesc;
    private String executorHandler;
    private String author;
}
