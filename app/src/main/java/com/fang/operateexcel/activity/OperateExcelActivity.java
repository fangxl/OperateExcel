package com.fang.operateexcel.activity;

import android.util.Log;

import com.fang.operateexcel.bean.XmlItem;
import com.fang.operateexcel.databinding.ActivityOperateExcelBinding;
import com.fang.operateexcel.utils.XmlUtil;
import com.my.common.base.BaseActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class OperateExcelActivity extends BaseActivity<ActivityOperateExcelBinding> {
    List<XmlItem> xmlBean = new ArrayList<>();

    @Override
    protected void initPresenter() {
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {

//        String path = Environment.getExternalStorageDirectory()+"/fang/national.xls";
//        File file = new File(path);
//        readExcel(file);
        readExcel2();
        String jsonXml = XmlUtil.jsonToXml(xmlBean);
        Log.d("TestActivity","jsonXml:" + jsonXml);
        XmlUtil.createXMLFile(jsonXml,"string");
    }

    // 去读Excel的方法readExcel，该方法的入口参数为一个File对象
    public void readExcel(File file) {
        try {
            // 创建输入流，读取Excel
            InputStream is = new FileInputStream(file.getAbsolutePath());
            // jxl提供的Workbook类
            Workbook wb = Workbook.getWorkbook(is);
            // Excel的页签数量
            int sheet_size = wb.getNumberOfSheets();
//            for (int index = 0; index < sheet_size; index++) {
                // 每个页签创建一个Sheet对象
                Sheet sheet = wb.getSheet(0);
                // sheet.getRows()返回该页的总行数
                for (int i = 0; i < sheet.getRows(); i++) {
                    XmlItem xmlItem = new XmlItem();
                    // sheet.getColumns()返回该页的总列数
                    for (int j = 0; j < sheet.getColumns(); j++) {
                        String cellinfo = sheet.getCell(j, i).getContents();
                        Log.d("excel",cellinfo+"\t");
                        if(j==1){
                            xmlItem.setName(cellinfo);
                        }else if(j==2){
                            xmlItem.setContent(cellinfo);
                        }
                    }
                    xmlBean.add(xmlItem);
                    System.out.println();
                }
//            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 读取assets目录下的文件
    public void readExcel2() {
        try {
            // 创建输入流，读取Excel
            InputStream is = this.getAssets().open("test.xls");
            // jxl提供的Workbook类
            Workbook wb = Workbook.getWorkbook(is);
            // Excel的页签数量
            int sheet_size = wb.getNumberOfSheets();
//            for (int index = 0; index < sheet_size; index++) {
            // 每个页签创建一个Sheet对象
            Sheet sheet = wb.getSheet(0);
            // sheet.getRows()返回该页的总行数
            for (int i = 0; i < sheet.getRows(); i++) {
                XmlItem xmlItem = new XmlItem();
                // sheet.getColumns()返回该页的总列数
                for (int j = 0; j < sheet.getColumns(); j++) {
                    String cellinfo = sheet.getCell(j, i).getContents();
                    Log.d("excel",cellinfo+"\t");
                    if(j==1){
                        xmlItem.setName(cellinfo);
                    }else if(j==2){
                        xmlItem.setContent(cellinfo);
                    }
                }
                xmlBean.add(xmlItem);
                System.out.println();
            }
//            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}