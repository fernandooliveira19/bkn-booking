package com.fernando.oliveira.booking.utils;

import com.fernando.oliveira.booking.domain.enums.ExceptionMessageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageUtils {

    @Autowired
    MessageSource messageSource;

    MessageUtils(MessageSource messageSource){
        this.messageSource = messageSource;
    }
    public String getMessage(ExceptionMessageEnum message){
        return messageSource.getMessage(message.toString(), null, LocaleContextHolder.getLocale());
    }
    public String getMessage(String property, Object[] args){
        return messageSource.getMessage(property, args, LocaleContextHolder.getLocale());
    }

}
