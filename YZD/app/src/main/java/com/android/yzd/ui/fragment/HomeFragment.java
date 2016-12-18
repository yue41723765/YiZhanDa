package com.android.yzd.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.been.AdvertListBean;
import com.android.yzd.been.GoodGoodsListBean;
import com.android.yzd.been.HomeDataEntity;
import com.android.yzd.been.RecommendGoodsListBean;
import com.android.yzd.been.UserInfoEntity;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.K;
import com.android.yzd.tools.SPUtils;
import com.android.yzd.tools.ScreenUtils;
import com.android.yzd.ui.activity.DetailsActivity;
import com.android.yzd.ui.activity.HomeSearchActivity;
import com.android.yzd.ui.activity.HotActivity;
import com.android.yzd.ui.activity.IntegralActivity;
import com.android.yzd.ui.activity.LoginActivity;
import com.android.yzd.ui.activity.MessageManagerActivity;
import com.android.yzd.ui.adapter.ViewPagerAdapter;
import com.android.yzd.ui.custom.BaseFragment;
import com.android.yzd.ui.view.AutoScrollViewPager;
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

    UserInfoEntity userInfo;
    HomeDataEntity homeData;
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
    @BindView(R.id.optimization_goods)
    ImageView optimizationGoods;
    @BindView(R.id.home_recommend_recycler)
    RecyclerView homeRecommendRecycler;


    CommonAdapter itemAdapter1;
    CommonAdapter itemAdapter2;

    List<View> views = new ArrayList<>();
    int width;
    @BindView(R.id.home_img_1)
    ImageView homeImg1;
    @BindView(R.id.home_img_2)
    ImageView homeImg2;
    @BindView(R.id.home_img_3)
    ImageView homeImg3;
    @BindView(R.id.home_img_4)
    ImageView homeImg4;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        width = ScreenUtils.getScreenWidth(getContext());
        userInfo = (UserInfoEntity) SPUtils.get(context, K.USERINFO, UserInfoEntity.class);
        getHomeData();

    }


    private void getHomeData() {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                homeData = gson.fromJson(gson.toJson(o), HomeDataEntity.class);
                showUi();
            }
        };

        builder.addParameter("m_id", userInfo.getM_id());
        setProgressSubscriber(onNextListener);
        HttpMethods.getInstance(context).index(progressSubscriber, builder.bulider());
    }

    private void showUi() {
        //轮播图片
        setViewPager();
        //本周推荐
        setAdapter1();

        if (homeData.getNot_read().equals("1"))
            homeMessage.setImageResource(R.mipmap.home_message_);

        if (homeData.getGood_goods_list().size() > 0) {
            List<GoodGoodsListBean> entityList = homeData.getGood_goods_list();
            try {
                Picasso.with(context).load(entityList.get(0).getGoods_logo()).into(homeImg1);
            } catch (Exception e) {
            }
            try {
                Picasso.with(context).load(entityList.get(1).getGoods_logo()).into(homeImg2);
            } catch (Exception e) {
            }
            try {
                Picasso.with(context).load(entityList.get(2).getGoods_logo()).into(homeImg3);
            } catch (Exception e) {
            }
            try {
                Picasso.with(context).load(entityList.get(3).getGoods_logo()).into(homeImg4);
            } catch (Exception e) {
            }

        }

    }

    private void setAdapter1() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        homeRecommendRecycler.addItemDecoration(new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_GRID, getResources().getColor(R.color.background), 10, 0, 0));
        homeRecommendRecycler.setLayoutManager(gridLayoutManager);
        itemAdapter2 = new CommonAdapter<RecommendGoodsListBean>(getContext(), R.layout.item_hot_2, homeData.getRecommend_goods_list()) {

            @Override
            protected void convert(ViewHolder holder, RecommendGoodsListBean goods, int position) {
                Picasso.with(getContext()).load(goods.getGoods_logo()).into((ImageView) holder.getView(R.id.hot_2_image));
                holder.setText(R.id.hot_2_title, goods.getGoods_name());
                holder.setText(R.id.hot_2_price, "￥" + goods.getGoods_price());
                holder.setText(R.id.hot_2_number, goods.getSales() + "人付款");
            }
        };
        itemAdapter2.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                intent = new Intent(context, DetailsActivity.class);
                intent.putExtra(K.GOODS_ID, homeData.getRecommend_goods_list().get(position).getGoods_id());
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
        for (int i = 0; i < homeData.getAdvert_list().size(); i++) {

            AdvertListBean adverList = homeData.getAdvert_list().get(i);
            ImageView imageView = new ImageView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Picasso.with(getContext()).load(adverList.getAd_pic()).into(imageView);
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

    @OnClick({R.id.home_search, R.id.home_hot, R.id.home_recommend, R.id.home_favorable,
            R.id.home_integral, R.id.home_viewPage, R.id.home_message,
            R.id.home_img_1, R.id.home_img_2, R.id.home_img_3, R.id.home_img_4})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.home_img_1:
                try {
                    intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra(K.GOODS_ID, homeData.getGood_goods_list().get(0).getGoods_id());
                    startActivity(intent);
                } catch (Exception e) {
                }

                break;
            case R.id.home_img_2:
                try {
                    intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra(K.GOODS_ID, homeData.getGood_goods_list().get(1).getGoods_id());
                    startActivity(intent);
                } catch (Exception e) {
                }
                break;
            case R.id.home_img_3:
                try {
                    intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra(K.GOODS_ID, homeData.getGood_goods_list().get(2).getGoods_id());
                    startActivity(intent);
                } catch (Exception e) {
                }

                break;
            case R.id.home_img_4:
                try {
                    intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra(K.GOODS_ID, homeData.getGood_goods_list().get(3).getGoods_id());
                    startActivity(intent);
                } catch (Exception e) {
                }
                break;

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
                homeMessage.setImageResource(R.mipmap.home_message);
                break;
        }
    }
}
