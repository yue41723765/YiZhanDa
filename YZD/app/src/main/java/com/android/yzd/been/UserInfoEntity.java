package com.android.yzd.been;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/29.
 */

public class UserInfoEntity implements Serializable {
    /**
     * m_id : 2
     * account : 18166485393
     * easemob_account : 201612062139446
     * easemob_password : 1481031593
     * head_pic : http://yzd.txunda.com/Uploads/Member/default.png
     * nickname : 18166485393
     * status : 1
     * not_read : 0
     */

    private String m_id;
    private String account;
    private String easemob_account;
    private String easemob_password;
    private String head_pic;
    private String nickname;
    private String status;
    private String not_read;

    public String getM_id() {
        return m_id;
    }

    public void setM_id(String m_id) {
        this.m_id = m_id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEasemob_account() {
        return easemob_account;
    }

    public void setEasemob_account(String easemob_account) {
        this.easemob_account = easemob_account;
    }

    public String getEasemob_password() {
        return easemob_password;
    }

    public void setEasemob_password(String easemob_password) {
        this.easemob_password = easemob_password;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNot_read() {
        return not_read;
    }

    public void setNot_read(String not_read) {
        this.not_read = not_read;
    }
}
