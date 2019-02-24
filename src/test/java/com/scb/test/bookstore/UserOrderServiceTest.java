package com.scb.test.bookstore;

import com.scb.test.bookstore.domain.Book;
import com.scb.test.bookstore.domain.Order;
import com.scb.test.bookstore.domain.User;
import com.scb.test.bookstore.repositories.BookRepository;
import com.scb.test.bookstore.repositories.OrderRepository;
import com.scb.test.bookstore.service.UserOrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class UserOrderServiceTest {

    private UserOrderService userOrderService;

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Before
    public void before() {
        userOrderService = new UserOrderService(orderRepository, bookRepository);
    }

    @Test
    public void testGetUserAndOrdersWhenOrderIsNotEmpty() {
        User user = createUser();

        assertThat(user.getUsername(), is("john.doe"));
        assertThat(user.getPassword(), is("test"));
        assertThat(user.getName(), is("john"));
        assertThat(user.getSurname(), is("doe"));
        assertThat(user.getDateOfBirth(), is(LocalDate.of(1989, 07, 23)));

        List<Order> userOrder = userOrderService.save(user, Arrays.asList(
                createBook(1L).getId(),
                createBook(2L).getId()
        ));

        assertThat(userOrder, hasSize(2));
    }

    private User createUser() {
        User user = new User();
        user.setUsername("john.doe");
        user.setPassword("test");
        user.setName("john");
        user.setSurname("doe");
        user.setDateOfBirth(LocalDate.of(1989, 07, 23));
        testEntityManager.persist(user);
        return user;
    }

    private Book createBook(Long bookId) {
        Book book = new Book();
        book.setId(bookId);
        book.setName("name " + bookId);
        book.setAuthor("author" + bookId);
        book.setPrice(new BigDecimal(100));
        book.setRecommend(true);
        testEntityManager.persist(book);
        return book;
    }
}
