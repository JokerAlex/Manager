package com.manager.pojo;

public class Product {
    private int productId;
    private String productName;
    private float productPrice;
    private String productComment;

    public Product() {
    }

    public Product(int productId, String productName, float productPrice, String productComment) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productComment = productComment;
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

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductComment() {
        return productComment;
    }

    public void setProductComment(String productComment) {
        this.productComment = productComment;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productComment='" + productComment + '\'' +
                '}';
    }
}
