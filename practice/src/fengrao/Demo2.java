package fengrao;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.apache.logging.log4j.message.ThreadInformation;
import org.apache.poi.ss.formula.ThreeDEval;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.CloseWindow;

import com.webtest.core.BaseTest;
import com.webtest.core.WebDriverEngine;
import com.webtest.dataprovider.ExcelDataProvider;
import com.webtest.utils.Log;
import com.webtest.utils.ReadProperties;
@Listeners(com.webtest.core.WebTestListener.class)
public class Demo2 extends BaseTest {
	@BeforeClass
	public void login() throws Exception  {	
		webtest.open(ReadProperties.getPropValue("Front_url"));
		Thread.sleep(1000);
		webtest.click("link=��¼");
		Thread.sleep(2000);
		webtest.type("name=username","feng");
		webtest.type("name=password","123456");
		webtest.click("class=login-btn");
		Thread.sleep(5000);
	}
	@DataProvider(name="con")
	public static Object[][] words() throws Exception{
		ExcelDataProvider d = new ExcelDataProvider();
		return d.getTestDataByExcel("d:\\txt\\TestData4.xlsx","Sheet1");
	}
	@DataProvider(name="con1")
	public static Object[][] words1() throws Exception{
		ExcelDataProvider d = new ExcelDataProvider();
		return d.getTestDataByExcel("d:\\txt\\TestData5.xlsx","Sheet1");
	}
	
	@BeforeMethod
	public void Inti() throws Exception  {	
		webtest.click("link=��Ѷ");
		webtest.type("xpath=//input[@placeholder='�������/ժҪ�ؼ���']","����");	
		webtest.tapClickEnter();
		Thread.sleep(3000);
	}
	
	/**
	 * ����Ѷ�Ĳ�������������ҳ��2����������(4)������(���벻ͬ�����ݣ�5��)������ ����Ĳ�����5����
	 */
	
	@Test(description="ͨ�������Ѷ�ı����������ҳ")
	public void test1() throws Exception  {	
		webtest.click("link=����");
		webtest.getWindow(1);
		Thread.sleep(3000);
		assertTrue(webtest.isTextPresent("����"));
		assertTrue(webtest.isTextPresent("����"));
	}
	@Test(description="ͨ�������Ѷ��ͼƬ��������ҳ")
	public void test2() throws Exception  {	
		webtest.click("xpath=//*[@id='contents']/div/div[1]/a/img");
		webtest.getWindow(1);
		Thread.sleep(3000);
		assertTrue(webtest.isTextPresent("����"));
		assertTrue(webtest.isTextPresent("����"));
	}
	@Test(description="������Ѷ��ϸ�������벻ͬ�����ݽ��з���",dataProvider="con")
	public void test3(String content,String content1) throws Exception  {	
		webtest.click("link=����");
		Thread.sleep(3000);
		webtest.getWindow(1);
		webtest.click("link=����");
		webtest.type("id=share_content",content);
		webtest.click("xpath=//input[@value='���� Ctrl+Enter']");
		Thread.sleep(2000);
		assertTrue(webtest.isTextPresent(content1));
	}
	@Test(description="������Ѷ��ϸ�������������з���")
	public void test4() throws Exception  {	
		webtest.click("link=����");
		Thread.sleep(3000);
		webtest.getWindow(1);
		webtest.click("link=����");
		webtest.click("xpath=//*[@id='frm-post-popup']/div/div[2]/div/a");
		webtest.click("xpath=//a[@title='baiyan']");
		webtest.click("xpath=//input[@value='���� Ctrl+Enter']");
		Thread.sleep(2000);
		assertTrue(webtest.isTextPresent("����ɹ�"));
	}
	@Test(description="���������������ӵ���վ",dataProvider="con1")
	public void test5(String param1,String param2) throws Exception  {	
		webtest.click("link=����");
		Thread.sleep(3000);
		webtest.getWindow(1);
		webtest.click("xpath=//a[@title='"+param1+"']");
		driver.close();
		webtest.getWindow(1);
		Thread.sleep(2000);
		assertTrue(webtest.isTextPresent(param2));	
		
	}
	@Test(description="�������΢��")
	public void test51() throws Exception  {	
		webtest.click("link=����");
		Thread.sleep(3000);
		webtest.getWindow(1);
		webtest.click("xpath=//a[@title='����΢��']");
		Thread.sleep(2000);
		assertTrue(webtest.isTextPresent("����΢������Ȧ"));		
	}
	@Test(description="���������������ַ")
	public void test52() throws Exception  {	
		webtest.click("link=����");
		Thread.sleep(3000);
		webtest.getWindow(1);
		webtest.click("xpath=//a[@title='����������ַ']");
		Thread.sleep(1000);
		String content=webtest.getAlertTest();
		assertTrue(content.contains("��ʹ�õ���"));
		webtest.alertAccept();
	}
	public void test6() throws Exception  {	
		webtest.click("link=����");
		Thread.sleep(3000);
		webtest.getWindow(1);
		webtest.type("id=local_comment_textarea","����Ѷ������");	
		webtest.click("link=��������");
		assertTrue(webtest.isTextPresent("�ո�"));
	}
	@Test(description="���ۿ�����")
	public void test7() throws Exception  {	
		webtest.click("link=����");
		Thread.sleep(3000);
		webtest.getWindow(1);
		webtest.type("id=local_comment_textarea","");	
		webtest.click("link=��������");
		assertTrue(webtest.isTextPresent("�������ݲ���Ϊ��"));
	}
	
	@Test(description="�ظ�����")
	public void test8() throws Exception  {	
		webtest.click("link=����");
		Thread.sleep(3000);
		webtest.getWindow(1);
		webtest.click("link=�ظ�");
		webtest.type("id=local_comment_textarea","1");	
		webtest.click("link=��������");
		assertTrue(webtest.isTextPresent("�ո�"));
	}
	
	@Test(description="ɾ������")
	public void test9() throws Exception  {	
		webtest.click("link=����");
		Thread.sleep(3000);
		webtest.getWindow(1);
		if(webtest.isTextPresent("ɾ��")){
			webtest.click("link=ɾ��");
			assertTrue(webtest.isTextPresent("ɾ�����۳ɹ�"));
		}else{
			assertTrue(true);
		}
	}
	@AfterMethod
	public void loginOut() throws Exception  {	
		driver.close();
		webtest.getWindow(0);
		webtest.open(ReadProperties.getPropValue("Front_url"));
	}
	@AfterClass
	public void closeDriver() throws Exception  {	
		driver.close();
	}
}
