package com.example.administrator.myapplication.text.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.text.activity.CustomerActivity;
import com.example.administrator.myapplication.text.adapter.common.CommonAdapter;
import com.example.administrator.myapplication.text.adapter.common.ViewHolder;
import com.example.administrator.myapplication.text.bean.KehuEvent;
import com.example.administrator.myapplication.text.bean.MessageEvent;
import com.example.administrator.myapplication.text.bean.OutboundBean;
import com.example.administrator.myapplication.text.bean.SerialBean;
import com.example.administrator.myapplication.text.bean.TimeCustomerBean;
import com.example.administrator.myapplication.text.db.DirectoryDao;
import com.example.administrator.myapplication.text.db.SerialDao;
import com.example.administrator.myapplication.text.db.TimeCustomerDao;
import com.example.administrator.myapplication.text.db.TimeDao;
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
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2018\9\3 0003.
 */

public class Scanning_Fragment extends Fragment {

    @BindView(R.id.scanning_tv)
    TextView scanningTv;
    Unbinder unbinder;
    @BindView(R.id.scanning_lrv)
    LRecyclerView scanningLrv;
    @BindView(R.id.scanning_name_tv)
    TextView scanningNameTv;
    @BindView(R.id.scanning_ll)
    LinearLayout scanningLl;
    @BindView(R.id.scanning_btn)
    Button scanningBtn;
    SerialDao serial1Dao;
    @BindView(R.id.scanning_geshu)
    TextView scanningGeshu;
    @BindView(R.id.scanning_shuliang)
    TextView scanningShuliang;
    @BindView(R.id.scanning_delete_tv)
    TextView scanningDeleteTv;


    private LRecyclerViewAdapter lRecyclerViewAdapter = null;
    private CommonAdapter<SerialBean> adapter;
    private List<SerialBean> datas = new ArrayList<>(); //PDA机屏幕上的List集合
    private SweetAlertDialog sweetAlertDialog;
    private SweetAlertDialog chongfuDialog;


    String name = "";
    String phone = "";
    String addres = "";
    String beizhu = "";

    int geshu = 0; //列表总个数
    int zongshuliang = 0;
    boolean isChongfu = false;//没有重复录入
    private File file;
    private String fileName;
    private ArrayList<ArrayList<String>> recordList;
    private static String[] title = {"序号", "扫描日期", "条码编号", "型号", "数量", "客户名称", "品牌", "备注"};
    private List<OutboundBean> outboundBeanList;//导出Excel的格式
    private List<TimeCustomerBean> timeCustomerBeans;//时间目录下苦的客户资料列表
    DirectoryDao directoryDao; //总数据的数据库
    TimeDao timeDao;//时间数据库
    TimeCustomerDao timeCustomerDao;//时间目录下客户资料数据库

    public static Scanning_Fragment newInstance() {
        Scanning_Fragment fragment = new Scanning_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text, container, false);
        unbinder = ButterKnife.bind(this, view);
        //注册订阅者
        EventBus.getDefault().register(this);
        serial1Dao = new SerialDao(getActivity());
        directoryDao = new DirectoryDao(getActivity());
        timeDao=new TimeDao(getActivity());
        timeCustomerDao=new TimeCustomerDao(getActivity());
        chongfuDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE);
        initAdapter();
        outboundBeanList = new ArrayList<>();
        timeCustomerBeans=new ArrayList<>();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    //接受扫码消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        scanningTv.setText(messageEvent.getMessage());
        initData(messageEvent.getMessage());

    }


    //接受客户资料的消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Eventkehu(KehuEvent kehuEvent) {
        name = kehuEvent.getName();
        phone = kehuEvent.getPhone();
        addres = kehuEvent.getAddress();
        scanningNameTv.setText(kehuEvent.getName());
        beizhu = kehuEvent.getBeizhu();
    }


    private void initData(String data) {
//     String str=   data.substring(0, data.indexOf("-"));
        String qian = data.substring(0, data.indexOf("-"));
        String tou = qian.substring(0, 7);
        String hou = data.substring(data.indexOf("-"));
        int intQian = Integer.parseInt(qian.substring(7, 12));
        int intHou = Integer.parseInt(hou.substring(8, 13));
        int shuliang = intHou - intQian + 1;
        List<SerialBean> list = serial1Dao.select(tou);

        for (int i = 0; i < datas.size(); i++) {
            if (data.equals(datas.get(i).getSerialNumber())) {
                chongfuDialog
                        .setTitleText("重复录入...");
                chongfuDialog.setConfirmText("确定");
                chongfuDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        chongfuDialog.dismiss();
                    }
                });
                chongfuDialog.show();
                isChongfu = true;  //重复了
                return;
            } else {
                chongfuDialog.dismiss();
                isChongfu = false;
            }

        }

        if (isChongfu == false) {
            for (int i = 0; i < list.size(); i++) {
                if (intQian >= list.get(i).getSerialNumberTouMin() && intQian < list.get(i).getSerialNumberTouMax() && intHou > list.get(i).getSerialNumberTouMin() && intHou <= list.get(i).getSerialNumberTouMax()) {
                    list.get(i).setSerialNumber(data);
                    list.get(i).setNumber(String.valueOf(shuliang));
                    datas.add(list.get(i));
                    geshu = datas.size();
                    zongshuliang += shuliang;
                    lRecyclerViewAdapter.notifyDataSetChanged();
                    Log.d("aaaaaaa", "正常录入");
                }
            }
        } else {
            Log.d("aaaaaaa", "重复录入了");
        }
        scanningGeshu.setText("总个数：" + geshu);
        scanningShuliang.setText("总数量：" + zongshuliang);
    }


    private void initOutExcel(List list, String name, String beizhu,String phone,String addres) {
        outboundBeanList.clear();
        if (list.size() == 0 || name.equals("")) {
            Toast.makeText(getActivity(), "请输入资料", Toast.LENGTH_SHORT).show();
        } else {
            List<SerialBean> xlsInfors = list;
            for (int i = 0; i < xlsInfors.size(); i++) {
                String SerialNumber = xlsInfors.get(i).getSerialNumber();
                String model = xlsInfors.get(i).getModel();
                String quantity = xlsInfors.get(i).getNumber();
                String brand = xlsInfors.get(i).getBrand();
                int xuhaoNumber = i + 1;
                outboundBeanList.add(new OutboundBean(String.valueOf(xuhaoNumber), DateUtils.getCurrentTime2(), SerialNumber, model, quantity, name, brand, beizhu,phone,addres));
            }
            boolean isdirectory = false;
            Log.d("aaaaaaa", outboundBeanList.toString() + "------aaaaaaaaaaaaaaaaaa");
            for (OutboundBean outboundBean : outboundBeanList) {
                isdirectory = directoryDao.insert(outboundBean);  //保存总数据
                timeDao.insert(outboundBean.getTime());//保存时间，以作为时间目录的数据源
                timeCustomerBeans.add(new TimeCustomerBean(outboundBean.getTime(),outboundBean.getCustomerName(),outboundBean.getPhone()));
            }
            for (TimeCustomerBean timeCustomerBean:timeCustomerBeans) {
                timeCustomerDao.insert(timeCustomerBean);//保存时间目录客户数据的数据源
            }
            if (isdirectory == true) {
                Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "保存失败", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void initAdapter() {

        adapter = new CommonAdapter<SerialBean>(getActivity(), R.layout.adapter_scanning, datas) {
            @Override
            public void setData(ViewHolder holder, SerialBean serialBean) {
                holder.setText(R.id.adapter_pinpai_tv, serialBean.getBrand());
                holder.setText(R.id.adapter_xinghao_tv, serialBean.getModel());
                holder.setText(R.id.adapter_tiaoma_tv, serialBean.getSerialNumber());
                holder.setText(R.id.adapter_shuliang_tv, serialBean.getNumber());
                holder.setText(R.id.adapter_time_tv, DateUtils.getCurrentTime2());
            }
        };

        scanningLrv.setLayoutManager(new LinearLayoutManager(getActivity()));
        lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        scanningLrv.setAdapter(lRecyclerViewAdapter);
        scanningLrv.setLoadMoreEnabled(false);
        scanningLrv.setPullRefreshEnabled(false);
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE);
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

                        int jian = Integer.parseInt(datas.get(position).getNumber());
                        zongshuliang -= jian;
                        scanningShuliang.setText("总数量：" + zongshuliang);
                        datas.remove(position);
                        lRecyclerViewAdapter.notifyDataSetChanged();
                        geshu = datas.size();
                        scanningGeshu.setText("总个数：" + geshu);
                        sweetAlertDialog.dismiss();
                    }
                });
                sweetAlertDialog.show();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        //解除订阅者
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @OnClick({R.id.scanning_ll, R.id.scanning_btn, R.id.scanning_shuliang, R.id.scanning_delete_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.scanning_ll:
                Intent intent = new Intent(getActivity(), CustomerActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                intent.putExtra("address", addres);
                intent.putExtra("beizhu", beizhu);
                startActivity(intent);
                break;
            case R.id.scanning_btn:
                initOutExcel(datas, name, beizhu,phone,addres);  //Excel表添加数据
                exportExcel(); //导出Excel表

//                List<SerialBean> list = serial1Dao.select("ABC-003", "ABC");
//                Log.d("aaaaaaa", list.toString() + "----多条件查询");
//                Collections.reverse(list);
//                Log.d("aaaaaaa", list.toString() + "----多条件查询倒叙");
                break;
            case R.id.scanning_shuliang:
//                List<OutboundBean> outboundBeanList = directoryDao.select_time_name("2018/09/18","四毛");
//                Log.d("aaaaaaa",outboundBeanList.size()+"");
//                for (OutboundBean outboundBean:outboundBeanList){
//                    Log.d("aaaaaaa",outboundBean.toString());
//                }
                break;
            case R.id.scanning_delete_tv:
//                timeDao.delete();
//                directoryDao.delete();
//                timeCustomerDao.delete();
                datas.clear();
                lRecyclerViewAdapter.notifyDataSetChanged();
                scanningShuliang.setText("总数量：0");
                scanningGeshu.setText("总个数：0");
                break;
        }
    }


    /**
     * 导出excel
     *
     * @param
     */
    public void exportExcel() {
        file = new File(getSDPath() + "/Record");
        makeDir(file);
        ExcelUtils.initExcel(file.toString() + "/出库明细.xls", title);
        fileName = getSDPath() + "/Record/出库明细.xls";
        ExcelUtils.writeObjListToExcel(getRecordData(), fileName, getActivity());
    }


    /**
     * 将数据集合 转化成ArrayList<ArrayList<String>>
     *
     * @return
     */
    private ArrayList<ArrayList<String>> getRecordData() {
        recordList = new ArrayList<>();
        for (int i = 0; i < outboundBeanList.size(); i++) {
            OutboundBean outboundBean = outboundBeanList.get(i);
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


}
