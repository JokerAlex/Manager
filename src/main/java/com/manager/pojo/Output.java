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

    private int teDing;

    private int beiJing;

    private int beiJingTeDing;

    private int bendDiHeTong;

    private int waiDiHeTong;

    private int deng;

    public Output() {
    }

    public Output(int yaer, int month, int productId, String productName, int xiaDan, int muGong, int youFang, int baoZhuang, int teDing, int beiJing, int beiJingTeDing, int bendDiHeTong, int waiDiHeTong, int deng) {
        this.year = yaer;
        this.month = month;
        this.productId = productId;
        this.productName = productName;
        this.xiaDan = xiaDan;
        this.muGong = muGong;
        this.youFang = youFang;
        this.baoZhuang = baoZhuang;
        this.teDing = teDing;
        this.beiJing = beiJing;
        this.beiJingTeDing = beiJingTeDing;
        this.bendDiHeTong = bendDiHeTong;
        this.waiDiHeTong = waiDiHeTong;
        this.deng = deng;
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

    public int getBeiJing() {
        return beiJing;
    }

    public void setBeiJing(int beiJing) {
        this.beiJing = beiJing;
    }

    public int getBeiJingTeDing() {
        return beiJingTeDing;
    }

    public void setBeiJingTeDing(int beiJingTeDing) {
        this.beiJingTeDing = beiJingTeDing;
    }

    public int getBendDiHeTong() {
        return bendDiHeTong;
    }

    public void setBendDiHeTong(int bendDiHeTong) {
        this.bendDiHeTong = bendDiHeTong;
    }

    public int getWaiDiHeTong() {
        return waiDiHeTong;
    }

    public void setWaiDiHeTong(int waiDiHeTong) {
        this.waiDiHeTong = waiDiHeTong;
    }

    public int getDeng() {
        return deng;
    }

    public void setDeng(int deng) {
        this.deng = deng;
    }

    @Override
    public String toString() {
        return "Output{" +
                "yaer=" + year +
                ", month=" + month +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", xiaDan=" + xiaDan +
                ", muGong=" + muGong +
                ", youFang=" + youFang +
                ", baoZhuang=" + baoZhuang +
                ", teDing=" + teDing +
                ", beiJing=" + beiJing +
                ", beiJingTeDing=" + beiJingTeDing +
                ", bendDiHeTong=" + bendDiHeTong +
                ", waiDiHeTong=" + waiDiHeTong +
                ", deng=" + deng +
                '}';
    }
}
