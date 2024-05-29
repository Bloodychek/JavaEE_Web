package org.example.javaeeweb.dao.impl;

import org.example.javaeeweb.dao.BaseDao;
import org.example.javaeeweb.dao.ReaderDao;
import org.example.javaeeweb.entity.Reader;
import org.example.javaeeweb.utils.DbConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReaderDaoImpl extends BaseDao implements ReaderDao {


    String findAllSQL = "SELECT * FROM \"readers\"";
    String saveSQL = "INSERT INTO \"readers\" (first_name, second_name, address) values (?,?,?)";
    String updateSQL = "UPDATE \"readers\" SET first_name = ?, second_name = ?, address = ? WHERE reader_id=?";
    String deleteSQL = "DELETE FROM \"readers\" WHERE reader_id = ?";

    private final DbConnectionProvider connectionProvider;

    public ReaderDaoImpl(DbConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
        createReaderTableIfNotExist();
    }

    @Override
    public List<Reader> getAll() {

        List<Reader> readerList = new ArrayList<>();
        try (Connection connection = this.connectionProvider.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(findAllSQL);

            while (resultSet.next()) {
                Reader reader = new Reader();
                reader.setReadersID(resultSet.getInt(4));
                reader.setFirstName(resultSet.getString(1));
                reader.setSecondName(resultSet.getString(2));
                reader.setAddress(resultSet.getString(3));

                readerList.add(reader);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return readerList;
    }

    @Override
    public void add(Reader reader) {
        try (Connection connection = this.connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(saveSQL);
            preparedStatement.setString(1, reader.getFirstName());
            preparedStatement.setString(2, reader.getSecondName());
            preparedStatement.setString(3, reader.getAddress());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void update(Reader reader) {
        try (Connection connection = this.connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setInt(4, reader.getReadersID());
            preparedStatement.setString(1, reader.getFirstName());
            preparedStatement.setString(2, reader.getSecondName());
            preparedStatement.setString(3, reader.getAddress());
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

    private void createReaderTableIfNotExist() {
        try (Connection connection = this.connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("create table if not exists readers ( " +
                    "first_name varchar, " +
                    "second_name varchar, " +
                    "address varchar, " +
                    "reader_id serial not null, " +
                    "primary key (reader_id))"
            );
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
