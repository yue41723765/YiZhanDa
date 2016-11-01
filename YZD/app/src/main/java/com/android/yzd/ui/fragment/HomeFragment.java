package com.android.yzd.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.tools.DensityUtils;
import com.android.yzd.tools.K;
import com.android.yzd.tools.ScreenUtils;
import com.android.yzd.ui.activity.DetailsActivity;
import com.android.yzd.ui.activity.HomeSearchActivity;
import com.android.yzd.ui.activity.HotActivity;
import com.android.yzd.ui.activity.IntegralActivity;
import com.android.yzd.ui.activity.LoginActivity;
import com.android.yzd.ui.activity.MessageManagerActivity;
import com.android.yzd.ui.adapter.ViewPagerAdapter;
import com.android.yzd.ui.custom.BaseFragment;
import com.android.yzd.ui.custom.RecyclerViewDivider;
import com.android.yzd.ui.view.AutoScrollViewPager;
import com.android.yzd.ui.view.MyItemDecoration;
import com.android.yzd.ui.view.RecyclerViewItemDecoration;
import com.squareup.picasso.Picasso;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Administrator on 2016/10/1 0001.
 */
public class HomeFragment extends BaseFragment {
    @BindView(R.id.home_message)
    ImageView homeMessage;
    @BindView(R.id.home_search)
    RelativeLayout homeSearch;
    @BindView(R.id.home_viewPage)
    AutoScrollViewPager homeViewPage;
    @BindView(R.id.home_circle)
    CircleIndicator homeCircle;
    @BindView(R.id.home_hot)
    TextView homeHot;
    @BindView(R.id.home_recommend)
    TextView homeRecommend;
    @BindView(R.id.home_favorable)
    TextView homeFavorable;
    @BindView(R.id.home_integral)
    TextView homeIntegral;
    @BindView(R.id.home_recycler_recommend)
    RecyclerView homeRecyclerRecommend;
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
    @BindView(R.id.home_recommend_recycler)
    RecyclerView homeRecommendRecycler;


    CommonAdapter itemAdapter1;
    CommonAdapter itemAdapter2;
    List<String> lists = new ArrayList<>();

    List<View> views = new ArrayList<>();
    int width;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        width = ScreenUtils.getScreenWidth(getContext());

        lists.add("https://img.alicdn.com/imgextra/i3/1971062271/TB26KWjacHA11Bjy0FiXXckfVXa_!!1971062271.jpg_430x430q90.jpg");
        lists.add("http://img0.imgtn.bdimg.com/it/u=1743793690,2889040548&fm=21&gp=0.jpg");
        lists.add("http://img2.imgtn.bdimg.com/it/u=1229763493,252127255&fm=21&gp=0.jpg");
        lists.add("http://imgmall.tg.com.cn/group1/M00/46/57/CgooalPoddOsG7OSAARd524pfKw795.jpg");
        lists.add("http://img2.imgtn.bdimg.com/it/u=2784392893,1098602407&fm=11&gp=0.jpg");

        Picasso.with(getContext()).load("http://img1.imgtn.bdimg.com/it/u=2561081877,3512148020&fm=21&gp=0.jpg").into(optimizationGoods);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        homeRecyclerRecommend.setLayoutManager(layoutManager);
        homeRecyclerRecommend.addItemDecoration(new MyItemDecoration(OrientationHelper.HORIZONTAL, getResources().getDrawable(android.R.color.white), DensityUtils.dp2px(context, 10)));
        setViewPager();

        itemAdapter1 = new CommonAdapter<String>(getContext(), R.layout.item_home_1, lists) {

            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                if (position == 0) {
                    View item_view = holder.getConvertView();
                    item_view.setPadding(DensityUtils.dp2px(context, 10), 0, 0, 0);
                }
                Picasso.with(getContext()).load(s).into((ImageView) holder.getView(R.id.home_1_image));
            }
        };
        itemAdapter1.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                intent = new Intent(context, DetailsActivity.class);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        homeRecyclerRecommend.setAdapter(itemAdapter1);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        homeRecommendRecycler.addItemDecoration(new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_GRID, getResources().getColor(R.color.background), 10, 0, 0));
        homeRecommendRecycler.setLayoutManager(gridLayoutManager);
        itemAdapter2 = new CommonAdapter<String>(getContext(), R.layout.item_hot_2, lists) {

            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                Picasso.with(getContext()).load(s).into((ImageView) holder.getView(R.id.hot_2_image));
            }
        };
        itemAdapter2.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                intent = new Intent(context, DetailsActivity.class);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        homeRecommendRecycler.setAdapter(itemAdapter2);

    }

    private void setViewPager() {
        for (int i = 0; i < lists.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Picasso.with(getContext()).load(lists.get(i)).into(imageView);
            views.add(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(views);
        homeViewPage.setAdapter(viewPagerAdapter);
        homeCircle.setViewPager(homeViewPage);
    }

    @OnClick({R.id.home_search, R.id.home_hot, R.id.home_recommend, R.id.home_favorable, R.id.home_integral, R.id.home_viewPage, R.id.home_message})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.home_integral:
                intent = new Intent(getContext(), IntegralActivity.class);
                startActivity(intent);
                break;
            case R.id.home_search:
                intent = new Intent(getContext(), HomeSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.home_hot:
                intent = new Intent(getContext(), HotActivity.class);
                intent.putExtra(K.TITLE, 1);
                startActivity(intent);
                break;
            case R.id.home_recommend:
                intent = new Intent(getContext(), HotActivity.class);
                intent.putExtra(K.TITLE, 2);
                startActivity(intent);
                break;
            case R.id.home_favorable:
                intent = new Intent(getContext(), HotActivity.class);
                intent.putExtra(K.TITLE, 3);
                startActivity(intent);
                break;
            case R.id.home_viewPage:
                intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.home_message:
                intent = new Intent(getContext(), MessageManagerActivity.class);
                startActivity(intent);
                break;
        }
    }
}
