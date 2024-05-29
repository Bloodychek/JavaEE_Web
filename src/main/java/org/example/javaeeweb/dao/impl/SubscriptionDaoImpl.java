package org.example.javaeeweb.dao.impl;

import org.example.javaeeweb.dao.BaseDao;
import org.example.javaeeweb.dao.SubscriptionDao;
import org.example.javaeeweb.entity.Subscription;
import org.example.javaeeweb.utils.DbConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionDaoImpl extends BaseDao implements SubscriptionDao {

    String findAllSQL = "SELECT * FROM \"subscriptions\"";
    String saveSQL = "INSERT INTO \"subscriptions\" (issue_date, return_date, fk_book_id, fk_reader_id) values (?,?,?,?)";
    String updateSQL = "UPDATE \"subscriptions\" SET issue_date = ?, return_date = ?, fk_book_id = ?, fk_reader_id = ? WHERE subscription_id=?";
    String deleteSQL = "DELETE FROM \"subscriptions\" WHERE subscription_id = ?";

    private final DbConnectionProvider connectionProvider;

    public SubscriptionDaoImpl(DbConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
        createSubscriptionTableIfNotExist();
    }

    @Override
    public List<Subscription> getAll() {
        List<Subscription> subscriptionList = new ArrayList<>();
        try (Connection connection = this.connectionProvider.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(findAllSQL);

            while (resultSet.next()) {
                Subscription subscription = new Subscription();
                subscription.setSubscriptionID(resultSet.getInt(5));
                subscription.setIssueDate(resultSet.getDate(1));
                subscription.setReturnDate(resultSet.getDate(2));
                subscription.setBookID(resultSet.getInt(3));
                subscription.setReaderID(resultSet.getInt(4));

                subscriptionList.add(subscription);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return subscriptionList;
    }

    @Override
    public void add(Subscription subscription) {
        try (Connection connection = this.connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(saveSQL);
            preparedStatement.setDate(1, subscription.getIssueDate());
            preparedStatement.setDate(2, subscription.getReturnDate());
            preparedStatement.setInt(3, subscription.getBookID());
            preparedStatement.setInt(4, subscription.getReaderID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void update(Subscription subscription) {
        try (Connection connection = this.connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setInt(5, subscription.getSubscriptionID());
            preparedStatement.setDate(1, subscription.getIssueDate());
            preparedStatement.setDate(2, subscription.getReturnDate());
            preparedStatement.setInt(3, subscription.getBookID());
            preparedStatement.setInt(4, subscription.getReaderID());
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

    private void createSubscriptionTableIfNotExist() {
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

                    "create table if not exists subscriptions ( " +
                    "issue_date date, " +
                    "return_date date, " +
                    "fk_book_id int REFERENCES books (book_id), " +
                    "fk_reader_id int REFERENCES readers (reader_id), " +
                    "subscription_id serial, " +
                    "primary key (subscription_id));"
            );
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
