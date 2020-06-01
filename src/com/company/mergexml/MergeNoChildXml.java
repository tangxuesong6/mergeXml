package com.company.mergexml;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MergeNoChildXml implements Runnable {
    private String mOriginalPath;
    private String mAppendPath;
    private List<String> mNameList = new ArrayList<>();

    public MergeNoChildXml(String originalPath, String appendPath) {
        mOriginalPath = originalPath;
        mAppendPath = appendPath;
    }

    @Override
    public void run() {
        System.out.println("开始写入。。。");
        SAXReader reader = new SAXReader();
        File original = new File(mOriginalPath);
        File append = new File(mAppendPath);
        try {
            Document originalDocument = reader.read(original);
            Element originalRoot = originalDocument.getRootElement();
            List<Element> originalElements = originalRoot.elements();
            for (Element child : originalElements) {
                String name = child.attributeValue("name");
                mNameList.add(name);
//                List<Attribute> attributeList = child.attributes();
//                for (Attribute attr : attributeList) {
//                    System.out.println(attr.getName() + ": " + attr.getValue());
//                }
            }

            Document appendDocument = reader.read(append);
            Element appendRoot = appendDocument.getRootElement();
            List<Element> appendElements = appendRoot.elements();
            for (Element child : appendElements) {
                String name = child.attributeValue("name");
                if (!mNameList.contains(name)) {
                    originalRoot.addComment("增加的部分");
                    originalRoot.add(child.detach());
                    System.out.println("增加节点:");
                    List<Attribute> listAttr = child.attributes();// 所有属性
                    for (Attribute attr : listAttr) {// 遍历所有属性
                        String attName = attr.getName();// 属性名称
                        String attValue = attr.getValue();// 属性的值
                        System.out.println("  [" + attName + ": " + attValue + "]");
                    }
                }else {
                    System.out.println("相同的值********\n");
                    List<Attribute> listAttr = child.attributes();// 所有属性
                    for (Attribute attr : listAttr) {// 遍历所有属性
                        String attName = attr.getName();// 属性名称
                        String attValue = attr.getValue();// 属性的值
                        System.out.println("  [" + attName + ": " + attValue + "]");
                    }
                    System.out.println("\n 相同的值 over ********");

                }
            }
            Utils.write2file(mOriginalPath, originalDocument);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }


}
