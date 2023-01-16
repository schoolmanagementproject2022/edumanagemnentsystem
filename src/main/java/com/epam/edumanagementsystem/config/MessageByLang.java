package com.epam.edumanagementsystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageByLang {

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        if (MessageByLang.messageSource == null)
            MessageByLang.messageSource = messageSource;
    }

    private static MessageSource messageSource;

    public static String getMessage(String key) {
        return messageSource.getMessage(
                key,
                null,
                LocaleContextHolder.getLocale());
    }
}
