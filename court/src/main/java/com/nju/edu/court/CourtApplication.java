package com.nju.edu.court;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zyi
 */
@SpringBootApplication
@RestController
public class CourtApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(CourtApplication.class, args);
    }
}
