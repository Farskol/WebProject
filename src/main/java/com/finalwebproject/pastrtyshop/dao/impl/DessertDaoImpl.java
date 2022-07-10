package com.finalwebproject.pastrtyshop.dao.impl;

import com.finalwebproject.pastrtyshop.dao.DessertDao;
import com.finalwebproject.pastrtyshop.entity.Dessert;
import com.finalwebproject.pastrtyshop.entity.Order;
import com.finalwebproject.pastrtyshop.exception.DaoException;
import com.finalwebproject.pastrtyshop.pool.CustomConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DessertDaoImpl implements DessertDao {
    private static final Logger logger = LogManager.getLogger();

    private static final String SELECT_ALL_DESSERTS = "SELECT dessert_id, description, cost, name, type FROM desserts";
    private static final String CREATE_DESSERT = "INSERT INTO desserts (dessert_id, description, cost, name, type)\n" +
            "VALUE (NULL, ?, ?, ?, ?);";
    private static final String DELETE_DESSERT = "DELETE FROM desserts WHERE dessert_id = ?";
    private static final String SELECT_BY_ID = "SELECT dessert_id, description, cost, name, type FROM desserts WHERE dessert_id = ?";
    private static final String SELECT_BY_COST = "SELECT dessert_id, description, cost, name, type FROM desserts WHERE cost = ?";
    private static final String SELECT_BY_NAME = "SELECT dessert_id, description, cost, name, type FROM desserts WHERE name = ?";
    private static final String SELECT_BY_TYPE = "SELECT dessert_id, description, cost, name, type FROM desserts WHERE type = ?";
    private static final String UPDATE_COST = "UPDATE desserts SET cost = ? WHERE dessert_id = ?";
    private static final String UPDATE_DESCRIPTION = "UPDATE desserts SET description = ? WHERE dessert_id = ?";
    private static final String UPDATE_NAME = "UPDATE desserts SET name = ? WHERE dessert_id = ?";
    private static final String UPDATE_TYPE = "UPDATE desserts SET type = ? WHERE dessert_id = ?";

    @Override
    public List<Dessert> findAll() throws DaoException {
        List<Dessert> desserts = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        Statement statement = null;
        try{
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_DESSERTS);
            desserts = createDessertList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);
        }

        return desserts;
    }

    @Override
    public boolean delete(Dessert dessert) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(DELETE_DESSERT);
            statement.setInt(1, dessert.getDessertId());
            int flag = statement.executeUpdate();
            if(flag == 0) return false;
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
    public boolean create(Dessert dessert) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(CREATE_DESSERT);
            statement.setString(1,dessert.getDescription());
            statement.setDouble(2, dessert.getCost());
            statement.setString(3, dessert.getName());
            statement.setString(4, dessert.getType());
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
    public List<Dessert> findById(int dessertId) throws DaoException {
        List<Dessert> desserts = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setInt(1, dessertId);
            ResultSet resultSet = statement.executeQuery();
            desserts = createDessertList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);
        }

        return desserts;
    }

    @Override
    public List<Dessert> findByCost(double cost) throws DaoException {
        List<Dessert> desserts = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SELECT_BY_COST);
            statement.setDouble(1, cost);
            ResultSet resultSet = statement.executeQuery();
            desserts = createDessertList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);
        }

        return desserts;
    }

    @Override
    public List<Dessert> findByName(String name) throws DaoException {
        List<Dessert> desserts = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SELECT_BY_NAME);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            desserts = createDessertList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);
        }

        return desserts;
    }

    @Override
    public List<Dessert> findByType(String type) throws DaoException {
        List<Dessert> desserts = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SELECT_BY_TYPE);
            statement.setString(1, type);
            ResultSet resultSet = statement.executeQuery();
            desserts = createDessertList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);
        }

        return desserts;
    }

    private List<Dessert> createDessertList(ResultSet resultSet) throws DaoException {
        List<Dessert> desserts = new ArrayList<>();

        try{
            while (resultSet.next()) {
                int dessertId = resultSet.getInt(1);
                String description = resultSet.getString(2);
                double cost = resultSet.getDouble(3);
                String name = resultSet.getString(4);
                String type = resultSet.getString(5);

                desserts.add(new Dessert(dessertId, description, cost, name, type));
            }
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        return desserts;
    }

    @Override
    public boolean updateCost(double cost, int dessertId) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_COST);
            statement.setDouble(1, cost);
            statement.setInt(2, dessertId);
            int flag = statement.executeUpdate();
            if(flag == 0) return false;
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
    public boolean updateDescription(String description, int dessertId) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_DESCRIPTION);
            statement.setString(1, description);
            statement.setInt(2, dessertId);
            int flag = statement.executeUpdate();
            if(flag == 0) return false;
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
    public boolean updateName(String name, int dessertId) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_NAME);
            statement.setString(1, name);
            statement.setInt(2, dessertId);
            int flag = statement.executeUpdate();
            if(flag == 0) return false;
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
    public boolean updateType(String type, int dessertId) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_TYPE);
            statement.setString(1, type);
            statement.setInt(2, dessertId);
            int flag = statement.executeUpdate();
            if(flag == 0) return false;
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
