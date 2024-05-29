package org.example.javaeeweb.dao;

import org.example.javaeeweb.dao.impl.BookDaoImpl;
import org.example.javaeeweb.dao.impl.ReaderDaoImpl;
import org.example.javaeeweb.dao.impl.SubscriptionDaoImpl;
import org.example.javaeeweb.entity.Book;
import org.example.javaeeweb.entity.Reader;
import org.example.javaeeweb.entity.Subscription;
import org.example.javaeeweb.utils.DbConnectionProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SubscriptionDaoTest {
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.3");
    SubscriptionDao subscriptionDao;

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @BeforeEach
    void setUp() {
        DbConnectionProvider dbConnectionProvider = new DbConnectionProvider(
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword()
        );
        subscriptionDao = new SubscriptionDaoImpl(dbConnectionProvider);

        BookDao bookDao = new BookDaoImpl(dbConnectionProvider);
        ReaderDao readerDao = new ReaderDaoImpl(dbConnectionProvider);

        bookDao.add(new Book(1, "qwe", "sfw", 1, 1));
        bookDao.add(new Book(2, "qwe2", "sf2w", 14, 156));
        readerDao.add(new Reader(1, "Kirill", "gdhg", "123"));
        readerDao.add(new Reader(2, "Kirill2", "gdhg4", "123df"));
    }

    @Test
    void getAll() {
        // Arrange
        subscriptionDao.add(new Subscription(1, new Date(9999, 4, 4), new Date(8888, 2, 1), 1, 2));
        subscriptionDao.add(new Subscription(2, new Date(7777, 7, 7), new Date(6666, 6, 6), 3, 4));

        // Act
        List<Subscription> subscriptionList = subscriptionDao.getAll();

        // Assert
        assertEquals(3, subscriptionList.size());
    }

    @Test
    void add() {
        // Arrange
        int countBeforeAdd = subscriptionDao.getAll().size();

        // Act
        subscriptionDao.add(new Subscription(1, new Date(9999, 4, 4), new Date(8888, 2, 1), 1, 2));

        // Assert
        int countAfterAdd = subscriptionDao.getAll().size();
        assertNotEquals(countBeforeAdd, countAfterAdd);
    }

    @Test
    void update() {
        // Arrange
        subscriptionDao.add(new Subscription(1, new Date(9999, 4, 4), new Date(8888, 2, 1), 1, 2));
        subscriptionDao.add(new Subscription(2, new Date(7777, 7, 7), new Date(6666, 6, 6), 2, 1));
        Subscription recordBeforeUpdate = subscriptionDao.getAll().get(1);

        // Act
        subscriptionDao.update(new Subscription(1, new Date(2023, 1, 1), new Date(2024, 4, 4), 1, 1));

        //Assert
        Subscription recordAfterUpdate = subscriptionDao.getAll().get(1);
        assertNotEquals(recordBeforeUpdate, recordAfterUpdate);
    }

    @Test
    void delete() {
        // Arrange
        subscriptionDao.add(new Subscription(1, new Date(9999, 4, 4), new Date(8888, 2, 1), 1, 2));
        subscriptionDao.add(new Subscription(2, new Date(7777, 7, 7), new Date(6666, 6, 6), 2, 1));
        int countBeforeDelete = subscriptionDao.getAll().size();

        // Act
        subscriptionDao.delete(1);

        // Assert
        int countAfterDelete = subscriptionDao.getAll().size();
        assertNotEquals(countBeforeDelete, countAfterDelete);
    }
}