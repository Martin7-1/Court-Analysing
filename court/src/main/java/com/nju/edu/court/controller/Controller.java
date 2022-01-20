package com.nju.edu.court.controller;

import com.nju.edu.court.entity.Analysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * 控制类，进行与前端的数据传输和对Bean的控制
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
    @RequestMapping(value = "/getResult", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin(origins = "*")
    public Map<String, List<String>> sendMessage(@RequestParam(value = "text", defaultValue = "我是一名大学生") String text) {
        // 清除之前的分析内容
        analysis.clear();
        analysis.setParagraph(text);
        analyse();
        return analysis.getRes();
    }

    /**
     * 接受一个文件的传输，返回词法分析的结果
     * @param uploadFile 用户上传的从前端接收的文件
     * @return 词法分析的结果
     * @throws IOException 文件接收异常
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin(origins = "*")
    public Map<String, List<String>> uploadFile(@RequestParam("uploadFile") MultipartFile uploadFile) throws IOException {
        if (uploadFile == null) {
            // 接收失败
            return null;
        }

        InputStream inputStream = uploadFile.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        StringBuilder temp = new StringBuilder();

        while (reader.ready()) {
            temp.append(reader.readLine());
        }

        // analyse
        String content = temp.toString();
        System.out.println(content);
        analysis.clear();
        analysis.setParagraph(content);
        analyse();
        System.out.println(analysis.getRes());
        return analysis.getRes();
    }

    /**
     * 接收多个文件的传输，返回词法分析的结果
     * @param uploadFiles 用户上传的从前端接收的文件
     * @return 多个文件词法分析的结果，以数组形式返回
     * @throws IOException 文件接收或传输异常
     */
    @RequestMapping(value = "/uploadFiles", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin(origins = "*")
    public Map<String, List<String>>[] uploadMultiFile(@RequestParam("uploadFiles") MultipartFile[] uploadFiles) throws IOException {
        // TODO
        return null;
    }

    /**
     * 设置分析模式
     * @param isUniqueAnalysis 是否是精确分析
     */
    @RequestMapping(value = "/setAnalysisMode", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin(origins = "*")
    public void setAnalysisMode(@RequestParam("isUniqueAnalysis") boolean isUniqueAnalysis) {
        // 设置分析模式
        this.analysis.setAnalysisMode(isUniqueAnalysis);
    }

    @RequestMapping(value = "/reptile", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin(origins = "*")
    public String reptile(@RequestParam("searchContent") String searchContent) {

    }

    private void analyse() {
        this.analysis.analyse();
    }
}
