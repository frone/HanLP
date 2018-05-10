package com.hankcs.hospital_handle;

import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.model.perceptron.Config;
import com.hankcs.hanlp.model.perceptron.PerceptronLexicalAnalyzer;
import com.hankcs.hanlp.model.perceptron.PerceptronPOSTagger;
import com.hankcs.hanlp.model.perceptron.PerceptronSegmenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HosNameSeg {
    public PerceptronLexicalAnalyzer getAnalyzer() throws IOException{
        CoreStopWordDictionary.add("医院");
        CoreStopWordDictionary.add("附属");
        CoreStopWordDictionary.add("分公司");
//        CoreStopWordDictionary.add("大学");
        CoreStopWordDictionary.add("分院");
        CoreStopWordDictionary.add("学院");
        CoreStopWordDictionary.add("中国");
        CoreStopWordDictionary.add("东部");
        CoreStopWordDictionary.add("西部");
        CoreStopWordDictionary.add("南部");
        CoreStopWordDictionary.add("北部");
        CoreStopWordDictionary.add("东区");
        CoreStopWordDictionary.add("西区");
        CoreStopWordDictionary.add("南区");
        CoreStopWordDictionary.add("北区");
        CoreStopWordDictionary.add("东院");
        CoreStopWordDictionary.add("西院");
        CoreStopWordDictionary.add("南院");
        CoreStopWordDictionary.add("北院");
        CoreStopWordDictionary.add("院区");
        CoreStopWordDictionary.add("站");
        CoreStopWordDictionary.add("指导站");
//        CoreStopWordDictionary.add("病院");
        CoreStopWordDictionary.add("有限公司");
//        CoreStopWordDictionary.add("醫務所");
//        CoreStopWordDictionary.add("药房");
        CoreStopWordDictionary.add("地段");
        CoreStopWordDictionary.add("专科");
        CoreStopWordDictionary.add("街道");
        CoreStopWordDictionary.add("城区");
        CoreStopWordDictionary.add("矿区");
        CoreStopWordDictionary.add("郊区");

//        CoreStopWordDictionary.add("北京市");
//        CoreStopWordDictionary.add("上海市");
//        CoreStopWordDictionary.add("天津市");
//        CoreStopWordDictionary.add("广州市");

        CoreStopWordDictionary.add(")");
        CoreStopWordDictionary.add("(");

        PerceptronLexicalAnalyzer analyzer = new PerceptronLexicalAnalyzer(Config.CWS_MODEL_FILE, Config.POS_MODEL_FILE, Config.NER_MODEL_FILE);
        analyzer.learn("中国人保/n 冀唐/n 中日/n 院区/n 三环/n 中美/n 中日/n 垂杨柳/n 水锥子/n 青年沟/n " +
            "新源里/n 于都/n");
        return analyzer;

    }

    //        测试医院名称分词
    public static void main(String args[]) throws IOException {
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

        list_hos.add("浙江省中医院下沙院区");
        list_hos.add("青岛市海慈医疗集团");
        list_hos.add("中山大学附属第一医院黄埔院区");
        list_hos.add("修文县计划生育宣传技术指导站");
        list_hos.add("吉林大学中日联谊医院二部");
//        延边朝鲜族, 恩施土家族苗族, 湘西土家族苗族, 凉山彝族, 甘孜藏族, 阿坝藏族羌族, 黔东南苗族侗族, 黔南布依族苗族, 黔西南布依族苗族, 大理白族, 德宏傣族景颇族, 怒江傈僳族, 文山壮族苗族, 楚雄彝族, 红河哈尼族彝族, 西双版纳傣族, 迪庆藏族, 临夏回族, 甘南藏族, 果洛藏族, 海北藏族, 海南藏族, 海西蒙古族藏族, 玉树藏族, 黄南藏族, 伊犁哈萨克, 克孜勒苏柯尔克孜, 博尔塔拉蒙古, 巴音郭楞蒙古, 昌吉回族, 九龙半岛

        list_hos.add("延边朝鲜族");
        list_hos.add("恩施土家族苗族");
        list_hos.add("湘西土家族苗族");
        list_hos.add("凉山彝族");
        list_hos.add("甘孜藏族");
        PerceptronLexicalAnalyzer analyzer = new HosNameSeg().getAnalyzer();
        for (String item : list_hos) {
            List<String> list= analyzer.segment(item);
            System.out.println(String.join(" ",list));
            System.out.println(list.subList(0,1));
        }
    }
}
