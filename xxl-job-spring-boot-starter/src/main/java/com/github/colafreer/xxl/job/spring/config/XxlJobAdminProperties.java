package com.github.colafreer.xxl.job.spring.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ren
 * @since 2023/12/18 23:45
 */
@Data
@ConfigurationProperties(prefix = "xxl.job.admin")
public class XxlJobAdminProperties {
    private String adminAddress;
    private String username;
    private String password;
}
