package com.nju.edu.court.controller;

import com.nju.edu.court.entity.Analysis;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Zyi
 */
@RestController
public class Controller {

    private Analysis analysis;

    /**
     * 返回词性分析的结果
     * @return 词性分析结果，词性 - 对应的单词
     */
    @GetMapping("/getResult")
    public Map<String, List<String>> sendMessage() {
        return null;
    }

    /**
     * 获得前端传过来的文本数据
     * @param text 文书内容
     */
    @RequestMapping("/requestText")
    public void getMessage(@RequestParam(value = "text", defaultValue = "我是一名大学生") String text) {

    }
}
