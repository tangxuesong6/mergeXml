package com.company.mergexml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.FileOutputStream;
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
}
