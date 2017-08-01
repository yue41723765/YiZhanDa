package com.android.yzd.been;

/**
 * Created by 33181 on 2017/7/28.
 */

public class DeliverFeeEntity {


    /**
     * flag : success
     * message : 请求成功
     * data : {"big_delivery_fee":"38","small_delivery_fee":"19","free_delivery_fee_limit":"1000"}
     */

    private String flag;
    private String message;
    private DataBean data;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * big_delivery_fee : 38
         * small_delivery_fee : 19
         * free_delivery_fee_limit : 1000
         */

        private String big_delivery_fee;
        private String small_delivery_fee;
        private String free_delivery_fee_limit;

        public String getBig_delivery_fee() {
            return big_delivery_fee;
        }

        public void setBig_delivery_fee(String big_delivery_fee) {
            this.big_delivery_fee = big_delivery_fee;
        }

        public String getSmall_delivery_fee() {
            return small_delivery_fee;
        }

        public void setSmall_delivery_fee(String small_delivery_fee) {
            this.small_delivery_fee = small_delivery_fee;
        }

        public String getFree_delivery_fee_limit() {
            return free_delivery_fee_limit;
        }

        public void setFree_delivery_fee_limit(String free_delivery_fee_limit) {
            this.free_delivery_fee_limit = free_delivery_fee_limit;
        }
    }
}
