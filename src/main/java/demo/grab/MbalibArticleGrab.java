package demo5.grab;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * MBA智库文档下载
 * https://doc.mbalib.com/
 *
 * @author guild
 */
public class MbalibArticleGrab {

    public static void main(String[] args) throws InterruptedException, IOException {
        String url = "https://doc.mbalib.com/view/95624160be8c25ddfd09eec4596628d5.html";
        url = "https://doc.mbalib.com/view/6ffc5c07c07fa52320ee50ecd9ffb726.html";
        url = "https://doc.mbalib.com/view/1257157ef443a3ea84bf1bfc5f64a30e.html";
        url = "https://doc.mbalib.com/view/80f671e064a23ab8f429088d04843a7d.html";

        // 无界面浏览器
        WebDriver driver = createDriver();
        driver.get(url);
        List<WebElement> elements = driver.findElements(By.cssSelector("#viewer .page"));
        System.out.println("文档总页数：" + elements.size());

        File tempFile = File.createTempFile("article", ".txt");
        tempFile.deleteOnExit();
        System.out.println("临时文件：" + tempFile.getAbsolutePath());

        for (int i = 0, size = elements.size(); i < size; ) {
            WebElement element = elements.get(i);
            String text = element.getText();
            if (StrUtil.isEmpty(text)) {
                // 移动至未加载文档位置
                scrollIntoView(driver, element);
                continue;
            }

            FileUtil.appendString(text, tempFile, Charset.defaultCharset());
            // 打印进度
            System.out.println((++i + "/" + size) + "\t" + text.length());
        }
        driver.close();

        openFile(tempFile);
        System.out.println("read complete!");
    }

    static void openFile(File file) throws InterruptedException, IOException {
        String command = "rundll32 url.dll FileProtocolHandler " + file.toURI();
        Runtime.getRuntime().exec(command);
        Thread.sleep(500L);
    }

    static void scrollIntoView(WebDriver driver, WebElement ele) throws InterruptedException {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", ele);
        Thread.sleep(500L);
    }

    static PhantomJSDriver createDriver() {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                "D:/Guild/browser-driver/phantomjs-2.1.1-windows/bin/phantomjs.exe");
        desiredCapabilities.setCapability("acceptSslCerts", true);
        desiredCapabilities.setCapability("takesScreenshot", false);
        desiredCapabilities.setCapability("cssSelectorsEnabled", true);
        desiredCapabilities.setJavascriptEnabled(true);

        PhantomJSDriver driver = new PhantomJSDriver(desiredCapabilities);
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.manage().timeouts().implicitlyWait(3L, TimeUnit.SECONDS);
        return driver;
    }
}
