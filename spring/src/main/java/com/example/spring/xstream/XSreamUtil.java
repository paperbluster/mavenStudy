package com.example.spring.xstream;

import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author wanjun
 * @create 2022-09-12 22:54
 */
public class XSreamUtil {
    public Object xml2bean(String fileName,Class ...classtypes) throws MalformedURLException {
        URL reader=new URL(this.getClass().getClassLoader().getResource("")+"xml_db"+ File.separator+fileName);
        XStream xstream = new XStream();
        for(Class clas:classtypes){
            xstream.processAnnotations(clas);
        }
        xstream.autodetectAnnotations(true);
        return xstream.fromXML(reader);
    }

    public void bean2xml(String fileName,Object obj) throws IOException {
        XStream xStream=new XStream();
        xStream.processAnnotations(obj.getClass());
        String xmlData=xStream.toXML(obj);
        URL reader=new URL(this.getClass().getClassLoader().getResource("")+"xml_db"+ File.separator+fileName);
        // true-追加写 false-覆盖写,默认false
        FileWriter fw=new FileWriter(reader.getPath(),false);
        fw.write(xmlData);
        fw.flush();
        fw.close();
        FileWriter fw2=new FileWriter("spring"+File.separator+"resources"+File.separator+"xml_db"+File.separator+fileName);
        fw2.write(xmlData);
        fw2.flush();
        fw2.close();
    }
}
