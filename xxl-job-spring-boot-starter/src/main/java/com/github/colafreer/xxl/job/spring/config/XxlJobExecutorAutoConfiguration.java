package com.github.colafreer.xxl.job.spring.config;

import com.github.colafreer.xxl.job.spring.annotation.EnableXxlJobExecutor;
import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ren
 * @since 2023/12/18 23:51
 */
@Configuration
@ConditionalOnBean(annotation = EnableXxlJobExecutor.class)
@EnableConfigurationProperties(XxlJobExecutorProperties.class)
public class XxlJobExecutorAutoConfiguration {
    private final XxlJobExecutorProperties properties;

    public XxlJobExecutorAutoConfiguration(XxlJobExecutorProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean(XxlJobExecutor.class)
    public XxlJobExecutor xxlJobExecutor() {
        XxlJobExecutor executor = new XxlJobSpringExecutor();
        executor.setAppname(properties.getAppName());
        executor.setAdminAddresses(properties.getAdminAddress());
        executor.setAccessToken(properties.getAccessToken());
        executor.setAddress(properties.getAddress());
        executor.setIp(properties.getIp());
        executor.setPort(properties.getPort());
        executor.setLogPath(properties.getLogPath());
        executor.setLogRetentionDays(properties.getLogRetentionDays());
        return executor;
    }
}
