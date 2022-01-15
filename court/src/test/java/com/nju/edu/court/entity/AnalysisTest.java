package com.nju.edu.court.entity;

import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalysisTest {

    private static Analysis analysis;
    private Map<String, List<String>> actual = new HashMap<>();
    private Map<String, List<String>> expect = new HashMap<>();

    @BeforeClass
    public static void setUp() {
        analysis = new Analysis();
    }

    @Before
    public void clear() {
        actual.clear();
        expect.clear();
    }

    @Test
    public void test1() {
        String text = "我是一名来自江苏南京的大学生，就读于软件工程专业";
        analysis.setParagraph(text);
        analysis.analyse();
        actual = analysis.getRes();
        System.out.println(actual);
    }

    @Test
    public void test2() {
        String text = "申诉人（申请执行人）：王x琼，女，汉族，1956年9月24日出生，住贵州省贵阳市乌当区。\n" +
                "委托诉讼代理人：吴昱辰，贵州天职律师事务所律师。\n" +
                "利害关系人：贵州永福矿业有限公司金沙县化觉乡永晟煤矿，住所地：贵州省毕节市金沙县化觉乡前顺村。\n" +
                "法定代表人：贾国洪。\n" +
                "被执行人：李x，男，汉族，1967年2月1日出生，住湖北省武汉市洪山区。\n" +
                "被执行人：湖北永福投资有限公司，住所地：湖北省武汉市武昌区东湖西路景天楼";
        analysis.setParagraph(text);
        analysis.analyse();
        actual = analysis.getRes();
        System.out.println(actual);
    }

    @Test
    public void test3() {
        String text = "贵州省高级人民法院";
        analysis.setParagraph(text);
        analysis.analyse();
        actual = analysis.getRes();
        System.out.println(actual);
    }
}
