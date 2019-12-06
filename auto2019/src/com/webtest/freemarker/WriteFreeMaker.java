package com.webtest.freemarker;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class WriteFreeMaker {
	public static String writeFree(Map<String, Object> emailContent) throws Exception 
    {
		Configuration cfg = new Configuration();   
        cfg.setDirectoryForTemplateLoading(   
                new File("D:/eclipse/sour/auto2019/freemak/"));   
        cfg.setEncoding(Locale.CHINA, "UTF-8");
        cfg.setObjectWrapper(new DefaultObjectWrapper());              
        cfg.setCacheStorage(new freemarker.cache.MruCacheStorage(20, 250));                      
        Template temp = cfg.getTemplate("sample.ftl");    
        StringWriter out = new StringWriter();   
        temp.process(emailContent, out);   
        return out.toString();
//      Writer out1 = new FileWriter(new File("D:\\eclipse\\sour\\auto2019\\hello.html"));
//      template.process(map, out1);
//      out1.close();

    }
}
