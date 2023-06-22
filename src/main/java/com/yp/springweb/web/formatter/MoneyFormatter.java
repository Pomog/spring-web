package com.yp.springweb.web.formatter;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.Locale;

@Component
public class MoneyFormatter implements Formatter<BigDecimal> {
    private static final String CURRENCY_CODE = "USD";
    private static final Locale LOCALE = Locale.ENGLISH;
    private final NumberFormat currencyInstance = NumberFormat.getCurrencyInstance(LOCALE);

    public MoneyFormatter() {
       currencyInstance.setCurrency(Currency.getInstance(CURRENCY_CODE));
    }

    @Override
    public BigDecimal parse(String text, Locale locale) throws ParseException {
        return (BigDecimal) currencyInstance.parse(text);
    }

    @Override
    public String print(BigDecimal object, Locale locale) {
        return currencyInstance.format(object);
    }
}
