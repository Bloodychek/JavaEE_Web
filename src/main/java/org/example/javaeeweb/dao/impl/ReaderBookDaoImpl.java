package org.example.javaeeweb.dao.impl;

import org.example.javaeeweb.dao.BaseDao;
import org.example.javaeeweb.dao.ReaderBookDao;
import org.example.javaeeweb.entity.ReaderBook;
import org.example.javaeeweb.utils.DbConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReaderBookDaoImpl extends BaseDao implements ReaderBookDao {

    String findAllSQL = "SELECT * FROM \"readers_books\"";
    String saveSQL = "INSERT INTO \"readers_books\" (fk_reader_id, fk_book_id) values (?,?)";
    String updateSQL = "UPDATE \"readers_books\" SET fk_reader_id = ?, fk_book_id = ? WHERE reader_book_id=?";
    String deleteSQL = "DELETE FROM \"readers_books\" WHERE reader_book_id = ?";

    private final DbConnectionProvider connectionProvider;

    public ReaderBookDaoImpl(DbConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
        createReaderBookTableIfNotExist();
    }

    @Override
    public List<ReaderBook> getAll() {
        List<ReaderBook> readerBookList = new ArrayList<>();
        try (Connection connection = this.connectionProvider.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(findAllSQL);

            while (resultSet.next()) {
                ReaderBook readerBook = new ReaderBook();
                readerBook.setReadersBooksID(resultSet.getInt(1));
                readerBook.setReaderID(resultSet.getInt(2));
                readerBook.setBookID(resultSet.getInt(3));

                readerBookList.add(readerBook);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return readerBookList;
    }

    @Override
    public void add(ReaderBook readerBook) {
        try (Connection connection = this.connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(saveSQL);
            preparedStatement.setInt(1, readerBook.getReaderID());
            preparedStatement.setInt(2, readerBook.getBookID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void update(ReaderBook readerBook) {
        try (Connection connection = this.connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setInt(1, readerBook.getReadersBooksID());
            preparedStatement.setInt(2, readerBook.getReaderID());
            preparedStatement.setInt(3, readerBook.getBookID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = this.connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void createReaderBookTableIfNotExist() {
        try (Connection connection = this.connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("create table if not exists books ( " +
                    "author varchar, " +
                    "book_name varchar, " +
                    "year_of_publishing int, " +
                    "deposit_price int, " +
                    "book_id serial not null, " +
                    "primary key (book_id));" +

                    "create table if not exists readers ( " +
                    "first_name varchar, " +
                    "second_name varchar, " +
                    "address varchar, " +
                    "reader_id serial not null, " +
                    "primary key (reader_id));" +

                    "create table if not exists readers_books ( " +
                    "reader_book_id serial, " +
                    "fk_reader_id int REFERENCES readers (reader_id), " +
                    "fk_book_id int REFERENCES books (book_id), " +
                    "primary key (reader_book_id));"
            );
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
