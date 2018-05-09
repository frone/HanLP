package com.hankcs.hospital_handle;


import com.opencsv.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
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

    public static void main(String[] args) throws IOException {
        OpenCSVReader handler = new OpenCSVReader();
//        List<HosBean> beans = new ArrayList<HosBean>();
//        beans.add(new HosBean(1, "sundar.pichai@gmail.com", "good bad"));
//        beans.add(new HosBean(2, "satya.nadella@outlook.com", "good bad"));
        try {
            List<HosBean> beans = handler.read_csv_by_line(SAMPLE_CSV_FILE_PATH);
//            for (HosBean bean : beans
//                ) {
//                System.out.print(bean.getId() + "\t");
//                System.out.print(bean.getName() + "\t");
//                System.out.println(bean.getSeg_words());

//            }
            handler.write_csv_by_bean(beans, STRING_ARRAY_SAMPLE + ".tmp.csv");
        } catch (CsvDataTypeMismatchException e) {
            e.printStackTrace();
        } catch (CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        }

    }

    /*
    逐行读取csv文件并装载入beans中
     */
    public List<HosBean> read_csv_by_line(String path) throws IOException {
        Reader reader = null;
        List<HosBean> beans = new ArrayList<HosBean>();
        try {
            reader = Files.newBufferedReader(Paths.get(path));
        } catch (IOException e) {
            System.out.println(e);
        }
        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
        // Reading Records One by One in a String array

        String[] nextRecord;
        PerceptronLexicalAnalyzer analyzer = new HosNameSeg().getAnalyzer();
        while ((nextRecord = csvReader.readNext()) != null) {
            HosBean bean = new HosBean(Integer.parseInt(nextRecord[0]), nextRecord[1],
                String.join(" ", analyzer.segment(replaceBlank(nextRecord[1]))));
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