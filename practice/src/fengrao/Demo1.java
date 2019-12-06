package fengrao;

import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.webtest.core.BaseTest;
import com.webtest.dataprovider.ExcelDataProvider;
import com.webtest.utils.ReadProperties;
@Listeners(com.webtest.core.WebTestListener.class)
public class Demo1 extends BaseTest{
	@DataProvider(name="number")
	public static Object[][] words0() throws Exception{
		ExcelDataProvider d = new ExcelDataProvider();
		return d.getTestDataByExcel("d:\\txt\\TestData3.xlsx","Sheet1");
	}
	@DataProvider(name="TestData")
	public static Object[][] words() throws Exception{
		ExcelDataProvider d = new ExcelDataProvider();
		return d.getTestDataByExcel("d:\\txt\\TestData1.xlsx","Sheet1");
	}
	@BeforeClass
	public void login() throws Exception  {	
		webtest.open(ReadProperties.getPropValue("Front_url"));
		Thread.sleep(1000);
		webtest.click("link=登录");
		Thread.sleep(2000);
		webtest.type("name=username","feng");   //参数化!!!
		webtest.type("name=password","123456");		//参数化!!!!!!
		webtest.click("class=login-btn");
		Thread.sleep(5000);
	}
/**
 * 搜索框查找用户（3个）
 **/
	@Test(description="查找存在的用户")
	public void FindPeopleByName1() throws Exception  {	
		
		webtest.click("link=会员展示");
		webtest.type("xpath=//input[@placeholder='昵称']","feng1");	
		webtest.tapClickEnter();//回车键
		Thread.sleep(3000);

		assertTrue(webtest.isTextPresent("积分"));	
	}
	@Test(description="查找空值")
	public void FindPeopleByName2() throws Exception  {	
		webtest.click("link=会员展示");
		webtest.type("xpath=//input[@placeholder='昵称']","");	
		webtest.tapClickEnter();//回车键
		Thread.sleep(3000);

		assertTrue(webtest.isTextPresent("积分"));	
	}
	@Test(description="查找不存在的用户")
	public void FindPeopleByName3() throws Exception  {	
		webtest.click("link=会员展示");
		webtest.type("xpath=//input[@placeholder='昵称']","zhang");	
		webtest.tapClickEnter();//回车键
		Thread.sleep(3000);
		assertTrue(!webtest.isTextPresent("积分"));
	}

/**
 * 通过身份查找用户（3个）
 * @throws Exception
 */
	@Test(description="查找开发者")
	public void FindPeopleByStatus1() throws Exception  {	
		webtest.click("link=会员展示");
		webtest.mouseoverElement("xpath=//*[text()='默认']");
		webtest.click("link=开发者");
		assertTrue(webtest.isTextPresent("feng"));
		assertTrue(webtest.isTextPresent("feng1"));
		assertTrue(webtest.isTextPresent("积分"));
	}
	@Test(description="查找站长")
	public void FindPeopleByStatus2() throws Exception  {	
		webtest.click("link=会员展示");
		webtest.mouseoverElement("xpath=//*[text()='默认']");
		webtest.click("link=站长");
		assertTrue(webtest.isTextPresent("feng2"));
		assertTrue(webtest.isTextPresent("feng3"));
		assertTrue(webtest.isTextPresent("积分"));
	}
	@Test(description="普通用户")
	public void FindPeopleByStatus3() throws Exception  {	
		webtest.click("link=会员展示");
		webtest.click("link=普通用户");
		assertTrue(webtest.isTextPresent("root"));
		assertTrue(webtest.isTextPresent("feng2"));
		assertTrue(webtest.isTextPresent("积分"));
	}
	
/***
 * 关注和取消关注，粉丝数量显示,关注人数显示（8个）
 */	
	@Test(description="进入用户个人主页取消关注",dataProvider="TestData") //3个用户
	public void HandlePeople1(String name) throws Exception  {	
		webtest.click("link=会员展示");
		webtest.type("xpath=//input[@placeholder='昵称']",name);	
		webtest.tapClickEnter();
		webtest.click("link="+name);
		Thread.sleep(2000);
		if(webtest.isTextPresent("已关注")){
			webtest.click("xpath=//button[contains(text(),'已关注')]");
			assertTrue(webtest.isTextPresent("关注"));
		}
		
	}
	@Test(description="关注用户",dataProvider="TestData")
	public void HandlePeople2(String name) throws Exception  {	
		webtest.click("link=会员展示");
		webtest.type("xpath=//input[@placeholder='昵称']",name);	
		webtest.tapClickEnter();
		webtest.click("link="+name);
		Thread.sleep(2000);
		if(!webtest.isTextPresent("已关注")){
			List<WebElement> list2=driver.findElements(By.tagName("button"));
			list2.get(0).click();
			Thread.sleep(5000);
			assertTrue(webtest.isTextPresent("已关注"));
		}		
	}

	@Test(description="指定用户显示的粉丝人数变化")
	public void HandlePeople3() throws Exception  {	
		webtest.click("link=会员展示");
		webtest.type("xpath=//input[@placeholder='昵称']","feng1");	
		webtest.tapClickEnter();
		Thread.sleep(3000);
		String number=driver.findElement(By.className("f2")).getText();//关注人数
		int n1=Integer.parseInt(number);
		System.out.println(number);
		Thread.sleep(3000);
		webtest.click("link=feng1");  //参数化
		
		if(webtest.isTextPresent("已关注")){
			webtest.click("xpath=//button[contains(text(),'已关注')]");
		}else{
			List<WebElement> list2=driver.findElements(By.tagName("button"));
			list2.get(0).click();
			Thread.sleep(5000);
			assertTrue(webtest.isTextPresent("已关注"));
		}
		webtest.click("link=会员展示");
		webtest.type("xpath=//input[@placeholder='昵称']","feng1");	
		webtest.tapClickEnter();
		Thread.sleep(3000);
		String number1=driver.findElement(By.className("f2")).getText();//关注或取消关注后的关注人数
		System.out.println(number);
		int n2=Integer.parseInt(number);
		assertTrue(n1!=n2);
	}
	@Test(description="登录用户的关注人数变化")
	public void HandlePeople4() throws Exception  {	
		webtest.click("link=会员展示");
		webtest.type("xpath=//input[@placeholder='昵称']","feng");	
		webtest.tapClickEnter();
		Thread.sleep(3000);
		List<WebElement> list=driver.findElements(By.className("fo2"));
		String number=list.get(0).getText();
		System.out.println(number);
		int n1=Integer.parseInt(number);
		Thread.sleep(3000);
		webtest.type("xpath=//input[@placeholder='昵称']","feng1");
		webtest.click("link=feng1");  //参数化
		if(webtest.isTextPresent("已关注")){
			webtest.click("xpath=//button[contains(text(),'已关注')]");
		}else{
			List<WebElement> list1=driver.findElements(By.tagName("button"));
			list1.get(0).click();
		}
		
		webtest.click("link=会员展示");
		webtest.type("xpath=//input[@placeholder='昵称']","feng");	
		webtest.tapClickEnter();
		Thread.sleep(3000);
		List<WebElement> list2=driver.findElements(By.className("fo2"));
		String number1=list2.get(0).getText();
		System.out.println(number1);
		int n2=Integer.parseInt(number1);
		assertTrue(n1!=n2);
	}
/**
 * 发起聊天（1个）
 * @throws Exception
 */
	@Test(description="对指定用户发起聊天")
	public void HandlePeople5() throws Exception  {	
		webtest.click("link=会员展示");
		webtest.type("xpath=//input[@placeholder='昵称']","feng1");	
		webtest.tapClickEnter();
		webtest.click("link=feng1");
		List<WebElement> list2=driver.findElements(By.tagName("button"));
		list2.get(1).click();
		Thread.sleep(5000);
		webtest.click("xpath=//button[contains(text(),'聊天')]");
		Thread.sleep(3000);
		assertTrue(webtest.isTextPresent("发送"));
		
	}
/**
 * 按地区找人（2个）
 * @throws InterruptedException 
 */
	@Test(description="按地区找人并关注，有用户存在的地区")
	public void FindPeopleByAddress1() throws InterruptedException{
		webtest.click("link=会员展示");
		webtest.click("link=按地区找人");
		webtest.click("link=河北省(2)");
		webtest.click("link=石家庄市(2)");
		webtest.click("link=裕华区(2)");
		Thread.sleep(2000);
		assertTrue(webtest.isTextPresent("feng1"));
		assertTrue(webtest.isTextPresent("feng3"));
		assertTrue(webtest.isTextPresent("积分"));
	}
	@Test(description="按地区找人并关注，无用户存在的地区")
	public void FindPeopleByAddress2() throws InterruptedException{
		webtest.click("link=会员展示");
		webtest.click("link=按地区找人");
		webtest.click("link=北京市(0)");
		webtest.click("link=北京市(0)");
		webtest.click("link=东城区(0)");
		Thread.sleep(2000);
		assertTrue(!webtest.isTextPresent("积分"));
	}	
	
	
	/**
	 * 根据标题或摘要关键字查找资讯(4个)
	 **/
 	@Test(description="根据不存在的内容查找")
	public void FindBytitle1() throws Exception  {
		webtest.click("link=资讯");
		Thread.sleep(2000);
		webtest.type("xpath=//input[@placeholder='输入标题/摘要关键字']","0");	
		webtest.tapClickEnter();//回车键
		Thread.sleep(3000);
		assertTrue(webtest.isTextPresent("该类型下没有资讯~"));
	}
	@Test(description="根据标题查找")
	public void FindBytitle2() throws Exception  {		
		webtest.click("link=资讯");
		Thread.sleep(2000);
		webtest.type("xpath=//input[@placeholder='输入标题/摘要关键字']","娱乐");	
		webtest.tapClickEnter();//回车键
		Thread.sleep(2000);
		assertTrue(webtest.isTextPresent("娱乐"));
		assertTrue(!webtest.isTextPresent("该类型下没有资讯~"));
	}
	@Test(description="根据摘要关键字查找")
	public void FindByContent() throws Exception  {		
		webtest.click("link=资讯");
		webtest.type("xpath=//input[@placeholder='输入标题/摘要关键字']","这篇资讯主要");	
		webtest.tapClickEnter();//回车键
		Thread.sleep(2000);
		assertTrue(webtest.isTextPresent("这篇资讯主要"));
		assertTrue(!webtest.isTextPresent("该类型下没有资讯~"));
	}
	@Test(description="根据空内容查找")
	public void FindByContent1() throws Exception  {	
		webtest.click("link=资讯");
		webtest.type("xpath=//input[@placeholder='输入标题/摘要关键字']","");	
		webtest.tapClickEnter();//回车键
		Thread.sleep(2000);
		assertTrue(!webtest.isTextPresent("该类型下没有资讯~"));
	}

	/**
	 * 资讯的显示（2）
	 * @throws Exception
	 */	
	@Test(description="国内新闻分类下的资讯列表显示")
	public void test1() throws Exception  {	
		webtest.click("link=资讯");
		webtest.click("link=国内新闻");
		assertTrue(!webtest.isTextPresent("全站热门"));
		assertTrue(webtest.isTextPresent("娱乐"));
	}
	@Test(description="默认分类分类下的资讯列表显示")
	public void test2() throws Exception  {	
		webtest.click("link=资讯");
		webtest.click("link=默认分类");
		assertTrue(!webtest.isTextPresent("全站热门"));
		assertTrue(webtest.isTextPresent("3333"));
	}
	
	/**
	 * 轮播图片操作（3）
	 * @throws Exception
	 */	
	@Test(description="轮播图片的变换,右侧按钮")
	public void test4() throws Exception  {	
		webtest.click("link=资讯");
		Thread.sleep(2000);
		webtest.mouseoverElement("class=main_image");
		webtest.click("xpath=/*[@id='adv_5']/div[1]/div/div[1]/a[1]");
		webtest.click("xpath=//*[@id='btn_next']");
		assertTrue(webtest.isTextPresent("广告2"));
	}
	@Test(description="轮播图片的变换,左侧按钮")
	public void test5() throws Exception  {	
		webtest.click("link=资讯");
		Thread.sleep(2000);
		webtest.mouseoverElement("class=main_image");
		webtest.click("xpath=/*[@id='adv_5']/div[1]/div/div[1]/a[1]");
		webtest.click("xpath=//*[@id='btn_prev']");
		assertTrue(webtest.isTextPresent("广告3"));
	}
	
	@Test(description="轮播图片的变换,右下侧按钮，测每一个按钮",dataProvider="number")
	public void test6(String number,String name) throws Exception  {	
		webtest.click("link=资讯");
		Thread.sleep(2000);
		webtest.mouseoverElement("class=main_image");
		webtest.click("xpath=/*[@id='adv_5']/div[1]/div/div[1]/a["+number+"]");
		assertTrue(webtest.isTextPresent(name));
	}	
	
	/**
	 * 投稿(4个)
	 * 
	 */

	@Test(description="投稿:不填写任何内容")
	public void test7() throws Exception  {	
		webtest.click("link=资讯");
		webtest.click("link=投稿");
		Thread.sleep(2000);
		webtest.alertAccept();
		Thread.sleep(2000);
		Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_SPACE);
        robot.keyRelease(KeyEvent.VK_SPACE);
        Thread.sleep(2000);
		webtest.click("xpath=//button[@class='btn btn-primary ']");
		assertTrue(webtest.isTextPresent("标题不能为空"));
	}
	@Test(description="投稿:不填写任何内容，点击返回")
	public void test8() throws Exception  {	
		webtest.click("link=资讯");
		webtest.click("link=投稿");
		Thread.sleep(2000);
		webtest.alertAccept();
		Thread.sleep(2000);
		Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_SPACE);
        robot.keyRelease(KeyEvent.VK_SPACE);
		webtest.click("xpath=//button[@class='btn btn-default ']");
		assertTrue(webtest.isTextPresent("首页"));
	}
	@Test(description="投稿:填写内容后，点击返回")
	public void test9() throws Exception  {	
		webtest.click("link=资讯");
		webtest.click("link=投稿");
		Thread.sleep(2000);
		webtest.alertAccept();
		Thread.sleep(2000);
		Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_SPACE);
        robot.keyRelease(KeyEvent.VK_SPACE);
        webtest.type("id=title", "娱乐1");
       	webtest.type("xpath=//iframe[starts-with(@id,'ueditor_0')]","该资讯所讲述的是。。。。。。。。。。。");
		webtest.click("xpath=//button[@class='btn btn-primary ']");
		assertTrue(webtest.isTextPresent("是否保存已填写的内容"));
	} 
	@Test(description="我的投稿")
	public void test10() throws Exception  {	
		webtest.click("link=资讯");
		webtest.click("link=我的投稿");
		Thread.sleep(1000);
		assertTrue(webtest.isTextPresent("审核"));
	}
	@AfterClass
	public void closeDriver() throws Exception  {	
		driver.close();
	}
}
