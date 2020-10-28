import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestIosApp {

    By switchCell = By.id("SwitchesCellID");
    By switchbutton = By.id("switchtintID");


    AppiumDriver<MobileElement> driver;
     WebDriverWait  wait;


    @Test
    public  void setupIosCapability() throws MalformedURLException {
        DesiredCapabilities cap = new DesiredCapabilities();
        //for ios
        cap.setCapability("deviceName","iPhone 11 Pro Max");
        cap.setCapability("platformVersion","13.3");
        cap.setCapability("udid","27C73CC1-E5CC-4CAB-9DFB-4667534D64B5");
        cap.setCapability("platformName","iOS");
         File classpathRoot = new File(System.getProperty("user.dir"));
         System.out.println(classpathRoot.getAbsolutePath());
         String urlForApp =  classpathRoot.getAbsolutePath() + "/src/test/resources/UICatalog.app";
        cap.setCapability("app", urlForApp);
        URL url = new URL(" http://0.0.0.0:4723/wd/hub");
        driver = new AppiumDriver<MobileElement>(url, cap);
        wait = new WebDriverWait(driver, 5);
        System.out.println("application started ......");
        scrolltableView();
        switchViewAction();
        stepperViewAction();
        sliderViewAction();
        pickerViewAction();
        putAppInBackGround();
    }




     public void scrolltableView(){
         wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("XCUIElementTypeTable")));

         MobileElement element =  driver.findElement(By.className("XCUIElementTypeTable"));
         String elementID = element.getId();
         HashMap<String, String> scrollObject = new HashMap<String, String>();
         scrollObject.put("element", elementID); // Only for ‘scroll in element’
         scrollObject.put("direction", "down");
         driver.executeScript("mobile:scroll", scrollObject);
     }


    public void switchViewAction() {

        wait.until(ExpectedConditions.visibilityOfElementLocated(switchCell)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(switchbutton)).click();
        driver.navigate().back();
    }


    public void stepperViewAction(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stepperCellID"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Increment")));
        List<MobileElement> buttons = driver.findElements(By.name("Increment"));
        for(int i = 0 ; i < 10 ; i++){
            buttons.get(1).click();
        }
        driver.navigate().back();
    }

    public void sliderViewAction(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sliderCellID"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sliderID")));
        MobileElement sliderItem = (MobileElement) driver.findElement(By.id("sliderID"));
        sliderItem.setValue("1");
        driver.navigate().back();
    }

    public void pickerViewAction(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("PickerCellID"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pickerItemID")));
        List<WebElement> pickerEls = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("pickerItemID")));
        pickerEls.get(0).findElements(MobileBy.className("UIAPickerWheel")).get(0).sendKeys("80");
        pickerEls.get(0).findElements(MobileBy.className("UIAPickerWheel")).get(1).sendKeys("200");
        pickerEls.get(0).findElements(MobileBy.className("UIAPickerWheel")).get(2).sendKeys("100");
    }


    public void putAppInBackGround(){
        driver.runAppInBackground(Duration.ofSeconds(3));
        driver.activateApp("com.apple.MobileSMS");
        List<MobileElement> messages = driver.findElements(By.className("XCUIElementTypeCell"));
        messages.get(0).click();
        driver.activateApp("com.example.apple-samplecode.UICatalog");
    }

    }
