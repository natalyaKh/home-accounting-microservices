package crawlerservice.controller;

import crawlerservice.crawler.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/crawler")
public class CrawlerController {

    @Autowired
    CrawlerService crawlerService;

    @GetMapping()
    public void parseShop(){
        crawlerService.parseRamiLevi();
    }
}
