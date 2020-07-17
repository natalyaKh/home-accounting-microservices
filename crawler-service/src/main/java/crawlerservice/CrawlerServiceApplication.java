package crawlerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CrawlerServiceApplication {

	public static void main(String[] args) {
		System.setProperty("webdriver.gecko.driver",
				"driver/geckodriver");

		SpringApplication.run(CrawlerServiceApplication.class, args);
	}


}
