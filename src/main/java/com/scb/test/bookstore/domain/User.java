package com.scb.test.bookstore.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.scb.test.bookstore.utils.PasswordConverter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(description="All details about the user")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class User {

    @Id
    @ApiModelProperty(notes = "The username", required = true)
    @NotEmpty
    private String username;

    @ApiModelProperty(notes = "The password", required = true)
    @NotEmpty
    @Convert(converter = PasswordConverter.class)
    private String password;

    @ApiModelProperty(notes = "The name")
    private String name;

    @ApiModelProperty(notes = "The surname")
    private String surname;

    @ApiModelProperty(notes = "The birth date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
