package com.example.administrator.myapplication.text.activity;

import android.content.Intent;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.text.adapter.common.CommonAdapter;
import com.example.administrator.myapplication.text.adapter.common.ViewHolder;
import com.example.administrator.myapplication.text.bean.DirectoryBean;
import com.example.administrator.myapplication.text.bean.KehuEvent;
import com.example.administrator.myapplication.text.bean.KehuEventXiugai;
import com.example.administrator.myapplication.text.bean.OutboundBean;
import com.example.administrator.myapplication.text.bean.SerialBean;
import com.example.administrator.myapplication.text.db.DirectoryDao;
import com.example.administrator.myapplication.text.db.TimeCustomerDao;
import com.example.administrator.myapplication.text.utris.DateUtils;
import com.example.administrator.myapplication.text.utris.ExcelUtils;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2018\9\14 0014.
 */

public class DirectoryActivity extends AppCompatActivity {
    @BindView(R.id.directory_lrv)
    LRecyclerView directoryLrv;
    @BindView(R.id.directory_geshu)
    TextView directoryGeshu;
    @BindView(R.id.directory_shuliang)
    TextView directoryShuliang;
    @BindView(R.id.directory_name_tv)
    TextView directoryNameTv;
    @BindView(R.id.directory_ll)
    LinearLayout directoryLl;
    @BindView(R.id.scanning_btn)
    Button scanningBtn;
    private LRecyclerViewAdapter lRecyclerViewAdapter = null;
    private CommonAdapter<OutboundBean> adapter;
    private List<OutboundBean> datas = new ArrayList<>(); //PDA机屏幕上的List集合
    private SweetAlertDialog sweetAlertDialog;
    int geshu = 0; //列表总个数
    int zongshuliang = 0;
    DirectoryDao directoryDao;
    String time = "";
    String name = "";
    String beizhu = "";
    String addres = "";
    String phone = "";
    int quantity = 0;
    TimeCustomerDao timeCustomerDao;

    private File file;
    private String fileName;
    private ArrayList<ArrayList<String>> recordList;
    private static String[] title = {"序号", "扫描日期", "条码编号", "型号", "数量", "客户名称", "品牌", "备注"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory);
        ButterKnife.bind(this);
        directoryDao = new DirectoryDao(this);
        timeCustomerDao = new TimeCustomerDao(this);
        Intent intent = getIntent();
        time = intent.getStringExtra("time");
        name = intent.getStringExtra("name");
        //注册订阅者
        EventBus.getDefault().register(this);

        initAdapter();
        initData();
    }


    //接受客户资料的消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void KehuEventXiugai(KehuEventXiugai kehuEvent) {
        name = kehuEvent.getName();
        phone = kehuEvent.getPhone();
        addres = kehuEvent.getAddress();
        directoryNameTv.setText(kehuEvent.getName());
        beizhu = kehuEvent.getBeizhu();
        directoryDao.xiugai(name,beizhu,phone,addres);
        timeCustomerDao.xiugai(name,phone);
        initData();
    }

    private void initData() {
        datas.clear();
        List<OutboundBean> outboundBeanList = directoryDao.select_time_name(time, name);
        Log.d("aaaaaaa", time + "------" + name);
        Log.d("aaaaaaa", outboundBeanList.toString() + "------" + outboundBeanList.size());
        for (OutboundBean outboundBean : outboundBeanList) {
            datas.add(outboundBean);
            geshu = datas.size();
            quantity = Integer.parseInt(outboundBean.getQuantity());
            zongshuliang += quantity;
            lRecyclerViewAdapter.notifyDataSetChanged();
        }
        directoryShuliang.setText("总数量：" + zongshuliang);
        directoryGeshu.setText("总个数：" + geshu);
        directoryNameTv.setText(name);
        addres=datas.get(0).getAddres();
        phone=datas.get(0).getPhone();
        beizhu=datas.get(0).getBeizhu();
    }

    private void initAdapter() {
        adapter = new CommonAdapter<OutboundBean>(DirectoryActivity.this, R.layout.adapter_scanning, datas) {
            @Override
            public void setData(ViewHolder holder, OutboundBean outboundBean) {
                holder.setText(R.id.adapter_pinpai_tv, outboundBean.getBrand());
                holder.setText(R.id.adapter_xinghao_tv, outboundBean.getModel());
                holder.setText(R.id.adapter_tiaoma_tv, outboundBean.getBarcodeNumber());
                holder.setText(R.id.adapter_shuliang_tv, outboundBean.getQuantity());
                holder.setText(R.id.adapter_time_tv, DateUtils.getCurrentTime2());
            }
        };
        directoryLrv.setLayoutManager(new LinearLayoutManager(this));
        lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        directoryLrv.setAdapter(lRecyclerViewAdapter);
        directoryLrv.setLoadMoreEnabled(false);
        directoryLrv.setPullRefreshEnabled(false);
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                sweetAlertDialog = new SweetAlertDialog(DirectoryActivity.this, SweetAlertDialog.WARNING_TYPE);
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

                        int jian = Integer.parseInt(datas.get(position).getXuhaoNumber());
                        zongshuliang -= jian;
                        directoryShuliang.setText("总数量：" + zongshuliang);
                        datas.remove(position);
                        lRecyclerViewAdapter.notifyDataSetChanged();
                        geshu = datas.size();
                        directoryGeshu.setText("总个数：" + geshu);
                        sweetAlertDialog.dismiss();
                    }
                });
                sweetAlertDialog.show();
            }
        });

    }

    @OnClick({R.id.directory_ll, R.id.scanning_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.directory_ll:
                Intent intent = new Intent(this, CustomerActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                intent.putExtra("address", addres);
                intent.putExtra("beizhu", beizhu);
                startActivity(intent);
                break;
            case R.id.scanning_btn:
                exportExcel("101");
                break;
        }
    }

    /**
     * 导出excel
     *
     * @param
     */
    public void exportExcel(String excelName) {
        file = new File(getSDPath() + "/Record");
        makeDir(file);
//        ExcelUtils.initExcel(file.toString() + "/出库明细.xls", title);
        ExcelUtils.initExcel(file.toString() + "/"+excelName+".xls", title,excelName);
        fileName = getSDPath() + "/Record/"+excelName+".xls";
        ExcelUtils.writeObjListToExcel(getRecordData(), fileName, this);
    }


    /**
     * 将数据集合 转化成ArrayList<ArrayList<String>>
     *
     * @return
     */
    private ArrayList<ArrayList<String>> getRecordData() {
        recordList = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            OutboundBean outboundBean = datas.get(i);
            ArrayList<String> beanList = new ArrayList<String>();
            beanList.add(outboundBean.getXuhaoNumber());
            beanList.add(outboundBean.getTime());
            beanList.add(outboundBean.getBarcodeNumber());
            beanList.add(outboundBean.getModel());
            beanList.add(outboundBean.getQuantity());
            beanList.add(outboundBean.getCustomerName());
            beanList.add(outboundBean.getBrand());
            beanList.add(outboundBean.getBeizhu());
            recordList.add(beanList);
        }
        return recordList;
    }


    private String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        String dir = sdDir.toString();
        return dir;
    }

    public void makeDir(File dir) {
        if (!dir.getParentFile().exists()) {
            makeDir(dir.getParentFile());
        }
        dir.mkdir();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除订阅者
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
