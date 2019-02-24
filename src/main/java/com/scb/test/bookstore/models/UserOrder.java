package com.scb.test.bookstore.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserOrder {

    private String name;
    private String surname;
    private List<Long> books;
    private BigDecimal price;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;

    public UserOrder(String name, String surname, LocalDate dateOfBirth, List<Long> books) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.books = books;
    }

    public UserOrder(BigDecimal price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public List<Long> getBooks() {
        return books;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
