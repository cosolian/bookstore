package com.scb.test.bookstore.service;

import com.scb.test.bookstore.models.BookExternal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookExternalService {

    private static final Logger logger = LoggerFactory.getLogger(BookExternalService.class);
    private RestTemplate restTemplate;

    @Autowired
    public BookExternalService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<BookExternal> findAllBooksExternal() {
        ResponseEntity<List<BookExternal>> books = restTemplate
                .exchange("https://scb-test-book-publisher.herokuapp.com/books"
                        , HttpMethod.GET
                        , null
                        , new ParameterizedTypeReference<List<BookExternal>>() {});
        return books.getBody();
    }

    public List<BookExternal> findRecommendBooksExternal() {
        ResponseEntity<List<BookExternal>> books = restTemplate
                .exchange("https://scb-test-book-publisher.herokuapp.com/books/recommendation"
                        , HttpMethod.GET
                        , null
                        , new ParameterizedTypeReference<List<BookExternal>>() {});
        return books.getBody();
    }

    public List<BookExternal> findAllBooks(){
        List<BookExternal> allBooks = findAllBooksExternal();
        List<BookExternal> recommendBooks = findRecommendBooksExternal();

        List<Long> recommendIdBooks = recommendBooks.stream()
                .map(recommendBook -> recommendBook.getId())
                .collect(Collectors.toList());

        logger.info(String.format("Recommend book external result is %s", recommendIdBooks.size()));

        allBooks.stream()
                .filter(book -> recommendIdBooks.contains(book.getId()))
                .forEach(book -> {
                    book.setRecommended(true);
        });

        return allBooks;
    }
}
