package com.finalwebproject.pastrtyshop.dao.impl;

import com.finalwebproject.pastrtyshop.dao.StuffingDao;
import com.finalwebproject.pastrtyshop.entity.Stuffing;
import com.finalwebproject.pastrtyshop.exception.DaoException;
import com.finalwebproject.pastrtyshop.pool.CustomConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StuffingDaoImpl implements StuffingDao {
    private static final Logger logger = LogManager.getLogger();

    private static final String SELECT_ALL_STUFFINGS = "SELECT stuffings_id, stuffing, description FROM stuffings";
    private static final String CREATE_STUFFING = "INSERT INTO stuffings (stuffings_id, stuffing, description)\n" +
            "VALUES (NULL, ?, ?)";
    private static final String DELETE_STUFFINGS = "DELETE FROM stuffings WHERE stuffings_id = ?";
    private static final String SELECT_BY_STUFFING_ID = "SELECT stuffings_id, stuffing, description FROM stuffings WHERE stuffings_id = ?";
    private static final String SELECT_BY_STUFFING = "SELECT stuffings_id, stuffing, description FROM stuffings WHERE stuffing = ?";
    private static final String UPDATE_STUFFING = "UPDATE stuffings SET stuffing = ? WHERE stuffings_id = ?";
    private static final String UPDATE_DESCRIPTION = "UPDATE stuffings SET description = ? WHERE stuffings_id = ?";

    @Override
    public List<Stuffing> findAll() throws DaoException {
        List<Stuffing> stuffings = new ArrayList<>();
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_STUFFINGS);
            stuffings = createStuffingsList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }

        return stuffings;
    }

    @Override
    public boolean delete(Stuffing stuffing) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(DELETE_STUFFINGS);
            statement.setInt(1, stuffing.getStuffingId());
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
    public boolean create(Stuffing stuffing) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(CREATE_STUFFING);
            statement.setString(1, stuffing.getStuffing());
            statement.setString(2, stuffing.getDescription());
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
    public List<Stuffing> findById(int stuffingId) throws DaoException {
        List<Stuffing> stuffings = new ArrayList<>();
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SELECT_BY_STUFFING_ID);
            statement.setInt(1, stuffingId);
            ResultSet resultSet = statement.executeQuery();
            stuffings = createStuffingsList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }

        return stuffings;
    }

    @Override
    public List<Stuffing> findByStuffing(String stuffing) throws DaoException {
        List<Stuffing> stuffings = new ArrayList<>();
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SELECT_BY_STUFFING);
            statement.setString(1, stuffing);
            ResultSet resultSet = statement.executeQuery();
            stuffings = createStuffingsList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }

        return stuffings;
    }

    private List<Stuffing> createStuffingsList(ResultSet resultSet) throws DaoException {
        List<Stuffing> stuffings = new ArrayList<>();

            try {
               while (resultSet.next()){
                   int id = resultSet.getInt(1);
                   String stuffing = resultSet.getString(2);
                   String description = resultSet.getString(3);
                   stuffings.add(new Stuffing(id, stuffing, description));
               }
            }
            catch (SQLException e) {
                logger.log(Level.ERROR, e.getMessage());
                throw new DaoException(e);
            }
        return stuffings;
    }

    @Override
    public boolean updateStuffing(String stuffing, int stuffingId) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE_STUFFING);
            statement.setString(1, stuffing);
            statement.setInt(2, stuffingId);
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
    public boolean updateDescription(String description, int stuffingId) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE_DESCRIPTION);
            statement.setString(1, description);
            statement.setInt(2, stuffingId);
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
