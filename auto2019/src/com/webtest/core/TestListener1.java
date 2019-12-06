package com.webtest.core;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.webtest.freemarker.Mail;
import com.webtest.freemarker.WriteFreeMaker;

public class TestListener1 extends TestListenerAdapter {
	private String writeResultToMail()
	{
		ITestNGMethod method[]=this.getAllTestMethods();
		List<ITestResult> failedList = this.getFailedTests();
		List<ITestResult> passedList = this.getPassedTests();
		Map<String, Object> context=new HashMap();
		context.put("length",getAllTestMethods().length);
    	context.put("date", new Date());
    	context.put("faillength", failedList.size());
    	context.put("passlength", passedList.size());
    	for(int j=0;j<failedList.size();j++)
		{
			ITestResult tr=(ITestResult) failedList.get(j);
			for(int i=0;i<method.length;i++)
			{
				if(tr.getMethod().getMethodName().equals(method[i].getMethodName()))
				{
					if(method[i].getDescription()!=null)
					{
						tr.setAttribute("name", method[i].getDescription());
					}
					else
					{
						tr.setAttribute("name", "");
					}
					break;
				}
			}
		}
        context.put("failedList", failedList);
        for(int j=0;j<passedList.size();j++)
		{
			ITestResult tr=(ITestResult) passedList.get(j);
			for(int i=0;i<method.length;i++)
			{
				if(tr.getMethod().getMethodName().equals(method[i].getMethodName()))
				{
					if(method[i].getDescription()!=null)
					{
						tr.setAttribute("name", method[i].getDescription());
					}
					else
					{
						tr.setAttribute("name", "");
					}
					break;
				}
			}
		}
        context.put("passedList", passedList);
        
        try {
			String content=WriteFreeMaker.writeFree(context);
			return content;
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return null;
	}
	@Override
	public void onFinish(ITestContext testContext) {
	
		super.onFinish(testContext);
		System.out.println(getAllTestMethods().length);
//		Map<String, Object> emailContent=this.writeResultToMail();
		String cString=this.writeResultToMail();
		try {
//			String mString = WriteFreeMaker.writeFree(emailContent);
			Mail.sendMail(cString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
