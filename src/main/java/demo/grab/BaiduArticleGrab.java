package demo.grab;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;

public class BaiduArticleGrab {

    public static void main(String[] args) throws InterruptedException {
        String url = "https://wenku.baidu.com/view/c7893a9181c758f5f61f67d2";

        WebDriver driver = createEdgeDriver();
        driver.get(url);
        Thread.sleep(15000L);
        driver.close();
    }

    static EdgeDriver createEdgeDriver() {
        System.setProperty(EdgeDriverService.EDGE_DRIVER_EXE_PROPERTY, "D:/Guild/browser-driver/msedgedriver.exe");
        return new EdgeDriver();
    }
}
