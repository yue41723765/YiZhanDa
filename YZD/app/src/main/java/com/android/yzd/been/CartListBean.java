package com.android.yzd.been;

/**
 * Created by Administrator on 2016/12/21 0021.
 */

public class CartListBean {
    /**
     * goods_name : 商品id
     * goods_logo : 商品logo
     * goods_price : 商品价格
     * number : 购物车数量
     */

    private String goods_name;
    private String goods_logo;
    private float goods_price;
    private int number;
    private String goods_id;
    private String cart_id;

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_logo() {
        return goods_logo;
    }

    public void setGoods_logo(String goods_logo) {
        this.goods_logo = goods_logo;
    }

    public float getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(float goods_price) {
        this.goods_price = goods_price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }
}
