package Selenium;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class RedBusCalendarAutomation {
static ChromeDriver driver;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/chromedriver.exe");
		ChromeOptions option = new ChromeOptions();
		option.addArguments("--disable-notifications");
		driver = new ChromeDriver(option);
		driver.get("https://www.redbus.in/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		driver.findElement(By.cssSelector("div.labelCalendarContainer")).click();
		WebElement CalendarUI = driver.findElement(By.cssSelector("div[class*='DatePicker__CalendarContainer']"));
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].setAttribute('style','background: yellow; border: 2px solid yellow;');", CalendarUI);  
		
		List<String> weekendDates = getWeekEndDates("Jun 2025");
		
		System.out.println(weekendDates);
		
		driver.close();
		
	}

	
	public static List<String> getWeekEndDates(String ExpectedMonth) {
		
		String month;
		String reqMonth="";
		do
		{   month= driver.findElement(By.cssSelector("div[class*='ayNavigator__IconBlock']:nth-child(2)")).getText();
			
			reqMonth=reqMonth+"\n"+month;
			driver.findElement(By.cssSelector("div[class*='ayNavigator__IconBlock']:nth-child(3)")).click();
		}
		while(!month.contains(ExpectedMonth));
		driver.findElement(By.cssSelector("div[class*='ayNavigator__IconBlock']:nth-child(1)")).click();
		
		ArrayList<String> weekendDate1 = new ArrayList<String>();
		
		try
		{
			System.out.println(reqMonth);
			
			List<WebElement> sat = driver.findElements(By.cssSelector("div[class*='DayTiles__CalendarDaysBlock']:nth-child(6)"));
			List<WebElement> sun = driver.findElements(By.cssSelector("div[class*='DayTiles__CalendarDaysBlock']:nth-child(7)"));
			
			
			for(WebElement satweek : sat)
			{
				if(!satweek.getText().isEmpty())
				{
					weekendDate1.add(satweek.getText());
				}
				
			}
			for(WebElement sunweek : sun)
			{
				if(!sunweek.getText().isEmpty())
				{
					weekendDate1.add(sunweek.getText());
				}
			}
			
		}
		catch(NoSuchElementException e)
		{
			e.printStackTrace();
		}
		return weekendDate1;
	}

}
