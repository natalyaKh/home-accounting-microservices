package currencyservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import currencyservice.model.Currency;
import currencyservice.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/currency")
public class CurrencyController {

    @Autowired
    CurrencyService currencyService;
    @PostMapping()
    public Currency createCurrency(@RequestHeader HttpHeaders header, @RequestBody final Currency currency) throws JsonProcessingException {
        return currencyService.addCurrency(currency);
    }
    @GetMapping("/{userUuid}")
    public List<Currency> getAllCurrencyByUser(@PathVariable String userUuid){

        return currencyService.getAllCurrency(userUuid);
    }

    @DeleteMapping("/{userUuid}/{abbr}")
    public Currency deleteCurrency(@PathVariable String userUuid, @PathVariable String abbr) throws JsonProcessingException {
        return currencyService.deleteCurrency(userUuid, abbr);
    }
}
