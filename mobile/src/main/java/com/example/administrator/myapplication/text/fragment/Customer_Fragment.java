package com.example.administrator.myapplication.text.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.text.ExcelManager;
import com.example.administrator.myapplication.text.activity.AedActivity;
import com.example.administrator.myapplication.text.bean.CustomerBean;
import com.example.administrator.myapplication.text.bean.SerialBean;
import com.example.administrator.myapplication.text.bean.XLSInfor;
import com.example.administrator.myapplication.text.db.CustomerDao;
import com.example.administrator.myapplication.text.db.SerialDao;
import com.example.administrator.myapplication.text.filemanager.FileManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018\9\3 0003.
 */

public class Customer_Fragment extends Fragment {

    @BindView(R.id.btn_data_daoru)
    LinearLayout btnDataDaoru;
    @BindView(R.id.btn_kehu_daoru)
    LinearLayout btnKehuDaoru;
    Unbinder unbinder;


    private ExcelManager em = new ExcelManager();
    private String excelFile = "";
    private String showFileName = "";
    private String zhengqueFileName = "";
    public boolean isError = true;
    private Handler myHandler;
    private ProgressDialog m_Dialog;
    private boolean isCloseDialog = false;


    SerialDao serial1Dao;
    CustomerDao customerDao;

    public static Customer_Fragment newInstance() {
        Customer_Fragment fragment = new Customer_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text2, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        serialDao = new SerialDao(getActivity());

        serial1Dao = new SerialDao(getActivity());

        customerDao = new CustomerDao(getActivity());
        myHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle bundle = new Bundle();
                bundle = msg.getData();
                excelFile = bundle.getString("xuanze");
                String type = bundle.getString("type");
                if (isError == false) {
                    if (em.isEXL(excelFile, type)) {
                        if (excelToSheet(excelFile, type) == true) {
                            isCloseDialog = true;
                            isError = true;
                            myHandler.sendEmptyMessageDelayed(0, 100);
                            Toast.makeText(getActivity(), "数据导入成功",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            isCloseDialog = true;
                            isError = true;
                            myHandler.sendEmptyMessageDelayed(0, 100);
                            Toast.makeText(getActivity(), "数据导入失败",
                                    Toast.LENGTH_SHORT).show();
                            // m_Dialog.dismiss();
                        }
                    } else {
                        isCloseDialog = true;
                        isError = true;
                        myHandler.sendEmptyMessageDelayed(0, 100);
                        Toast.makeText(getActivity(), "你打开的EXCEL文件格式不对。",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (isCloseDialog) {
                        m_Dialog.dismiss();
                        isCloseDialog = false;
                        isError = false;
                    } else {
                        myHandler.sendEmptyMessageDelayed(0, 100);
                        isError = true;
                    }
                }
            }
        };
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_data_daoru, R.id.btn_kehu_daoru})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_data_daoru:
                openFileManager("ziliao");
                break;
            case R.id.btn_kehu_daoru:
                openFileManager("kehu");
                break;
        }
    }

    private void openFileManager(String type) {
        // 调用文件资源管理器
        Intent intent = new Intent();
        intent.setClass(getActivity(), FileManager.class);
        // 向资源管理器传递参数
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("xuanze", "");
        bundle.putString("filename", "");
        bundle.putBoolean("iserror", true);
        intent.putExtras(bundle);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case AedActivity.RESULT_OK:
                // 取得来自文件管理器传递的文件名
                Message msg = new Message();
                Bundle bundle = data.getExtras();
                excelFile = bundle.getString("xuanze");
                showFileName = (bundle.getString("filename"));
                // wenjian_text.setText(excelFile);
                isError = bundle.getBoolean("iserror");
                msg.setData(bundle);
//				myHandler.sendEmptyMessageDelayed(0, 100);
                myHandler.sendMessage(msg);
                m_Dialog = ProgressDialog.show(getActivity(), "请等待...",
                        "正在导入数据...", true);
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * excel表格数据进行导入
     */
    private boolean excelToSheet(String file, String type) {
        boolean isErr = false;

        if (type.equals("ziliao")) {
            List<XLSInfor> list = em.findMereExcelRecord(file, XLSInfor.class, type);
            if (list.size() > 0) {
                isErr = true;
            }
            String str = "";
            for (int i = 0; i < list.size(); i++) {
                try {
                    str = list.get(i).getSerialNumber();
                    String qian = str.substring(0, str.indexOf("-"));
                    String tou = qian.substring(0, 7);
                    String hou = str.substring(str.indexOf("-"));
                    int intQian = Integer.parseInt(qian.substring(7, 12));
                    int intHou = Integer.parseInt(hou.substring(8, 13));
                    //截取Excel表源信息的数据， 条形编码截取条码头，前数量和后数量
                    SerialBean serialBean = new SerialBean();
                    serialBean.setSerialNumber(list.get(i).getSerialNumber());
                    serialBean.setSerialNumberTou(tou);
                    serialBean.setSerialNumberTouMin(intQian);
                    serialBean.setSerialNumberTouMax(intHou);
                    serialBean.setNumber(list.get(i).getNumber());
                    serialBean.setModel(list.get(i).getModel());
                    serialBean.setBrand(list.get(i).getBrand());
                    boolean insert = serial1Dao.insert(serialBean);
                } catch (Exception x) {
                    Log.d("feng", str +"======="+ i);
                }
            }


//                for (XLSInfor xlsInfor : list) {
//
//                    //                boolean insert = serialDao.insert(xlsInfor);  原来的添加进数据库的代码
//                    String str =xlsInfor.getSerialNumber();
//                    String qian = str.substring(0, str.indexOf("-"));
//                    String tou=qian.substring(0,7);
//                    Log.d("feng",qian+"-----"+tou);
//                    String hou = str.substring(str.indexOf("-"));
//                    int intQian = Integer.parseInt(qian.substring(7, 12));
//                    int intHou = Integer.parseInt(hou.substring(8, 13));
//                    //截取Excel表源信息的数据， 条形编码截取条码头，前数量和后数量
//                    SerialBean serialBean =new SerialBean();
//                    serialBean.setSerialNumber(xlsInfor.getSerialNumber());
//                    serialBean.setSerialNumberTou(tou);
//                    serialBean.setSerialNumberTouMin(intQian);
//                    serialBean.setSerialNumberTouMax(intHou);
//                    serialBean.setNumber(xlsInfor.getNumber());
//                    serialBean.setModel(xlsInfor.getModel());
//                    serialBean.setBrand(xlsInfor.getBrand());
//                    boolean insert =    serial1Dao.insert(serialBean);
//                }
        } else if (type.equals("kehu")) {
            List<CustomerBean> list = em.findMereExcelRecord(file, CustomerBean.class, type);
            if (list.size() > 0) {
                isErr = true;
            }
            for (CustomerBean customerBean : list) {
                boolean insert = customerDao.insert(customerBean);
            }
        }
        return isErr;
    }
}
