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

    public String reptile() {
        ChromeOptions options = new ChromeOptions();
        List<String> arguments = initOptions();
        options.addArguments(arguments);

        String driverPath = "D:\\Java\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", driverPath);
        System.out.println("webdriver path: " + driverPath);
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);

        StringBuilder content = new StringBuilder();

        String url = "https://anli.court.gov.cn/static/web/index.html#/index";
        try {
            driver.get(url);
            // 点击参考性案例
            WebElement search = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div/div/ul/li[3]"));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div/div/ul/li[3]")));
            clickButton(search);
            WebElement button = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[2]/div/div[2]/div[2]/ul/li[1]/div[1]/div[1]"));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[2]/div[2]/div[2]/div/div[2]/div[2]/ul/li[1]/div[1]/div[1]")));
            clickButton(button);
            WebElement text = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[2]/div[1]/div[2]/div[2]/p"));
            System.out.println("get content!");
            List<WebElement> list = new ArrayList<>();
            list.add(driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[2]/div[1]/div[2]/div[2]/p")));
            list.addAll(text.findElements(By.tagName("p")));
            System.out.println("get list!");

            for (WebElement element : list) {
                content.append(element.getText());
            }
        } catch (InterruptedException e) {
            System.err.println("sleep interrupted!");
        } finally {
            driver.close();
        }

        return content.toString();
    }

    private void clickButton(WebElement button) throws InterruptedException {
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
        res.add("--headless");
        // Chrome需要的操作
        res.add("--disable-gpu");
        // 不显示图片，加快加载速度
        res.add("blink-setting=imagesEnabled=false");

        return res;
    }

    public static void main(String[] args) throws Exception {
        String res = new Reptile().reptile();
        System.out.println(res);
    }
}
