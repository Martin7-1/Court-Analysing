package com.nju.edu.court.controller;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        String newText = segInCh(text);
        return new ArrayList<>(Arrays.asList(newText.split("\n")));
    }

    public void init() {
        pipeline = new StanfordCoreNLP("StanfordCoreNLP-chinese.properties");
    }

    private String segInCh(String text) {
        // 载入properties文件
        Annotation annotation = new Annotation(text);

        // 解析
        pipeline.annotate(annotation);
        // pipeline.prettyPrint(annotation, System.out);
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);

        List<String> res = new ArrayList<>();
        for (CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                res.add(word);
            }
        }

        output(sentences);

        StringBuilder tokens = new StringBuilder();
        for (String word : res) {
            tokens.append(word);
            tokens.append("\n");
        }

        return tokens.toString();
    }

    private void output(List<CoreMap> sentences) {
        for (CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                // 获取分词
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                // 获取词性标注
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                // 获取命名实体识别结果
                String ner = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                // 获取词形还原结果
                String lemma = token.get(CoreAnnotations.LemmaAnnotation.class);
                System.out.println(word + "\t" + pos + "\t" + ner + "\t" + lemma);
            }

            Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
            System.out.println(tree.toString());
        }
    }

    public static void main(String[] args) throws Exception {
        Controller controller = new Controller();
        String res = controller.getStr("我是一名大学生，现在在南京读书，我的专业是软件工程");
        System.out.println(res);
    }
}
