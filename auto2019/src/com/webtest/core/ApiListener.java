package com.webtest.core;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.functors.FactoryTransformer;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.webtest.freemarker.WriteFreeMaker;
import com.webtest.utils.ReadProperties;

public class ApiListener extends TestListenerAdapter{
	WriteFreeMaker ft=new WriteFreeMaker();
	private String writeResultToMail()
	{
		ITestNGMethod method[]=this.getAllTestMethods();
		List failedList=this.getFailedTests();
		List passedList=this.getPassedTests();
		List failedList1=new ArrayList();
		List passedList1=new ArrayList();		
		for(int j=0;j<failedList.size();j++)
		{
			
			ITestResult tr=(ITestResult) failedList.get(j);
			if(tr.getMethod().getDescription()!=null)
			{
				tr.setAttribute("name", tr.getMethod().getDescription());
			}
			else
			{
				tr.setAttribute("name", "");
			}
		
			failedList1.add(tr);
		}
		for(int j=0;j<passedList.size();j++)
		{
			ITestResult tr=(ITestResult) passedList.get(j);
			if(tr.getMethod().getDescription()!=null)
			{
				tr.setAttribute("name", tr.getMethod().getDescription());
			}
			else
			{
				tr.setAttribute("name", "");
			}
		
			passedList1.add(tr);
		}
		Map context=new HashMap();
    	context.put("date", new Date());
        context.put("faillength",failedList.size());   
        context.put("passlength",passedList1.size()); 
        context.put("length",passedList.size()+failedList.size()); 
        context.put("failedList", failedList);
        context.put("passedList", passedList);
        try {
			
        	String content=ft.writeFree(context);  
//        	mapToString(context);
			return content;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static String  mapToString(Map<String, Object> para) {
		
		StringBuilder  sBuilder =new StringBuilder();
		int size=para.size();
		for(Entry<String, Object> entry:para.entrySet()) {
			sBuilder.append(entry.getKey()+"="+entry.getValue()+"\n");
			
		}
		
	return  sBuilder.toString();
				
	}
	@Override
	public void onFinish(ITestContext testContext) {
	
		super.onFinish(testContext);
		System.out.println(getAllTestMethods().length);
		String emailContent=this.writeResultToMail();
		System.out.println(emailContent);
		
		String emailTitle=ReadProperties.getPropValue("mail_title")+"----"+this.getTime();
		String toMail=ReadProperties.getPropValue("to_mail");
		try {
			MailUtil.sendEmail(toMail,emailTitle, emailContent);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	   public String getTime()
	    {
	    	java.util.Calendar c=java.util.Calendar.getInstance();    
	        java.text.SimpleDateFormat f=new java.text.SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");    
	       	return  f.format(c.getTime());    
	    }
//	  @Override
//	  public void onTestSuccess(ITestResult tr) {
//		  Log.info(tr.getInstance()+"-"+tr.getName()+"运行成功");
//	  }
//	  @Override
//	  public void onTestFailure(ITestResult tr) {
//		  
//		  Log.error(tr.getInstance()+"-"+tr.getName()+"运行失败");
//	  }

}
