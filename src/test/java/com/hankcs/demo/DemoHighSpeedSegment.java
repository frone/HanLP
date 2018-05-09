/*
 * <summary></summary>
 * <author>He Han</author>
 * <email>hankcs.cn@gmail.com</email>
 * <create-date>2014/12/24 23:20</create-date>
 *
 * <copyright file="DemoHighSpeedSegment.java" company="上海林原信息科技有限公司">
 * Copyright (c) 2003-2014, 上海林原信息科技有限公司. All Right Reserved, http://www.linrunsoft.com/
 * This source is subject to the LinrunSpace License. Please contact 上海林原信息科技有限公司 to get more information.
 * </copyright>
 */
package com.hankcs.demo;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.tokenizer.SpeedTokenizer;

import java.util.ArrayList;

/**
 * 演示极速分词，基于DoubleArrayTrie实现的词典正向最长分词，适用于“高吞吐量”“精度一般”的场合
 * @author hankcs
 */
public class DemoHighSpeedSegment
{
    public static void main(String[] args)
    {
        String text = "江西鄱阳湖干枯，中国最大淡水湖变成大草原";
        HanLP.Config.ShowTermNature = false;
        System.out.println(SpeedTokenizer.segment(text));

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
            System.out.println(SpeedTokenizer.segment(item));
        }
//        long start = System.currentTimeMillis();
//        int pressure = 1000000;
//        for (int i = 0; i < pressure; ++i)
//        {
//            SpeedTokenizer.segment(text);
//        }
//        double costTime = (System.currentTimeMillis() - start) / (double)1000;
//        System.out.printf("SpeedTokenizer分词速度：%.2f字每秒\n", text.length() * pressure / costTime);
    }
}
