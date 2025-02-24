package com.lime.jasypt.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.jasypt.encryption.pbe.config.StringPBEConfig;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfig {

    @Bean("limeDefaultJasyptConfig")
    @ConfigurationProperties(prefix = "jasypt.encryptor")
    public StringPBEConfig simpleStringPBEConfig() {
        return new SimpleStringPBEConfig();
    }

    @Bean
    @ConditionalOnBean(name = "limeDefaultJasyptConfig")
    @ConditionalOnMissingBean(name = "jasyptStringEncryptor")
    public StringEncryptor jasyptStringEncryptor(StringPBEConfig config) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(config);
        return encryptor;
    }
}
