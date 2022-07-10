package com.finalwebproject.pastrtyshop.dao.impl;

import com.finalwebproject.pastrtyshop.dao.DiscountDao;
import com.finalwebproject.pastrtyshop.entity.Client;
import com.finalwebproject.pastrtyshop.entity.ClientDiscount;
import com.finalwebproject.pastrtyshop.entity.ClientRole;
import com.finalwebproject.pastrtyshop.entity.ClientStatus;
import com.finalwebproject.pastrtyshop.exception.DaoException;
import com.finalwebproject.pastrtyshop.pool.CustomConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiscountDaoImpl implements DiscountDao {
    private static final Logger logger = LogManager.getLogger();

    private static final String SELECT_ALL_DISCOUNT = "SELECT discounts_id, value, discount_name FROM discounts";
    private static final String CREATE_DISCOUNT = "INSERT INTO discounts (discounts_id, value, discount_name) VALUES (NULL, ?, ?)";
    private static final String DELETE_DISCOUNT = "DELETE FROM discounts WHERE discounts_id = ?";
    private static final String SELECT_BY_DISCOUNT_ID = "SELECT discounts_id, value, discount_name FROM discounts " +
            "WHERE discounts_id = ?";
    private static final String SELECT_BY_DISCOUNT_VALUE = "SELECT discounts_id, value, discount_name FROM discounts " +
            "WHERE value = ?";
    private static final String SELECT_BY_DISCOUNT_NAME = "SELECT discounts_id, value, discount_name FROM discounts " +
            "WHERE discount_name = ?";
    private static final String UPDATE_DISCOUNT_VALUE = "UPDATE discounts SET value = ? WHERE discounts_id = ?";
    private static final String UPDATE_DISCOUNT_NAME = "UPDATE discounts SET discount_name = ? WHERE discounts_id = ?";

    @Override
    public List<ClientDiscount> findAll() throws DaoException {
        List<ClientDiscount> discounts = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        Statement statement = null;
        try{
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_DISCOUNT);
            discounts = createClientDiscountList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);

        }

        return discounts;
    }

    @Override
    public boolean delete(ClientDiscount discount) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(DELETE_DISCOUNT);
            statement.setInt(1, discount.getDiscountId());
            int flag = statement.executeUpdate();
            if (flag == 0){
                return false;
            }
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);
        }

        return true;
    }

    @Override
    public boolean create(ClientDiscount discount) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(CREATE_DISCOUNT);
            statement.setDouble(1, discount.getValue());
            statement.setString(2, discount.getNameOfDiscount());
            int flag = statement.executeUpdate();
            if (flag == 0){
                return false;
            }
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);
        }

        return true;
    }

    @Override
    public List<ClientDiscount> findById(int id) throws DaoException {
        List<ClientDiscount> discounts = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SELECT_BY_DISCOUNT_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            discounts = createClientDiscountList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);

        }

        return discounts;
    }

    @Override
    public List<ClientDiscount> findByValue(double value) throws DaoException {
        List<ClientDiscount> discounts = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SELECT_BY_DISCOUNT_VALUE);
            statement.setDouble(1, value);
            ResultSet resultSet = statement.executeQuery();
            discounts = createClientDiscountList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);

        }

        return discounts;
    }

    @Override
    public List<ClientDiscount> findByNameOfDiscount(String nameOfDiscount) throws DaoException {
        List<ClientDiscount> discounts = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SELECT_BY_DISCOUNT_NAME);
            statement.setString(1, nameOfDiscount);
            ResultSet resultSet = statement.executeQuery();
            discounts = createClientDiscountList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);

        }

        return discounts;
    }

    private List<ClientDiscount> createClientDiscountList(ResultSet resultSet) throws DaoException {
        List<ClientDiscount> discounts = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int discountId = resultSet.getInt(1);
                double value = resultSet.getDouble(2);
                String discount_name = resultSet.getString(3);
                discounts.add(new ClientDiscount(discountId, value, discount_name));
            }
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        return discounts;
    }

    @Override
    public boolean updateValue(double value, int discountId) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE_DISCOUNT_VALUE);
            statement.setDouble(1, value);
            statement.setInt(2, discountId);
            int flag = statement.executeUpdate();
            if (flag == 0) return false;
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);

        }
        return true;
    }

    @Override
    public boolean updateDiscountName(String nameOfDiscount,int discountId) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE_DISCOUNT_NAME);
            statement.setString(1, nameOfDiscount);
            statement.setInt(2, discountId);
            int flag = statement.executeUpdate();
            if (flag == 0) return false;
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);

        }
        return true;
    }
}
