package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/th/3ice")
public class MainController {

    @GetMapping("/index")
    public void index(){
        log.info("Get/th/3ice/index");
    }

    @GetMapping("/join")
    public void join(){
        log.info("Get/th/3ice/join");
    }

    @GetMapping("/myPage")
    public void myPage(){
        log.info("Get/th/3ice/myPage");
    }

    @GetMapping("/update")
    public void update(){
        log.info("Get/th/3ice/update");
    }

    @GetMapping("/withdraw")
    public void withdraw(){
        log.info("Get/th/3ice/withdraw");
    }


}
