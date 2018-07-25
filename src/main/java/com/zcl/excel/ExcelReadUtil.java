package com.zcl.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by zhaocl1 on 2018/6/10.
 */
public class ExcelReadUtil {
    private static final String EXCEL_XLS ="xls";
    private static final String EXCEL_XLSX ="xlsx";

    /**
     * 判断Excel的版本,获取Workbook
     * @param in
     * @param file
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbok(InputStream in,File file) throws IOException {
        Workbook wb = null;
        if(file.getName().endsWith(EXCEL_XLS)){  //Excel 2003
            wb = new HSSFWorkbook(in);
        }else if(file.getName().endsWith(EXCEL_XLSX)){  // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }

    /**
     * 判断文件是否是excel
     * @throws Exception
     */
    public static void checkExcelVaild(File file) throws Exception{
        if(!file.exists()){
            throw new Exception("文件不存在");
        }
        if(!(file.isFile() && (file.getName().endsWith(EXCEL_XLS) || file.getName().endsWith(EXCEL_XLSX)))){
            throw new Exception("文件不是Excel");
        }
    }

    public static List<Person> readExcel(){
        try {
            // 同时支持Excel 2003、2007
            File excelFile = new File("d:/excel/2018-06.xlsx"); // 创建文件对象
            FileInputStream in = new FileInputStream(excelFile); // 文件流
            checkExcelVaild(excelFile);
            Workbook workbook = getWorkbok(in,excelFile);
            //Workbook workbook = WorkbookFactory.create(is); // 这种方式 Excel2003/2007/2010都是可以处理的

            int sheetCount = workbook.getNumberOfSheets(); // Sheet的数量
            /**
             * 设置当前excel中sheet的下标：0开始
             */
//            Sheet sheet = workbook.getSheetAt(0);   // 遍历第一个Sheet
            Sheet sheet = workbook.getSheetAt(0);   // 遍历第三个Sheet

            //获取总行数
            System.out.println(sheet.getLastRowNum());
            List<Person> persons = new ArrayList<>();

            for (int rownum=1;rownum<sheet.getLastRowNum();rownum++){
                Row row = sheet.getRow(rownum);
                if (row != null){
                    Person person = new Person();
                    Cell cellNo = row.getCell(1);
                    Cell cellName = row.getCell(2);
                    Cell cellPartment = row.getCell(3);
                    Cell cellTime = row.getCell(4);
                    Cell cellDate = row.getCell(7);
                    Cell celltimes = row.getCell(8);

                    person.setNo((String) getValue(cellNo));
                    person.setName((String) getValue(cellName));
                    person.setDapartment((String) getValue(cellPartment));
                    person.setAlldate(strintToDate((String) getValue(cellTime)));
                    person.setSdate(null);
                    person.setStime(null);
                    person.setDay(0);
                    persons.add(person);
//                    System.out.println(getValue2(cellNo));
//                    System.out.print(getValue(cellName));
//                    System.out.print(getValue(cellPartment));
//                    System.out.print(getValue(cellTime));
//                    System.out.println();
                }
            }
            System.out.println(persons);
            return persons;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date strintToDate(String datess){
        String newDate = datess.substring(0,10)+" "+datess.substring(11).replace("-",":");
        SimpleDateFormat fmt3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return fmt3.parse(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     *
     * 读取Excel测试，兼容 Excel 2003/2007/2010
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        List<Person> persons = readExcel();

        Map<String,Person> map = persons.stream().collect(Collectors.toMap(Person::getName, person -> person));

        System.out.println(map);
    }

    private static String getValue2(Cell cell) {
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
            return String.valueOf(cell.getNumericCellValue());
        }else {
            return String.valueOf(cell.getStringCellValue());
        }
    }

    private static Object getValue(Cell cell) {
        Object obj = null;
        switch (cell.getCellTypeEnum()) {
            case BOOLEAN:
                obj = cell.getBooleanCellValue();
                break;
            case ERROR:
                obj = cell.getErrorCellValue();
                break;
            case NUMERIC:
               cell.getNumericCellValue();
                break;
            case STRING:
                obj = cell.getStringCellValue();
                break;
            default:
                break;
        }
        return obj;
    }
}
