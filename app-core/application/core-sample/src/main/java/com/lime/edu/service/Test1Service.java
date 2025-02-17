package com.lime.edu.service;

import com.lime.edu.test1.repository.Test1SampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Test1Service {

    private final Test1SampleRepository test1SampleRepository;


    public String test1() {
        return test1SampleRepository.sample();
    }
}
