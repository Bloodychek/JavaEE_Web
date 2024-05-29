package org.example.javaeeweb.dao;

public class BaseDao {
    public BaseDao() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
