package com.company.mergexml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class Utils {
    public static void write2file(String originalPath, Document originalDocument) throws DocumentException, IOException {
        //指定文件输出位置
        FileOutputStream out = new FileOutputStream(originalPath);
        OutputFormat format = OutputFormat.createPrettyPrint();//标准化布局，适合查看时显示。
        format.setEncoding("utf-8");//指定文件格式
        XMLWriter writer = new XMLWriter(out, format);
        Document generateDocument = DocumentHelper.parseText(originalDocument.asXML());
        writer.write(generateDocument);//写入文件
        System.out.println("写入成功");
        writer.close();
    }

    public static Integer string2Integer(String strId) {
        //转为十进制 Integer
        return Integer.parseInt(strId.substring(2), 16);
    }

    public static void writeLog(String originalPath, String content) throws IOException {
        String path = originalPath.substring(0, originalPath.length() - 10);
        File F = new File(path + "log.txt");
        //如果文件不存在,就动态创建文件
        if (!F.exists()) {
            F.createNewFile();
        }
        FileWriter fw = null;
        try {
            //设置为:True,表示写入的时候追加数据
            fw = new FileWriter(F, true);
            //回车并换行
            fw.write(content + "\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fw != null) {
                fw.close();
            }
        }

    }

}
