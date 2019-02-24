package com.scb.test.bookstore.initializer;

import com.scb.test.bookstore.domain.Book;
import com.scb.test.bookstore.models.BookExternal;
import com.scb.test.bookstore.service.BookExternalService;
import com.scb.test.bookstore.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookExternalDataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(BookExternalDataInitializer.class);
    private BookExternalService bookExternalService;
    private BookService bookService;

    @Autowired
    public BookExternalDataInitializer(BookExternalService bookExternalService, BookService bookService) {
        this.bookExternalService = bookExternalService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) {
        logger.info("Start data initializer for book external.");

        List<BookExternal> bookExternals = bookExternalService.findAllBooks();

        logger.info(String.format("Book external result is %s" , bookExternals.size()));

        List<Book> books = bookService.save(bookExternals);

        logger.info(String.format("Save book external to %s books in locally is successful.", books.size()));
    }
}
