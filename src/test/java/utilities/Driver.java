package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


import java.net.MalformedURLException;

import java.net.URL;
import java.time.Duration;

import static utilities.ConfigReader.getProperty;

public class Driver {
    // Her bir thread için ayrı bir WebDriver örneğini yönetmek için ThreadLocal kullanılır.
    private static final ThreadLocal<WebDriver> driverPool = new ThreadLocal<>();

    public Driver() {
    }

    public static WebDriver getDriver()  {
        // Eğer sürücü havuzunda bir sürücü yoksa

        if (driverPool.get() == null) {
            // Ortam değişkenlerinden tarayıcı parametresini al
            String browserParamFromEnv = System.getProperty("browser");

            // Eğer ortam değişkeni yoksa varsayılan değeri al
            String browser = (browserParamFromEnv == null) ? getProperty("browser") : browserParamFromEnv;

            WebDriver driver = null;

            // Chrome için sürücüyü ayarla ve oluştur
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();//DesiredCapabilities oluşturulur
            desiredCapabilities.setCapability("browserName","chrome");//indirilen browser driver'ı burada belirtilir
            desiredCapabilities.setCapability("platformName","Windows 11");//İşletim sistemi belirtilir


            try {
                driver = new RemoteWebDriver(new URL("http://192.168.2.16:4444"), desiredCapabilities);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }

            // Oluşturulan sürücü varsa pencereyi maksimize et ve bekleme süresini ayarla
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
            driverPool.set(driver); // Sürücüyü havuza ekle
        }
        return driverPool.get(); // Oluşturulan veya mevcut sürücüyü döndür
    }

    // Mevcut WebDriver örneğini kapatır ve temizler.
    public static void closeDriver() {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        WebDriver driver = driverPool.get();
        if (driver != null) {
            driver.quit();
            driverPool.remove();
        }
    }
}