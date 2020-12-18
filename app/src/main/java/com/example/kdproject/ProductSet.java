package com.example.kdproject;

public class ProductSet {

    public String type,mimg, simg,other;
    public int price,count;

    public ProductSet() {
    }

    public ProductSet(String type,int price,int count,String simg,String mimg, String other) {
        this.type=type;
        this.mimg=mimg;
        this.simg=simg;
        this.other=other;
        this.count=count;
        this.price=price;

    }
}
