package com.scb.test.bookstore.config;

import com.scb.test.bookstore.domain.Book;
import com.scb.test.bookstore.models.BookExternal;
import com.scb.test.bookstore.service.BookExternalService;
import com.scb.test.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Component
public class ScheduledTasksConfig {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasksConfig.class);
    private BookService bookService;
    private BookExternalService bookExternalService;

    @Autowired
    public ScheduledTasksConfig(BookService bookService, BookExternalService bookExternalService) {
        this.bookService = bookService;
        this.bookExternalService = bookExternalService;
    }

    @Scheduled(cron = "0 0 0 * * SUN")
    public void updateBooks() {
        logger.info("Start create or update data for book external.");

        List<BookExternal> bookExternals = bookExternalService.findAllBooks();

        logger.info(String.format("Book external result is %s" , bookExternals.size()));

        List<Book> books = bookService.save(bookExternals);

        logger.info(String.format("Save book external to %s books in locally is successful.", books.size()));
    }
}
