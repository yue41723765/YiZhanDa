package com.android.yzd.tools.update;

/**
 * Created by yzy on 2016/5/20.
 */
public interface UpdateCallBack {

    void onError();

    void isUpdate(String result);

    void isNoUpdate();


}
