package crawlerservice.crawler;

import crawlerservice.model.Price;
import crawlerservice.model.Rezult;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Service
public class CrawlerService {

    @Value("${url.rami.levi}")
    private String ramiLeviUrl;

    public void parseRamiLevi(){
        LocalDate start = LocalDate.now();
        WebDriver driver = getWebDriver();
        driver.get(ramiLeviUrl);
        List<WebElement> x = driver.findElements(By.xpath("//li[@class='rl-group position-relative']//a"));
        for (int l = 0; l <= x.size(); l++) {
            String link = x.get(l).getAttribute("href");
            WebDriver driverLink = getWebDriver();
            driverLink.get(link);
            int size = driverLink.findElements(By.xpath("//body//div[@id='checkout-main']//div//div//div//div[1]//div[2]//div//div//div[@class='position-relative text-left']")).size();
            for (int i = 1; i <= size; i++) {
                WebElement list = driverLink.findElement(By.xpath("//body//div[@id='checkout-main']//div//div//div//div[1]//div[2]//div[" + i + "]//div//div[@class='position-relative text-left']"));
                String name = list.findElement(By.xpath("//body//div[@id='checkout-main']//div//div//div//div[1]//div[2]//div[" + i + "]//div//div[@class='product-title']")).getText();
                String price = list.findElement(By.xpath("//body//div[@id='checkout-main']//div//div//div//div[1]//div[2]//div[" + i + "]//div//div[@class='position-relative text-left']//span//span" +
                        "")).getText();
                String[] summ = price.split(" ");
                String[] sumraz = summ[0].split("\\.");
                Integer rub = Integer.valueOf(sumraz[0]);
                Integer cop = Integer.valueOf(sumraz[1]);
                Rezult rez = new Rezult();
                Price pr = new Price();

                pr.setRub(rub);
                pr.setCop(cop);
                pr.setVal(summ[1]);

                rez.setProductName(name);
                rez.setPrice(pr);

                System.out.println("name " + rez.getProductName() + " " +
                        "rub: " + rez.getPrice().getRub() +
                        " cop: " + rez.getPrice().getCop());
            }
            driverLink.quit();
        }
        driver.close();
        LocalDate finish = LocalDate.now();
        Duration duration = Duration.between(start, finish);
        System.out.println(duration);
    }

    private WebDriver getWebDriver() {
        WebDriver driver = new FirefoxDriver();
        return driver;
    }



}
