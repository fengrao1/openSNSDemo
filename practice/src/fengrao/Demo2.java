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
		webtest.click("link=登录");
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
		webtest.click("link=资讯");
		webtest.type("xpath=//input[@placeholder='输入标题/摘要关键字']","娱乐");	
		webtest.tapClickEnter();
		Thread.sleep(3000);
	}
	
	/**
	 * 对资讯的操作：进入详情页（2个）、评论(4)、分享(输入不同的内容，5个)、分享到 后面的操作（5个）
	 */
	
	@Test(description="通过点击资讯的标题进入详情页")
	public void test1() throws Exception  {	
		webtest.click("link=娱乐");
		webtest.getWindow(1);
		Thread.sleep(3000);
		assertTrue(webtest.isTextPresent("评论"));
		assertTrue(webtest.isTextPresent("娱乐"));
	}
	@Test(description="通过点击资讯的图片进入详情页")
	public void test2() throws Exception  {	
		webtest.click("xpath=//*[@id='contents']/div/div[1]/a/img");
		webtest.getWindow(1);
		Thread.sleep(3000);
		assertTrue(webtest.isTextPresent("评论"));
		assertTrue(webtest.isTextPresent("娱乐"));
	}
	@Test(description="进入资讯详细界面输入不同的内容进行分享",dataProvider="con")
	public void test3(String content,String content1) throws Exception  {	
		webtest.click("link=娱乐");
		Thread.sleep(3000);
		webtest.getWindow(1);
		webtest.click("link=分享");
		webtest.type("id=share_content",content);
		webtest.click("xpath=//input[@value='发表 Ctrl+Enter']");
		Thread.sleep(2000);
		assertTrue(webtest.isTextPresent(content1));
	}
	@Test(description="进入资讯详细界面输入表情进行分享")
	public void test4() throws Exception  {	
		webtest.click("link=娱乐");
		Thread.sleep(3000);
		webtest.getWindow(1);
		webtest.click("link=分享");
		webtest.click("xpath=//*[@id='frm-post-popup']/div/div[2]/div/a");
		webtest.click("xpath=//a[@title='baiyan']");
		webtest.click("xpath=//input[@value='发表 Ctrl+Enter']");
		Thread.sleep(2000);
		assertTrue(webtest.isTextPresent("分享成功"));
	}
	@Test(description="点击分享到后面的链接的网站",dataProvider="con1")
	public void test5(String param1,String param2) throws Exception  {	
		webtest.click("link=娱乐");
		Thread.sleep(3000);
		webtest.getWindow(1);
		webtest.click("xpath=//a[@title='"+param1+"']");
		driver.close();
		webtest.getWindow(1);
		Thread.sleep(2000);
		assertTrue(webtest.isTextPresent(param2));	
		
	}
	@Test(description="点击分享到微信")
	public void test51() throws Exception  {	
		webtest.click("link=娱乐");
		Thread.sleep(3000);
		webtest.getWindow(1);
		webtest.click("xpath=//a[@title='分享到微信']");
		Thread.sleep(2000);
		assertTrue(webtest.isTextPresent("分享到微信朋友圈"));		
	}
	@Test(description="点击分享到到复制网址")
	public void test52() throws Exception  {	
		webtest.click("link=娱乐");
		Thread.sleep(3000);
		webtest.getWindow(1);
		webtest.click("xpath=//a[@title='分享到复制网址']");
		Thread.sleep(1000);
		String content=webtest.getAlertTest();
		assertTrue(content.contains("您使用的是"));
		webtest.alertAccept();
	}
	public void test6() throws Exception  {	
		webtest.click("link=娱乐");
		Thread.sleep(3000);
		webtest.getWindow(1);
		webtest.type("id=local_comment_textarea","此资讯。。。");	
		webtest.click("link=发表评论");
		assertTrue(webtest.isTextPresent("刚刚"));
	}
	@Test(description="评论空内容")
	public void test7() throws Exception  {	
		webtest.click("link=娱乐");
		Thread.sleep(3000);
		webtest.getWindow(1);
		webtest.type("id=local_comment_textarea","");	
		webtest.click("link=发表评论");
		assertTrue(webtest.isTextPresent("评论内容不能为空"));
	}
	
	@Test(description="回复评论")
	public void test8() throws Exception  {	
		webtest.click("link=娱乐");
		Thread.sleep(3000);
		webtest.getWindow(1);
		webtest.click("link=回复");
		webtest.type("id=local_comment_textarea","1");	
		webtest.click("link=发表评论");
		assertTrue(webtest.isTextPresent("刚刚"));
	}
	
	@Test(description="删除评论")
	public void test9() throws Exception  {	
		webtest.click("link=娱乐");
		Thread.sleep(3000);
		webtest.getWindow(1);
		if(webtest.isTextPresent("删除")){
			webtest.click("link=删除");
			assertTrue(webtest.isTextPresent("删除评论成功"));
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
