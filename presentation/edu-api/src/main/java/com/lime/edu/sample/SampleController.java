package com.lime.edu.sample;

import com.lime.edu.service.Test1Service;
import lombok.RequiredArgsConstructor;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class SampleController {

    @Value("${sample.encrypt.test1}")
    String test1;
    @Value("${sample.encrypt.test2}")
    String test2;

    private final Test1Service test1Service;
    private final StringEncryptor jasyptStringEncryptor;

    @GetMapping("/")
    public String index() {
        Map<String, Object> map = new HashMap<>();
        map.put("test1", "test1");
        return test1 + " | " + test2;

    }

    @GetMapping("/test1")
    public String test1() {
        return test1Service.test1();
    }
}
