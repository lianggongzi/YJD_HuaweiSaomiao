package com.example.administrator.myapplication.text.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.text.adapter.common.CommonAdapter;
import com.example.administrator.myapplication.text.adapter.common.ViewHolder;
import com.example.administrator.myapplication.text.bean.TimeCustomerBean;
import com.example.administrator.myapplication.text.db.DirectoryDao;
import com.example.administrator.myapplication.text.db.TimeCustomerDao;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnItemLongClickListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2018\9\14 0014.
 * 时间目录下的客户名字页面
 */

public class DirectoryCustomerAcvitity extends AppCompatActivity {

    @BindView(R.id.customer_search_lrv)
    LRecyclerView customerSearchLrv;
    private LRecyclerViewAdapter lRecyclerViewAdapter = null;
    private CommonAdapter<TimeCustomerBean> adapter;
    private List<TimeCustomerBean> datas = new ArrayList<>(); //导出Excel表数据库里时间的List集合
    String time = "";
    TimeCustomerDao timeCustomerDao;
    DirectoryDao directoryDao;
    private SweetAlertDialog sweetAlertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory_customer);
        ButterKnife.bind(this);
        timeCustomerDao = new TimeCustomerDao(this);
        directoryDao = new DirectoryDao(this);
        initAdapter();
        initData();
    }

    private void initAdapter() {
        adapter = new CommonAdapter<TimeCustomerBean>(this, R.layout.adapter_directory_customer, datas) {
            @Override
            public void setData(ViewHolder holder, TimeCustomerBean bean) {
                holder.setText(R.id.adapter_direcory_name_tv, bean.getName());
                holder.setText(R.id.adapter_direcory_phone_tv, bean.getPhone());
            }
        };
        customerSearchLrv.setLayoutManager(new LinearLayoutManager(this));
        lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        customerSearchLrv.setAdapter(lRecyclerViewAdapter);
        customerSearchLrv.setPullRefreshEnabled(false);//禁止下拉刷新
        customerSearchLrv.setLoadMoreEnabled(false);//禁止加载更多
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(DirectoryCustomerAcvitity.this, DirectoryActivity.class);
                intent.putExtra("time", datas.get(position).getTime());
                intent.putExtra("name", datas.get(position).getName());
                intent.putExtra("beizhu", datas.get(position).getBeizhu());
                startActivity(intent);
            }
        });
        lRecyclerViewAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                sweetAlertDialog = new SweetAlertDialog(DirectoryCustomerAcvitity.this, SweetAlertDialog.WARNING_TYPE);
                sweetAlertDialog.showCancelButton(true);
                sweetAlertDialog.setCancelText("取消");
                sweetAlertDialog.setTitleText("确定删除此条信息?");
                sweetAlertDialog.setConfirmText("确定");
                sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        timeCustomerDao.delete(datas.get(position).getPhone());
                        directoryDao.delete(datas.get(position).getTime(), datas.get(position).getPhone());
                        datas.remove(position);
                        lRecyclerViewAdapter.notifyDataSetChanged();
                        sweetAlertDialog.dismiss();
                    }
                });
                sweetAlertDialog.show();


            }
        });

    }

    private void initData() {
        Intent intent = getIntent();
        time = intent.getStringExtra("time");
        datas.clear();
        List<TimeCustomerBean> stringList = timeCustomerDao.selectTime(time);
        Collections.reverse(stringList);
        Log.d("aaaaaaa", stringList.toString());
        for (int i = 0; i < stringList.size(); i++) {
            datas.add(stringList.get(i));
            lRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

}
