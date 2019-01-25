package com.example.administrator.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
public class MainActivity extends BaseActivity {
    BottomNavigationBar bottomNavigationBar;
    TextView PAKID_Tv;
    TextView PN_Tv;
    TextView PO_Tv;
    TextView MFGCODE_Tv;
    TextView DATE_Tv;
    TextView GW_Tv;
    TextView NW_Tv;
    TextView MLot_Tv;
    TextView Qty_tv;
    TextView Baocun;
    CheckBox checkBox;
    private long mExitTime;

    static String firm_PAKID = "[)>06F01001P52S";
    static String firm_PN = "18VLEHWTF02010I1P";
    static String firm_PO = "K";
    static String firm_MFGCODE = "1V";
    static String firm_DATE = "10D";
    static String firm_GW = "Q";
    static String firm_NW = "7Q";
    static String firm_MLot = "1T";
    static String firm_Qty = "Q";
    String imei = "";


//    private ArrayList<ArrayList<String>> recordList;
//    private List<PO> poList;
//    private static String[] title = {"PKG ID", "PN", "PO", "MFG CODE", "DATE", "G.W", "N.W", "M.Lot", "Qty"};
//    private File file;
//    private String fileName;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PAKID_Tv = findViewById(R.id.pakgid_tv);
        PN_Tv = findViewById(R.id.pn_tv);
        PO_Tv = findViewById(R.id.po_tv);
        MFGCODE_Tv = findViewById(R.id.mfgcode_tv);
        DATE_Tv = findViewById(R.id.date_tv);
        GW_Tv = findViewById(R.id.gw_tv);
        NW_Tv = findViewById(R.id.nw_tv);
        MLot_Tv = findViewById(R.id.mlot_tv);
        Qty_tv = findViewById(R.id.qty_tv);
        Baocun = findViewById(R.id.baocun);
        checkBox = findViewById(R.id.checkbox);
        TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        imei = tm.getDeviceId();
        Log.d("feng", tm.getDeviceId() + "-------");
    }


    @Override
    public void updateCount() {
//        Log.d("feng",pressed+"---------"+scanned);
//        mainTv.setText("Scan: " + pressed + "times\nResult: " + scanned + "times");
    }

    @Override
    public void updateList(String data) {
        Log.d("feng", data + "-------");
        String test = "[)>\u001E06\u001DF01001P\u001D52SLAZ01VS11829AA0007\u001D18VLEHWT\u001DF02010I\u001D1P99056AAW\u001DK100243257\u001D1VZ01VS1\u001D10D1829(20)\u001D1Q0.63KG\u001D\u001D1TS173372\u001DQ20\u001E\u0004 QR";
//
//        if (imei.equals("861094030427992")) {
            String delete001E = data.replaceAll("\u001E", "");
            String delete001D = delete001E.replaceAll("\u001D", "");
            String delete004 = delete001D.replaceAll("\u0004", "");
            String resultData = delete004.replaceAll(" ", "");
            try {
                //获取PAKID
                String PAKID = PAKID_Data(resultData, firm_PN);
                //裁剪PN---
                String str_PN1 = resultData.replace(firm_PAKID + PAKID, "");
                //获取PN
                String PN = subString(str_PN1, firm_PN, firm_PO);
                //裁剪PO---
                String str_PO1 = str_PN1.replace(firm_PN + PN, "");
                //获取PO
                String PO = subString(str_PO1, firm_PO, firm_MFGCODE);
                //裁剪MFGCODE---
                String str_MFGCODE1 = str_PO1.replace(firm_PO + PO, "");
                //获取MFGCODE
                String MFGCODE = subString(str_MFGCODE1, firm_MFGCODE, firm_DATE);
                //裁剪DATE---
                String str_DATE1 = str_MFGCODE1.replace(firm_MFGCODE + MFGCODE, "");
                //获取DATE
                String DATE = subString(str_DATE1, firm_DATE, firm_GW);
                //裁剪GW
                String str_GW1 = str_DATE1.replace(firm_DATE + DATE, "");
                //获取GW
                String GWs = subString(str_GW1, firm_GW, firm_MLot);
//            //裁剪NW
//            String str_NW1 = str_GW1.replace(firm_GW + GW, "");
//            //获取NW
//            String NW = subString(str_NW1, firm_NW, firm_MLot);
                String NW = "";
                String GW = "";
                if (GWs.indexOf(firm_NW) != -1) {
                    GW = GWs.substring(0, GWs.indexOf(firm_NW));
                    NW = GWs.substring(GWs.indexOf(firm_NW) + firm_NW.length());
                } else {
                    GW = GWs;
                }
                //裁剪Mlot
                String str_Mlot1 = str_GW1.replace(firm_GW + GWs, "");
                //获取Mlot
                String MLot = subString(str_Mlot1, firm_MLot, firm_Qty);
                //裁剪Qty
                String str_Qty1 = str_Mlot1.replace(firm_MLot + MLot, "");
                //获取GW
//            String Qty = str_Qty1.replace(firm_Qty, "");
                String Qty = str_Qty1.substring(str_Qty1.indexOf(firm_Qty) + firm_Qty.length());
                PAKID_Tv.setText("PKG ID：" + PAKID);
                PN_Tv.setText("PN：" + PN);
                PO_Tv.setText("PO：" + PO);
                MFGCODE_Tv.setText("MFG CODE：" + MFGCODE);
                DATE_Tv.setText("DATE：" + DATE);
                GW_Tv.setText("G.W：" + GW);
                NW_Tv.setText("N.W：" + NW);
                MLot_Tv.setText("M.Lot：" + MLot);
                Qty_tv.setText("Qty：" + Qty);


                if (checkBox.isChecked() == true) {
                    //直接往本地存储
                    StringBuilder str = new StringBuilder();
                    str.append(PAKID_Tv.getText().toString() + "\n");
                    str.append(PN_Tv.getText().toString() + "\n");
                    str.append(PO_Tv.getText().toString() + "\n");
                    str.append(MFGCODE_Tv.getText().toString() + "\n");
                    str.append(DATE_Tv.getText().toString() + "\n");
                    str.append(GW_Tv.getText().toString() + "\n");
                    str.append(NW_Tv.getText().toString() + "\n");
                    str.append(MLot_Tv.getText().toString() + "\n");
                    str.append(Qty_tv.getText().toString() + "\n");
                    byte[] b = str.toString().getBytes();
                    SDCardHelper.saveFileToSDCardPrivateFilesDir(b, "", PO, MainActivity.this);
                }


            } catch (Exception e) {
                Log.d("feng", e + "");
                Toast.makeText(this, "扫码有误！！！", Toast.LENGTH_SHORT).show();
            }
//        } else {
//            showDialog(imei);
//        }
    }


//
//    /**
//     * 将数据集合 转化成ArrayList<ArrayList<String>>
//     *
//     * @return
//     */
//    private ArrayList<ArrayList<String>> getRecordData() {
//        recordList = new ArrayList<>();
//        for (int i = 0; i < poList.size(); i++) {
//            PO po = poList.get(i);
//            ArrayList<String> beanList = new ArrayList<String>();
//            beanList.add(po.firm_PAKID);
//            beanList.add(po.firm_PN);
//            beanList.add(po.firm_PO);
//            beanList.add(po.firm_MFGCODE);
//            beanList.add(po.firm_DATE);
//            beanList.add(po.firm_GW);
//            beanList.add(po.firm_NW);
//            beanList.add(po.firm_MLot);
//            beanList.add(po.firm_Qty);
//
//            recordList.add(beanList);
//        }
//        return recordList;
//    }
//
//    private String getSDPath() {
//        File sdDir = null;
//        boolean sdCardExist = Environment.getExternalStorageState().equals(
//                android.os.Environment.MEDIA_MOUNTED);
//        if (sdCardExist) {
//            sdDir = Environment.getExternalStorageDirectory();
//        }
//        String dir = sdDir.toString();
//        return dir;
//    }
//
//    public void makeDir(File dir) {
//        if (!dir.getParentFile().exists()) {
//            makeDir(dir.getParentFile());
//        }
//        dir.mkdir();
//    }


    public static String PAKID_Data(String str, String firm_PN) {
        int strStartIndex = 15;
        int strEndIndex = str.indexOf(firm_PN);
        String result = str.substring(strStartIndex, strEndIndex);
        return result;
    }

    public static String subString(String str_Data, String strStart, String strEnd) {
        int strStartIndex = str_Data.indexOf(strStart) + strStart.length();
        int strEndIndex = str_Data.indexOf(strEnd);
        String result = str_Data.substring(strStartIndex, strEndIndex);
        return result;
    }

    public void showDialog(String imei) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("本设备（" + imei + "）暂未激活");
        builder.setMessage("请联系服务商工作人员");
        builder.show();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
