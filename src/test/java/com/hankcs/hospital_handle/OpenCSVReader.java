package com.hankcs.hospital_handle;


import com.opencsv.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.hankcs.hanlp.model.perceptron.PerceptronLexicalAnalyzer;

public class OpenCSVReader {
    private static final String SAMPLE_CSV_FILE_PATH = "./hospital_names2018-5-9.csv";
    private static final String STRING_ARRAY_SAMPLE = "./seg_hospital_names";

    /*
    替换掉特殊符号
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n|\\(|\\)");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }


    //    计算医院名称分词结果的分词，返回计算得分
    public static int calcMatchScore(List<String> l1, List<String> l2) throws IOException {
        int result = 0;
        String l1_head = l1.get(0);
        String l2_head = l2.get(0);
        int other_match = getIntersectCount(l1, l2);
        result += other_match;
        if (l1_head == l2_head) {
//            头部匹配
            if (other_match > 3) {
                result += other_match - 1;
            } else {
                result += 2;
            }
//            头部是城市额外加成2分
            if (GenShortPosition.getCityList().contains(l1_head)) {
                result += 2;
            }
//        如果分词结果包含头部只有一个匹配则得分清零
            if (other_match <= 1) {
                result = 0;
            }
        }

        return result;
    }


    /*
    计算两个list的交集数量
     */
    public static int getIntersectCount(List ls, List ls2) {
        List list = new ArrayList(Arrays.asList(new Object[ls.size()]));
        Collections.copy(list, ls);
        list.retainAll(ls2);
        return list.size();
    }

    public static void main(String[] args) throws IOException {
        OpenCSVReader handler = new OpenCSVReader();
//        List<HosBean> beans = new ArrayList<HosBean>();
        try {
            List<HosBean> beans = handler.getSegNameBeansFromCSV(SAMPLE_CSV_FILE_PATH);
            for (HosBean bean : beans
                ) {
                HashMap<String, Integer> map = new HashMap<String, Integer>();
                for (HosBean bean2 : beans
                    ) {
                    if (bean.equals(bean2)) {
                        continue;
                    } else {
                        List seg1 = bean.getSeg_words();
                        List seg2 = bean2.getSeg_words();
                        if (seg1.size() == 0 || seg2.size() == 0) {
                            continue;
                        }
                        int interCount = calcMatchScore(seg1, seg2);
                        if (interCount > 0) {
                            map.put(bean2.getName(), interCount);
                        }
                    }

                }
//            ArrayList list = new ArrayList(((HashMap<String, Integer>) table).values());

                //这里将map.entrySet()转换成list
                List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
//                System.out.println(list.size());
                //然后通过比较器来实现排序
                list.sort(new Comparator<Map.Entry<String, Integer>>() {//降序序排序
                    public int compare(Map.Entry<String, Integer> o1,
                                       Map.Entry<String, Integer> o2) {
                        return o2.getValue().compareTo(o1.getValue());
                    }
                });
                List<Map.Entry<String, Integer>> match_list = null;
                if (list.size() >= 10) {
                    match_list = list.subList(0, 10);
                } else if (list.size() >= 5) {
                    match_list = list.subList(0, 5);
                } else {
                    match_list = list;
                }
//                if (match_list.size() == 0) {
//                    beans.remove(bean);
//                } else {
                bean.setMatch_table(match_list);
            }

//            System.out.print(bean.getId() + "\t");
//            System.out.print(bean.getName() + "\t");
//            System.out.println(bean.getSeg_words() + "\t");
//            for (Map.Entry<String, Integer> mapping : match_list) {
//                System.out.println(mapping.getKey() + ":" + mapping.getValue());
            handler.write_csv_by_bean(beans, STRING_ARRAY_SAMPLE + ".0510tmp.csv");
        } catch (CsvDataTypeMismatchException e) {
            System.out.println(e.getMessage());
        } catch (CsvRequiredFieldEmptyException e) {
            System.out.println(e.getMessage());
        }

    }

    /*
    逐行读取csv文件,分词并装载入beans中返回
     */
    public List<HosBean> getSegNameBeansFromCSV(String path) throws IOException {
        Reader reader = null;
        List<HosBean> beans = new ArrayList<HosBean>();
        try {
            reader = Files.newBufferedReader(Paths.get(path));
        } catch (IOException e) {
            System.out.println(e);
        }
//        读取csv文件 跳过首行
        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
        String[] nextRecord;
        PerceptronLexicalAnalyzer analyzer = new HosNameSeg().getAnalyzer();
        while ((nextRecord = csvReader.readNext()) != null) {
//      替换掉医院名称中的特殊字符
            String hos_name = replaceBlank(nextRecord[1]);
//      将切词结果和原医院名称放入Bean
            HosBean bean = new HosBean(Integer.parseInt(nextRecord[0]), hos_name,
                analyzer.segment(hos_name));
            beans.add(bean);
        }
        reader.close();
        return beans;
    }

    public void write_csv_by_line() throws IOException {
        Writer writer = Files.newBufferedWriter(Paths.get(STRING_ARRAY_SAMPLE));
        CSVWriter csvWriter = new CSVWriter(writer,
            CSVWriter.DEFAULT_SEPARATOR,
            CSVWriter.NO_QUOTE_CHARACTER,
            CSVWriter.DEFAULT_ESCAPE_CHARACTER,
            CSVWriter.DEFAULT_LINE_END);
        String[] headerRecord = {"Name", "Email", "Phone", "Country"};
        csvWriter.writeNext(headerRecord);

        csvWriter.writeNext(new String[]{"Sundar Pichai ♥", "sundar.pichai@gmail.com", "+1-1111111111", "India"});
        csvWriter.writeNext(new String[]{"Satya Nadella", "satya.nadella@outlook.com", "+1-1111111112", "India"});
        csvWriter.close();
    }

    public void write_csv_by_bean(List<HosBean> beans, String path) throws IOException,
        CsvDataTypeMismatchException,
        CsvRequiredFieldEmptyException {
        Writer writer = Files.newBufferedWriter(Paths.get(path));
        StatefulBeanToCsv<HosBean> beanToCsv = new StatefulBeanToCsvBuilder(writer)
            .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).withSeparator(CSVWriter.DEFAULT_SEPARATOR)
            .withLineEnd(CSVWriter.DEFAULT_LINE_END).withEscapechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
            .build();
        beanToCsv.write(beans);
        writer.close();
    }
}