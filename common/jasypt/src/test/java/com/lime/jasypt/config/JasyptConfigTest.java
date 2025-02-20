package com.lime.jasypt.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.jasypt.encryption.pbe.config.StringPBEConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import static org.junit.jupiter.api.Assertions.*;


@ConfigurationPropertiesScan
class JasyptConfigTest {
//    password: "test"
//    algorithm: "PBEWITHHMACSHA512ANDAES_256"
//    key-obtention-iterations: 1000
//    pool-size: 1
//    provider-name: "SunJCE"
//    salt-generator-classname: "org.jasypt.salt.RandomSaltGenerator"
//    iv-generator-classname: "org.jasypt.iv.RandomIvGenerator"
//    string-output-type: "base64"
//    proxy-property-sources: false




    String password = "test";
    String algorithm = "PBEWITHHMACSHA512ANDAES_256";
    int keyObtentionIterations = 1000;
    int poolSize = 1;
    String providerName = "SunJCE";
    String saltGeneratorClassname = "org.jasypt.salt.RandomSaltGenerator";
    String ivGeneratorClassname = "org.jasypt.iv.RandomIvGenerator";
    String stringOutputType = "base64";

    StringEncryptor stringEncryptor;

    @BeforeEach
    void setup() {
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);
        config.setAlgorithm(algorithm);
        config.setKeyObtentionIterations(keyObtentionIterations);
        config.setPoolSize(poolSize);
        config.setProviderName(providerName);
        config.setSaltGeneratorClassName(saltGeneratorClassname);
        config.setIvGeneratorClassName(ivGeneratorClassname);
        config.setStringOutputType(stringOutputType);

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(config);
        stringEncryptor = encryptor;
    }

    @ParameterizedTest
    @ValueSource(strings = {"test1234", "lime123!"})
    void jasyptConfig(String input) {
        String encrypt = stringEncryptor.encrypt(input);
        String decrypt = stringEncryptor.decrypt(encrypt);
        System.out.println("%s : %s".formatted(input, encrypt));
        assertEquals(input, decrypt);

    }

}