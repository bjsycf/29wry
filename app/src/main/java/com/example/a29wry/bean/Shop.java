package com.example.a29wry.bean;

import java.io.Serializable;
import java.util.List;

public class Shop implements Serializable {
    /**
     * id : 1
     * shopName : 蛋糕房
     * saleNum : 996
     * offerPrice : 100
     * distributionCost : 5
     * welfare : 进店可获得一个香草冰淇淋
     * time : 配送约2-5小时
     * shopPic : http://111.231.199.143:3003/order/img/shop/shop1.png
     * shopNotice : 公告：下单后2-5小时送达！请耐心等候
     */
    private int        id;
    private String     shopName;
    private int        saleNum;
    private int        offerPrice;
    private int        distributionCost;
    private String     welfare;
    private String     time;
    private String     shopPic;
    private String     shopNotice;
    private List<Food> foodList;

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(int saleNum) {
        this.saleNum = saleNum;
    }

    public int getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(int offerPrice) {
        this.offerPrice = offerPrice;
    }

    public int getDistributionCost() {
        return distributionCost;
    }

    public void setDistributionCost(int distributionCost) {
        this.distributionCost = distributionCost;
    }

    public String getWelfare() {
        return welfare;
    }

    public void setWelfare(String welfare) {
        this.welfare = welfare;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getShopPic() {
        return shopPic;
    }

    public void setShopPic(String shopPic) {
        this.shopPic = shopPic;
    }

    public String getShopNotice() {
        return shopNotice;
    }

    public void setShopNotice(String shopNotice) {
        this.shopNotice = shopNotice;
    }
}
