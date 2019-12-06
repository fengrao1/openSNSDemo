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
		webtest.click("link=��¼");
		Thread.sleep(2000);
		webtest.type("name=username","feng");   //������!!!
		webtest.type("name=password","123456");		//������!!!!!!
		webtest.click("class=login-btn");
		Thread.sleep(5000);
	}
/**
 * ����������û���3����
 **/
	@Test(description="���Ҵ��ڵ��û�")
	public void FindPeopleByName1() throws Exception  {	
		
		webtest.click("link=��Աչʾ");
		webtest.type("xpath=//input[@placeholder='�ǳ�']","feng1");	
		webtest.tapClickEnter();//�س���
		Thread.sleep(3000);

		assertTrue(webtest.isTextPresent("����"));	
	}
	@Test(description="���ҿ�ֵ")
	public void FindPeopleByName2() throws Exception  {	
		webtest.click("link=��Աչʾ");
		webtest.type("xpath=//input[@placeholder='�ǳ�']","");	
		webtest.tapClickEnter();//�س���
		Thread.sleep(3000);

		assertTrue(webtest.isTextPresent("����"));	
	}
	@Test(description="���Ҳ����ڵ��û�")
	public void FindPeopleByName3() throws Exception  {	
		webtest.click("link=��Աչʾ");
		webtest.type("xpath=//input[@placeholder='�ǳ�']","zhang");	
		webtest.tapClickEnter();//�س���
		Thread.sleep(3000);
		assertTrue(!webtest.isTextPresent("����"));
	}

/**
 * ͨ����ݲ����û���3����
 * @throws Exception
 */
	@Test(description="���ҿ�����")
	public void FindPeopleByStatus1() throws Exception  {	
		webtest.click("link=��Աչʾ");
		webtest.mouseoverElement("xpath=//*[text()='Ĭ��']");
		webtest.click("link=������");
		assertTrue(webtest.isTextPresent("feng"));
		assertTrue(webtest.isTextPresent("feng1"));
		assertTrue(webtest.isTextPresent("����"));
	}
	@Test(description="����վ��")
	public void FindPeopleByStatus2() throws Exception  {	
		webtest.click("link=��Աչʾ");
		webtest.mouseoverElement("xpath=//*[text()='Ĭ��']");
		webtest.click("link=վ��");
		assertTrue(webtest.isTextPresent("feng2"));
		assertTrue(webtest.isTextPresent("feng3"));
		assertTrue(webtest.isTextPresent("����"));
	}
	@Test(description="��ͨ�û�")
	public void FindPeopleByStatus3() throws Exception  {	
		webtest.click("link=��Աչʾ");
		webtest.click("link=��ͨ�û�");
		assertTrue(webtest.isTextPresent("root"));
		assertTrue(webtest.isTextPresent("feng2"));
		assertTrue(webtest.isTextPresent("����"));
	}
	
/***
 * ��ע��ȡ����ע����˿������ʾ,��ע������ʾ��8����
 */	
	@Test(description="�����û�������ҳȡ����ע",dataProvider="TestData") //3���û�
	public void HandlePeople1(String name) throws Exception  {	
		webtest.click("link=��Աչʾ");
		webtest.type("xpath=//input[@placeholder='�ǳ�']",name);	
		webtest.tapClickEnter();
		webtest.click("link="+name);
		Thread.sleep(2000);
		if(webtest.isTextPresent("�ѹ�ע")){
			webtest.click("xpath=//button[contains(text(),'�ѹ�ע')]");
			assertTrue(webtest.isTextPresent("��ע"));
		}
		
	}
	@Test(description="��ע�û�",dataProvider="TestData")
	public void HandlePeople2(String name) throws Exception  {	
		webtest.click("link=��Աչʾ");
		webtest.type("xpath=//input[@placeholder='�ǳ�']",name);	
		webtest.tapClickEnter();
		webtest.click("link="+name);
		Thread.sleep(2000);
		if(!webtest.isTextPresent("�ѹ�ע")){
			List<WebElement> list2=driver.findElements(By.tagName("button"));
			list2.get(0).click();
			Thread.sleep(5000);
			assertTrue(webtest.isTextPresent("�ѹ�ע"));
		}		
	}

	@Test(description="ָ���û���ʾ�ķ�˿�����仯")
	public void HandlePeople3() throws Exception  {	
		webtest.click("link=��Աչʾ");
		webtest.type("xpath=//input[@placeholder='�ǳ�']","feng1");	
		webtest.tapClickEnter();
		Thread.sleep(3000);
		String number=driver.findElement(By.className("f2")).getText();//��ע����
		int n1=Integer.parseInt(number);
		System.out.println(number);
		Thread.sleep(3000);
		webtest.click("link=feng1");  //������
		
		if(webtest.isTextPresent("�ѹ�ע")){
			webtest.click("xpath=//button[contains(text(),'�ѹ�ע')]");
		}else{
			List<WebElement> list2=driver.findElements(By.tagName("button"));
			list2.get(0).click();
			Thread.sleep(5000);
			assertTrue(webtest.isTextPresent("�ѹ�ע"));
		}
		webtest.click("link=��Աչʾ");
		webtest.type("xpath=//input[@placeholder='�ǳ�']","feng1");	
		webtest.tapClickEnter();
		Thread.sleep(3000);
		String number1=driver.findElement(By.className("f2")).getText();//��ע��ȡ����ע��Ĺ�ע����
		System.out.println(number);
		int n2=Integer.parseInt(number);
		assertTrue(n1!=n2);
	}
	@Test(description="��¼�û��Ĺ�ע�����仯")
	public void HandlePeople4() throws Exception  {	
		webtest.click("link=��Աչʾ");
		webtest.type("xpath=//input[@placeholder='�ǳ�']","feng");	
		webtest.tapClickEnter();
		Thread.sleep(3000);
		List<WebElement> list=driver.findElements(By.className("fo2"));
		String number=list.get(0).getText();
		System.out.println(number);
		int n1=Integer.parseInt(number);
		Thread.sleep(3000);
		webtest.type("xpath=//input[@placeholder='�ǳ�']","feng1");
		webtest.click("link=feng1");  //������
		if(webtest.isTextPresent("�ѹ�ע")){
			webtest.click("xpath=//button[contains(text(),'�ѹ�ע')]");
		}else{
			List<WebElement> list1=driver.findElements(By.tagName("button"));
			list1.get(0).click();
		}
		
		webtest.click("link=��Աչʾ");
		webtest.type("xpath=//input[@placeholder='�ǳ�']","feng");	
		webtest.tapClickEnter();
		Thread.sleep(3000);
		List<WebElement> list2=driver.findElements(By.className("fo2"));
		String number1=list2.get(0).getText();
		System.out.println(number1);
		int n2=Integer.parseInt(number1);
		assertTrue(n1!=n2);
	}
/**
 * �������죨1����
 * @throws Exception
 */
	@Test(description="��ָ���û���������")
	public void HandlePeople5() throws Exception  {	
		webtest.click("link=��Աչʾ");
		webtest.type("xpath=//input[@placeholder='�ǳ�']","feng1");	
		webtest.tapClickEnter();
		webtest.click("link=feng1");
		List<WebElement> list2=driver.findElements(By.tagName("button"));
		list2.get(1).click();
		Thread.sleep(5000);
		webtest.click("xpath=//button[contains(text(),'����')]");
		Thread.sleep(3000);
		assertTrue(webtest.isTextPresent("����"));
		
	}
/**
 * ���������ˣ�2����
 * @throws InterruptedException 
 */
	@Test(description="���������˲���ע�����û����ڵĵ���")
	public void FindPeopleByAddress1() throws InterruptedException{
		webtest.click("link=��Աչʾ");
		webtest.click("link=����������");
		webtest.click("link=�ӱ�ʡ(2)");
		webtest.click("link=ʯ��ׯ��(2)");
		webtest.click("link=ԣ����(2)");
		Thread.sleep(2000);
		assertTrue(webtest.isTextPresent("feng1"));
		assertTrue(webtest.isTextPresent("feng3"));
		assertTrue(webtest.isTextPresent("����"));
	}
	@Test(description="���������˲���ע�����û����ڵĵ���")
	public void FindPeopleByAddress2() throws InterruptedException{
		webtest.click("link=��Աչʾ");
		webtest.click("link=����������");
		webtest.click("link=������(0)");
		webtest.click("link=������(0)");
		webtest.click("link=������(0)");
		Thread.sleep(2000);
		assertTrue(!webtest.isTextPresent("����"));
	}	
	
	
	/**
	 * ���ݱ����ժҪ�ؼ��ֲ�����Ѷ(4��)
	 **/
 	@Test(description="���ݲ����ڵ����ݲ���")
	public void FindBytitle1() throws Exception  {
		webtest.click("link=��Ѷ");
		Thread.sleep(2000);
		webtest.type("xpath=//input[@placeholder='�������/ժҪ�ؼ���']","0");	
		webtest.tapClickEnter();//�س���
		Thread.sleep(3000);
		assertTrue(webtest.isTextPresent("��������û����Ѷ~"));
	}
	@Test(description="���ݱ������")
	public void FindBytitle2() throws Exception  {		
		webtest.click("link=��Ѷ");
		Thread.sleep(2000);
		webtest.type("xpath=//input[@placeholder='�������/ժҪ�ؼ���']","����");	
		webtest.tapClickEnter();//�س���
		Thread.sleep(2000);
		assertTrue(webtest.isTextPresent("����"));
		assertTrue(!webtest.isTextPresent("��������û����Ѷ~"));
	}
	@Test(description="����ժҪ�ؼ��ֲ���")
	public void FindByContent() throws Exception  {		
		webtest.click("link=��Ѷ");
		webtest.type("xpath=//input[@placeholder='�������/ժҪ�ؼ���']","��ƪ��Ѷ��Ҫ");	
		webtest.tapClickEnter();//�س���
		Thread.sleep(2000);
		assertTrue(webtest.isTextPresent("��ƪ��Ѷ��Ҫ"));
		assertTrue(!webtest.isTextPresent("��������û����Ѷ~"));
	}
	@Test(description="���ݿ����ݲ���")
	public void FindByContent1() throws Exception  {	
		webtest.click("link=��Ѷ");
		webtest.type("xpath=//input[@placeholder='�������/ժҪ�ؼ���']","");	
		webtest.tapClickEnter();//�س���
		Thread.sleep(2000);
		assertTrue(!webtest.isTextPresent("��������û����Ѷ~"));
	}

	/**
	 * ��Ѷ����ʾ��2��
	 * @throws Exception
	 */	
	@Test(description="�������ŷ����µ���Ѷ�б���ʾ")
	public void test1() throws Exception  {	
		webtest.click("link=��Ѷ");
		webtest.click("link=��������");
		assertTrue(!webtest.isTextPresent("ȫվ����"));
		assertTrue(webtest.isTextPresent("����"));
	}
	@Test(description="Ĭ�Ϸ�������µ���Ѷ�б���ʾ")
	public void test2() throws Exception  {	
		webtest.click("link=��Ѷ");
		webtest.click("link=Ĭ�Ϸ���");
		assertTrue(!webtest.isTextPresent("ȫվ����"));
		assertTrue(webtest.isTextPresent("3333"));
	}
	
	/**
	 * �ֲ�ͼƬ������3��
	 * @throws Exception
	 */	
	@Test(description="�ֲ�ͼƬ�ı任,�Ҳఴť")
	public void test4() throws Exception  {	
		webtest.click("link=��Ѷ");
		Thread.sleep(2000);
		webtest.mouseoverElement("class=main_image");
		webtest.click("xpath=/*[@id='adv_5']/div[1]/div/div[1]/a[1]");
		webtest.click("xpath=//*[@id='btn_next']");
		assertTrue(webtest.isTextPresent("���2"));
	}
	@Test(description="�ֲ�ͼƬ�ı任,��ఴť")
	public void test5() throws Exception  {	
		webtest.click("link=��Ѷ");
		Thread.sleep(2000);
		webtest.mouseoverElement("class=main_image");
		webtest.click("xpath=/*[@id='adv_5']/div[1]/div/div[1]/a[1]");
		webtest.click("xpath=//*[@id='btn_prev']");
		assertTrue(webtest.isTextPresent("���3"));
	}
	
	@Test(description="�ֲ�ͼƬ�ı任,���²ఴť����ÿһ����ť",dataProvider="number")
	public void test6(String number,String name) throws Exception  {	
		webtest.click("link=��Ѷ");
		Thread.sleep(2000);
		webtest.mouseoverElement("class=main_image");
		webtest.click("xpath=/*[@id='adv_5']/div[1]/div/div[1]/a["+number+"]");
		assertTrue(webtest.isTextPresent(name));
	}	
	
	/**
	 * Ͷ��(4��)
	 * 
	 */

	@Test(description="Ͷ��:����д�κ�����")
	public void test7() throws Exception  {	
		webtest.click("link=��Ѷ");
		webtest.click("link=Ͷ��");
		Thread.sleep(2000);
		webtest.alertAccept();
		Thread.sleep(2000);
		Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_SPACE);
        robot.keyRelease(KeyEvent.VK_SPACE);
        Thread.sleep(2000);
		webtest.click("xpath=//button[@class='btn btn-primary ']");
		assertTrue(webtest.isTextPresent("���ⲻ��Ϊ��"));
	}
	@Test(description="Ͷ��:����д�κ����ݣ��������")
	public void test8() throws Exception  {	
		webtest.click("link=��Ѷ");
		webtest.click("link=Ͷ��");
		Thread.sleep(2000);
		webtest.alertAccept();
		Thread.sleep(2000);
		Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_SPACE);
        robot.keyRelease(KeyEvent.VK_SPACE);
		webtest.click("xpath=//button[@class='btn btn-default ']");
		assertTrue(webtest.isTextPresent("��ҳ"));
	}
	@Test(description="Ͷ��:��д���ݺ󣬵������")
	public void test9() throws Exception  {	
		webtest.click("link=��Ѷ");
		webtest.click("link=Ͷ��");
		Thread.sleep(2000);
		webtest.alertAccept();
		Thread.sleep(2000);
		Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_SPACE);
        robot.keyRelease(KeyEvent.VK_SPACE);
        webtest.type("id=title", "����1");
       	webtest.type("xpath=//iframe[starts-with(@id,'ueditor_0')]","����Ѷ���������ǡ���������������������");
		webtest.click("xpath=//button[@class='btn btn-primary ']");
		assertTrue(webtest.isTextPresent("�Ƿ񱣴�����д������"));
	} 
	@Test(description="�ҵ�Ͷ��")
	public void test10() throws Exception  {	
		webtest.click("link=��Ѷ");
		webtest.click("link=�ҵ�Ͷ��");
		Thread.sleep(1000);
		assertTrue(webtest.isTextPresent("���"));
	}
	@AfterClass
	public void closeDriver() throws Exception  {	
		driver.close();
	}
}
