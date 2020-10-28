import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

public class TestAndroidApp {

    AppiumDriver<MobileElement> driver;
    WebDriverWait  wait;

    @Test
    public  void setupAndroidCapability() throws MalformedURLException {
        DesiredCapabilities cap = new DesiredCapabilities();

       // for android
        cap.setCapability("deviceName","Pixel 3a API 28");
        cap.setCapability("platformVersion","9");
        cap.setCapability("udid","emulator-5554");
        cap.setCapability("platformName","Android");
        // cap.setCapability("app", "/Users/ragaiealfy/Downloads/Examples NativeScript_v7.0.1_apkpure.com.apk");
        File classpathRoot = new File(System.getProperty("user.dir"));
        System.out.println(classpathRoot.getAbsolutePath());
        String urlForApp =  classpathRoot.getAbsolutePath() + "/src/test/resources/ExamplesNativeScript.apk";
        cap.setCapability("app", urlForApp);
        URL url = new URL(" http://0.0.0.0:4723/wd/hub");
        driver = new AppiumDriver<MobileElement>(url, cap);
        wait = new WebDriverWait(driver, 30);
        System.out.println("application started ......");

       // driver.sendSMS("555-555-5555", "Your code is 123456");
        clickStart();
        scrollAndSelectReservation();
        makeReserve();
        selectOlditem();
        showSidemenu();
    }




    public void clickStart(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("android.widget.Button"))).click();

        driver.runAppInBackground(Duration.ofSeconds(5));

        driver.activateApp("com.google.android.apps.messaging");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("android.widget.LinearLayout")));
        List<MobileElement> listData = driver.findElements(By.className("android.widget.LinearLayout"));
        listData.get(0).click();
        driver.launchApp();
        //driver.activateApp("org.nativescript.examples");
    }


    public void scrollAndSelectReservation(){
        //android.view.ViewGroup//android.widget.ScrollView
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("android.widget.Button"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("android.widget.ScrollView")));
        scrollToAnElementByText(driver,"Reservations");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.view.ViewGroup[@content-desc=\"Reservations\"]"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("android.widget.ImageView")));
        List<MobileElement> listData = driver.findElements(By.className("android.widget.ImageView"));
        listData.get((int) (listData.stream().count() - 1)).click();
    }



public void makeReserve(){
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("android.widget.EditText")));
    List<MobileElement> listData = driver.findElements(By.className("android.widget.EditText"));
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("h:mm a");
    LocalDateTime now = LocalDateTime.now();
    listData.get(0).sendKeys("test " + dtf.format(now));
    listData.get(1).sendKeys("3423432");
    listData.get(3).clear();
    listData.get(3).sendKeys(dtf.format(now));
    dtf = DateTimeFormatter.ofPattern("EEE,dd. MM");
    listData.get(2).clear();
    listData.get(2).sendKeys(dtf.format(now));
    driver.findElement(By.xpath("//android.widget.TextView[@text=\"+\"]")).click();
    driver.findElement(By.xpath("//android.widget.TextView[@text=\"1st floor\"]")).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.CheckedTextView[@text=\"3rd floor\"]"))).click();
    driver.findElement(By.xpath("//android.widget.TextView[@text=\"1\"]")).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.CheckedTextView[@text=\"4\"]"))).click();
    driver.findElement(By.xpath("//android.widget.TextView[@text=\"in-person\"]")).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.CheckedTextView[@text=\"online\"]"))).click();
    driver.findElement(By.xpath("//android.widget.TextView[@text=\"DONE\"]")).click();
    //driver.navigate().back();
}


    public void selectOlditem(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text=\"Rachel Nabors\"]"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text=\"DONE\"]"))).click();
      driver.navigate().back();
    }



public void showSidemenu(){
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.ImageButton"))).click();

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text=\"Code samples\"]"))).click();
    driver.activateApp("org.nativescript.examples");
    driver.launchApp();
}


    public WebElement scrollToAnElementByText(AppiumDriver<MobileElement> driver, String text) {
        return driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector())" +
                ".scrollIntoView(new UiSelector().text(\"" + text + "\"));"));
    }
}
