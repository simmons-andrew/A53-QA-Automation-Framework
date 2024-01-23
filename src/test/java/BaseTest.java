import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;

import static io.github.bonigarcia.wdm.WebDriverManager.edgedriver;


public class BaseTest {


    //Data Providers
    @DataProvider(name = "InvalidLoginData")
    public Object[][] getDataFromDataProviders() {
        return new Object[][]{
                {"invalid@mail.com", "invalidPassword"},
                {"demo@class.com", ""},
                {"", "te$t$tudent"},
                {"", ""}
        };
    }

    public WebDriver driver = null;

    private static final ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();

    public WebDriverWait wait = null;

    public Wait<WebDriver> fluentWait = null;
    public Actions actions = null;

    public String url = "https://qa.koel.app";

    @BeforeSuite
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @Parameters({"BaseUrl"})
    @BeforeMethod
    public void launchBrowser(String BaseUrl) throws MalformedURLException {

        //driver = pickBrowser(System.getProperty("browser"));
        threadDriver.set(pickBrowser(System.getProperty("browser")));

        //ChromeOptions options = new ChromeOptions();
        //options.addArguments("--remote-allow-origins=*");

        //Manage Browser - wait for 10 seconds before failing/quitting.
        //driver = new ChromeDriver(options);

        //Implement for Firefox Browser
        //driver = new FirefoxDriver();

        //implicit wait
        //getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        //Explicit Wait
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        //Fluent Wait
        fluentWait = new FluentWait<WebDriver>(getDriver())
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);

        actions = new Actions(driver);
        driver.manage().window().maximize();

        //url = BaseUrl;

        //Navigate to URL
        navigateToUrl(BaseUrl);

    }

    public static WebDriver getDriver(){
        return threadDriver.get();
    }

    public void tearDown(){
        threadDriver.get().close();
        threadDriver.remove();
    }

    /*@AfterMethod
    public void closeBrowser() {
        driver.quit();
    }
*/
    void provideEmail(String email) {

        WebElement emailField = wait.until((ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='email']"))));
        //WebElement emailField = driver.findElement(By.cssSelector("input[type='email']"));
        emailField.clear();
        emailField.sendKeys(email);
    }

    void providePassword(String password) {

        //WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='password']")));
        WebElement passwordField = driver.findElement(By.cssSelector("input[type='password']"));
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    void clickSubmit() {

        WebElement submitButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type='submit']")));
        //WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();
    }

    //Helper Method
    public void navigateToUrl(String givenUrl) {
        driver.get(givenUrl);
    }

    public void loginToKoelApp(){
        navigateToUrl(url);
        provideEmail("andrew.simmons@testpro.io");
        providePassword("Andrew.Simmons24");
        clickSubmit();
    }

    /**
     * Homework 24 - Create Browser Factory conditions for Selenium Grid Standalone
     *
     */

    public WebDriver pickBrowser(String browser) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        String gridURL = "http://192.168.1.184:4444";
        switch(browser){
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                return driver = new FirefoxDriver();

            case "MicrosoftEdge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--remote-allow-origins=*");
                return driver = new EdgeDriver();

            case "grid-edge": // gradle clean test -Dbrowser=grid-edge
                caps.setCapability("browserName", "MicrosoftEdge");
                return driver = new RemoteWebDriver(URI.create(gridURL).toURL(), caps);

            case "grid-firefox": // gradle clean test -Dbrowser=grid-firefox
                caps.setCapability("browserName", "firefox");
                return driver = new RemoteWebDriver(URI.create(gridURL).toURL(), caps);

            case "grid-chrome":
                caps.setCapability("browserName", "chrome");
                return driver = new RemoteWebDriver(URI.create(gridURL).toURL(),caps);

            case "cloud":
                return lambdaTest();

            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*");
                //Manage Browser - wait for 10 seconds before failing/quitting.
                return driver = new ChromeDriver(options);
        }


    }
    public WebDriver lambdaTest() throws MalformedURLException{

        String hubURL = "https://hub.lambdatest.com/wd/hub";

        DesiredCapabilities capabilities = new DesiredCapabilities();


        capabilities.setCapability("browserName", "chrome");
        capabilities.setCapability("browserVersion", "120.0");

        /*ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setPlatformName("Windows 10");
        browserOptions.setBrowserVersion("120.0");*/
        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("username", "andrew.simmons");
        ltOptions.put("accessKey", "TwtHi0j8VeDj0mJoHVofi8r23B3yRy1OhzNWA5SfcC2gCQssgD");
        ltOptions.put("resolution", "1920x1080");
        ltOptions.put("project", "Untitled");
        ltOptions.put("selenium_version", "4.5.0");
        ltOptions.put("driver_version", "120.0");
        ltOptions.put("w3c", true);
        ltOptions.put("plugin", "java-testNG");
        capabilities.setCapability("LT:Options", ltOptions);

        return new RemoteWebDriver(new URL(hubURL), capabilities);

    }
}





