package com.nju.edu.court.util;

import java.io.*;

/**
 * 更改文件的名字
 * @author Zyi
 */
public class TransferFileName {

    private static final String ROOT = "src/main/resources/demo_analysis/";

    private static final String FILE_READER_MARK_PATH = ROOT + "mark1";
    private static final String TEMP_FILE_WRITER_MARK_PATH = ROOT + "mark";
    private static final String FILE_WRITER_MARK_PATH = ROOT + "标注";

    private static final String FILE_READER_CONTENT_PATH = ROOT + "content1";
    private static final String TEMP_FILE_WRITER_CONTENT_PATH = ROOT + "content";
    private static final String FILE_WRITER_CONTENT_PATH = ROOT + "案件文本";

    private static BufferedReader reader;
    private static BufferedWriter writer;
    private static final int SIZE = 50;

    /**
     * 用于将文件名从mark1 (i).json 变成 marki (其中i是数字1-50)
     * @throws IOException IO异常，可能是文件路径问题
     */
    public static void transferMark() throws IOException {
        for (int i = 1; i <= SIZE; i++) {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_READER_MARK_PATH + " (" + i + ").json")));
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(TEMP_FILE_WRITER_MARK_PATH + i + ".json")));
            while (reader.ready()) {
                writer.write(reader.readLine());
            }
            writer.flush();

            reader.close();
            writer.close();
        }
    }

    /**
     * 用于将文件名从content1 (i).txt 变成 contenti (其中i是数字1-50)
     * @throws IOException IO异常，可能是文件路径问题
     */
    public static void transferContent() throws IOException {
        for (int i = 1; i <= SIZE; i++) {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_READER_CONTENT_PATH + " (" + i + ").txt")));
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(TEMP_FILE_WRITER_CONTENT_PATH + i + ".txt")));
            while (reader.ready()) {
                writer.write(reader.readLine());
            }
            writer.flush();

            reader.close();
            writer.close();
        }
    }

    /**
     * 用于将文件名从marki.json 变成 标注i.txt (其中i是数字1-50)
     * @throws IOException IO异常，可能是文件路径问题
     */
    public static void finishMark() throws IOException {
        for (int i = 51; i <= 100; i++) {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(TEMP_FILE_WRITER_MARK_PATH + i + ".json")));
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE_WRITER_MARK_PATH + i + ".json")));
            while (reader.ready()) {
                writer.write(reader.readLine());
            }
            writer.flush();

            reader.close();
            writer.close();
        }
    }

    /**
     * 用于将文件名从contenti.txt 变成 案件文本i.txt (其中i是数字1-50)
     * @throws IOException IO异常，可能是文件路径问题
     */
    public static void finishContent() throws IOException {
        for (int i = 51; i <= 100; i++) {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(TEMP_FILE_WRITER_CONTENT_PATH + i + ".txt")));
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE_WRITER_CONTENT_PATH + i + ".txt")));
            while (reader.ready()) {
                writer.write(reader.readLine());
            }
            writer.flush();

            reader.close();
            writer.close();
        }
    }

    public static void main(String[] args) {
        try {
            TransferFileName.finishMark();
            TransferFileName.finishContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
