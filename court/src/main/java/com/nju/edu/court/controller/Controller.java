package com.nju.edu.court.controller;

import com.nju.edu.court.entity.Analysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    /**
     * 返回词性分析的结果
     * @param text 待分析的文书
     * @return 词性分析结果，词性 - 对应的单词
     */
    @GetMapping("/getResult")
    public Map<String, List<String>> sendMessage(@RequestParam(value = "text", defaultValue = "我是一名大学生") String text) {
        analysis.setParagraph(text);
        analyse();
        return analysis.getRes();
    }

    private void analyse() {
        this.analysis.analyse();
    }
}
