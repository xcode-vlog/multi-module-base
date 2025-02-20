package com.lime.edu.sample;

import com.lime.edu.service.Test1Service;
//import com.lime.token.provider.TokenProvider;
import com.lime.token.provider.TokenProvider;
import com.lime.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class SampleController {

    private final Test1Service test1Service;
    private final TokenProvider tokenProvider;

    @GetMapping("/")
    public String index() {
        Map<String, Object> map = new HashMap<>();
        map.put("test1", "test1");
        tokenProvider.createToken(map);
        return CommonUtils.test();

    }

    @GetMapping("/test1")
    public String test1() {
        return test1Service.test1();
    }
}
