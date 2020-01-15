package com.company.mergexml;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MergePublicXml implements Runnable {
    private String mOriginalPath;
    private String mAppendPath;
    private Map<String, String> mNameMap;
    private Map<String, Integer> mMaxValue;

    public MergePublicXml(String originalPath, String appendPath) {
        mOriginalPath = originalPath;
        mAppendPath = appendPath;
        mNameMap = new HashMap<>();
        mMaxValue = new HashMap<>();
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
                String type = child.attributeValue("type");
                String name = child.attributeValue("name");
                String strId = child.attributeValue("id");
                Integer id = Utils.string2Integer(strId);
                if (mMaxValue.containsKey(type)) {
                    if (id > mMaxValue.get(type)) {
                        mMaxValue.put(type, id);
                    }
                } else {
                    mMaxValue.put(type, id);
                }
                mNameMap.put(name, type);
            }

            Document appendDocument = reader.read(append);
            Element appendRoot = appendDocument.getRootElement();
            List<Element> appendElements = appendRoot.elements();
            for (Element child : appendElements) {
                String name = child.attributeValue("name");
                String type = child.attributeValue("type");
                boolean dontAdd = mNameMap.containsKey(name) && mNameMap.get(name).equals(type);

                if (!dontAdd) {
                    originalRoot.addComment("增加的部分");
                    if (!mMaxValue.containsKey(type)) {
                        originalRoot.add(child.detach());
                    } else {
                        Integer intID = mMaxValue.get(type);
                        int realId = intID + 1;
                        mMaxValue.put(type, realId);
                        String ID = "0x" + Integer.toHexString(realId);
                        Attribute idAtt = child.attribute("id");
                        idAtt.setValue(ID);
                        originalRoot.add(child.detach());
                    }
                    System.out.println("增加节点:");
                    List<Attribute> listAttr = child.attributes();// 所有属性
                    for (Attribute attr : listAttr) {// 遍历所有属性
                        String attName = attr.getName();// 属性名称
                        String attValue = attr.getValue();// 属性的值
                        System.out.println("  [" + attName + ": " + attValue + "]");
                    }

                }

            }
            Utils.write2file(mOriginalPath,originalDocument);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }


}
