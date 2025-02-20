package com.lime.jasypt.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.jasypt.encryption.pbe.config.StringPBEConfig;
import org.jasypt.util.password.PasswordEncryptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfig {

    @Bean
    @ConfigurationProperties(prefix = "jasypt.encryptor")
    public StringPBEConfig simpleStringPBEConfig() {
        return new SimpleStringPBEConfig();
    }

    @Bean

    public StringEncryptor jasyptStringEncryptor(StringPBEConfig config) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(config);
        return encryptor;
    }
}
