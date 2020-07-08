package currencyservice.service;


import currencyservice.model.IsraelCurrency;
import currencyservice.repo.IsraelCurrencyRepository;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;



@Service
public class ParseIsraelCurrencyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(currencyservice.service.ParseService.class);

    @Value("${israel.currency.xml}")
    private String link;


    @Autowired
    IsraelCurrencyRepository israelCurrencyRepository;

    public void parseIsraBankXml() throws IOException {

        israelCurrencyRepository.deleteAll();
        LOGGER.info("Deleted all old israel currency DB {} ", LocalDate.now());
        String url = link;
        Document page = Jsoup.connect(url).get();

        ArrayList<Element> elements = page.getElementsByTag("CURRENCY");
        List<IsraelCurrency> currencies = elements.stream().map(this::toCurrency).collect(Collectors.toList());

        israelCurrencyRepository.saveAll(currencies);
        LOGGER.info("data with israel currency parsed {} from link {} ", LocalDate.now(), link);

    }

    private IsraelCurrency toCurrency(Element el) {
        IsraelCurrency israelCurrency = IsraelCurrency.builder()
                .name(el.select("NAME").get(0).childNode(0).toString().trim())
                .country(el.select("COUNTRY").get(0).childNode(0).toString().trim())
                .abbr(el.select("CURRENCYCODE").get(0).childNode(0).toString().trim())
                .changeCourse(Double.valueOf(el.select("CHANGE").get(0).childNode(0).toString()))
                .rate(Double.valueOf(el.select("RATE").get(0).childNode(0).toString()))
                .quantity(Double.valueOf(el.select("UNIT").get(0).childNode(0).toString()))
                .build();
        return israelCurrency;
    }

    public List<IsraelCurrency> getAllIsraelCurrency() {
       return israelCurrencyRepository.findAll();
    }
}
