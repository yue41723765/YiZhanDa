package com.android.yzd.ui.fragment;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.been.ClassifyInfo;
import com.android.yzd.tools.DensityUtils;
import com.android.yzd.ui.activity.ClassitySearchActivity;
import com.android.yzd.ui.activity.DetailsActivity;
import com.android.yzd.ui.custom.BaseFragment;
import com.android.yzd.ui.custom.RecyclerViewDivider;
import com.android.yzd.ui.view.MyItemDecoration;
import com.android.yzd.ui.view.RecyclerViewItemDecoration;
import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.squareup.picasso.Picasso;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/1 0001.
 */
public class ClassifyFragment extends BaseFragment {

    @BindView(R.id.classify_more)
    ImageView classifyMore;
    @BindView(R.id.classify_search)
    RelativeLayout classifySearch;
    @BindView(R.id.classify_recycler)
    RecyclerView classifyRecycler;
    @BindView(R.id.classify_contents)
    RecyclerView classifyContents;
    @BindView(R.id.recycler_head)
    RecyclerViewHeader head;
    @BindView(R.id.optimization_goods)
    ImageView optimizationGoods;
    @BindView(R.id.optimization_prompt_1)
    TextView optimizationPrompt1;
    @BindView(R.id.optimization_prompt_2)
    TextView optimizationPrompt2;
    @BindView(R.id.optimization_recycler)
    RecyclerView optimizationRecycler;
    @BindView(R.id.home_select_details)
    TextView homeSelectDetails;
    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.head_content)
    FrameLayout headContent;

    CommonAdapter toolsAdapter;
//    ClassifyContentAdapter contentAdapter;


    List<ClassifyInfo> classifyInfos = new ArrayList<>();
    Map<Integer, Boolean> isCheck = new HashMap<>();

    PopupWindow popupWindow;
    View view;


    int[] image = new int[]{R.drawable.classify_1, R.drawable.classify_2, R.drawable.classify_3,
            R.drawable.classify_4, R.drawable.classify_5, R.drawable.classify_6,
            R.drawable.classify_7, R.drawable.classify_8};

    @Override
    public int getContentViewId() {
        return R.layout.fragment_classify;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {

        LinearLayoutManager toolsManager = new LinearLayoutManager(getContext());
        toolsManager.setOrientation(OrientationHelper.VERTICAL);
        classifyRecycler.setLayoutManager(toolsManager);
        classifyRecycler.addItemDecoration(new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_HORIZONTAL, getResources().getColor(R.color.background), 2, 10, 10));
        classifyContents.addItemDecoration(new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_GRID, getResources().getColor(R.color.background), 10, 0, 0));

        Picasso.with(getContext()).load("http://img1.imgtn.bdimg.com/it/u=2561081877,3512148020&fm=21&gp=0.jpg").into(optimizationGoods);
        setData();

        setTools();

        setPopup();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        classifyContents.setLayoutManager(gridLayoutManager);
        classifyContents.setItemAnimator(new DefaultItemAnimator());
//        head_2.attachTo(classifyContents);
        head.attachTo(classifyContents);
        CommonAdapter adapter = new CommonAdapter<ClassifyInfo>(getContext(), R.layout.item_classity_content, classifyInfos) {
            @Override
            protected void convert(ViewHolder holder, ClassifyInfo classifyInfo, int position) {
                holder.setText(R.id.item_title, classifyInfo.getStr());
                ImageView imageView = holder.getView(R.id.item_image);
                imageView.setImageResource(image[position]);
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                intent = new Intent(getContext(), DetailsActivity.class);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
//        contentAdapter = new ClassifyContentAdapter(context, classifyInfos);
        classifyContents.setAdapter(adapter);
    }

    @OnClick({R.id.classify_more, R.id.classify_search})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.classify_more:
                popupWindow.showAsDropDown(classifyMore, 0, DensityUtils.dp2px(getContext(), 10));
                break;
            case R.id.popup_refresh:
                popupWindow.dismiss();
                break;
            case R.id.popup_message:
                popupWindow.dismiss();
                break;
            case R.id.popup_home:
                popupWindow.dismiss();
                break;
            case R.id.classify_search:
                intent = new Intent(getContext(), ClassitySearchActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void setPopup() {
        view = LayoutInflater.from(context).inflate(R.layout.popup_classity, null);
        TextView relresh = (TextView) view.findViewById(R.id.popup_refresh);
        TextView message = (TextView) view.findViewById(R.id.popup_message);
        TextView home = (TextView) view.findViewById(R.id.popup_home);
        relresh.setOnClickListener(this);
        message.setOnClickListener(this);
        home.setOnClickListener(this);

        popupWindow = new PopupWindow(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setContentView(view);
        popupWindow.setAnimationStyle(R.style.TopStyle);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });
    }

    private void setTools() {

        toolsAdapter = new CommonAdapter<ClassifyInfo>(getContext(), R.layout.item_classify_tools, classifyInfos) {
            @Override
            protected void convert(ViewHolder holder, ClassifyInfo classifyInfo, int position) {
                CheckBox checkBox = holder.getView(R.id.checkbox);
                checkBox.setText(classifyInfo.getStr());
                checkBox.setChecked(isCheck.get(position));
            }
        };
        if (classifyInfos.size() > 0)
            title.setText(classifyInfos.get(0).getStr());
        toolsAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                int temp = -1;
                for (int i = 0; i < isCheck.size(); i++) {
                    if (isCheck.get(i)) {
                        temp = i;
                    }
                    isCheck.put(i, false);
                }
                if (temp != -1) {
                    toolsAdapter.notifyItemChanged(temp);
                }
                isCheck.put(position, true);
                toolsAdapter.notifyItemChanged(position);
                title.setText(classifyInfos.get(position).getStr());
                if (position != 0) {
                    headContent.setVisibility(View.GONE);
                } else {
                    headContent.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        classifyRecycler.setAdapter(toolsAdapter);
    }

    private void setData() {
        ClassifyInfo classifyInfo = new ClassifyInfo();
        classifyInfo.setStr("为您推荐");
        classifyInfo.setCheck(false);
        classifyInfos.add(classifyInfo);

        classifyInfo = new ClassifyInfo();
        classifyInfo.setStr("五金工具");
        classifyInfo.setCheck(false);
        classifyInfos.add(classifyInfo);
        classifyInfo = new ClassifyInfo();
        classifyInfo.setStr("灯饰照明");
        classifyInfo.setCheck(false);
        classifyInfos.add(classifyInfo);
        classifyInfo = new ClassifyInfo();
        classifyInfo.setStr("厨房卫浴");
        classifyInfo.setCheck(false);
        classifyInfos.add(classifyInfo);
        classifyInfo = new ClassifyInfo();
        classifyInfo.setStr("家装主材");
        classifyInfo.setCheck(false);
        classifyInfos.add(classifyInfo);
        classifyInfo = new ClassifyInfo();
        classifyInfo.setStr("书房儿童");
        classifyInfo.setCheck(false);
        classifyInfos.add(classifyInfo);
        classifyInfo = new ClassifyInfo();
        classifyInfo.setStr("卧家家居");
        classifyInfo.setCheck(false);
        classifyInfos.add(classifyInfo);
        classifyInfo = new ClassifyInfo();
        classifyInfo.setStr("客厅餐厅");
        classifyInfo.setCheck(false);
        classifyInfos.add(classifyInfo);

        for (int i = 0; i < classifyInfos.size(); i++) {
            if (i == 0) {
                isCheck.put(i, true);
            } else {
                isCheck.put(i, false);
            }
        }
    }

}
