package com.nju.edu.court.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 访问template下的页面
 * @author Zyi
 */
@Controller
public class Action {

    @GetMapping("/index")
    public String getPage() {
        return "index";
    }
}
