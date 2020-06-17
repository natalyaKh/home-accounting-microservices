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
}


