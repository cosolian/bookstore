package com.scb.test.bookstore;

import com.scb.test.bookstore.domain.Book;
import com.scb.test.bookstore.models.BookExternal;
import com.scb.test.bookstore.service.BookExternalService;
import com.scb.test.bookstore.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookExternalServiceTest {
    private BookExternalService bookExternalService;

    @Autowired
    private BookService bookService;

    @Before
    public void before() {
        RestTemplate restTemplate = mock(RestTemplate.class);
        bookExternalService = spy(new BookExternalService(restTemplate));
    }

    @Test
    public void testFindBooksWhenAllBooksAndRecommendBooksAreEmpty() {
        doReturn(new ArrayList<>()).when(bookExternalService).findAllBooksExternal();
        doReturn(new ArrayList<>()).when(bookExternalService).findRecommendBooksExternal();
        List<BookExternal> books = bookExternalService.findAllBooks();
        assertThat(books, hasSize(0));
    }

    @Test
    public void testFindBooksWhenRecommendBooksAreEmpty() {
        doReturn(Arrays.asList(
                createBook(1L, 100d),
                createBook(2L, 200d)
        )).when(bookExternalService).findAllBooks();
        doReturn(new ArrayList<>()).when(bookExternalService).findRecommendBooksExternal();

        List<Book> books = bookService.save(bookExternalService.findAllBooks());
        assertThat(books, hasSize(2));
        assertBook(books.get(0), 1L, false);
        assertBook(books.get(1), 2L, false);
    }

    @Test
    public void testFindBooksWhenAllBooksAndRecommendBooksAreNotEmpty() {
        doReturn(Arrays.asList(
                createBook(1L, 100d),
                createBook(2L, 200d)
        )).when(bookExternalService).findAllBooksExternal();
        doReturn(Arrays.asList(
                createBook(1L, 100d)
        )).when(bookExternalService).findRecommendBooksExternal();

        List<Book> books = bookService.save(bookExternalService.findAllBooks());
        assertThat(books, hasSize(2));
        assertBook(books.get(0), 1L, true);
        assertBook(books.get(1), 2L, false);
    }

    private BookExternal createBook(Long id, Double price) {
        BookExternal book = new BookExternal();
        book.setId(id);
        book.setPrice(price);
        book.setBookName("book" + id);
        book.setAuthorName("author" + id);
        return book;
    }

    private void assertBook(Book book, Long bookId, boolean isRecommend) {
        assertThat(book.getId(), is(bookId));
        assertThat(book.getName(), is("book" + bookId));
        assertThat(book.getAuthor(), is("author" + bookId));
        assertThat(book.getRecommend(), is(isRecommend));
    }
}
