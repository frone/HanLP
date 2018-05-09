/*
 * <summary></summary>
 * <author>He Han</author>
 * <email>hankcs.cn@gmail.com</email>
 * <create-date>2014/12/7 21:13</create-date>
 *
 * <copyright file="DemoAll.java" company="上海林原信息科技有限公司">
 * Copyright (c) 2003-2014, 上海林原信息科技有限公司. All Right Reserved, http://www.linrunsoft.com/
 * This source is subject to the LinrunSpace License. Please contact 上海林原信息科技有限公司 to get more information.
 * </copyright>
 */
package com.hankcs.demo;

import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.model.perceptron.Config;
import com.hankcs.hanlp.model.perceptron.PerceptronSegmenter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 第一个Demo，惊鸿一瞥
 *
 * @author hankcs
 */
public class DemoAtFirstSight {
    public static void main(String[] args) {
//        System.out.println("首次编译运行时，HanLP会自动构建词典缓存，请稍候……");
//        HanLP.Config.enableDebug();         // 为了避免你等得无聊，开启调试模式说点什么:-)
//        System.out.println(HanLP.segment("你好，欢迎使用HanLP汉语处理包！接下来请从其他Demo中体验HanLP丰富的功能~"));

        try {
            PerceptronSegmenter segmenter = new PerceptronSegmenter(Config.CWS_MODEL_FILE);

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
                System.out.println(segmenter.segment(item));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
