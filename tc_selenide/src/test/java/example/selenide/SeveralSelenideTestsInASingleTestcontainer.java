package example.selenide;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testcontainers.containers.BrowserWebDriverContainer;

import java.io.File;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.Selenide.switchTo;

public class SeveralSelenideTestsInASingleTestcontainer {
/*
    @ClassRule
    public static BrowserWebDriverContainer container =
            new BrowserWebDriverContainer()
                    .withRecordingMode(BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL, new File("build"))
                    .withDesiredCapabilities(DesiredCapabilities.chrome());
*/

    @BeforeClass
    public static void setupClass() {
//        WebDriverRunner.setWebDriver(container.getWebDriver());
//        String chromeDriverPath = System.getenv("webdriver.chrome.driver");
//        System.setProperty("webdriver.chrome.driver", chromeDriverPath != null ? chromeDriverPath : "c:/tools/chromedriver-2.30/chromedriver.exe");
    }

    @AfterClass
    public static void cleanUp() {
        WebDriverRunner.closeWebDriver();
    }

    @Test
    public void fail() {
        open("https://google.com/");
        $(By.name("qq")).val("Selenide").pressEnter();
        sleep(1000);
    }

    @Test
    public void search() {
        open("https://google.com/");
        $(By.name("q")).val("Selenide").pressEnter();
        ElementsCollection results = $$("#ires .g");
        results.shouldHave(CollectionCondition.sizeGreaterThan(5));
        $("#ires .g").shouldHave(text("selenide.org"));

        WebDriver driver = WebDriverRunner.getWebDriver();
        String chromeTab = driver.getWindowHandle();

        for (int i = 0; i < 5; i++) {
            SelenideElement link = results.get(i).find("a");
            System.out.println(link.attr("href"));
            link.click();
//            closeOtherTabs(driver, chromeTab);
            switchTo().window(chromeTab);
        }
        sleep(1000);
    }

    private void closeOtherTabs(WebDriver driver, String originalHandle) {
        for(String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalHandle)) {
                driver.switchTo().window(handle);
                driver.close();
            }
        }
    }

    @Test
    public void searchAgain() {
        search();
    }
}
