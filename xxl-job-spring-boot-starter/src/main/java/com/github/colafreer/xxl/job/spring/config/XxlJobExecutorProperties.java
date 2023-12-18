package com.github.colafreer.xxl.job.spring.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ren
 * @since 2023/12/18 23:52
 */
@Data
@ConfigurationProperties(prefix = "xxl.job.executor")
public class XxlJobExecutorProperties {
    private String adminAddress;
    private String accessToken;
    private String appName;
    private String address;
    private String ip;
    private int port;
    private String logPath;
    private int logRetentionDays;
}
