package com.android.yzd.been;

import java.util.List;

/**
 * Created by Administrator on 2016/12/21 0021.
 */

public class ScEntity {

    /**
     * not_read : 是否有未读消息，0没有 1有
     * cart_list : [{"goods_name":"商品id","goods_logo":"商品logo","goods_price":"商品价格","number":"购物车数量"}]
     */

    private String not_read;
    private List<CartListBean> cart_list;

    public String getNot_read() {
        return not_read;
    }

    public void setNot_read(String not_read) {
        this.not_read = not_read;
    }

    public List<CartListBean> getCart_list() {
        return cart_list;
    }

    public void setCart_list(List<CartListBean> cart_list) {
        this.cart_list = cart_list;
    }
}
