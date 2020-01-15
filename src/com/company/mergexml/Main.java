package com.company.mergexml;

import org.dom4j.DocumentException;

public class Main {

    public static void main(String[] args) throws DocumentException {
        String originalPath = "/home/txs/Downloads/jdom-2.0.6/";
        String appendPath = "/home/txs/Downloads/jdom-2.0.6/";
//        mergePublicXml(originalPath, appendPath);
//        mergeArraysXml(originalPath,appendPath);
        mergeAttrsXml(originalPath, appendPath);

    }

    private static void mergeAttrsXml(String originalPath, String appendPath) {
        originalPath = originalPath + "attrs.xml";
        appendPath = appendPath + "attrs.xml";
        new Thread(new MergeWidthChildXml(originalPath, appendPath)).start();
    }

    private static void mergeArraysXml(String originalPath, String appendPath) {
        originalPath = originalPath + "arrays.xml";
        appendPath = appendPath + "arrays.xml";
        new Thread(new MergeNoChildXml(originalPath, appendPath)).start();
    }

    private static void mergePublicXml(String originalPath, String appendPath) {
        originalPath = originalPath + "public.xml";
        appendPath = appendPath + "public.xml";
        new Thread(new MergePublicXml(originalPath, appendPath)).start();
    }

    private static void mergeBoolsXml(String originalPath, String appendPath) {
        originalPath = originalPath + "bools.xml";
        appendPath = appendPath + "boos.xml";
        new Thread(new MergeNoChildXml(originalPath, appendPath)).start();
    }

    private static void mergeColorsXml(String originalPath, String appendPath) {
        originalPath = originalPath + "colors.xml";
        appendPath = appendPath + "colors.xml";
        new Thread(new MergeNoChildXml(originalPath, appendPath)).start();
    }

    private static void mergeDimensXml(String originalPath, String appendPath) {
        originalPath = originalPath + "dimens.xml";
        appendPath = appendPath + "dimens.xml";
        new Thread(new MergeNoChildXml(originalPath, appendPath)).start();
    }

    private static void mergeDrawablesXml(String originalPath, String appendPath) {
        originalPath = originalPath + "drawables.xml";
        appendPath = appendPath + "drawables.xml";
        new Thread(new MergeNoChildXml(originalPath, appendPath)).start();
    }

    private static void mergeIdsXml(String originalPath, String appendPath) {
        originalPath = originalPath + "ids.xml";
        appendPath = appendPath + "ids.xml";
        new Thread(new MergeNoChildXml(originalPath, appendPath)).start();
    }

    private static void mergeIntegersXml(String originalPath, String appendPath) {
        originalPath = originalPath + "integers.xml";
        appendPath = appendPath + "integers.xml";
        new Thread(new MergeNoChildXml(originalPath, appendPath)).start();
    }

    private static void mergeStringsXml(String originalPath, String appendPath) {
        originalPath = originalPath + "strings.xml";
        appendPath = appendPath + "strings.xml";
        new Thread(new MergeNoChildXml(originalPath, appendPath)).start();
    }

    private static void mergeStylesXml(String originalPath, String appendPath) {
        originalPath = originalPath + "styles.xml";
        appendPath = appendPath + "styles.xml";
        new Thread(new MergeWidthChildXml(originalPath, appendPath)).start();
    }

}
