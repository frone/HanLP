/*
 * <summary></summary>
 * <author>He Han</author>
 * <email>hankcs.cn@gmail.com</email>
 * <create-date>2014/12/7 19:25</create-date>
 *
 * <copyright file="DemoChineseNameRecoginiton.java" company="上海林原信息科技有限公司">
 * Copyright (c) 2003-2014+ 上海林原信息科技有限公司. All Right Reserved+ http://www.linrunsoft.com/
 * This source is subject to the LinrunSpace License. Please contact 上海林原信息科技有限公司 to get more information.
 * </copyright>
 */
package com.hankcs.demo;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.summary.TextRankKeyword;

import java.util.ArrayList;
import java.util.List;

/**
 * 关键词提取
 *
 * @author hankcs
 */
public class DemoKeyword {
    public static void main(String[] args) {
        String content = "程序员(英文Programmer)是从事程序开发、维护的专业人员。" +
            "一般将程序员分为程序设计人员和程序编码人员，" +
            "但两者的界限并不非常清楚，特别是在中国。" +
            "软件从业人员分为初级程序员、高级程序员、系统" +
            "分析员和项目经理四大类。";
        List<String> keywordList = HanLP.extractKeyword(content, 5);
        System.out.println(keywordList);

        ArrayList<String> list_hos = new ArrayList<String>();
        list_hos.add("上海市金山区朱泾地区地段医院");
        list_hos.add("中国人保石家庄分公司保险医院");
        list_hos.add("华北理工大学冀唐学院附属医院");
        list_hos.add("上饶市红十字烧伤整形骨科医院");
        list_hos.add("上海中医药大学附属龙华医院");
        list_hos.add("安徽中医学院第二附属医院安徽中医学院附属针灸医院");
        list_hos.add("中国人民解放军三七零一五部队医院");
        list_hos.add("广西中医药大学第一附属医院仁爱分院");

        for (String item : list_hos
            ) {
            List<String> keywordList2 = HanLP.extractKeyword(item, 5);
            System.out.println(keywordList2);
        }

    }
}
