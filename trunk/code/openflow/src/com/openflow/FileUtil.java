package com.openflow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

/**
 * created by lh
 * 文件操作相关
 */
public class FileUtil {

	 /**
     * 得到文件内容
     * @param path
     * @return
     */
    public static String getFileContent(String path) {
        try {
            if(path == null || path.trim().equals("")) {
                System.out.println("路径为空！");
                return null;
            }
            File file = new File(path);
            if(!file.exists()) {
                System.out.println("文件不存在！");
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                                            new FileInputStream(path)));
            String line = new String();
            StringBuffer temp = new StringBuffer();

            while ((line = reader.readLine()) != null) {
                temp.append(line);
            }
            reader.close();
            return temp.toString();
        }catch(FileNotFoundException e) {
           e.printStackTrace();
           System.out.println("文件没有找到！");
        }catch(IOException e) {
           e.printStackTrace();
           System.out.println("文件读取错误！");
        } 
        
          return "";
    }
    
    /**
     * 读取03版word文件的内容
     * @param fileName
     * @return
     * @throws Exception
     */
    public static String Word_03_Reader(String fileName) throws Exception {
            StringBuffer temp = new StringBuffer();

            FileInputStream fis = new FileInputStream(fileName);
             HWPFDocument doc = new HWPFDocument(fis);
             Range range = doc.getRange();
            int paragraphCount = range.numParagraphs();// 段落
            for (int i = 0; i < paragraphCount; i++) {
                    // 遍历段落读取数据
                    Paragraph pp = range.getParagraph(i);
                    temp.append(pp.text());
             }

            fis.close();
            return temp.toString();
    }
    
    /**
     * 读取07版word文件的内容
     * @param fileName
     * @return
     * @throws Exception
     */
    public static String Word_07_Reader(String fileName) throws Exception {
            OPCPackage opcPackage = POIXMLDocument.openPackage(fileName); 
            POIXMLTextExtractor ex = new XWPFWordExtractor(opcPackage); 
             opcPackage.close();
            return ex.getText(); 
    }
    
    /**
     * 读取03版excel文件的内容
     * @param fileName
     * @return
     * @throws Exception
     */
    public static String Excel_03_Reader(String fileName) throws Exception {
            StringBuffer temp = new StringBuffer();
            FileInputStream fis = new FileInputStream(fileName);

            HSSFWorkbook wb = new HSSFWorkbook(fis);

            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                    HSSFSheet sheet = wb.getSheetAt(i);
                    for (Iterator<Row> rowIt = sheet.iterator(); rowIt.hasNext();) {
                            Row r = rowIt.next();
                            for (Iterator<org.apache.poi.ss.usermodel.Cell> cellIt = r
                                            .iterator(); cellIt.hasNext();) {
                                    org.apache.poi.ss.usermodel.Cell cell = cellIt.next();
                                    String cellContent = "";

                                    //如果是数字类型
                                     if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                                             cellContent = String
                                                             .valueOf(cell.getNumericCellValue());
                                     }
                                    //如果是字符串类型
                                     if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                                             cellContent = cell.getStringCellValue();
                                     }

                                      temp.append(cellContent);
                            }
                     }
            }
            fis.close();
             return getPureText(temp.toString());
     }
     
     /**
     * 读取07版excel文件的内容
     * @param fileName
     * @return
     * @throws Exception
     */
    public static String Excel_07_Reader(String fileName) throws Exception {
            StringBuffer temp = new StringBuffer();
            FileInputStream fis = new FileInputStream(fileName);

            XSSFWorkbook wb = new XSSFWorkbook(fis);
             for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                     XSSFSheet sheet = wb.getSheetAt(i);
                      for (Iterator<Row> rowIt = sheet.iterator(); rowIt.hasNext();) {
                              Row r = rowIt.next();
                              for (Iterator<org.apache.poi.ss.usermodel.Cell> cellIt = r
                                             .iterator(); cellIt.hasNext();) {
                                     org.apache.poi.ss.usermodel.Cell cell = cellIt.next();
                                       String cellContent = "";

                                      //如果是数字类型
                                     if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                                              cellContent = String
                                                             .valueOf(cell.getNumericCellValue());
                                    }
                                     //如果是字符串类型
                                     if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                                               cellContent = cell.getStringCellValue();
                                     }

                                     temp.append(cellContent);
                             }
                     }
             }
            fis.close();
            return getPureText(temp.toString());
    }
    
    /**
     * 向文件中写入内容
     * @param text
     * @param path
     */
    public static void writeToFile(String text, String path) {
        try {
          FileWriter fw = new FileWriter(path);
          fw.write(text);
          fw.flush();
          fw.close();
        }catch(IOException e) {
          e.printStackTrace();
          System.out.println("文件写入错误！");
        }  
    }
    
    public static void writeToFile(String text, File file) {
        try {
          FileWriter fw = new FileWriter(file);
          fw.write(text);
          fw.flush();
          fw.close();
        }catch(IOException e) {
          e.printStackTrace();
          System.out.println("文件写入错误！");
        }  
    }
     
    /**
     * 得到路径path下的所有文件
     * @param path
     * @return
     */
    public static List<File> getAllFiles(File path, List<File> fileList) throws Exception{
        if(path.isFile()) {
            fileList.add(path);
            return fileList;
        }
        File[] files = path.listFiles();
        for(int i=0; i<files.length; i++) {
            getAllFiles(new File(files[i].getCanonicalPath()), fileList);
        }
        return fileList;
    }
    
    /**
     * 得到path路径下的所有文件夹
     * @param path
     * @param fileList
     * @return
     * @throws Exception
     */
    public static List<File> getAllDirectories(File path, List<File> fileList) throws Exception{
        File[] files = path.listFiles();
        for(int i=0; i<files.length; i++) {
            if(files[i].isDirectory()) {
                fileList.add(files[i]);
                getAllDirectories(new File(files[i].getCanonicalPath()), fileList);
            }
        }
        return fileList;
    }
    
    /**
     * 得到纯文本
     * @param text
     * @return
     */
    public static String getPureText(String text) {
        return text.replaceAll("<([^>]*)>", "").replaceAll("\\s*|\t|\r|\n","").replaceAll("&nbsp;", "");
    }
    
    /**
     * 删除文件
     * @param list
     */
    public static void deleteFile(List<File> list) {
        if(list != null) {
            for(int i=0; i<list.size(); i++) {
                File f = list.get(i);
                if(f.exists()) {
                    f.delete();
                }
            }
        }
    }
}

