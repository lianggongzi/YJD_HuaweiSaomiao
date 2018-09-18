package com.example.administrator.myapplication.text.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.administrator.myapplication.BaseActivity;
import com.example.administrator.myapplication.MainActivity;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.text.fragment.Customer_Fragment;
import com.example.administrator.myapplication.text.fragment.TimeFragment;
import com.example.administrator.myapplication.text.fragment.Scanning_Fragment;
import com.example.administrator.myapplication.text.bean.MessageEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2018\9\3 0003.
 */

public class AedActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    LinearLayout linearLayout;
    BottomNavigationBar bottomNavigationBar;

    Scanning_Fragment scanningFragment;
    Customer_Fragment customerFragment;
    TimeFragment directoryFragment;

    private Fragment mContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        initView();
    }

    @Override
    public void updateCount() {

    }

    @Override
    public void updateList(String data) {
        EventBus.getDefault().post(new MessageEvent(data));
    }

    private void initView() {
        linearLayout=findViewById(R.id.fragment_demo_ll);
        bottomNavigationBar=findViewById(R.id.bottom_navigation_bar);

        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.saoma, "扫码").setActiveColor("#3F51B5"))
                .addItem(new BottomNavigationItem(R.mipmap.saoma, "目录").setActiveColor("#3F51B5"))
                .addItem(new BottomNavigationItem(R.mipmap.daoru, "导入").setActiveColor("#3F51B5"))
                .setFirstSelectedPosition(0)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
//        setDefaultFragment();

        setDefaultFragment();
    }


    /**
     * 设置默认的fragment，即//第一次加载界面;
     */
    private void setDefaultFragment() {
        scanningFragment = Scanning_Fragment.newInstance();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.fragment_demo_ll, scanningFragment).commit();
        mContent = scanningFragment;
    }


//    private void setDefaultFragment() {
//        FragmentManager fm = getFragmentManager();
//        FragmentTransaction transaction = fm.beginTransaction();
//        fragment1 = Scanning_Fragment.newInstance();
//        transaction.add(R.id.fragment_demo_ll, fragment1);
//        transaction.commit();
//    }

    /**
     * 修改显示的内容 不会重新加载 *
     */
    public void switchContent(Fragment to) {
        FragmentManager fragmentManager = getFragmentManager();
        if (mContent != to) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (!to.isAdded()) { // 先判断是否被add过
                transaction.hide(mContent).add(R.id.fragment_demo_ll, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(mContent).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
            mContent = to;
        }
    }


    @Override
    public void onTabSelected(int position) {
        FragmentManager fm = this.getFragmentManager();
        //开启事务
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position) {
            case 0:
                if (scanningFragment == null) {
                    scanningFragment = Scanning_Fragment.newInstance();
                }
//                showCustomizeDialog(srttingFragmenr, (Integer) SPUtils.get(this, "position", 1));
                //将当前的事务添加到了回退栈
//                transaction.addToBackStack(null);
//                transaction.add(R.id.fragment_demo_ll, fragment1);
                switchContent(scanningFragment);
                break;
            case 1:
                if (directoryFragment == null) {
                    directoryFragment = TimeFragment.newInstance();
                }
//                transaction.replace(R.id.fragment_demo_ll, directoryFragment);
                switchContent(directoryFragment);
                break;
            case 2:
                if (customerFragment == null) {
                    customerFragment = Customer_Fragment.newInstance();
                }
//                transaction.add(R.id.fragment_demo_ll, fragment2);

                switchContent(customerFragment);

                break;
            default:
                break;
        }
        // 事务提交
        transaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }



}
