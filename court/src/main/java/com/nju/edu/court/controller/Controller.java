package com.nju.edu.court.controller;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Zyi
 */
@RestController
public class Controller {

    StanfordCoreNLP pipeline;

    @GetMapping("/nlp")
    public String getStr(@RequestParam(value = "text", defaultValue = "我是一名大学生") String text) {
        init();
        return segInCh(text);
    }

    public List<String> get(String text) {
        return null;
    }

    public void init() {
        pipeline = new StanfordCoreNLP("StanfordCoreNLP-chinese.properties");
    }

    private String segInCh(String text) {
        // 载入properties文件
        Annotation annotation = new Annotation(text);

        // 解析
        pipeline.annotate(annotation);
        pipeline.prettyPrint(annotation, System.out);
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);

        List<String> res = new ArrayList<>();
        for (CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                res.add(word);
            }
        }

        StringBuilder tokens = new StringBuilder();
        for (String word : res) {
            tokens.append(word);
            tokens.append("\r\n");
        }

        return tokens.toString();
    }

    public static void main(String[] args) throws Exception {
        Controller controller = new Controller();
        String res = controller.getStr("我是一名大学生，现在在南京读书，我的专业是软件工程");
        System.out.println(res);
    }
}
