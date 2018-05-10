package com.hankcs.hospital_handle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HosBean {
    public HosBean(int id, String name, List<String> seg_words) {
        this.id = id;
        this.name = name;
        this.seg_words = seg_words;
    }

    private int id;
    private String name;

    public List<Map.Entry<String, Integer>> getMatch_table() {
        return match_table;
    }

    public void setMatch_table(List<Map.Entry<String, Integer>> match_table) {
        this.match_table = match_table;
    }

    private List<Map.Entry<String, Integer>> match_table;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSeg_words() {
        return seg_words;
    }

    public void setSeg_words(List<String> seg_words) {
        this.seg_words = seg_words;
    }

    private List<String> seg_words;

}
