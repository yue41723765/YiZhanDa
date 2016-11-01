package com.android.yzd.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.ui.adapter.ViewPagerAdapter;
import com.android.yzd.ui.custom.BaseActivity;
import com.android.yzd.ui.view.MyItemDecoration;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Administrator on 2016/10/10 0010.
 */
public class IntegralActivity extends BaseActivity {

    @BindView(R.id.ecshop_viepage)
    ViewPager ecshopViepage;
    @BindView(R.id.integral_number)
    TextView integralNumber;
    @BindView(R.id.integral_getIntegral)
    TextView integralGetIntegral;
    @BindView(R.id.integral_history)
    Button integralHistory;
    @BindView(R.id.integral_recycler)
    RecyclerView integralRecycler;

    CommonAdapter adapter_2;
    List<View> views = new ArrayList<>();
    @BindView(R.id.circleIndicator)
    CircleIndicator circleIndicator;

    @Override
    public int getContentViewId() {
        return R.layout.activiti_integral_ecshop;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        List<String> list = new ArrayList<>();
        final List<Integer> image = new ArrayList<>();

        image.add(R.drawable.find_find);
        image.add(R.drawable.find_find);

        for (int i = 0; i < image.size(); i++) {
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(params);
            imageView.setImageResource(image.get(i));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            views.add(imageView);
        }

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(views);
        ecshopViepage.setAdapter(viewPagerAdapter);

        circleIndicator.setViewPager(ecshopViepage);

        list.clear();
        list.add("小海豚电动车");
        list.add("欧式简约换鞋凳");
        list.add("简约时尚衣架");
        list.add("xxxxxxx");
        image.clear();
        image.add(R.drawable.find2_1);
        image.add(R.drawable.find2_2);
        image.add(R.drawable.find2_3);
        image.add(R.drawable.find2_3);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        linearLayoutManager2.setOrientation(OrientationHelper.VERTICAL);
        linearLayoutManager2.setSmoothScrollbarEnabled(true);
        integralRecycler.setLayoutManager(linearLayoutManager2);
        Drawable drawable = getResources().getDrawable(R.color.background);
        integralRecycler.addItemDecoration(new MyItemDecoration(OrientationHelper.VERTICAL, drawable, 2));
        adapter_2 = new CommonAdapter<String>(this, R.layout.item_find_2, list) {

            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.find_title, s);
                ImageView imageView = holder.getView(R.id.find_image);
                imageView.setImageResource(image.get(position));
            }
        };
        integralRecycler.setAdapter(adapter_2);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.integral_getIntegral:
                intent = new Intent(this, IntegralQuestionActivity.class);
                startActivity(intent);
                break;
            case R.id.integral_history:
                intent = new Intent(this, ConversionActivity.class);
                startActivity(intent);
                break;
        }
    }
}
