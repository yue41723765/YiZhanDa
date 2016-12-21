package com.android.yzd.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.android.yzd.R;
import com.android.yzd.been.ClassListEntity;
import com.android.yzd.been.ClassityListEntity;
import com.android.yzd.been.TypeListBean;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.AppManager;
import com.android.yzd.tools.DensityUtils;
import com.android.yzd.tools.K;
import com.android.yzd.ui.custom.BaseActivity;
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

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.search_show)
    ImageView searchShow;
    @BindView(R.id.classify_search)
    RelativeLayout classifySearch;
    @BindView(R.id.synthesize)
    RadioButton synthesize;
    @BindView(R.id.sales_volume)
    RadioButton salesVolume;
    @BindView(R.id.price)
    RadioButton price;
    @BindView(R.id.screen)
    RadioButton screen;
    @BindView(R.id.hot_recycler_1)
    RecyclerView hotRecycler1;
    @BindView(R.id.hot_recycler_2)
    RecyclerView hotRecycler2;
    @BindView(R.id.empty)
    ImageView empty;
    @BindView(R.id.start_price)
    EditText startPrice;
    @BindView(R.id.end_price)
    EditText endPrice;
    @BindView(R.id.hot_recycler)
    RecyclerView hotRecycler;
    @BindView(R.id.popup_3_clear)
    Button popup3Clear;
    @BindView(R.id.popup_3_sure)
    Button popup3Sure;
    @BindView(R.id.search_drawer)
    DrawerLayout searchDrawer;

    int price_status = 0;
    CommonAdapter adapter_1;
    CommonAdapter adapter_2;
    CommonAdapter popupAdapter;
    Map<Integer, Boolean> isCheck = new HashMap<>();
    String sec_type_id;
    int sort = 1;//1综合排序，2销量排序;3价格升序；4价格降序；

    float d_price = 0;
    float h_price = 0;

    int lastVisibleItem = -1;
    int p = 1;
    List<ClassListEntity> goodsList = new ArrayList<>();
    List<TypeListBean> typeList = new ArrayList<>();
    boolean isData = true;

    @Override
    public int getContentViewId() {
        return R.layout.activity_classity_search;
    }


    private void getListData() {
        if (isData) {
            SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
                @Override
                public void onNext(Object o) {
                    if (p == 1)
                        goodsList.clear();
                    typeList.clear();
                    ClassityListEntity list = gson.fromJson(gson.toJson(o), ClassityListEntity.class);
                    if (list.getGoods_list().size() == 0)
                        isData = false;
                    goodsList.addAll(list.getGoods_list());
                    typeList.addAll(list.getType_list());
                    adapter_1.notifyDataSetChanged();
                    popupAdapter.notifyDataSetChanged();
                }
            };
            setProgressSubscriber(onNextListener);
            httpParamet.addParameter("sec_type_id", sec_type_id);
            httpParamet.addParameter("sort", sort + "");
            httpParamet.addParameter("d_price", d_price == 0 ? "" : "" + d_price);
            httpParamet.addParameter("h_price", h_price == 0 ? "" : "" + h_price);
            httpParamet.addParameter("p", p + "");
            HttpMethods.getInstance(this).goodsList(progressSubscriber, httpParamet.bulider());
        }

    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        sec_type_id = getIntent().getExtras().getString(K.SEC_TYPE_ID);

        //模式一
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        hotRecycler1.setLayoutManager(gridLayoutManager);
        hotRecycler1.addItemDecoration(new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_GRID, getResources().getColor(R.color.background), 10, 0, 0));
        //模式二
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        hotRecycler2.setLayoutManager(manager);
        hotRecycler2.addItemDecoration(new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_HORIZONTAL, getResources().getColor(R.color.background), 10, 0, 0));

        //热门
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        hotRecycler.setLayoutManager(layoutManager);
        hotRecycler.addItemDecoration(new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_GRID, Color.WHITE, DensityUtils.dp2px(this, 10), 0, 0));


        hotRecycler1.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter_1.getItemCount()) {
                    p++;
                    getListData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });

        hotRecycler2.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter_2.getItemCount()) {
                    p++;
                    getListData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });

        setAdapter();
        setHotAdapter();
        getListData();

    }

    private void setHotAdapter() {

        popupAdapter = new CommonAdapter<TypeListBean>(this, R.layout.item_hot_popup, typeList) {

            @Override
            protected void convert(ViewHolder holder, TypeListBean s, int position) {
                CheckBox checkBox = holder.getView(R.id.hot_classify);
                checkBox.setBackgroundResource(R.drawable.check_blue90_blue);
                checkBox.setText(s.getType_name());
                if (isCheck.get(position) == null)
                    isCheck.put(position, false);
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

    private void setAdapter() {
        adapter_1 = new CommonAdapter<ClassListEntity>(this, R.layout.item_hot_2, goodsList) {

            @Override
            protected void convert(ViewHolder holder, ClassListEntity s, int position) {
                Picasso.with(ClassitySearchActivity.this).load(s.getGoods_logo()).into((ImageView) holder.getView(R.id.hot_2_image));
                holder.setText(R.id.hot_2_title, s.getGoods_name());
                holder.setText(R.id.hot_2_price, "￥" + s.getGoods_price());
                holder.setText(R.id.hot_2_number, s.getSales() + "人付款");
            }
        };
        adapter_1.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                intent = new Intent(ClassitySearchActivity.this, DetailsActivity.class);
                intent.putExtra(K.GOODS_ID, goodsList.get(position).getGoods_id());
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        hotRecycler1.setAdapter(adapter_1);

        adapter_2 = new CommonAdapter<ClassListEntity>(this, R.layout.item_hot_1, goodsList) {

            @Override
            protected void convert(ViewHolder holder, ClassListEntity goodsInfoEntity, int position) {
                Picasso.with(ClassitySearchActivity.this).load(goodsInfoEntity.getGoods_logo()).into((ImageView) holder.getView(R.id.hot_image));
                holder.setText(R.id.hot_title, goodsInfoEntity.getGoods_name());
                holder.setText(R.id.hot_price, "￥" + goodsInfoEntity.getGoods_price());
                holder.setText(R.id.hot_number, goodsInfoEntity.getSales() + "人付款");

            }
        };
        adapter_2.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                intent = new Intent(ClassitySearchActivity.this, DetailsActivity.class);
                intent.putExtra(K.GOODS_ID, goodsList.get(position).getGoods_id());
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        hotRecycler2.setAdapter(adapter_2);
    }

    int showStatus = 1;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.synthesize:
                p = 1;
                sort = 1;
                getListData();
                break;
            case R.id.sales_volume:
                p = 1;
                sort = 2;
                getListData();
                break;

            case R.id.classify_search:
                intent = new Intent(this, HomeSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.search_show:
                if (showStatus == 1) {
                    showStatus = 2;
                    searchShow.setImageResource(R.mipmap.show_black);
                    hotRecycler1.setVisibility(View.GONE);
                    hotRecycler2.setVisibility(View.VISIBLE);

                } else {
                    showStatus = 1;
                    searchShow.setImageResource(R.mipmap.list_black);
                    hotRecycler1.setVisibility(View.VISIBLE);
                    hotRecycler2.setVisibility(View.GONE);

                }
                break;
            case R.id.screen:
                searchDrawer.openDrawer(Gravity.RIGHT);
                break;
            case R.id.price:
                price_status++;
                Drawable drawable;
                if (price_status % 2 == 0) {
                    drawable = getResources().getDrawable(R.mipmap.price_down);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    price.setCompoundDrawables(null, null, drawable, null);

                    //升序
                    p = 1;
                    sort = 3;
                    isData = true;
                    getListData();
                } else {
                    drawable = getResources().getDrawable(R.mipmap.price_up);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    price.setCompoundDrawables(null, null, drawable, null);
                    p = 1;
                    sort = 4;
                    isData = true;
                    getListData();
                }
                break;
            case R.id.popup_3_sure:
                String start = startPrice.getText().toString();
                String end = endPrice.getText().toString();

                for (int i = 0; i < isCheck.size(); i++) {
                    if (isCheck.get(i)) {
                        sec_type_id = typeList.get(i).getSec_type_id();
                    }
                }

                d_price = Float.valueOf(start.equals("") ? "0" : start);
                h_price = Float.valueOf(end.equals("") ? "0" : end);

                isData = true;
                p = 1;
                getListData();
                goodsList.clear();
                adapter_1.notifyDataSetChanged();
                adapter_2.notifyDataSetChanged();

                searchDrawer.closeDrawer(Gravity.RIGHT);
                break;
            case R.id.popup_3_clear:
                startPrice.setText("");
                endPrice.setText("");
                for (int i = 0; i < isCheck.size(); i++) {
                    isCheck.put(i, false);
                }
                popupAdapter.notifyDataSetChanged();
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
