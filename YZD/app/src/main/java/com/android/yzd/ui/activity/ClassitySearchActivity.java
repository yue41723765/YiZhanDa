package com.android.yzd.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.android.yzd.R;
import com.android.yzd.tools.DensityUtils;
import com.android.yzd.ui.custom.BaseActivity;
import com.android.yzd.ui.view.MyItemDecoration;
import com.android.yzd.ui.view.RecyclerViewItemDecoration;
import com.squareup.picasso.Picasso;
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
 * Created by Administrator on 2016/10/5 0005.
 */
public class ClassitySearchActivity extends BaseActivity {

    @BindView(R.id.edit)
    EditText edit;
    @BindView(R.id.price)
    RadioButton price;
    @BindView(R.id.search_recycler)
    RecyclerView searchRecycler;

    int price_status = 0;

    CommonAdapter adapter;
    List<String> list = new ArrayList<>();
    @BindView(R.id.start_price)
    EditText startPrice;
    @BindView(R.id.end_price)
    EditText endPrice;
    @BindView(R.id.hot_recycler)
    RecyclerView hotRecycler;

    CommonAdapter popupAdapter;
    Map<Integer, Boolean> isCheck = new HashMap<>();
    @BindView(R.id.search_drawer)
    DrawerLayout searchDrawer;

    @Override
    public int getContentViewId() {
        return R.layout.activity_classity_search;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        list.add("http://pic31.nipic.com/20130710/12425116_114805479333_2.jpg");
        list.add("http://img5.imgtn.bdimg.com/it/u=712109773,2660893576&fm=21&gp=0.jpg");
        list.add("http://img3.imgtn.bdimg.com/it/u=1044444211,2572483856&fm=21&gp=0.jpg");
        list.add("http://img3.imgtn.bdimg.com/it/u=483858826,2272895723&fm=21&gp=0.jpg");
        list.add("http://img3.imgtn.bdimg.com/it/u=4071608181,27029970&fm=21&gp=0.jpg");
        list.add("http://img0.imgtn.bdimg.com/it/u=1887652557,2285785299&fm=21&gp=0.jpg");
        list.add("http://img1.imgtn.bdimg.com/it/u=762511538,651924239&fm=21&gp=0.jpg");
        list.add("http://img4.imgtn.bdimg.com/it/u=2968956529,3618979033&fm=21&gp=0.jpg");

        for (int i = 0; i < list.size(); i++) {
            isCheck.put(i, false);
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        searchRecycler.setLayoutManager(gridLayoutManager);
        searchRecycler.addItemDecoration(new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_GRID, getResources().getColor(R.color.background), 10, 0, 0));

        adapter = new CommonAdapter<String>(this, R.layout.item_hot_2, list) {

            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                Picasso.with(ClassitySearchActivity.this).load(s).into((ImageView) holder.getView(R.id.hot_2_image));
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                intent = new Intent(ClassitySearchActivity.this, DetailsActivity.class);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        searchRecycler.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        hotRecycler.setLayoutManager(layoutManager);
        hotRecycler.addItemDecoration(new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_GRID, Color.WHITE, DensityUtils.dp2px(this, 10), 0, 0));
        popupAdapter = new CommonAdapter<String>(this, R.layout.item_hot_popup, list) {

            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                CheckBox checkBox = holder.getView(R.id.hot_classify);
                checkBox.setBackgroundResource(R.drawable.check_blue90_blue);
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
        hotRecycler.setAdapter(popupAdapter);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.search_show:
                searchDrawer.openDrawer(Gravity.RIGHT);
                break;
            case R.id.price:
                price_status++;
                Drawable drawable;
                if (price_status % 2 == 0) {
                    drawable = getResources().getDrawable(R.mipmap.price_down);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

                    price.setCompoundDrawables(null, null, drawable, null);
                } else {
                    drawable = getResources().getDrawable(R.mipmap.price_up);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    price.setCompoundDrawables(null, null, drawable, null);

                }
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
