package com.nju.edu.court.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zyi
 */
@RestController
public class HelloController {

    @GetMapping("/")
    public String index() {
        return "Greeting from Spring boot!";
    }
}
