package com.example.administrator.myapplication.text.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.text.adapter.common.CommonAdapter;
import com.example.administrator.myapplication.text.adapter.common.ViewHolder;
import com.example.administrator.myapplication.text.bean.CustomerBean;
import com.example.administrator.myapplication.text.bean.KehuEvent;
import com.example.administrator.myapplication.text.bean.KehuEventXiugai;
import com.example.administrator.myapplication.text.db.CustomerDao;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018\9\5 0005.
 * 搜索客户界面
 */

public class CustomerActivity extends AppCompatActivity {
    @BindView(R.id.customer_data)
    TextView customerData;
    @BindView(R.id.customer_name)
    TextView customerName;
    @BindView(R.id.customer_phone)
    TextView customerPhone;
    @BindView(R.id.customer_address)
    TextView customerAddress;
    @BindView(R.id.customer_rl)
    RelativeLayout customerRl;
    @BindView(R.id.customer_search_edt)
    EditText customerSearchEdt;
    @BindView(R.id.customer_search_tv)
    TextView customerSearchTv;
    @BindView(R.id.customer_search_lrv)
    LRecyclerView customerSearchLrv;
    @BindView(R.id.beizhu_edt)
    EditText beizhuEdt;
    @BindView(R.id.beizhu_btn)
    Button beizhuBtn;

    private LRecyclerViewAdapter lRecyclerViewAdapter = null;
    private CommonAdapter<CustomerBean> adapter;
    private List<CustomerBean> datas = new ArrayList<>();
    CustomerDao customerDao;

    String name = "";
    String phone = "";
    String address = "";
    String beizhu="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //让布局向上移来显示软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_customer);
        ButterKnife.bind(this);

        initView();
        customerDao = new CustomerDao(this);
        initAdapter();
    }

    private void initView() {
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        address = intent.getStringExtra("address");
        beizhu=intent.getStringExtra("beizhu");
        if (name.equals("")) {
            customerData.setText("当前客户信息：无");
            customerRl.setVisibility(View.GONE);
        } else {
            customerData.setText("当前客户信息：");
            customerName.setText("姓名：" + name);
            customerPhone.setText(phone);
            customerAddress.setText("地址：" + address);
            beizhuEdt.setText(beizhu);
            customerRl.setVisibility(View.VISIBLE);
        }
    }

    private void initAdapter() {
        adapter = new CommonAdapter<CustomerBean>(this, R.layout.adapter_customer, datas) {
            @Override
            public void setData(ViewHolder holder, CustomerBean customerBean) {
                holder.setText(R.id.adapter_customer_name, "姓名：" + customerBean.getName());
                holder.setText(R.id.adapter_customer_phone, customerBean.getPhone());
                holder.setText(R.id.adapter_customer_address, "地址：" + customerBean.getAddres());
            }
        };
        customerSearchLrv.setLayoutManager(new LinearLayoutManager(this));
        lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        customerSearchLrv.setAdapter(lRecyclerViewAdapter);
        customerSearchLrv.setLoadMoreEnabled(false);
        customerSearchLrv.setPullRefreshEnabled(false);
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                name = datas.get(position).getName();
                phone = datas.get(position).getPhone();
                address = datas.get(position).getAddres();
                customerData.setText("当前客户信息：");
                customerName.setText("姓名：" + datas.get(position).getName());
                customerPhone.setText(datas.get(position).getPhone());
                customerAddress.setText("地址：" + datas.get(position).getAddres());
                customerRl.setVisibility(View.VISIBLE);
                EventBus.getDefault().post(new KehuEvent(name, phone, address, beizhuEdt.getText().toString()));
                EventBus.getDefault().post(new KehuEventXiugai(name, phone, address, beizhuEdt.getText().toString()));
//                finish();
            }
        });

    }

    @OnClick({R.id.customer_search_tv, R.id.beizhu_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.customer_search_tv:
                String data = customerSearchEdt.getText().toString();

                initData(data);
                break;
            case R.id.beizhu_btn:
                EventBus.getDefault().post(new KehuEvent(name, phone, address, beizhuEdt.getText().toString()));
                EventBus.getDefault().post(new KehuEventXiugai(name, phone, address, beizhuEdt.getText().toString()));
                finish();
                break;
        }

    }

    private void initData(String data) {
        datas.clear();
        List<CustomerBean> list = customerDao.select(data);
        for (int i = 0; i < list.size(); i++) {
        }
        datas.addAll(list);
        lRecyclerViewAdapter.notifyDataSetChanged();
    }

}
