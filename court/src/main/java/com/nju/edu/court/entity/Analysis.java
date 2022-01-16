package com.nju.edu.court.entity;

import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    public Analysis(String paragraph) {
        this.paragraph = paragraph;
        this.res = new HashMap<>();
        System.out.println("the map is init!");
    }

    public Analysis() {
        this.res = new HashMap<>();
        System.out.println("the map is init!");
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
        if (res.isEmpty()) {
            throw new RuntimeException("Result is Empty!");
        }
        return res;
    }

    /**
     * 对文本进行词性分析
     */
    public void analyse() {
        if (this.paragraph == null) {
            throw new NullPointerException("The text is null!");
        }

        tokenize();
    }

    private void tokenize() {
        // TODO: 打造自己的词典，训练自己的模型
        List<Term> termList = StandardTokenizer.segment(paragraph);
        // 根据词性分词
        for (Term term : termList) {
            if (term.nature.startsWith('n') || "l".equals(term.nature.toString())) {
                // 名词
                if (!res.containsKey("noun")) {
                    List<String> temp = new ArrayList<>();
                    temp.add(term.word);
                    res.put("noun", temp);
                } else {
                    List<String> temp = res.get("noun");
                    temp.add(term.word);
                }
            } else if (term.nature.startsWith('v')) {
                if (!res.containsKey("verb")) {
                    List<String> temp = new ArrayList<>();
                    temp.add(term.word);
                    res.put("verb", temp);
                } else {
                    List<String> temp = res.get("verb");
                    temp.add(term.word);
                }
            } else if ("a".equals(term.nature.toString())) {
                if (!res.containsKey("adj")) {
                    List<String> temp = new ArrayList<>();
                    temp.add(term.word);
                    res.put("adj", temp);
                } else {
                    List<String> temp = res.get("adj");
                    temp.add(term.word);
                }
            }
        }
    }

    /**
     * 清除之前分析的内容
     */
    public void clear() {
        this.res.clear();
    }
}
