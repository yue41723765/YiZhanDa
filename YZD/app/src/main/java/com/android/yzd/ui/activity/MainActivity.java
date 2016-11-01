package com.android.yzd.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.yzd.R;
import com.android.yzd.ui.custom.BaseActivity;
import com.android.yzd.ui.fragment.ClassifyFragment;
import com.android.yzd.ui.fragment.FindFragment;
import com.android.yzd.ui.fragment.HomeFragment;
import com.android.yzd.ui.fragment.MyFragment;
import com.android.yzd.ui.fragment.ShoppingCartFragment;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/10/1 0001.
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.main_tools)
    RadioGroup mainTools;


    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    Fragment mContent;

    HomeFragment homeFragment;
    ClassifyFragment classifyFragment;
    ShoppingCartFragment shoppingCartFragment;
    FindFragment findFragment;
    MyFragment myFragment;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        homeFragment = new HomeFragment();
        classifyFragment = new ClassifyFragment();
        shoppingCartFragment = new ShoppingCartFragment();
        findFragment = new FindFragment();
        myFragment = new MyFragment();

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_contents, homeFragment);
        transaction.commit();
        mContent = homeFragment;
        mainTools.setOnCheckedChangeListener(onCheckedChangeListener);
    }


    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (group.getCheckedRadioButtonId()) {
                case R.id.main_home:
                    showFragment(homeFragment);
                    break;
                case R.id.main_classify:
                    showFragment(classifyFragment);
                    break;
                case R.id.main_shoppingCart:
                    showFragment(shoppingCartFragment);
                    break;
                case R.id.main_find:
                    showFragment(findFragment);
                    break;
                case R.id.main_my:
                    showFragment(myFragment);
                    break;
            }
        }
    };


    private void showFragment(Fragment to) {
        if (mContent != to) {
            transaction = fragmentManager.beginTransaction();

            if (!to.isAdded()) {
                transaction.hide(mContent).add(R.id.main_contents, to);
            } else {
                transaction.hide(mContent).show(to);
            }
            mContent = to;
            transaction.commit();
        }
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();      //调用双击退出函数
        }
        return false;
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }
}
