package org.example.javaeeweb.services.impl;

import org.example.javaeeweb.dao.BookDao;
import org.example.javaeeweb.dao.ReaderDao;
import org.example.javaeeweb.dao.SubscriptionDao;
import org.example.javaeeweb.dto.BookDto;
import org.example.javaeeweb.dto.ReaderDto;
import org.example.javaeeweb.dto.SubscriptionDto;
import org.example.javaeeweb.entity.Book;
import org.example.javaeeweb.entity.Reader;
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
class SubscriptionServiceImplTest {
    @Mock
    BookDao bookDao;

    @Mock
    ReaderDao readerDao;

    @Mock
    SubscriptionDao subscriptionDao;

    @InjectMocks
    SubscriptionServiceImpl subscriptionService;

    @Test
    void getAll_WhenCallGetAll_ShouldReturnSubscriptionCollection() {
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

        List<Book> bookList = new ArrayList<>() {{
            add(new Book(1,
                    "author1",
                    "bookName1",
                    2020,
                    100));
        }};

        List<SubscriptionDto> expectedCollection = new ArrayList<>() {{
            add(new SubscriptionDto(1,
                    Date.valueOf("2024-05-26"),
                    Date.valueOf("2024-05-27"),
                    new BookDto(1,
                            "author1",
                            "bookName1",
                            2020,
                            100,
                            null,
                            null),
                    new ReaderDto(1,
                            "Name1",
                            "LastName1",
                            "Address1",
                            null,
                            null)));
        }};

        when(bookDao.getAll()).thenReturn(bookList);
        when(subscriptionDao.getAll()).thenReturn(subscriptionList);
        when(readerDao.getAll()).thenReturn(readerList);

        // Act
        List<SubscriptionDto> actualCollection = subscriptionService.getAll();

        // Assert
        assertEquals(expectedCollection.size(), actualCollection.size());
        Assertions.assertTrue(expectedCollection.containsAll(actualCollection));
    }

    @Test
    void add() {
        // Arrange
        SubscriptionDto subscriptionDto = new SubscriptionDto(1, Date.valueOf("1111-1-1"), Date.valueOf("1111-1-1"), new BookDto(1, "author1", "bookName1",
                2020, 100, null, null), new ReaderDto(1, "1", "1", "1", null, null));
        Subscription subscription = new Subscription(1, Date.valueOf("1111-1-1"), Date.valueOf("1111-1-1"), 1, 1);

        // Act
        subscriptionService.add(subscriptionDto);

        // Assert
        verify(subscriptionDao, times(1)).add(subscription);
    }

    @Test
    void update() {
        // Arrange
        SubscriptionDto subscriptionDto = new SubscriptionDto(1, Date.valueOf("1111-1-1"), Date.valueOf("1111-1-1"), new BookDto(1, "author1", "bookName1",
                2020, 100, null, null), new ReaderDto(1, "1", "1", "1", null, null));
        Subscription subscription = new Subscription(1, Date.valueOf("1111-1-1"), Date.valueOf("1111-1-1"), 1, 1);

        // Act
        subscriptionService.update(subscriptionDto);

        // Assert
        verify(subscriptionDao, times(1)).update(subscription);
    }

    @Test
    void delete() {
        // Act
        subscriptionService.delete(1);

        // Assert
        verify(subscriptionDao, times(1)).delete(1);
    }
}