import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by apereira on 30/08/17.
 */
public class Upload {
    WebDriver driver;

    @Before
    public void setUp() throws Exception {

        FirefoxOptions options = new FirefoxOptions()
                .addPreference("dom.file.createInChild", true);

        driver = new FirefoxDriver(options);

        //driver = new ChromeDriver();
    }

    @Test

    public void uploadFileTest() throws Exception {

        String filename = "some-file.txt";
        File file = new File(filename);

        String path = file.getAbsolutePath();
        WebDriverWait wait = new WebDriverWait(driver, 10);

        driver.get("http://localhost:9292/upload");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("file-upload")));
        driver.findElement(By.id("file-upload")).sendKeys(path);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("file-submit")));
        driver.findElement(By.id("file-submit")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("uploaded-files")));
        String text = driver.findElement(By.id("uploaded-files")).getText();
        assertThat(text, is(equalTo(filename)));
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}
