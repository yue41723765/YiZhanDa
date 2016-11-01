package com.android.yzd.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.tools.DensityUtils;
import com.android.yzd.tools.K;
import com.android.yzd.ui.adapter.HotAdapter;
import com.android.yzd.ui.custom.BaseActivity;
import com.android.yzd.ui.view.RecyclerViewItemDecoration;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/3 0003.
 */
public class HotActivity extends BaseActivity {

    @BindView(R.id.titleBar_title)
    TextView title;

    @BindView(R.id.hot_show_type)
    ImageView hotShowType;
    @BindView(R.id.label)
    TextView label;
    @BindView(R.id.translucence)
    TextView translucence;
    @BindView(R.id.hot_type)
    RadioButton hotType;
    @BindView(R.id.hot_sort_rank)
    RadioButton hotSortRank;
    @BindView(R.id.hot_screen_filtrate)
    RadioButton hotScreenFiltrate;
    RecyclerView classityRecycler;

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.hot_recycler_1)
    RecyclerView hotRecycler1;
    @BindView(R.id.hot_recycler_2)
    RecyclerView hotRecycler2;

    CommonAdapter adapter1;
    CommonAdapter adapter2;


    PopupWindow popupWindow;

    View view1;
    RadioButton popup_hot;
    RadioButton popup_new;
    RadioButton popup_sale;
    View view2;
    View view3;

    CommonAdapter popupAdapter;
    int status = 1;//显示风格1  显示风格2两种
    int showStatus;
    int classityStatus = 1;//1---热销(默认) 2----最新 3----优惠
    int sortRankStatus = 1;//1---智能(默认) 2----人气 3----价格


    Map<Integer, Boolean> isCheck = new HashMap<>();

    @Override
    public int getContentViewId() {
        return R.layout.activity_hot;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {


        init();

        status = getIntent().getExtras().getInt(K.TITLE);
        switch (status) {
            case 1:
                popup_hot.setChecked(true);
                title.setText("热销分类");
                break;
            case 2:
                popup_new.setChecked(true);
                title.setText("最新推荐");
                break;
            case 3:
                popup_sale.setChecked(true);
                title.setText("特价优惠");
                break;
        }


        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");

        for (int i = 0; i < list.size(); i++) {
            isCheck.put(i, false);
        }

        adapter1 = new CommonAdapter<String>(this, R.layout.item_hot_1, list) {

            @Override
            protected void convert(ViewHolder holder, String s, int position) {

            }
        };
        hotRecycler1.setAdapter(adapter1);
        adapter2 = new CommonAdapter<String>(this, R.layout.item_hot_2, list) {

            @Override
            protected void convert(ViewHolder holder, String s, int position) {

            }
        };
        hotRecycler2.setAdapter(adapter2);


        popupAdapter = new CommonAdapter<String>(this, R.layout.item_hot_popup, list) {

            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                CheckBox checkBox = holder.getView(R.id.hot_classify);
                checkBox.setText("分类" + position);
                checkBox.setChecked(isCheck.get(position));
            }
        };

        popupAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                for (int i = 0; i < isCheck.size(); i++) {
                    isCheck.put(i, false);
                }
                isCheck.put(position, true);
                popupAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        classityRecycler.setAdapter(popupAdapter);


    }

    private void init() {

        view1 = getLayoutInflater().inflate(R.layout.popup_hot_1, null);
        view2 = getLayoutInflater().inflate(R.layout.popup_hot_2, null);
        view3 = getLayoutInflater().inflate(R.layout.popup_hot_3, null);
        popup_hot = (RadioButton) view1.findViewById(R.id.popup_hot);
        popup_new = (RadioButton) view1.findViewById(R.id.popup_new);
        popup_sale = (RadioButton) view1.findViewById(R.id.popup_sale);

        classityRecycler = (RecyclerView) view3.findViewById(R.id.hot_recycler);

        popupWindow = new PopupWindow(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(R.style.TopStyle);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                translucence.setVisibility(View.GONE);
                hotType.setChecked(false);
                hotSortRank.setChecked(false);
                hotScreenFiltrate.setChecked(false);
            }
        });
        //风格一
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        hotRecycler1.setLayoutManager(layoutManager);
        //风格二
        GridLayoutManager gridManager = new GridLayoutManager(this, 2);
        hotRecycler2.setLayoutManager(gridManager);
        hotRecycler2.addItemDecoration(new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_GRID, getResources().getColor(R.color.background), 10, 0, 0));

        GridLayoutManager pupupGridManager = new GridLayoutManager(this, 4);
        classityRecycler.setLayoutManager(pupupGridManager);
        classityRecycler.addItemDecoration(new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_GRID, Color.WHITE, DensityUtils.dp2px(this, 10), 0, 0));
    }


    private void showPopup(View view) {
        translucence.setVisibility(View.VISIBLE);
        popupWindow.dismiss();
        popupWindow.setContentView(view);
        popupWindow.showAsDropDown(label, 0, 0);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titleBar_right_image:
                break;
            case R.id.hot_show_type:
                if (showStatus == 1) {
                    showStatus = 2;
                    hotShowType.setImageResource(R.mipmap.list_black);
                    hotRecycler1.setVisibility(View.GONE);
                    hotRecycler2.setVisibility(View.VISIBLE);
                } else {
                    showStatus = 1;
                    hotShowType.setImageResource(R.mipmap.show_black);
                    hotRecycler1.setVisibility(View.VISIBLE);
                    hotRecycler2.setVisibility(View.GONE);
                }
                break;
            case R.id.hot_type:
                showPopup(view1);
                break;
            case R.id.hot_sort_rank:
                showPopup(view2);
                break;
            case R.id.hot_screen_filtrate:
                showPopup(view3);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
