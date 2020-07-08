package currencyservice.controller;

import currencyservice.model.DefaultCurrency;
import currencyservice.model.IsraelCurrency;
import currencyservice.model.UkraineCurrency;
import currencyservice.service.ParseIsraelCurrencyService;
import currencyservice.service.ParseService;
import currencyservice.service.ParseUkraineCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/parse")
public class ParseCurrencyController {

    @Autowired
    ParseService parseService;

    @Autowired
    ParseIsraelCurrencyService parseIsraelCurrencyService;

    @Autowired
    ParseUkraineCurrencyService parseUkraineCurrencyService;

    @GetMapping()
    public void parseCurrencyFromJson() throws FileNotFoundException, JSONException {
        parseService.parseCurrencyFromJson();
    }


    @GetMapping("/{all}")
    public List<DefaultCurrency> getAllDefaultCurrency() {
        return parseService.getAllCurrency();
    }



    @GetMapping("/israel")
    public void parseIsraBank() throws IOException {
        parseIsraelCurrencyService.parseIsraBankXml();
    }

    @GetMapping("israel/all")
    public List<IsraelCurrency> getAllIsraelCurrency() {

        return parseIsraelCurrencyService.getAllIsraelCurrency();
    }

    @GetMapping("/ukraine")
    public void parseUkrBank() throws IOException {
        parseUkraineCurrencyService.parseUkrBankXml();
    }

    @GetMapping("ukraine/all")
    public List<UkraineCurrency> getAllUkraineCurrency() {

        return parseUkraineCurrencyService.getAllIsraelCurrency();
    }

}
