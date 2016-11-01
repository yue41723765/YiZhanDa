package com.android.yzd.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.yzd.R;
import com.android.yzd.tools.DensityUtils;
import com.android.yzd.tools.T;
import com.android.yzd.ui.activity.AddOrderActivity;
import com.android.yzd.ui.activity.DetailsActivity;
import com.android.yzd.ui.custom.BaseFragment;
import com.squareup.picasso.Picasso;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/2 0002.
 */
public class ShoppingCartFragment extends BaseFragment {

    @BindView(R.id.sc_message)
    ImageView scMessage;
    @BindView(R.id.sc_edit)
    TextView scEdit;
    @BindView(R.id.top_tools)
    RelativeLayout topTools;
    @BindView(R.id.cs_choose_all)
    CheckBox csChooseAll;
    @BindView(R.id.sc_total_title)
    TextView scTotalTitle;
    @BindView(R.id.sc_total_price)
    TextView scTotalPrice;
    @BindView(R.id.close_an_account)
    TextView closeAnAccount;
    @BindView(R.id.sc_status_1)
    RelativeLayout scStatus1;
    @BindView(R.id.add_collect)
    Button addCollect;
    @BindView(R.id.delete)
    Button delete;
    @BindView(R.id.sc_status_2)
    LinearLayout scStatus2;
    @BindView(R.id.bottom_tools)
    RelativeLayout bottomTools;
    @BindView(R.id.sc_recycler)
    RecyclerView scRecycler;

    List<String> lists = new ArrayList<>();
    Map<Integer, Boolean> isCheck = new HashMap<>();
    CommonAdapter adapter;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_shopping_cart;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {

        lists.add("https://img.alicdn.com/imgextra/i3/1971062271/TB26KWjacHA11Bjy0FiXXckfVXa_!!1971062271.jpg_430x430q90.jpg");
        lists.add("http://img0.imgtn.bdimg.com/it/u=1743793690,2889040548&fm=21&gp=0.jpg");
        lists.add("http://img2.imgtn.bdimg.com/it/u=1229763493,252127255&fm=21&gp=0.jpg");
        lists.add("http://imgmall.tg.com.cn/group1/M00/46/57/CgooalPoddOsG7OSAARd524pfKw795.jpg");
        lists.add("http://img2.imgtn.bdimg.com/it/u=2784392893,1098602407&fm=11&gp=0.jpg");


        for (int i = 0; i < lists.size(); i++) {
            isCheck.put(i, false);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        scRecycler.setLayoutManager(linearLayoutManager);
        scRecycler.setItemAnimator(new DefaultItemAnimator());

        adapter = new CommonAdapter<String>(getContext(), R.layout.item_shopping_cart, lists) {
            @Override
            protected void convert(ViewHolder holder, String s, final int position) {
                if (position == 0) {
                    View view = holder.getConvertView();
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0, DensityUtils.dp2px(context, 10), 0, 0);
                    view.setLayoutParams(params);
                }
                CheckBox checkBox = holder.getView(R.id.item_check);
                Picasso.with(getContext()).load(s).into((ImageView) holder.getView(R.id.image));
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        isCheck.put(position, isChecked);
                    }
                });
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
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
        scRecycler.setAdapter(adapter);
    }

    @OnClick({R.id.sc_edit, R.id.delete, R.id.sc_message})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.sc_message:
                break;
            case R.id.sc_edit:
                if (scEdit.getText().toString().equals("编辑")) {
                    scEdit.setText("完成");

                    scStatus1.setVisibility(View.GONE);
                    scStatus2.setVisibility(View.VISIBLE);
                } else {
                    scEdit.setText("编辑");
                    scStatus1.setVisibility(View.VISIBLE);
                    scStatus2.setVisibility(View.GONE);
                }
                break;
            case R.id.close_an_account:
                //结算
                intent = new Intent(getContext(), AddOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.add_collect:
                //收藏
                break;
            case R.id.delete:
//                List<Integer> del_position = new ArrayList<>();
//                //删除
//                if (isCheck.size() > 0) {
//                    for (int i = 0; i < isCheck.size(); i++) {
//                        if (isCheck.get(i)) {
//                            del_position.add(i);
//                        }
//                    }
////                    Collections.sort(lists);
//                    Collections.reverse(lists);
//                    for (int i : del_position) {
//                        lists.remove(i);
//                    }
//                    adapter.notifyDataSetChanged();
//                } else {
//                    T.show(getContext(), "请选中商品", Toast.LENGTH_SHORT);
//                }

                break;
        }
    }
}
