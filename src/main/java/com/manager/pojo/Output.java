package com.manager.pojo;

public class Output {
    private int year;
    private int month;

    private int productId;
    private String productName;

    private int xiaDan;

    private int muGong;

    private int youFang;

    private int baoZhuang;

    private float baoZhuangJinE;

    private int teDing;

    private float teDingJinE;

    public Output() {
    }

    public Output(int year, int month, int productId, String productName) {
        this.year = year;
        this.month = month;
        this.productId = productId;
        this.productName = productName;
    }

    public Output(int year, int month, int productId, String productName, int xiaDan, int muGong, int youFang, int baoZhuang, float baoZhuangJinE, int teDing, float teDingJinE) {
        this.year = year;
        this.month = month;
        this.productId = productId;
        this.productName = productName;
        this.xiaDan = xiaDan;
        this.muGong = muGong;
        this.youFang = youFang;
        this.baoZhuang = baoZhuang;
        this.baoZhuangJinE = baoZhuangJinE;
        this.teDing = teDing;
        this.teDingJinE = teDingJinE;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int yaer) {
        this.year = yaer;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getXiaDan() {
        return xiaDan;
    }

    public void setXiaDan(int xiaDan) {
        this.xiaDan = xiaDan;
    }

    public int getMuGong() {
        return muGong;
    }

    public void setMuGong(int muGong) {
        this.muGong = muGong;
    }

    public int getYouFang() {
        return youFang;
    }

    public void setYouFang(int youFang) {
        this.youFang = youFang;
    }

    public int getBaoZhuang() {
        return baoZhuang;
    }

    public void setBaoZhuang(int baoZhuang) {
        this.baoZhuang = baoZhuang;
    }

    public int getTeDing() {
        return teDing;
    }

    public void setTeDing(int teDing) {
        this.teDing = teDing;
    }

    public float getBaoZhuangJinE() {
        return baoZhuangJinE;
    }

    public void setBaoZhuangJinE(float baoZhuangJinE) {
        this.baoZhuangJinE = baoZhuangJinE;
    }

    public float getTeDingJinE() {
        return teDingJinE;
    }

    public void setTeDingJinE(float teDingJinE) {
        this.teDingJinE = teDingJinE;
    }

    @Override
    public String toString() {
        return "Output{" +
                "year=" + year +
                ", month=" + month +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", xiaDan=" + xiaDan +
                ", muGong=" + muGong +
                ", youFang=" + youFang +
                ", baoZhuang=" + baoZhuang +
                ", baoZhuangJinE=" + baoZhuangJinE +
                ", teDing=" + teDing +
                ", teDingJinE=" + teDingJinE +
                '}';
    }
}
