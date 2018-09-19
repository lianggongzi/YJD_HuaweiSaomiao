package com.example.administrator.myapplication.text.utris;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * Created by Administrator on 2018\9\19 0019.
 */

public class ExcelUtils1 {
    public static WritableFont arial14font = null;

    public static WritableCellFormat arial14format = null;
    public static WritableFont arial10font = null;
    public static WritableCellFormat arial10format = null;
    public static WritableFont arial12font = null;
    public static WritableCellFormat arial12format = null;

    public final static String UTF8_ENCODING = "UTF-8";
    public final static String GBK_ENCODING = "GBK";


    /**
     * 单元格的格式设置 字体大小 颜色 对齐方式、背景颜色等...
     */
    public static void format() {
        try {
            arial14font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
            arial14font.setColour(jxl.format.Colour.LIGHT_BLUE);
            arial14format = new WritableCellFormat(arial14font);
            arial14format.setAlignment(jxl.format.Alignment.CENTRE);
            arial14format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial14format.setBackground(jxl.format.Colour.VERY_LIGHT_YELLOW);

            arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            arial10format = new WritableCellFormat(arial10font);
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);
            arial10format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial10format.setBackground(Colour.GRAY_25);

            arial12font = new WritableFont(WritableFont.ARIAL, 10);
            arial12format = new WritableCellFormat(arial12font);
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);//对齐格式
            arial12format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN); //设置边框

        } catch (WriteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化Excel
     *
     * @param fileName
     * @param colName
     */
    public static void initExcel(String fileName, String[] colName) {
        format();
        WritableWorkbook workbook = null;
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            workbook = Workbook.createWorkbook(file);
            WritableSheet sheet = workbook.createSheet("102", 0);
            //创建标题栏
            sheet.addCell((WritableCell) new Label(0, 0, fileName, arial14format));
            for (int col = 0; col < colName.length; col++) {
                sheet.addCell(new Label(col, 0, colName[col], arial10format));
            }
            sheet.setRowView(0, 340); //设置行高

            workbook.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static <T> void writeObjListToExcel(List<T> objList, String fileName, Context c) {
        if (objList != null && objList.size() > 0) {
            WritableWorkbook writebook = null;
//            InputStream in = null;
            File file = new File(fileName);
            try {
                WorkbookSettings setEncode = new WorkbookSettings();
                setEncode.setEncoding(UTF8_ENCODING);
//                in = new FileInputStream(new File(fileName));
//                Workbook workbook = Workbook.getWorkbook(in);
                Workbook workbook = Workbook.getWorkbook(file);
                WritableSheet sheetold= (WritableSheet) workbook.getSheet(0);
                writebook = Workbook.createWorkbook(file);
                WritableSheet sheet = writebook.getSheet(0);

                int row = sheetold.getRows();
                for (int j = 0; j <row ; j++) {
                    ArrayList<String> list = (ArrayList<String>) objList.get(j);
                    for (int i = 0; i < list.size(); i++) {
                        sheet.addCell(new Label(i, j , list.get(i), arial12format));
                        if (list.get(i).length() <= 5) {
                            sheet.setColumnView(i, list.get(i).length() + 8); //设置列宽
                        } else {
                            sheet.setColumnView(i, list.get(i).length() + 5); //设置列宽
                        }
                    }
                }



                for (int j = 0; j < objList.size(); j++) {
                    ArrayList<String> list = (ArrayList<String>) objList.get(j);
                    for (int i = 0; i < list.size(); i++) {
                        sheet.addCell(new Label(i, j + 1, list.get(i), arial12format));
                        if (list.get(i).length() <= 5) {
                            sheet.setColumnView(i, list.get(i).length() + 8); //设置列宽
                        } else {
                            sheet.setColumnView(i, list.get(i).length() + 5); //设置列宽
                        }
                    }
                    sheet.setRowView(j + 1, 350); //设置行高
                }

                writebook.write();
                Toast.makeText(c, "导出到手机存储中文件夹Record成功", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if (writebook != null) {
                    try {
                        writebook.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
//                if (in != null) {
//                    try {
//                        in.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
            }

        }
    }


//    // 创建excel表.
//    public void createExcel(File file) {
//        WritableSheet ws = null;
//        WritableWorkbook workbook = null;
//        try {
//            if (!file.exists()) {
//                // 创建表
//                workbook = Workbook.createWorkbook(file);
//                // 创建表单,其中sheet表示该表格的名字,0表示第一个表格,
//                ws = workbook.createSheet(sheet1, 0);
//                // 在指定单元格插入数据
//                Label lbl1 = new Label(0, 0, 姓名);
//                // 第一个参数表示,0表示第一列,第二个参数表示行,同样0表示第一行,第三个参数表示想要添加到单元格里的数据.
//                Label bll2 = new Label(1, 0, 性别);
//                // 添加到指定表格里.
//                ws.addCell(lbl1);
//                ws.addCell(bll2);
//                // 从内存中写入文件中
//                workbook.write();
//                workbook.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    //    然后当想往表里添加数据时,应该:
//    public void writeToExcel(String name, String gender) {
//        WritableWorkbook workbook = null;
//        try {
////            每次插入数据,都要取原来的表,然后新建一个表,然后将原来的表的内容添加到新表上.但只要两个路径相同的话,效果相当于在原来的表添加.
//            Workbook oldWwb = Workbook.getWorkbook(excelFile);
//            workbook = Workbook.createWorkbook(excelFile, oldWwb);
////             获取指定索引的表格
//            WritableSheet ws = workbook.getSheet(0);
////             获取该表格现有的行数
//            int row = ws.getRows();
//            Label lbl1 = new Label(0, row, name);
//            Label bll2 = new Label(1, row, gender);
//            ws.addCell(lbl1);
//            ws.addCell(bll2);
////             从内存中写入文件中,只能刷一次.
//            workbook.write();
//            workbook.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}

