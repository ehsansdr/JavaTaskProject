package com.example.JavaTaskProject.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demoController-controller")
public class demoController {
    @GetMapping
    public ResponseEntity<String> sayHello() {
        //this will return simple string with body and status ok
        return ResponseEntity.ok("Hello from secured endpoint and it is done dude!!!!!!");
    }
}
