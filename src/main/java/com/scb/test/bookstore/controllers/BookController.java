package com.scb.test.bookstore.controllers;

import com.scb.test.bookstore.domain.Book;
import com.scb.test.bookstore.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
@Api(value = "books", description = "Operations pertaining to books")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @ApiOperation(value = "Get all books information.")
    @GetMapping()
    public List<Book> getById(){
        return bookService.findAll();
    }
}
