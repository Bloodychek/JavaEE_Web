package org.example.javaeeweb.dao;

import org.example.javaeeweb.dao.impl.ReaderDaoImpl;
import org.example.javaeeweb.entity.Reader;
import org.example.javaeeweb.utils.DbConnectionProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ReaderDaoTest {
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.3");
    ReaderDao readerDao;

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
        readerDao = new ReaderDaoImpl(dbConnectionProvider);
    }

    @Test
    void getAll() {
        // Arrange
        readerDao.add(new Reader(1, "Evgeniy", "Egorov", "1"));
        readerDao.add(new Reader(2, "e", "r", "1"));

        // Act
        List<Reader> readerList = readerDao.getAll();

        // Assert
        assertEquals(3, readerList.size());
    }

    @Test
    void add() {
        //Arrange
        int countBeforeAdd = readerDao.getAll().size();

        // Act
        readerDao.add(new Reader(1, "Evgeniy", "Egorov", "1"));

        // Assert
        int countAfterAdd = readerDao.getAll().size();
        assertNotEquals(countBeforeAdd, countAfterAdd);
    }

    @Test
    void update() {
        // Arrange
        readerDao.add(new Reader(1, "q", "w", "1"));
        readerDao.add(new Reader(2, "Evgeniy", "Egorov", "1"));
        Reader recordBeforeUpdate = readerDao.getAll().get(1);

        // Act
        readerDao.update(new Reader(1, "qwe", "123", "123"));

        // Assert
        Reader recordAfterUpdate = readerDao.getAll().get(1);
        assertNotEquals(recordBeforeUpdate, recordAfterUpdate);
    }

    @Test
    void delete() {
        // Arrange
        readerDao.add(new Reader(1, "Evgeniy", "Egorov", "1"));
        readerDao.add(new Reader(2, "e", "r", "3"));
        int countBeforeDelete = readerDao.getAll().size();

        // Act
        readerDao.delete(2);

        // Assert
        int countAfterDelete = readerDao.getAll().size();
        assertNotEquals(countBeforeDelete, countAfterDelete);
    }
}