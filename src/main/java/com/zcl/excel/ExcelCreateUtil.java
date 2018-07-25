package com.zcl.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhaocl1 on 2018/6/10.
 */
public class ExcelCreateUtil {

    public static void createExcel(Map<String,List<Person>> personMap){
        int maxDate = getDaysByYearMonth(2018,6);
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("考勤");
        HSSFRow first = sheet.createRow(0);
        HSSFRow second = sheet.createRow(1);
        HSSFRow third = sheet.createRow(2);
        HSSFCellStyle headStyle = wb.createCellStyle();
        headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        HSSFCellStyle titleStyle = wb.createCellStyle();
        titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

        //迟到黄色
        HSSFCellStyle errorStyle = wb.createCellStyle();
        errorStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
        errorStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        //4  31+3
        //1 31
        sheet.addMergedRegion(new CellRangeAddress(0,0,3,maxDate+2));
        HSSFCell firstCell0 = first.createCell(3);
        firstCell0.setCellValue("2018年6月份");
        firstCell0.setCellStyle(headStyle);

        sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(1,2,1,1));
        sheet.addMergedRegion(new CellRangeAddress(1,2,2,2));

        HSSFCell cell0 = second.createCell(0);
        cell0.setCellValue("序号");
        cell0.setCellStyle(titleStyle);

        HSSFCell cell1 = second.createCell(1);
        cell1.setCellValue("姓 名");
        cell1.setCellStyle(titleStyle);

        HSSFCell cell2 = second.createCell(2);
        cell2.setCellValue("时间");
        cell2.setCellStyle(titleStyle);

        for (int i = 1; i <= maxDate; i++) {
            HSSFCell cell = second.createCell(i+2);
            cell.setCellValue(i);
            cell.setCellStyle(titleStyle);

            HSSFCell week = third.createCell(i+2);
            week.setCellValue(dateToWeek(6,i));
            week.setCellStyle(titleStyle);
        }
        //------------------------------
        int row =3;
        int no=0;
        for(Map.Entry<String,List<Person>> entry : personMap.entrySet()){
            String name = entry.getKey();
            List<Person> persons = entry.getValue();

            sheet.addMergedRegion(new CellRangeAddress(row,row+1,0,0));
            sheet.addMergedRegion(new CellRangeAddress(row,row+1,1,1));
            HSSFRow rowUp = sheet.createRow(row);
            HSSFRow rowDown = sheet.createRow(row+1);
            HSSFCell noCell = rowUp.createCell(0);
            noCell.setCellValue(++no);
            noCell.setCellStyle(titleStyle);

            HSSFCell nameCell = rowUp.createCell(1);
            nameCell.setCellValue(name);
            nameCell.setCellStyle(titleStyle);

            HSSFCell morningCell = rowUp.createCell(2);
            morningCell.setCellValue("上午");
            morningCell.setCellStyle(titleStyle);

            HSSFCell afterCell = rowDown.createCell(2);
            afterCell.setCellValue("下午");
            afterCell.setCellStyle(titleStyle);

            //---填充日期开始
            Map<Integer,List<Person>> mapPerson = App.dayPerson(persons);
            for (int i = 1; i <= maxDate; i++) {
                List<Person> ma = mapPerson.get(i);
                if (ma == null || ma.size()==0){
                    continue;
                }
                for (Person p : ma){
                    if (isAm(p.getStime())){
                        //上午
                        HSSFCell timeMorningCell = rowUp.createCell(i+2);
                        timeMorningCell.setCellValue(p.getStime());
                        if (beLate(p.getStime())){
                            timeMorningCell.setCellStyle(errorStyle);
                        }else {
                            timeMorningCell.setCellStyle(titleStyle);
                        }
                    }else {
                        HSSFCell timeafterCell = rowDown.createCell(i+2);
                        timeafterCell.setCellValue(p.getStime());
                        if (leftEarly(p.getStime())){
                            timeafterCell.setCellStyle(errorStyle);
                        }else {
                            timeafterCell.setCellStyle(titleStyle);
                        }
                    }
                }

            }

            //填充日期结束

            row +=2;
        }



        //----------------------------------
        try {
            FileOutputStream fout = new FileOutputStream("d:/excel/6月份考勤"+ System.currentTimeMillis() +".xls");
            wb.write(fout);
            fout.flush();
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("excel 文件生成 成功！");
    }

    //获取指定月份的天数
    public static int getDaysByYearMonth(int year, int month) {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    //上午 true表示上午
    public static boolean isAm(String time){
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");
        String start = "00:00:00";
        String end = "12:00:00";
        try {
            Date d1 = fmt.parse(time);
            Date d2 = fmt.parse(start);
            Date d3 = fmt.parse(end);
            if (d1.getTime() <= d3.getTime() && d1.getTime() > d2.getTime()){
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    //下午 true表示下午
    public static boolean isPm(String time){
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");
        String start = "12:00:00";
        String end = "23:59:59";
        try {
            Date d1 = fmt.parse(time);
            Date d2 = fmt.parse(start);
            Date d3 = fmt.parse(end);
            if (d1.getTime() > d2.getTime() && d1.getTime() <= d3.getTime()){
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    //迟到 true表示迟到
    public static boolean beLate(String time){
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");
        String water = "09:30:00";
        try {
            Date d1 = fmt.parse(time);
            Date d2 = fmt.parse(water);
            if (d1.getTime() > d2.getTime()){
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    //早退 true表示早退
    public static boolean leftEarly(String time){
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");
        String water = "18:30:00";
        try {
            Date d1 = fmt.parse(time);
            Date d2 = fmt.parse(water);
            if (d1.getTime() < d2.getTime()){
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String dateToWeek(int month,int day){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String date ="2018-"+month+"-"+day;
        String[] weekDays ={"周日","周一","周二","周三","周四","周五","周六"};
        Calendar cal = Calendar.getInstance();
        try {
            Date datat = fmt.parse(date);
            cal.setTime(datat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK)-1;
        if (w <0){
            w = 0;
        }
        return weekDays[w];
    }
    public static void main(String[] args) {
//        createExcel();
//        System.out.println(isAm("00:00:01"));
//        System.out.println(isPm("12:00:01"));
        System.out.println(dateToWeek(5,20));
    }
}
