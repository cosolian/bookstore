package com.scb.test.bookstore.service;

import com.scb.test.bookstore.domain.Book;
import com.scb.test.bookstore.domain.Order;
import com.scb.test.bookstore.domain.User;
import com.scb.test.bookstore.exceptions.BadRequestException;
import com.scb.test.bookstore.repositories.BookRepository;
import com.scb.test.bookstore.repositories.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserOrderService {

    private static final Logger logger = LoggerFactory.getLogger(UserOrderService.class);
    private OrderRepository orderRepository;
    private BookRepository bookRepository;

    @Autowired
    public UserOrderService(OrderRepository orderRepository, BookRepository bookRepository) {
        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
    }

    public List<Long> findBookIdByUser(User user) {
        return orderRepository.findByUser(user).stream()
                .filter(order -> Objects.nonNull(order.getBook()))
                .map(order -> order.getBook().getId())
                .collect(Collectors.toList());
    }

    @Transactional
    public List<Order> save(User user, List<Long> bookIds){

        if(Objects.isNull(user))
            throw new BadRequestException("Create failed for user order - This User are empty.");

        List<Book> books = bookRepository.findAllById(bookIds);

        if(books.isEmpty())
            throw new BadRequestException("Create failed for user order - This Books are empty.");

        List<Order> orders = new ArrayList<>();
        books.forEach(book -> {
            Order order = new Order();
            order.setUser(user);
            order.setBook(book);
            orders.add(order);
            logger.info(String.format("Order is %d for order book. - %s", orders.size(), order.toString()));
        });
        return orderRepository.saveAll(orders);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(User user) {
        List<Order> orders = orderRepository.findByUser(user);
        orderRepository.deleteAll(orders);
    }

    public BigDecimal getTotalPrice(List<Order> orders){
        return orders
                .stream()
                .filter(order -> Objects.nonNull(order.getBook()))
                .map(order -> order.getBook().getPrice())
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }
}
