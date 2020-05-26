package ThreadOrnekleri;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MySeleniumThread extends Thread {

	
    private String e_posta;
    private String password1;
    private String phone_number;
    
    public MySeleniumThread(String e_posta, String password1, String phone_number) {
        
    	this.e_posta=e_posta;
        this.password1=password1;
        this.phone_number=phone_number;
    }
    @Override
    public void run() {
    	 
    	System.setProperty("webdriver.chrome.driver","C:\\chromedriver.exe");

        try {

                ChromeOptions optionsC = new ChromeOptions();
                optionsC.addArguments(Arrays.asList("disable-infobars", "ignore-certificate-errors", "start-maximized","use-fake-ui-for-media-stream"));
                WebDriver driver = new ChromeDriver(optionsC);

                driver.manage().window().maximize();
                driver.get("ewrwr");
                driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES);

                WebElement email = driver.findElement(By.xpath("//input[@name='email']"));
                email.sendKeys(e_posta);
                Thread.sleep(2000);

                WebElement password = driver.findElement(By.xpath("//input[@name='password']"));
                WebDriverWait wait = new WebDriverWait(driver, 2);
                wait.until(ExpectedConditions.elementToBeClickable(password));

                password.sendKeys(password1);
                Thread.sleep(2000);
                driver.findElement(By.id("login-login-button")).click();
                Thread.sleep(2000);

                driver.get("www");
                Thread.sleep(2000);
              
                driver.findElement(By.xpath("//button[contains(.,'Tamam')]")).click();
                
                driver.findElement(By.id("agent-status-available")).click();
                Thread.sleep(2000);

                WebElement strLocator = driver.findElement(By.xpath("//*[@id='agent-softphone-phonenumber']"));
                strLocator.sendKeys(phone_number);

                driver.findElement(By.id("agent-softphone-dial")).click();
                Thread.sleep(2000);
            }
        catch (Exception e) {
            e.printStackTrace();
        }
 }
    
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		
       
		JSONParser parser = new JSONParser();
		
		Object obj = parser.parse(new FileReader("C:data.json"));

        JSONObject jsonObject = (JSONObject) obj;

        JSONArray companyList = (JSONArray) jsonObject.get("users");

            for(int j=0;j<companyList.size();j++) {
            	
            	 JSONObject objx = (JSONObject) companyList.get(j);

                 System.out.println(objx.get("email") + " " + objx.get("password") + " " + objx.get("tel"));
                 
                Thread th1 = new Thread(new MySeleniumThread(objx.get("email").toString(), objx.get("password").toString(), objx.get("tel").toString()));
                th1.start();
            }
            
        
    }

}
