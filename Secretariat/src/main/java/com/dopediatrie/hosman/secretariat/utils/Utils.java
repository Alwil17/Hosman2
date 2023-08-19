package com.dopediatrie.hosman.secretariat.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.w3c.tidy.Tidy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

public class Utils {
    public static String xhtmlConvert(String html) throws UnsupportedEncodingException {
        Document doc = Jsoup.parse(html, "UTF-8");
        doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        /*Tidy tidy = new Tidy();
        tidy.setInputEncoding("UTF-8");
        tidy.setOutputEncoding("UTF-8");
        tidy.setXHTML(true);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(html.getBytes("UTF-8"));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        tidy.parseDOM(inputStream, outputStream);
        return outputStream.toString("UTF-8");*/
        return doc.outerHtml();
    }
}
