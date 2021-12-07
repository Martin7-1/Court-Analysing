package com.nju.edu.court.controller;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ControllerTest {

    private static Controller controller;

    @BeforeClass
    public static void setUp() {
        controller = new Controller();
        controller.init();
    }

    @Test
    public void NLPTest1() {
        String sentence = "我是一名大学生";
        List<String> actual = controller.getStr(sentence);

        for (String word : actual) {
            System.out.println(word);
        }

        List<String> expect = new ArrayList<>();
        expect.add("我");
        expect.add("是");
        expect.add("一");
        expect.add("名");
        expect.add("大学生");

        for (int i = 0; i < actual.size(); i++) {
            assertEquals(expect.get(i), actual.get(i));
        }
    }

    @Test
    public void NLPTest2() throws Exception {
        BufferedReader in = new BufferedReader(new FileReader("D:\\Java\\Programs\\Court\\court\\src\\test\\java\\com\\nju\\edu\\court\\controller\\courtIn.txt"));
        BufferedWriter out = new BufferedWriter(new FileWriter("D:\\Java\\Programs\\Court\\court\\src\\test\\java\\com\\nju\\edu\\court\\controller\\courtOut.txt"));
        List<String> sentences = new ArrayList<>();
        String input;
        while (((input = in.readLine()) != null)) {
            sentences.add(input);
        }
        List<String> res;

        try {
            for (String sentence : sentences) {
                res = controller.getStr(sentence);
                for (String word : res) {
                    out.write(word);
                    out.write("\r\n");
                }
            }
        } finally {
            in.close();
            out.close();
        }

        assertEquals(10, 10);
    }
}
