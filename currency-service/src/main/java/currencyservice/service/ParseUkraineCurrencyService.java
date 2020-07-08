package currencyservice.service;

import currencyservice.model.UkraineCurrency;
import currencyservice.repo.UkraineCurrencyRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class ParseUkraineCurrencyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParseUkraineCurrencyService.class);

    @Value("${ukraine.currency.xml}")
    private String link;


    @Autowired
    UkraineCurrencyRepository ukraineCurrencyRepository;

    public void parseUkrBankXml() throws IOException {

        ukraineCurrencyRepository.deleteAll();
        LOGGER.info("Deleted all old ukrainian currency DB {} ", LocalDate.now());
        String url = link;
        Document page = Jsoup.connect(url).get();

        ArrayList<Element> elements = page.getElementsByTag("currency");
        List<UkraineCurrency> currencies = elements.stream().map(this::toCurrency).collect(Collectors.toList());

        ukraineCurrencyRepository.saveAll(currencies);
        LOGGER.info("data with ukrainian currency parsed {} from link {} ", LocalDate.now(), link);

    }

    private UkraineCurrency toCurrency(Element el) {
        UkraineCurrency ukraineCurrency = UkraineCurrency.builder()
                .name(el.select("txt").get(0).childNode(0).toString().trim())
                .abbr(el.select("cc").get(0).childNode(0).toString().trim())
                .rate(Double.valueOf(el.select("rate").get(0).childNode(0).toString()))
                .build();
        return ukraineCurrency;
    }


    public List<UkraineCurrency> getAllIsraelCurrency() {
        return ukraineCurrencyRepository.findAll();
    }
}
