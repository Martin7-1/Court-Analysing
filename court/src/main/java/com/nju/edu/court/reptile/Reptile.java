package com.nju.edu.court.reptile;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Zyi
 */
public class Reptile {

    private static WebDriver driver;
    private static WebDriverWait wait;

    public void reptile() {
        ChromeOptions options = new ChromeOptions();
        List<String> arguments = initOptions();
        options.addArguments(arguments);

        String driverPath = "D:\\Java\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", driverPath);
        System.out.println("webdriver path: " + driverPath);
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, 10);

        String url = "https://wenshu.court.gov.cn/";
        try {
            driver.get(url);
            // 最大化页面
            TimeUnit.SECONDS.sleep(2);
            // 登录
            login();
        } catch (InterruptedException e) {
            System.err.println("sleep interrupted!");
        } finally {
            driver.close();
        }
    }

    private void login() throws InterruptedException {
        // 登录界面
        WebElement login = driver.findElement(By.xpath("/html/body/div[1]/div[2]/ul/li[1]/a"));
        // 防止过快点击
        TimeUnit.SECONDS.sleep(10);
        System.out.println(login.getText());
        // 点击登录界面
        login.click();
        // wait until first element is visible
//        wait.until(ExpectedConditions.elementToBeSelected(By.xpath("/html/body/div/h3/img")));

        // 刷新
        TimeUnit.SECONDS.sleep(2);
        driver.navigate().refresh();
        // 刷新
        TimeUnit.SECONDS.sleep(10);
        driver.navigate().refresh();
        TimeUnit.SECONDS.sleep(20);
        // 登录界面在一个iframe上，需要切换到iframe
        WebElement iframe = driver.findElement(By.tagName("iframe"));
        driver.switchTo().frame(iframe);
        // TODO: 输入账号和密码
        WebElement userName = driver.findElement(By.xpath("/html/body/div[2]/div/form/div/div[1]/div/div/div/input"));
        // 首先清空输入栏
        userName.clear();
        userName.sendKeys("15859579166");
        // 防止过快点击ip被封
        TimeUnit.SECONDS.sleep(10);
        WebElement password = driver.findElement(By.xpath("/html/body/div[2]/div/form/div/div[2]/div/div/div/input"));
        password.clear();
        password.sendKeys("Zzzyi123456");
        // 防止过快点击ip被封
        TimeUnit.SECONDS.sleep(10);
        WebElement button = driver.findElement(By.xpath("/html/body/div[2]/div/form/div/div[3]/span"));
        button.click();
        TimeUnit.SECONDS.sleep(10);
    }

    private List<String> initOptions() {
        List<String> res = new ArrayList<>();
        res.add("lang=zh_CN.UTF-8");
        String userAgent = "--user-agent=Mozilla/5.0 (Linux; Android 6.0; HTC One M9 Build/MRA58K) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.98 Mobile Safari/537.36";
        // 增加代理头
        res.add("--user-agent=" + userAgent);
        // 最大化页面
        res.add("--start-maximized");
        // 不显示页面
        // res.add("--headless");
        // Chrome需要的操作
        res.add("--disable-gpu");
        // 不显示图片，加快加载速度
        res.add("blink-setting=imagesEnabled=false");

        return res;
    }

    public static void main(String[] args) throws Exception {
        new Reptile().reptile();
    }
}
