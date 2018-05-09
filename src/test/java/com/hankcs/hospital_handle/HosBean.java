package com.hankcs.hospital_handle;

import java.util.List;

public class HosBean {
    public HosBean(int id, String name, String seg_words) {
        this.id = id;
        this.name = name;
        this.seg_words = seg_words;

    }

    private int id;
    private String name;

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

    public String getSeg_words() {
        return seg_words;
    }

    public void setSeg_words(String seg_words) {
        this.seg_words = seg_words;
    }

    private String seg_words;

}
