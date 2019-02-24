package com.scb.test.bookstore.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "books")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(description="All details about the book")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Book {

    @Id
    @ApiModelProperty(notes = "The user Id (database generated)")
    private long id;

    private String name;

    private String author;

    private BigDecimal price;

    private Boolean isRecommend;

    public Book() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getRecommend() {
        return isRecommend;
    }

    public void setRecommend(Boolean recommend) {
        isRecommend = recommend;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", isRecommend=" + isRecommend +
                '}';
    }
}
