package com.scb.test.bookstore.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "orders")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(description="All details about the order")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The user Id (database generated)")
    private long id;

    @ManyToOne
    @JoinColumn(name="username")
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name="book_id")
    @NotNull
    private Book book;

    public Order() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", book=" + book +
                '}';
    }
}
