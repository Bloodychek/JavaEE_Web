package org.example.javaeeweb.dao.impl;

import org.example.javaeeweb.dao.BaseDao;
import org.example.javaeeweb.dao.BookDao;
import org.example.javaeeweb.entity.Book;
import org.example.javaeeweb.utils.DbConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl extends BaseDao implements BookDao {

    String findAllSQL = "SELECT * FROM \"books\"";
    String saveSQL = "INSERT INTO \"books\" (author, book_name, year_of_publishing, deposit_price) values (?,?,?,?)";
    String updateSQL = "UPDATE \"books\" SET author = ?, book_name = ?, year_of_publishing = ?, deposit_price = ? WHERE book_id=?";
    String deleteSQL = "DELETE FROM \"books\" WHERE book_id = ?";

    private final DbConnectionProvider connectionProvider;

    public BookDaoImpl(DbConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
        createBookTableIfNotExist();
    }

    @Override
    public List<Book> getAll() {
        List<Book> bookList = new ArrayList<>();
        try (Connection connection = this.connectionProvider.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(findAllSQL);

            while (resultSet.next()) {
                Book book = new Book();
                book.setBooksID(resultSet.getInt(5));
                book.setBookName(resultSet.getString(1));
                book.setAuthor(resultSet.getString(2));
                book.setYearOfPublishing(resultSet.getInt(3));
                book.setDepositPrice(resultSet.getInt(4));

                bookList.add(book);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return bookList;
    }

    @Override
    public void add(Book book) {
        try (Connection connection = this.connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(saveSQL);
            preparedStatement.setString(1, book.getBookName());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setInt(3, book.getYearOfPublishing());
            preparedStatement.setInt(4, book.getDepositPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void update(Book book) {
        try (Connection connection = this.connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setInt(5, book.getBooksID());
            preparedStatement.setString(1, book.getBookName());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setInt(3, book.getYearOfPublishing());
            preparedStatement.setInt(4, book.getDepositPrice());
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

    private void createBookTableIfNotExist() {
        try (Connection connection = this.connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("create table if not exists books ( " +
                    "author varchar, " +
                    "book_name varchar, " +
                    "year_of_publishing int, " +
                    "deposit_price int, " +
                    "book_id serial not null, " +
                    "primary key (book_id))"
            );
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
