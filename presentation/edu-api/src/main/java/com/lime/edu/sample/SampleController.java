package com.lime.edu.sample;

import com.lime.edu.service.Test1Service;
import com.lime.edu.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SampleController {

    private final Test1Service test1Service;

    @GetMapping("/")
    public String index() {
        return CommonUtils.test();

    }

    @GetMapping("/test1")
    public String test1() {
        return test1Service.test1();
    }
}
