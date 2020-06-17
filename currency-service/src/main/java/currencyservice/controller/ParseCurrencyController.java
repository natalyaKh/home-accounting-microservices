package currencyservice.controller;

import currencyservice.model.DefaultCurrency;
import currencyservice.service.ParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/parse")
public class ParseCurrencyController {

    @Autowired
    ParseService parseService;
    @GetMapping()
    public void parseCurrencyFromJson() throws FileNotFoundException, JSONException {
        parseService.parseCurrencyFromJson();
    }

    @GetMapping("/{all}")
    public List<DefaultCurrency> getAllDefaultCurrency() {
        return parseService.getAllCurrency();
    }

}
