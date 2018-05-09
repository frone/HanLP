/*
 * <author>Han He</author>
 * <email>me@hankcs.com</email>
 * <create-date>2018-03-30 下午10:01</create-date>
 *
 * <copyright file="DemoCRFLexicalAnalyzer.java">
 * Copyright (c) 2018, Han He. All Right Reserved, http://www.hankcs.com/
 * This source is subject to Han He. Please contact Han He to get more information.
 * </copyright>
 */
package com.hankcs.demo;

import com.hankcs.hanlp.model.crf.CRFLexicalAnalyzer;

import java.io.IOException;
import java.util.ArrayList;

/**
 * CRF词法分析器
 * @author hankcs
 */
public class DemoCRFLexicalAnalyzer
{
    public static void main(String[] args) throws IOException
    {
        CRFLexicalAnalyzer analyzer = new CRFLexicalAnalyzer();
        String[] tests = new String[]{
            "商品和服务",
            "上海华安工业（集团）公司董事长谭旭光和秘书胡花蕊来到美国纽约现代艺术博物馆参观",
            "微软公司於1975年由比爾·蓋茲和保羅·艾倫創立，18年啟動以智慧雲端、前端為導向的大改組。" // 支持繁体中文
        };
        for (String sentence : tests)
        {
            System.out.println(analyzer.analyze(sentence));
//            System.out.println(analyzer.seg(sentence));
        }
        ArrayList<String> list_hos = new ArrayList<String>();
        list_hos.add("上海市金山区朱泾地区地段医院");
        list_hos.add("中国人保石家庄分公司保险医院");
        list_hos.add("华北理工大学冀唐学院附属医院");
        list_hos.add("上饶市红十字烧伤整形骨科医院");
        list_hos.add("上海中医药大学附属龙华医院");
        list_hos.add("安徽中医学院第二附属医院安徽中医学院附属针灸医院");
        list_hos.add("中国人民解放军三七零一五部队医院");
        list_hos.add("广西中医药大学第一附属医院仁爱分院");
        for (String item : list_hos) {
            System.out.println(analyzer.analyze(item));
        }
    }
}
