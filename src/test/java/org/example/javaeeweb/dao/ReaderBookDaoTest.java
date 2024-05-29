package org.example.javaeeweb.dao;

import org.example.javaeeweb.dao.impl.BookDaoImpl;
import org.example.javaeeweb.dao.impl.ReaderBookDaoImpl;
import org.example.javaeeweb.dao.impl.ReaderDaoImpl;
import org.example.javaeeweb.entity.Book;
import org.example.javaeeweb.entity.Reader;
import org.example.javaeeweb.entity.ReaderBook;
import org.example.javaeeweb.utils.DbConnectionProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ReaderBookDaoTest {
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.3");
    ReaderBookDao readerBookDao;

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
        readerBookDao = new ReaderBookDaoImpl(dbConnectionProvider);
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
        readerBookDao.add(new ReaderBook(1, 1, 1));
        readerBookDao.add(new ReaderBook(2, 2, 2));

        // Act
        List<ReaderBook> readerBookList = readerBookDao.getAll();

        // Assert
        assertEquals(3, readerBookList.size());
    }

    @Test
    void add() {
        // Arrange
        int countBeforeAdd = readerBookDao.getAll().size();

        // Act
        readerBookDao.add(new ReaderBook(1, 1, 1));

        // Assert
        int countAfterAdd = readerBookDao.getAll().size();
        assertNotEquals(countBeforeAdd, countAfterAdd);
    }

    @Test
    void update() {
        // Arrange
        readerBookDao.add(new ReaderBook(1, 1, 1));
        readerBookDao.add(new ReaderBook(2, 2, 1));
        ReaderBook recordBeforeUpdate = readerBookDao.getAll().get(1);

        // Act
        readerBookDao.update(new ReaderBook(1, 2, 2));

        // Assert
        ReaderBook recordAfterUpdate = readerBookDao.getAll().get(1);
        assertNotEquals(recordBeforeUpdate, recordAfterUpdate);
    }

    @Test
    void delete() {
        // Arrange
        readerBookDao.add(new ReaderBook(1, 1, 1));
        readerBookDao.add(new ReaderBook(2, 2, 2));
        int countBeforeDelete = readerBookDao.getAll().size();

        // Act
        readerBookDao.delete(1);

        // Assert
        int countAfterDelete = readerBookDao.getAll().size();
        assertNotEquals(countBeforeDelete, countAfterDelete);
    }
}