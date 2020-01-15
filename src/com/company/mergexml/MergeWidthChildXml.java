package com.company.mergexml;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MergeWidthChildXml implements Runnable {
    private String mOriginalPath;
    private String mAppendPath;
    private List<String> mNameList = new ArrayList<>();
    private Map<String, Element> mMap = new HashMap<>();

    public MergeWidthChildXml(String originalPath, String appendPath) {
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
                boolean hasChild = child.hasContent();
                if (hasChild) {
                    mMap.put(name, child);
                } else {
                    mMap.put(name, null);
                }
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
                if (!mMap.containsKey(name)) {
                    originalRoot.addComment("增加的部分");
                    originalRoot.add(child.detach());
                    System.out.println("增加节点:");
                    List<Attribute> listAttr = child.attributes();// 所有属性
                    for (Attribute attr : listAttr) {// 遍历所有属性
                        String attName = attr.getName();// 属性名称
                        String attValue = attr.getValue();// 属性的值
                        System.out.println("  [" + attName + ": " + attValue + "]");
                    }
                } else {
                    if (child.hasContent() && mMap.get(name) != null) {
                        List<String> list = new ArrayList<>();
                        for (Element element : mMap.get(name).elements()) {
                            list.add(element.attributeValue("name"));
                        }
                        for (Element element : child.elements()) {
                            String attrName = element.attributeValue("name");
                            if (!list.contains(attrName)) {
                                throw new RuntimeException("child maybe not equals [ " + name + " ]");
                            }
                        }
                    }
                }
            }
            Utils.write2file(mOriginalPath, originalDocument);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}
