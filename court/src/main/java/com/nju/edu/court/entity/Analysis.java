package com.nju.edu.court.entity;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分析类
 * @author Zyi
 */
@Component
public class Analysis {

    private String paragraph;
    private Map<String, List<String>> res;
    private StanfordCoreNLP pipeline;

    public Analysis(String paragraph) {
        this.paragraph = paragraph;
        this.res = new HashMap<>();
        System.out.println("the map is init!");
        init();
    }

    public Analysis() {
        this.res = new HashMap<>();
        System.out.println("the map is init!");
    }

    private void init() {
        // 加载默认的配置文件
        // 后续可以根据自己的需求进行修改
        this.pipeline = new StanfordCoreNLP("StanfordCoreNLP-chinese.properties");
    }

    /**
     * 获得需要分析的内容
     * @return 文书内容
     */
    public String getParagraph() {
        return paragraph;
    }

    public void setParagraph(String paragraph) {
        this.paragraph = paragraph;
    }

    /**
     * 获得分析完成的结果
     * @return 完成词性标注后的结果
     */
    public Map<String, List<String>> getRes() {
        return res;
    }

    /**
     * 对文本进行词性分析
     */
    public void analyse() {
        if (this.paragraph == null) {
            throw new NullPointerException("The text is null!");
        }

        List<Term> termList = StandardTokenizer.segment(paragraph);
        // 根据词性分词
    }

    public static void main(String[] args) {
        System.out.println(HanLP.segment("你好，欢迎使用HanLP汉语处理包！"));
        List<Term> termList = StandardTokenizer.segment("商品和服务");
        System.out.println(termList);
    }
}
