package com.example.administrator.myapplication.text.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * <pre>
 *     author : Nian
 *     e-mail : 457505316@qq.com
 *     time  : 2018-02-09
 *     desc  :
 *     version: 1.0
 * </pre>
 */
public class ConditionScrollView extends ScrollView {

    private static StopCall stopCall;

    //ScrollView向上滑动到顶部的距离
    private int upH;

    public ConditionScrollView(Context context) {
        super(context);
    }

    public ConditionScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //赋值：200很重要，这个值是顶部2上面的高度
        upH = dpTopx(208);//单位是dp
    }

    public ConditionScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public static void setCallback(StopCall c) {
        stopCall = c;
    }

    /**
     * 关键部分在这里，测量当前ScrollView滑动的距离
     * <p>
     * 其中t就是，单位是px，不是dp
     * <p>
     * stopCall是一个接口，是为了在Activity中实现设置顶部1/2可不可见
     */

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (t > upH) {//如果向上滑动距离>本例中图片高度
            stopCall.stopSlide(true);//设置顶部1可见，顶部2不可见
        } else {//否则
            stopCall.stopSlide(false);//设置顶部1不可见，顶部2可见

        }

    }

    /**
     * F: 将dp转成为px
     */

    private int dpTopx(int dpValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public interface StopCall {

        void stopSlide(boolean isStop);
    }
}
