package com.scb.test.bookstore.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Converter
public class PasswordConverter implements AttributeConverter<String, String> {

    private BCryptPasswordEncoder passwordEncoder;

    public PasswordConverter() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String convertToDatabaseColumn(String password) {
        return encode(password);
    }

    @Override
    public String convertToEntityAttribute(String password) {
        return password;
    }

    private String encode(String password){
        return (Objects.nonNull(password) || !password.isEmpty() ? passwordEncoder.encode(password) : null);
    }
}
