package com.example.demo;

import org.springframework.web.bind.annotation.*;

@RestController
public class SampleController {

    // preHandle
    // 요청처리
    // postHandler
    // 뷰 렌더링
    // afterCompletion

    @GetMapping("hello")
    public String hello(@RequestParam("id") Person person){
        return "hello " + person.getName();
    }

    @GetMapping("/message")
    public String message(@RequestBody String body){
        return "hello";
    }

    @GetMapping("/jsonMessage")
    public Person jsonMessage(@RequestBody Person person) {
        return person;
    }
}
