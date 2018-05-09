/*
 * <summary></summary>
 * <author>He Han</author>
 * <email>hankcs.cn@gmail.com</email>
 * <create-date>2015/4/6 23:20</create-date>
 *
 * <copyright file="DemoStopword.java" company="上海林原信息科技有限公司">
 * Copyright (c) 2003-2014, 上海林原信息科技有限公司. All Right Reserved, http://www.linrunsoft.com/
 * This source is subject to the LinrunSpace License. Please contact 上海林原信息科技有限公司 to get more information.
 * </copyright>
 */
package com.hankcs.demo;

import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NotionalTokenizer;

import java.util.ArrayList;
import java.util.List;

/**
 * 演示自动去除停用词、自动断句的分词器
 * @author hankcs
 */
public class DemoNotionalTokenizer
{
    public static void main(String[] args)
    {
        String text = "小区居民有的反对喂养流浪猫，而有的居民却赞成喂养这些小宝贝";
        // 自动去除停用词
        System.out.println(NotionalTokenizer.segment(text));    // 停用词典位于data/dictionary/stopwords.txt，可以自行修改
        // 自动断句+去除停用词
        for (List<Term> sentence : NotionalTokenizer.seg2sentence(text))
        {
            CoreStopWordDictionary.apply(sentence);
            System.out.println(sentence);
        }
//        测试医院名称分词
        ArrayList<String> list_hos = new ArrayList<String>();
        list_hos.add("上海市金山区朱泾地区地段医院");
        list_hos.add("中国人保石家庄分公司保险医院");
        list_hos.add("华北理工大学冀唐学院附属医院");
        list_hos.add("上饶市红十字烧伤整形骨科医院");
        list_hos.add("上海中医药大学附属龙华医院");
        list_hos.add("安徽中医学院第二附属医院安徽中医学院附属针灸医院");
        list_hos.add("中国人民解放军三七零一五部队医院");
        list_hos.add("广西中医药大学第一附属医院仁爱分院");
        list_hos.add("上海交通大学医学院附属第九人民医院（北院）");
        list_hos.add("解放军第3542工厂医院");
        for (String item : list_hos) {
            CoreStopWordDictionary.add("医院");
            CoreStopWordDictionary.add("附属");
            List<Term> sentence = NotionalTokenizer.segment(item);
            System.out.println(sentence);
        }
    }
}
