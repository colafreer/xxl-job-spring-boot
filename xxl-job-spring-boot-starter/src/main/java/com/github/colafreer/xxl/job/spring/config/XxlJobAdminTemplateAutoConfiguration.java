package com.github.colafreer.xxl.job.spring.config;

import com.github.colafreer.xxl.job.spring.AdminTemplate;
import com.github.colafreer.xxl.job.spring.FeignTemplateImpl;
import com.github.colafreer.xxl.job.spring.annotation.EnableXxlJobAdminTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ren
 * @since 2023/12/18 23:46
 */
@Configuration
@ConditionalOnBean(annotation = EnableXxlJobAdminTemplate.class)
@EnableConfigurationProperties(XxlJobAdminProperties.class)
public class XxlJobAdminTemplateAutoConfiguration {
    private final XxlJobAdminProperties properties;

    public XxlJobAdminTemplateAutoConfiguration(XxlJobAdminProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean(AdminTemplate.class)
    public AdminTemplate adminTemplate() {
        return new FeignTemplateImpl(properties);
    }
}
