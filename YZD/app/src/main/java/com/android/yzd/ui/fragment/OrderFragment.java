package com.android.yzd.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.yzd.R;
import com.android.yzd.been.OrderInfoEntity;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.DensityUtils;
import com.android.yzd.tools.K;
import com.android.yzd.tools.T;
import com.android.yzd.ui.activity.OrderDetailsActivity;
import com.android.yzd.ui.custom.BaseFragment;
import com.android.yzd.ui.view.MyItemDecoration;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/10/8 0008.
 */
public class OrderFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.not_data)
    ImageView notData;

    CommonAdapter adapter;

    String status;
    int p = 1;
    int lastVisibleItem = -1;
    List<OrderInfoEntity> orderList = new ArrayList<>();
    boolean isData = true;


    @Override
    public int getContentViewId() {
        return R.layout.refresh_recycler;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        status = getArguments().getString(K.STATUS);
        init();
        setAdapter();
        getOrderInfo();
        refresh.setOnRefreshListener(this);
    }

    private void init() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recycler.setLayoutManager(linearLayoutManager);
        Drawable drawable1 = getResources().getDrawable(android.R.color.black);
        recycler.addItemDecoration(new MyItemDecoration(OrientationHelper.VERTICAL, drawable1, DensityUtils.dp2px(context, 3)));

        recycler.setLayoutManager(linearLayoutManager);
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter.getItemCount()) {
                    p++;
                    getOrderInfo();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void setAdapter() {
        adapter = new CommonAdapter<OrderInfoEntity>(getContext(), R.layout.item_order, orderList) {

            @Override
            protected void convert(ViewHolder holder, final OrderInfoEntity s, int position) {
                switch (s.getStatus()) {
                    case "0":
                        holder.setText(R.id.order_status, "待支付");
                        holder.setVisible(R.id.order_cancel, true);
                        holder.setVisible(R.id.order_pay, true);
                        holder.setText(R.id.order_cancel, "取消订单");
                        holder.setText(R.id.order_pay, "查看支付方式");
                        break;
                    case "1":
                        holder.setText(R.id.order_status, "待发货");
                        holder.setVisible(R.id.order_cancel, true);
                        holder.setText(R.id.order_cancel, "取消订单");
                        break;
                    case "2":
                        holder.setText(R.id.order_status, "待收货");
                        holder.setVisible(R.id.order_pay, true);
                        holder.setText(R.id.order_pay, "确定支付");
                        break;
                    case "3":
                        holder.setText(R.id.order_status, "已完成");
                        holder.setVisible(R.id.order_cancel, true);
                        holder.setText(R.id.order_cancel, "删除订单");
                        break;
                    case "4":
                        holder.setText(R.id.order_status, "已取消");
                        holder.setVisible(R.id.order_cancel, true);
                        holder.setText(R.id.order_cancel, "删除订单");
                        break;
                }


                RecyclerView recyclerView = holder.getView(R.id.order_recycler);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                Drawable drawable1 = getResources().getDrawable(android.R.color.white);
                recyclerView.addItemDecoration(new MyItemDecoration(OrientationHelper.VERTICAL, drawable1, DensityUtils.dp2px(context, 3)));
                CommonAdapter adapter = new CommonAdapter<OrderInfoEntity.GoodsListBean>(context, R.layout.item_order_item, s.getGoods_list()) {

                    @Override
                    protected void convert(ViewHolder holder, OrderInfoEntity.GoodsListBean s, int position) {
                        Picasso.with(context).load(s.getGoods_logo()).into((ImageView) holder.getView(R.id.order_image));
                        holder.setText(R.id.order_title_, s.getGoods_name());
                        holder.setText(R.id.order_number, s.getNumber() + "");
                        holder.setText(R.id.order_price, "￥" + s.getGoods_price());
                    }
                };
                recyclerView.setAdapter(adapter);


                holder.setOnClickListener(R.id.order_cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (s.getStatus()) {
                            case "0":
                            case "1":
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("提示");
                                builder.setMessage("是否取消订单?");
                                builder.setNegativeButton("取消", new AlertDialog.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                builder.setPositiveButton("确定", new AlertDialog.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        cancelOrder(s.getOrder_id());
                                    }
                                });
                                builder.show();
                                break;
                            case "3":
                            case "4":
                                builder = new AlertDialog.Builder(context);
                                builder.setTitle("提示");
                                builder.setMessage("是否删除订单?");
                                builder.setNegativeButton("取消", new AlertDialog.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                builder.setPositiveButton("确定", new AlertDialog.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        deleteOrder(s.getOrder_id());
                                    }
                                });
                                break;
                        }
                    }
                });
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                intent = new Intent(context, OrderDetailsActivity.class);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        recycler.setAdapter(adapter);
    }

    //获取订单数据
    private void getOrderInfo() {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                List<OrderInfoEntity> list = gson.fromJson(gson.toJson(o), new TypeToken<List<OrderInfoEntity>>() {
                }.getType());
                orderList.addAll(list);
                adapter.notifyDataSetChanged();
                if (list.size() == 0) {
                    isData = false;
                    if (p == 1) {
                        notData.setVisibility(View.VISIBLE);
                        refresh.setVisibility(View.GONE);
                    }
                } else {
                    notData.setVisibility(View.GONE);
                    refresh.setVisibility(View.VISIBLE);
                }
            }
        };
        if (isData) {
            builder.clear();
            builder.addParameter("m_id", getUserInfo().getM_id());
            switch (status) {
                case "全部":
                    builder.addParameter("type", "");
                    break;
                case "待支付":
                    builder.addParameter("type", "0");
                    break;
                case "待发货":
                    builder.addParameter("type", "1");
                    break;
                case "待收货":
                    builder.addParameter("type", "2");
                    break;
                case "已完成":
                    builder.addParameter("type", "4");
                    break;
            }

            builder.addParameter("p", p + "");
            setProgressSubscriber(onNextListener);
            HttpMethods.getInstance(context).orderList(progressSubscriber, builder.bulider());
        }
    }

    //取消订单
    private void cancelOrder(String order_id) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                T.show(context, "取消订单成功", Toast.LENGTH_SHORT);
                onRefresh();
            }
        };
        setProgressSubscriber(onNextListener);
        builder.clear();
        builder.addParameter("order_id", order_id);
        HttpMethods.getInstance(context).cancelOrder(progressSubscriber, builder.bulider());
    }

    //删除订单
    private void deleteOrder(String order_id) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                T.show(context, "取消订单成功", Toast.LENGTH_SHORT);
                onRefresh();
            }
        };
        setProgressSubscriber(onNextListener);
        builder.clear();
        builder.addParameter("order_id", order_id);
        HttpMethods.getInstance(context).deleteOrder(progressSubscriber, builder.bulider());
    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(false);
                p = 1;
                isData = true;
                orderList.clear();
                adapter.notifyDataSetChanged();
                getOrderInfo();
            }
        }, 500);
    }
}
