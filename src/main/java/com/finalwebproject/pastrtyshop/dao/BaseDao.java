package com.finalwebproject.pastrtyshop.dao;

import com.finalwebproject.pastrtyshop.entity.Entity;
import com.finalwebproject.pastrtyshop.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface BaseDao <T extends Entity>{
    List<T> findAll() throws DaoException;
    boolean delete(T t) throws DaoException;
    boolean create(T t) throws DaoException;
  //  T update(T t);

    default void closeConnection(Connection connection) throws DaoException {
        try {
            if (connection != null){
                connection.close();
            }
        }catch (SQLException e){
            throw new DaoException(e);
        }
    }

    default void closeStatement(Statement statement) throws DaoException {
        try {
            if (statement != null){
                statement.close();
            }
        }catch (SQLException e){
            throw new DaoException(e);
        }
    }


}
