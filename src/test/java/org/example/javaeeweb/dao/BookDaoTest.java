package org.example.javaeeweb.dao;

import org.example.javaeeweb.dao.impl.BookDaoImpl;
import org.example.javaeeweb.entity.Book;
import org.example.javaeeweb.utils.DbConnectionProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BookDaoTest {
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.3");
    BookDao bookDao;

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
        bookDao = new BookDaoImpl(dbConnectionProvider);
    }

    @Test
    void getAll() {
        // Arrange
        bookDao.add(new Book(1, "q", "w", 1, 2));
        bookDao.add(new Book(2, "e", "r", 3, 4));

        // Act
        List<Book> bookList = bookDao.getAll();

        // Assert
        assertEquals(3, bookList.size());
    }

    @Test
    void add() {
        // Arrange
        int countBeforeAdd = bookDao.getAll().size();

        // Act
        bookDao.add(new Book(1, "Tolstoy", "Peace and War", 1223, 10));

        // Assert
        int countAfterAdd = bookDao.getAll().size();
        assertNotEquals(countBeforeAdd, countAfterAdd);
    }

    @Test
    void update() {
        // Arrange
        bookDao.add(new Book(1, "q", "w", 1, 2));
        bookDao.add(new Book(2, "e", "r", 3, 4));
        Book recordBeforeUpdate = bookDao.getAll().get(1);

        // Act
        bookDao.update(new Book(1, "qwe", "123", 123, 123));

        // Assert
        Book recordAfterUpdate = bookDao.getAll().get(1);
        assertNotEquals(recordBeforeUpdate, recordAfterUpdate);
    }

    @Test
    void delete() {
        // Arrange
        bookDao.add(new Book(1, "q", "w", 1, 2));
        bookDao.add(new Book(2, "e", "r", 3, 4));
        int countBeforeDelete = bookDao.getAll().size();

        // Act
        bookDao.delete(1);

        // Assert
        int countAfterDelete = bookDao.getAll().size();
        assertNotEquals(countBeforeDelete, countAfterDelete);
    }
}