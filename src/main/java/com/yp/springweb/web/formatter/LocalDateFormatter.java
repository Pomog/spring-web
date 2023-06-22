package com.yp.springweb.web.formatter;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class LocalDateFormatter implements Formatter<LocalDate> {

    private final Locale englishLocale = Locale.CANADA;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy", englishLocale);

    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        return LocalDate.parse(text, dateTimeFormatter);
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return dateTimeFormatter.format(object);
    }
}
