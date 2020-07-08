package currencyservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import currencyservice.model.DefaultCurrency;

import currencyservice.repo.BaseCurrencyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(currencyservice.service.ParseService.class);
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    BaseCurrencyRepository baseCurrencyRepository;

    @Value("${currency.file}")
    private String fileName;

    public void parseCurrencyFromJson() throws FileNotFoundException, JSONException {
        baseCurrencyRepository.deleteAll();
        LOGGER.info("Deleted all old currency DB {} ", LocalDate.now());
        InputStream is = new FileInputStream(fileName);
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(is))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = new JSONArray(stringBuilder.toString());
        List<DefaultCurrency> defaultCurrencies = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            DefaultCurrency c = new DefaultCurrency().builder()
                    .country((String) jsonArray.getJSONObject(i).get("COUNTRY"))
                    .name((String) jsonArray.getJSONObject(i).get("NAME"))
                    .build();
            defaultCurrencies.add(c);

        }
        baseCurrencyRepository.saveAll(defaultCurrencies);
        LOGGER.info("Json with currency parsed {} from file {} ", LocalDate.now(), fileName);
    }

    public List<DefaultCurrency> getAllCurrency() {
        List<DefaultCurrency> currencies = baseCurrencyRepository.findAll();
        if (currencies == null) {
            return new ArrayList<>(0);
        }
        LOGGER.info("getting List of default currency");
        return currencies;
    }

//    public void parseIsraBankXml() throws IOException {
//        String url = "https://www.boi.org.il/currency.xml";
//        Document page = Jsoup.connect(url).get();
//
//        ArrayList<Element> elements = page.getElementsByTag("CURRENCY");
//        List<IsraelCurrency> currencies = elements.stream().map(this::toCurrency).collect(Collectors.toList());
//
//        israelCurrencyRepository.saveAll(currencies);

//        page.getElementsByTag("CURRENCY").get(0).childNodes.get(1).childNode(0)
//        Node y = elements.get(0).childNode(1);
//        System.err.println(y);
//        String z = elements.get(0).select("NAME").get(0).childNode(0).toString();
//        System.err.println(z + " rez");
//        String xu = "0";
//        Elements x = page.select("Name");
//        System.out.println(x.eachText());
//        Elements y = page.select("Value");
//        System.out.println(y.eachText());

//        page.getElementsByTag("CURRENCY")
//    }
//
//    private IsraelCurrency toCurrency(Element el) {
//        String name = el.select("NAME").get(0).childNode(0).toString();
//        String co = el.select("COUNTRY").get(0).childNode(0).toString();
//        String a = el.select("CURRENCYCODE").get(0).childNode(0).toString();
//        Double ctoString = Double.valueOf(el.select("CHANGE").get(0).childNode(0).toString());
//        Double r = Double.valueOf(el.select("RATE").get(0).childNode(0).toString());
//        Double q = Double.valueOf(el.select("UNIT").get(0).childNode(0).toString());
//
//
//
//       IsraelCurrency israelCurrency = IsraelCurrency.builder()
//               .name(name)
//               .country(co)
//               .abbr(a)
//               .changeCourse(ctoString)
//               .rate(r)
//               .quantity(q)
//               .build();
//        return israelCurrency;
//    }
}


