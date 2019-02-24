package com.scb.test.bookstore.service;

import com.scb.test.bookstore.domain.Book;
import com.scb.test.bookstore.exceptions.BadRequestException;
import com.scb.test.bookstore.models.BookExternal;
import com.scb.test.bookstore.repositories.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Book> save(List<BookExternal> bookExternals){

        if(bookExternals.isEmpty())
            throw new BadRequestException("Create failed for books - This book externals are empty.");

        List<Book> books = new ArrayList<>();
        bookExternals.forEach(bookExternal -> {
            Book book = new Book();
            book.setId(bookExternal.getId());
            book.setName(bookExternal.getBookName());
            book.setAuthor(bookExternal.getAuthorName());
            book.setPrice(new BigDecimal(bookExternal.getPrice()));
            book.setRecommend(bookExternal.isRecommended());
            books.add(book);
            logger.info(String.format("Order is %d for order book. - %s", books.size(), book.toString()));
        });

       return  bookRepository.saveAll(books);
    }

    public List<Book> findAll(){
        return bookRepository.findAll();
    }
}
