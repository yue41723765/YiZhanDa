package com.android.yzd.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/11/30 0030.
 */

public class MessageEntity implements Parcelable {

    /**
     * msg_id : 消息id
     * status : 0未读 1已读
     * msg_content : 消息标题
     * order_id : 订单id
     * order_sn : 订单编号
     * pick_addr : 取货地址
     * create_time : 时间
     */

    private String msg_id;
    private String status;
    private String msg_content;
    private String order_id;
    private String order_sn;
    private String pick_addr;
    private String create_time;

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg_content() {
        return msg_content;
    }

    public void setMsg_content(String msg_content) {
        this.msg_content = msg_content;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getPick_addr() {
        return pick_addr;
    }

    public void setPick_addr(String pick_addr) {
        this.pick_addr = pick_addr;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.msg_id);
        dest.writeString(this.status);
        dest.writeString(this.msg_content);
        dest.writeString(this.order_id);
        dest.writeString(this.order_sn);
        dest.writeString(this.pick_addr);
        dest.writeString(this.create_time);
    }

    public MessageEntity() {
    }

    protected MessageEntity(Parcel in) {
        this.msg_id = in.readString();
        this.status = in.readString();
        this.msg_content = in.readString();
        this.order_id = in.readString();
        this.order_sn = in.readString();
        this.pick_addr = in.readString();
        this.create_time = in.readString();
    }

    public static final Creator<MessageEntity> CREATOR = new Creator<MessageEntity>() {
        @Override
        public MessageEntity createFromParcel(Parcel source) {
            return new MessageEntity(source);
        }

        @Override
        public MessageEntity[] newArray(int size) {
            return new MessageEntity[size];
        }
    };
}
