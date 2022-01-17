package com.nju.edu.court.reptile;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * duplicate 目前没有任何作用
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
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);

        String url = "https://wenshu.court.gov.cn/";
        try {
            driver.get(url);
            // 登录
            login();
            // 点击刑事案件
            // WebElement criminalCase = driver.findElement(By.xpath("/html/body/div[1]/div[3]/div[2]/diy:lawyee/div/ul/li[2]/a"));
            // clickButton(criminalCase);

            // 搜索栏
            WebElement search = driver.findElement(By.xpath("/html/body/div[1]/div[3]/div[3]/diy:lawyee/div/div[1]/div[2]/input"));
            search.clear();
            search.sendKeys("抢劫");
            WebElement searchButton = driver.findElement(By.xpath("/html/body/div[1]/div[3]/div[3]/diy:lawyee/div/div[1]/div[2]/input"));
            clickButton(searchButton);
        } catch (InterruptedException e) {
            System.err.println("sleep interrupted!");
        } finally {
            driver.close();
        }
    }

    private void clickButton(WebElement button) throws InterruptedException {
        button.click();
    }

    private void login() throws InterruptedException {
        // 登录界面
        WebElement login = driver.findElement(By.xpath("/html/body/div[1]/div[2]/ul/li[1]/a"));
        System.out.println(login.getText());
        // 点击登录界面
        login.click();

        // 刷新
        driver.navigate().refresh();
        driver.navigate().refresh();
        // 登录界面在一个iframe上，需要切换到iframe
        WebElement iframe = driver.findElement(By.tagName("iframe"));
        driver.switchTo().frame(iframe);
        WebElement userName = driver.findElement(By.xpath("/html/body/div[2]/div/form/div/div[1]/div/div/div/input"));
        // 首先清空输入栏
        userName.clear();
        userName.sendKeys("15859579166");
        WebElement password = driver.findElement(By.xpath("/html/body/div[2]/div/form/div/div[2]/div/div/div/input"));
        password.clear();
        password.sendKeys("Zzzyi123456");
        WebElement button = driver.findElement(By.xpath("/html/body/div[2]/div/form/div/div[3]/span"));
        button.click();
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
