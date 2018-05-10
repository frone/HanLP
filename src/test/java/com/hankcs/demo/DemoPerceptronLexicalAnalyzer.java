/*
 * <author>Hankcs</author>
 * <email>me@hankcs.com</email>
 * <create-date>2018-03-15 下午5:39</create-date>
 *
 * <copyright file="DemoPerceptronLexicalAnalyzer.java" company="码农场">
 * Copyright (c) 2018, 码农场. All Right Reserved, http://www.hankcs.com/
 * This source is subject to Hankcs. Please contact Hankcs to get more information.
 * </copyright>
 */
package com.hankcs.demo;

import com.hankcs.hanlp.corpus.document.sentence.Sentence;
import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.model.perceptron.Config;
import com.hankcs.hanlp.model.perceptron.PerceptronLexicalAnalyzer;
import com.hankcs.hanlp.model.perceptron.PerceptronSegmenter;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.hankcs.hanlp.tokenizer.NotionalTokenizer;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 基于感知机序列标注的词法分析器，默认模型训练自1998人民日报语料1月份。欢迎在更大的语料库上训练，以得到更好的效果。
 * 无论在何种语料上训练，都完全支持简繁全半角和大小写。
 *
 * @author hankcs
 */
public class DemoPerceptronLexicalAnalyzer {
    public static void main(String[] args) throws IOException {
        PerceptronLexicalAnalyzer analyzer = new PerceptronLexicalAnalyzer();
        System.out.println(analyzer.analyze("上海华安工业（集团）公司董事长谭旭光和秘书胡花蕊来到美国纽约现代艺术博物馆参观"));
        System.out.println(analyzer.analyze("微软公司於1975年由比爾·蓋茲和保羅·艾倫創立，18年啟動以智慧雲端、前端為導向的大改組。"));

        // 任何模型总会有失误，特别是98年这种陈旧的语料库
        System.out.println(analyzer.analyze("总统普京与特朗普通电话讨论太空探索技术公司"));
        // 支持在线学习
        analyzer.learn("与/c 特朗普/nr 通/v 电话/n 讨论/v [太空/s 探索/vn 技术/n 公司/n]/nt");
        // 学习到新知识
        System.out.println(analyzer.analyze("总统普京与特朗普通电话讨论太空探索技术公司"));
        // 还可以举一反三
        System.out.println(analyzer.analyze("主席和特朗普通电话"));

        // 知识的泛化不是死板的规则，而是比较灵活的统计信息
        System.out.println(analyzer.analyze("我在浙江金华出生"));
        analyzer.learn("在/p 浙江/ns 金华/ns 出生/v");
        System.out.println(analyzer.analyze("我在四川金华出生，我的名字叫金华"));

        // 请用户按需执行对空格制表符等的预处理，只有你最清楚自己的文本中都有些什么奇怪的东西
        System.out.println(analyzer.analyze("空格 \t\n\r\f&nbsp;统统都不要"
            .replaceAll("\\s+", "")    // 去除所有空白符
            .replaceAll("&nbsp;", "")));  // 如果一些文本中含有html控制符

        // nlp分词
        System.out.println(NLPTokenizer.segment("我新造一个词叫幻想乡你能识别并标注正确词性吗？"));
        // 注意观察下面两个“希望”的词性、两个“晚霞”的词性
        System.out.println(NLPTokenizer.analyze("我的希望是希望张晚霞的背影被晚霞映红").translateLabels());
        System.out.println(NLPTokenizer.analyze("支援臺灣正體香港繁體：微软公司於1975年由比爾·蓋茲和保羅·艾倫創立。"));

        //在线学习
        System.out.println("=================在线学习===================");
        PerceptronSegmenter segmenter = new PerceptronSegmenter(Config.CWS_MODEL_FILE);
        System.out.println(segmenter.segment("下雨天地面积水"));
        segmenter.learn("下雨天 地面 积水");
        System.out.println(segmenter.segment("下雨天地面积水"));

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
        list_hos.add("和布克塞尔蒙古自治县人民医院");
        list_hos.add("察布查尔锡伯");
        list_hos.add("巴里坤哈萨克");
        list_hos.add("民和回族土族");
        list_hos.add("新疆维吾尔");

        CoreStopWordDictionary.add("医院");
        CoreStopWordDictionary.add("附属");
        CoreStopWordDictionary.add("北院");
        CoreStopWordDictionary.add("中国");

//        PerceptronLexicalAnalyzer analyzer2 = new PerceptronLexicalAnalyzer();
        PerceptronLexicalAnalyzer analyzer2 = new PerceptronLexicalAnalyzer(Config.CWS_MODEL_FILE, Config.POS_MODEL_FILE, Config.NER_MODEL_FILE);

//        analyzer2.enableIndexMode(4).enableNumberQuantifierRecognize(true).enablePartOfSpeechTagging(true);
//            .enablePlaceRecognize(true)
        analyzer2.learn("人保/n 民和/n 冀唐/n");
        for (String item : list_hos) {
            List result = analyzer2.segment(item);
            System.out.println(result);
//            System.out.println(result.get(0).toString().replace("市",""));
//            System.out.println(result.remove(0));
//            CoreStopWordDictionary.apply(sentence);
//            System.out.println(sentence);
        }
    }

}