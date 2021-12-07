package com.nju.edu.court.reptile;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Zyi
 */
public class Reptile {

    private static RemoteWebDriver driver;

    public static void main(String[] args) {
        ChromeOptions options = new ChromeOptions();
        List<String> arguments = initOptions();
        options.addArguments(arguments);

        String driverPath = "D:\\Java\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", driverPath);
        System.out.println("webdriver path: " + driverPath);
        driver = new ChromeDriver(options);

        String url = "https://wenshu.court.gov.cn/";
        try {
            driver.get(url);
            // 最大化页面
            TimeUnit.SECONDS.sleep(2);
            driver.manage().window().maximize();
            // 登录
            login();
        } catch (InterruptedException e) {
            System.err.println("sleep interrupted!");
        } finally {
            driver.close();
        }
    }

    private static void login() throws InterruptedException {
        // 登录界面
        WebElement login = driver.findElement(By.xpath("/html/body/div[1]/div[2]/ul/li[1]/a"));
        // 防止过快点击
        TimeUnit.SECONDS.sleep(10);
        System.out.println(login.getText());
        // 点击登录界面
        login.click();
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
    }

    private static List<String> initOptions() {
        List<String> res = new ArrayList<>();
        res.add("lang=zh_CN.UTF-8");
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.0.3 Safari/605.1.15";
        // 增加代理头
        res.add("--user-agent=" + userAgent);
        // 不显示页面
        // res.add("--headless");
        // Chrome需要的操作
        res.add("--disable-gpu");
        // 不显示图片，加快加载速度
        res.add("blink-setting=imagesEnabled=false");

        return res;
    }
}
