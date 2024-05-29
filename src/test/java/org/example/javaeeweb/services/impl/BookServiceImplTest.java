package org.example.javaeeweb.services.impl;

import org.example.javaeeweb.dao.BookDao;
import org.example.javaeeweb.dao.ReaderBookDao;
import org.example.javaeeweb.dao.ReaderDao;
import org.example.javaeeweb.dao.SubscriptionDao;
import org.example.javaeeweb.dto.BookDto;
import org.example.javaeeweb.dto.ReaderDto;
import org.example.javaeeweb.dto.SubscriptionDto;
import org.example.javaeeweb.entity.Book;
import org.example.javaeeweb.entity.Reader;
import org.example.javaeeweb.entity.ReaderBook;
import org.example.javaeeweb.entity.Subscription;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Mock
    BookDao bookDao;

    @Mock
    ReaderDao readerDao;

    @Mock
    SubscriptionDao subscriptionDao;

    @Mock
    ReaderBookDao readerBookDao;

    @InjectMocks
    BookServiceImpl bookService;

    @Test
    void getAll_WhenCallGetAll_ShouldReturnBookCollection() {
        // Arrange
        List<Subscription> subscriptionList = new ArrayList<>() {{
            add(new Subscription(1,
                    Date.valueOf("2024-05-26"),
                    Date.valueOf("2024-05-27"),
                    1,
                    1));
        }};

        List<Reader> readerList = new ArrayList<>() {{
            add(new Reader(1,
                    "Name1",
                    "LastName1",
                    "Address1"));
        }};

        List<ReaderBook> readerBookList = new ArrayList<>() {{
            add(new ReaderBook(1, 1, 1));
        }};

        List<Book> bookList = new ArrayList<>() {{
            add(new Book(1,
                    "author1",
                    "bookName1",
                    2020,
                    100));
        }};

        List<BookDto> expectedCollection = new ArrayList<>() {{
            add(new BookDto(1,
                    "author1",
                    "bookName1",
                    2020,
                    100,
                    new ArrayList<>() {{
                        add(new ReaderDto(1,
                                "Name1",
                                "LastName1",
                                "Address1",
                                null,
                                null));
                    }},
                    new ArrayList<>() {{
                        add(new SubscriptionDto(1,
                                Date.valueOf("2024-05-26"),
                                Date.valueOf("2024-05-27"),
                                null,
                                null));
                    }}));
        }};

        when(bookDao.getAll()).thenReturn(bookList);
        when(subscriptionDao.getAll()).thenReturn(subscriptionList);
        when(readerDao.getAll()).thenReturn(readerList);
        when(readerBookDao.getAll()).thenReturn(readerBookList);

        // Act
        List<BookDto> actualCollection = bookService.getAll();

        // Assert
        assertEquals(expectedCollection.size(), actualCollection.size());
        Assertions.assertTrue(expectedCollection.containsAll(actualCollection));
    }

    @Test
    void add() {
        // Arrange
        BookDto bookDto = new BookDto(1, "1", "1", 1, 1, null, null);
        Book book = new Book(1, "1", "1", 1, 1);

        // Act
        bookService.add(bookDto);

        // Assert
        verify(bookDao, times(1)).add(book);
    }

    @Test
    void update() {
        // Arrange
        BookDto bookDto = new BookDto(1, "1", "1", 1, 1, null, null);
        Book book = new Book(1, "1", "1", 1, 1);

        // Act
        bookService.update(bookDto);

        // Assert
        verify(bookDao, times(1)).update(book);
    }

    @Test
    void delete() {
        // Act
        bookService.delete(1);

        // Assert
        verify(bookDao, times(1)).delete(1);
    }
}