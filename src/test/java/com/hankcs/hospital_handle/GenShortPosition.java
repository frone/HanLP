package com.hankcs.hospital_handle;

import com.hankcs.hanlp.model.perceptron.PerceptronLexicalAnalyzer;

import java.io.*;
import java.util.ArrayList;

/*
将完整的省市地区名称处理为简写
 */
public class GenShortPosition {


    public void handledCities() throws IOException {
        /* 读入TXT文件 */
        PerceptronLexicalAnalyzer analyzer = new HosNameSeg().getAnalyzer();
        String pathname = "./cities.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
        File filename = new File(pathname); // 要读取以上路径的input。txt文件
        InputStreamReader reader = new InputStreamReader(
            new FileInputStream(filename)); // 建立一个输入流对象reader
        BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
        String line = "";
        line = br.readLine();

        /* 写入Txt文件 */
        File writename = new File("./cities_handled.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件
        writename.createNewFile(); // 创建新文件
        BufferedWriter out = new BufferedWriter(new FileWriter(writename));
        ArrayList list = new ArrayList();
        try {
            while (line != null) {
                out.write(analyzer.segment(line).get(0) + "\r\n");
                //            System.out.println(line);
                line = br.readLine(); // 一次读入一行数据
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
//            System.out.println(line);
            list.add(line);
        } finally {
            System.out.println(list.toString());
        }

        br.close();
        reader.close();
        out.flush(); // 把缓存区内容压入文件
        out.close(); // 最后记得关闭文件
    }

    /*
    读取城市简称保存到list中 并返回
     */
    public static ArrayList<String> getCityList() throws IOException {
        ArrayList<String> result = new ArrayList<String>();
        /* 读入TXT文件 */
        PerceptronLexicalAnalyzer analyzer = new HosNameSeg().getAnalyzer();
        String pathname = "./cities_handled.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
        File filename = new File(pathname); // 要读取以上路径的input。txt文件
        InputStreamReader reader = new InputStreamReader(
            new FileInputStream(filename)); // 建立一个输入流对象reader
        BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
        String line = "";
        line = br.readLine();
        while (line != null) {
            result.add(line);
            line = br.readLine();
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
//        new GenShortPosition().handledCities();
        System.out.println(GenShortPosition.getCityList().toString());
    }
}
