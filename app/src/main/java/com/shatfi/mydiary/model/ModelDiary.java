package com.shatfi.mydiary.model;

public class ModelDiary {

    private String tittle;
    private String description;
    private String date;
    private String userid;

    public ModelDiary() {
    }

    public ModelDiary(String tittle, String description, String date, String userid) {
        this.tittle = tittle;
        this.description = description;
        this.date = date;
        this.userid = userid;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
