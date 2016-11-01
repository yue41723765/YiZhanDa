package com.android.yzd.ui.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.tools.DensityUtils;
import com.android.yzd.tools.K;
import com.android.yzd.ui.activity.EditAddressActivity;
import com.android.yzd.ui.activity.IntegralActivity;
import com.android.yzd.ui.custom.BaseFragment;
import com.android.yzd.ui.view.MyItemDecoration;
import com.squareup.picasso.Picasso;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/2 0002.
 */
public class FindFragment extends BaseFragment {

    @BindView(R.id.sc_title)
    TextView scTitle;
    @BindView(R.id.find_message)
    ImageView findMessage;
    @BindView(R.id.top_tools)
    RelativeLayout topTools;
    @BindView(R.id.boutique_recycler)
    RecyclerView boutiqueRecycler;
    @BindView(R.id.find_more)
    TextView findMore;
    @BindView(R.id.integration_recycler)
    RecyclerView integrationRecycler;


    CommonAdapter adapter_1;
    CommonAdapter adapter_2;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_find;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        boutiqueRecycler.setLayoutManager(linearLayoutManager);
        Drawable drawable = getResources().getDrawable(android.R.color.white);
        boutiqueRecycler.addItemDecoration(new MyItemDecoration(OrientationHelper.HORIZONTAL, drawable, DensityUtils.dp2px(context, 10)));
        List<String> list = new ArrayList<>();
        final List<Integer> image = new ArrayList<>();
        list.add("欧式吊扇");
        list.add("后现代壁纸");
        list.add("简约现代吊扇");
        list.add("xxxxxxx");
        image.add(R.drawable.find_1);
        image.add(R.drawable.find_2);
        image.add(R.drawable.find_3);
        image.add(R.drawable.find_3);
        adapter_1 = new CommonAdapter<String>(context, R.layout.item_find_1, list) {

            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                View view = holder.getConvertView();
                if (position == 0) {
                    view.setPadding(DensityUtils.dp2px(context, 10), 0, 0, 0);
                }
                holder.setText(R.id.find_name, s);
                ImageView imageView = holder.getView(R.id.find_image);
                imageView.setImageResource(image.get(position));
            }
        };
        boutiqueRecycler.setAdapter(adapter_1);

        List<String> list2 = new ArrayList<>();
        final List<Integer> image2 = new ArrayList<>();
        list2.add("小海豚电动车");
        list2.add("欧式简约换鞋凳");
        list2.add("简约时尚衣架");
        list2.add("xxxxxxx");
        image2.add(R.drawable.find2_1);
        image2.add(R.drawable.find2_2);
        image2.add(R.drawable.find2_3);
        image2.add(R.drawable.find2_3);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        linearLayoutManager2.setOrientation(OrientationHelper.VERTICAL);
        linearLayoutManager2.setSmoothScrollbarEnabled(true);
        integrationRecycler.setLayoutManager(linearLayoutManager2);
        drawable = getResources().getDrawable(R.color.background);
        integrationRecycler.addItemDecoration(new MyItemDecoration(OrientationHelper.VERTICAL, drawable, 2));
        adapter_2 = new CommonAdapter<String>(context, R.layout.item_find_2, list2) {

            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.find_title, s);
                ImageView imageView = holder.getView(R.id.find_image);
                imageView.setImageResource(image2.get(position));
            }
        };
        integrationRecycler.setAdapter(adapter_2);
    }

    @OnClick({R.id.find_more})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.find_more:
                intent = new Intent(context, IntegralActivity.class);
                startActivity(intent);
                break;

        }
    }
}
