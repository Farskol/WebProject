package com.finalwebproject.pastrtyshop.dao.impl;

import com.finalwebproject.pastrtyshop.dao.CakeDao;
import com.finalwebproject.pastrtyshop.entity.Cake;
import com.finalwebproject.pastrtyshop.entity.Stuffing;
import com.finalwebproject.pastrtyshop.exception.DaoException;
import com.finalwebproject.pastrtyshop.pool.CustomConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CakeDaoImpl implements CakeDao {
    private static final Logger logger = LogManager.getLogger();

    private static final String SELECT_ALL_CAKES = "SELECT cakes.cakes_id, cakes.cost, cakes.design_description, cakes.weight, " +
            "cakes.stuffings_stuffings_id, stuffings.stuffing, stuffings.description FROM cakes\n" +
            "LEFT JOIN stuffings ON cakes.stuffings_stuffings_id = stuffings_id";
    private static final String CREATE_CAKE = "INSERT INTO cakes (cakes_id, cost, design_description, weight, stuffings_stuffings_id)\n" +
            "VALUES (NULL, ?, ?, ?, ?)";
    private static final String DELETE_CAKE = "DELETE FROM cakes WHERE cakes_id = ?";
    private static final String SELECT_BY_CAKE_ID = "SELECT cakes.cakes_id, cakes.cost, cakes.design_description, cakes.weight, " +
            "cakes.stuffings_stuffings_id, stuffings.stuffing, stuffings.description FROM cakes\n" +
            "LEFT JOIN stuffings ON cakes.stuffings_stuffings_id = stuffings_id WHERE cakes.cakes_id = ?";
    private static final String SELECT_BY_CAKE_COST = "SELECT cakes.cakes_id, cakes.cost, cakes.design_description, cakes.weight, " +
            "cakes.stuffings_stuffings_id, stuffings.stuffing, stuffings.description FROM cakes\n" +
            "LEFT JOIN stuffings ON cakes.stuffings_stuffings_id = stuffings_id WHERE cakes.cost = ?";
    private static final String SELECT_BY_CAKE_WEIGHT = "SELECT cakes.cakes_id, cakes.cost, cakes.design_description, cakes.weight, " +
            "cakes.stuffings_stuffings_id, stuffings.stuffing, stuffings.description FROM cakes\n" +
            "LEFT JOIN stuffings ON cakes.stuffings_stuffings_id = stuffings_id WHERE cakes.weight = ?";
    private static final String SELECT_BY_CAKE_STUFFING = "SELECT cakes.cakes_id, cakes.cost, cakes.design_description, cakes.weight, " +
            "cakes.stuffings_stuffings_id, stuffings.stuffing, stuffings.description FROM cakes\n" +
            "LEFT JOIN stuffings ON cakes.stuffings_stuffings_id = stuffings_id WHERE cakes.stuffings_stuffings_id = ?";
    private static final String UPDATE_CAKE_COST = "UPDATE cakes SET cost = ? WHERE cakes_id = ?";
    private static final String UPDATE_CAKE_WEIGHT = "UPDATE cakes SET weight = ? WHERE cakes_id = ?";
    private static final String UPDATE_CAKE_STUFFING = "UPDATE cakes SET stuffings_stuffings_id = ? WHERE cakes_id = ?";
    private static final String UPDATE_CAKE_DESIGN_DESCRIPTION = "UPDATE cakes SET design_description = ? WHERE cakes_id = ?";

    @Override
    public List<Cake> findAll() throws DaoException {
        List<Cake> cakes = new ArrayList<>();
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_CAKES);
            cakes = createCakeList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);
        }

        return cakes;
    }

    @Override
    public boolean delete(Cake cake) throws DaoException{
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(DELETE_CAKE);
            statement.setInt(1, cake.getCakeId());
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
    public boolean create(Cake cake) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(CREATE_CAKE);
            statement.setDouble(1, cake.getCost());
            statement.setString(2, cake.getDesignDescription());
            statement.setDouble(3, cake.getWeight());
            statement.setInt(4, cake.getStuffing().getStuffingId());
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
    public int createWithId(Cake cake) throws DaoException {
        int lastId = 0;
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(CREATE_CAKE);
            statement.setDouble(1, cake.getCost());
            statement.setString(2, cake.getDesignDescription());
            statement.setDouble(3, cake.getWeight());
            statement.setInt(4, cake.getStuffing().getStuffingId());
            statement.executeUpdate();

            statement = connection.prepareStatement("SELECT last_insert_id()");
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            lastId = resultSet.getInt(1);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return lastId;
    }

    @Override
    public List<Cake> findById(int id) throws DaoException {
        List<Cake> cakes = new ArrayList<>();
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SELECT_BY_CAKE_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            cakes = createCakeList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }

        return cakes;
    }

    @Override
    public List<Cake> findByCost(double cost) throws DaoException {
        List<Cake> cakes = new ArrayList<>();
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SELECT_BY_CAKE_COST);
            statement.setDouble(1, cost);
            ResultSet resultSet = statement.executeQuery();
            cakes = createCakeList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }

        return cakes;
    }

    @Override
    public List<Cake> findByWeight(double weight) throws DaoException {
        List<Cake> cakes = new ArrayList<>();
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SELECT_BY_CAKE_WEIGHT);
            statement.setDouble(1, weight);
            ResultSet resultSet = statement.executeQuery();
            cakes = createCakeList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }

        return cakes;
    }

    @Override
    public List<Cake> findByStuffing(Stuffing stuffing) throws DaoException {
        List<Cake> cakes = new ArrayList<>();
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SELECT_BY_CAKE_STUFFING);
            statement.setInt(1, stuffing.getStuffingId());
            ResultSet resultSet = statement.executeQuery();
            cakes = createCakeList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }

        return cakes;
    }

    private List<Cake> createCakeList(ResultSet resultSet) throws DaoException{
        List<Cake> cakes = new ArrayList<>();

        try {
            while (resultSet.next()){
                int CakeId = resultSet.getInt(1);
                double cost = resultSet.getDouble(2);
                String designDescription = resultSet.getString(3);
                double weight = resultSet.getDouble(4);
                Stuffing stuffing = new Stuffing(resultSet.getInt(5), resultSet.getString(6), resultSet.getString(7));

                cakes.add(new Cake(CakeId, cost, designDescription, weight, stuffing));
            }
        }
        catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        return cakes;
    }

    @Override
    public boolean updateCost(double cost, int cakeId) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE_CAKE_COST);
            statement.setDouble(1, cost);
            statement.setInt(2, cakeId);
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
    public boolean updateWeight(double weight, int cakeId) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE_CAKE_WEIGHT);
            statement.setDouble(1, weight);
            statement.setInt(2, cakeId);
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
    public boolean updateStuffing(Stuffing stuffing, int cakeId) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE_CAKE_STUFFING);
            statement.setInt(1, stuffing.getStuffingId());
            statement.setInt(2, cakeId);
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
    public boolean updateDesignDescription(String description, int cakeId) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE_CAKE_DESIGN_DESCRIPTION);
            statement.setString(1, description);
            statement.setInt(2, cakeId);
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
