package com.nju.edu.court.dic;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 生成词典
 * @author Zyi
 */
public class Dictionary {

    private static final String ROOT = "src/main/resources/";
    private static final String FILE_READER_PATH = ROOT + "criminal.txt";
    private static final String FILE_WRITER_PATH = ROOT + "res.txt";

    public static void generateDic() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_READER_PATH)));
        List<String> content = new ArrayList<>();

        // 读取文件
        while (reader.ready()) {
            String temp = reader.readLine();
            if (!"".equals(temp)) {
                content.add(temp);
            }
        }

        // 写入新文件
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE_WRITER_PATH)));
        content = remove(content);
        for (String s : content) {
            writer.write(s);
            writer.write("\r\n");
        }

        writer.flush();
        writer.close();
        reader.close();
    }

    private static List<String> remove(List<String> content) {
        List<String> newContent = new ArrayList<>();
        StringBuilder temp;

        for (String criminal : content) {
            temp = new StringBuilder();
            for (int j = 0; j < criminal.length(); j++) {
                char aChar = criminal.charAt(j);
                if (aChar >= '0' && aChar <= '9') {
                    continue;
                } else if (aChar == '罪') {
                    // 判断结尾的标志
                    temp.append(aChar);
                    break;
                } else {
                    temp.append(aChar);
                }
            }

            newContent.add(temp.toString());
        }

        return newContent;
    }

    public static void main(String[] args){
        try {
            Dictionary.generateDic();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
