package com.nju.edu.court.controller;

import com.nju.edu.court.entity.Analysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author Zyi
 */
@RestController
public class Controller {

    @Autowired
    private Analysis analysis;

    @GetMapping()
    public String init() {
        return "Welcome to court analyse!";
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        // http://localhost:8080/hello
        return String.format("Hello %s!", name);
    }

    /**
     * 返回词性分析的结果
     * @param text 待分析的文书
     * @return 词性分析结果，词性 - 对应的单词
     */
    @GetMapping("/getResult")
    public Map<String, List<String>> sendMessage(@RequestParam(value = "text", defaultValue = "我是一名大学生") String text) {
        // 清除之前的分析内容
        analysis.clear();
        analysis.setParagraph(text);
        analyse();
        return analysis.getRes();
    }

    private void analyse() {
        this.analysis.analyse();
    }
}
