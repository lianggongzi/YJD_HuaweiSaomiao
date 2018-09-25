package com.example.administrator.myapplication.text.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.style.TtsSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.text.activity.DirectoryCustomerAcvitity;
import com.example.administrator.myapplication.text.adapter.common.CommonAdapter;
import com.example.administrator.myapplication.text.adapter.common.ViewHolder;
import com.example.administrator.myapplication.text.bean.DirectoryBean;
import com.example.administrator.myapplication.text.db.DirectoryDao;
import com.example.administrator.myapplication.text.db.TimeDao;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
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
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2018\9\13 0013.
 */

public class TimeFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.time_search_edt)
    EditText timeSearchEdt;
    @BindView(R.id.time_search_tv)
    TextView timeSearchTv;
    @BindView(R.id.time_search_lrv)
    LRecyclerView timeSearchLrv;


    private LRecyclerViewAdapter lRecyclerViewAdapter = null;
    private CommonAdapter<String> adapter;
    private List<String> datas = new ArrayList<>(); //导出Excel表数据库里时间的List集合
    TimeDao timeDao;


    public static TimeFragment newInstance() {
        TimeFragment fragment = new TimeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    //通过add hide show 方式来切换Fragment（Fragment1不走任何生命周期,但会调onHiddenChanged方法）
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        initData();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        timeDao = new TimeDao(getActivity());
        initAdapter();
        initData();
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        initData();
    }

    private void initData() {
        datas.clear();
        List<String> stringList = timeDao.select();
        Collections.reverse(stringList);
        for (int i = 0; i < stringList.size(); i++) {
            datas.add(stringList.get(i));
            lRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    private void initAdapter() {
        adapter = new CommonAdapter<String>(getActivity(), R.layout.adapter_directory, datas) {
            @Override
            public void setData(ViewHolder holder, String s) {
                holder.setText(R.id.adapter_direcory_tv, s);
            }
        };
        timeSearchLrv.setLayoutManager(new LinearLayoutManager(getActivity()));
        lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        timeSearchLrv.setAdapter(lRecyclerViewAdapter);

        timeSearchLrv.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新 Progress 的样式
//        timeSearchLrv.setArrowImageView(R.drawable.iconfont_downgrey);  //设置下拉刷新箭头
        timeSearchLrv.setOnRefreshListener(new OnRefreshListener() {  //下拉刷新
            @Override
            public void onRefresh() {
                initData();
                timeSearchLrv.refreshComplete(1);
                lRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
        timeSearchLrv.setLoadMoreEnabled(false);//禁止加载更多
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getActivity(), DirectoryCustomerAcvitity.class);
                intent.putExtra("time",datas.get(position));
                startActivity(intent);



            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.time_search_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.time_search_tv:
                String time = timeSearchEdt.getText().toString();
                List<String> stringList = timeDao.selectTime(time);
                Collections.reverse(stringList);
                datas.clear();
                for (int i = 0; i < stringList.size(); i++) {
                    datas.add(stringList.get(i));
                    lRecyclerViewAdapter.notifyDataSetChanged();
                }
                break;
        }
    }
}
