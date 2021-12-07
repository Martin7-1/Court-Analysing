package com.nju.edu.court.controller;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Zyi
 */
@RestController
@RequestMapping("/test")
public class Controller {

    StanfordCoreNLP pipeline;

    @RequestMapping("getStr")
    public List<String> getStr(String text) {
        return segInCh(text);
    }

    public void init() {
        pipeline = new StanfordCoreNLP("StanfordCoreNLP-chinese.properties");
    }

    private List<String> segInCh(String text) {
        // 载入properties文件
        Annotation annotation = new Annotation(text);

        // 解析
        pipeline.annotate(annotation);
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);

        List<String> res = new ArrayList<>();
        for (CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                res.add(word);
            }
        }

        return res;
    }
}
