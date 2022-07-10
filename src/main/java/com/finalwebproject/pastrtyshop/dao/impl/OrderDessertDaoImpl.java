package com.finalwebproject.pastrtyshop.dao.impl;

import com.finalwebproject.pastrtyshop.dao.OrderDessertDao;
import com.finalwebproject.pastrtyshop.entity.*;
import com.finalwebproject.pastrtyshop.exception.DaoException;
import com.finalwebproject.pastrtyshop.pool.CustomConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDessertDaoImpl implements OrderDessertDao {
    private static final Logger logger = LogManager.getLogger();

    private static final String SELECT_ALL_ORDER_DESSERTS = "SELECT order_desserts.order_desserts_id, order_desserts.dessert_count, " +
            "order_desserts.orders_orders_id, order_desserts.desserts_dessert_id, \n" +
            "desserts.description, desserts.cost, desserts.name, desserts.type, order_desserts.cakes_cakes_id, cakes.cost, " +
            "cakes.design_description, cakes.weight, cakes.stuffings_stuffings_id, \n" +
            "stuffings.stuffing, stuffings.description FROM order_desserts \n" +
            "LEFT JOIN desserts ON order_desserts.desserts_dessert_id = desserts.dessert_id \n" +
            "LEFT JOIN cakes ON order_desserts.cakes_cakes_id = cakes.cakes_id \n" +
            "LEFT JOIN stuffings ON cakes.stuffings_stuffings_id = stuffings.stuffings_id;  ";
    private static final String CREATE_ODER_CAKE = "INSERT INTO order_desserts (order_desserts_id, dessert_count, " +
            "orders_orders_id, desserts_dessert_id, cakes_cakes_id) VALUES (NULL, ?, ?, NULL, ?)";
    private static final String CREATE_ODER_DESSERT = "INSERT INTO order_desserts (order_desserts_id, dessert_count, " +
            "orders_orders_id, desserts_dessert_id, cakes_cakes_id) VALUES (NULL, ?, ?, ?, NULL)";
    private static final String DELETE_ORDER_DESSERT = "DELETE FROM order_desserts WHERE order_desserts_id = ?";
    private static final String DELETE_ORDER_DESSERT_BY_ORDER = "DELETE FROM order_desserts WHERE orders_orders_id = ?";
    private static final String SELECT_BY_ID = "SELECT order_desserts.order_desserts_id, order_desserts.dessert_count, " +
            "order_desserts.orders_orders_id, order_desserts.desserts_dessert_id, \n" +
            "desserts.description, desserts.cost, desserts.name, desserts.type, order_desserts.cakes_cakes_id, cakes.cost, " +
            "cakes.design_description, cakes.weight, cakes.stuffings_stuffings_id, \n" +
            "stuffings.stuffing, stuffings.description FROM order_desserts \n" +
            "LEFT JOIN desserts ON order_desserts.desserts_dessert_id = desserts.dessert_id \n" +
            "LEFT JOIN cakes ON order_desserts.cakes_cakes_id = cakes.cakes_id \n" +
            "LEFT JOIN stuffings ON cakes.stuffings_stuffings_id = stuffings.stuffings_id " +
            "WHERE order_desserts.order_desserts_id = ?";
    private static final String SELECT_BY_COUNT = "SELECT order_desserts.order_desserts_id, order_desserts.dessert_count, " +
            "order_desserts.orders_orders_id, order_desserts.desserts_dessert_id, \n" +
            "desserts.description, desserts.cost, desserts.name, desserts.type, order_desserts.cakes_cakes_id, cakes.cost, " +
            "cakes.design_description, cakes.weight, cakes.stuffings_stuffings_id, \n" +
            "stuffings.stuffing, stuffings.description FROM order_desserts \n" +
            "LEFT JOIN desserts ON order_desserts.desserts_dessert_id = desserts.dessert_id \n" +
            "LEFT JOIN cakes ON order_desserts.cakes_cakes_id = cakes.cakes_id \n" +
            "LEFT JOIN stuffings ON cakes.stuffings_stuffings_id = stuffings.stuffings_id " +
            "WHERE order_desserts.dessert_count = ?";
    private static final String SELECT_BY_ORDER = "SELECT order_desserts.order_desserts_id, order_desserts.dessert_count, " +
            "order_desserts.orders_orders_id, order_desserts.desserts_dessert_id, \n" +
            "desserts.description, desserts.cost, desserts.name, desserts.type, order_desserts.cakes_cakes_id, cakes.cost, " +
            "cakes.design_description, cakes.weight, cakes.stuffings_stuffings_id, \n" +
            "stuffings.stuffing, stuffings.description FROM order_desserts \n" +
            "LEFT JOIN desserts ON order_desserts.desserts_dessert_id = desserts.dessert_id \n" +
            "LEFT JOIN cakes ON order_desserts.cakes_cakes_id = cakes.cakes_id \n" +
            "LEFT JOIN stuffings ON cakes.stuffings_stuffings_id = stuffings.stuffings_id " +
            "WHERE order_desserts.orders_orders_id = ?";
    private static final String SELECT_BY_CAKE = "SELECT order_desserts.order_desserts_id, order_desserts.dessert_count, " +
            "order_desserts.orders_orders_id, order_desserts.desserts_dessert_id, \n" +
            "desserts.description, desserts.cost, desserts.name, desserts.type, order_desserts.cakes_cakes_id, cakes.cost, " +
            "cakes.design_description, cakes.weight, cakes.stuffings_stuffings_id, \n" +
            "stuffings.stuffing, stuffings.description FROM order_desserts \n" +
            "LEFT JOIN desserts ON order_desserts.desserts_dessert_id = desserts.dessert_id \n" +
            "LEFT JOIN cakes ON order_desserts.cakes_cakes_id = cakes.cakes_id \n" +
            "LEFT JOIN stuffings ON cakes.stuffings_stuffings_id = stuffings.stuffings_id " +
            "WHERE order_desserts.cakes_cakes_id = ?";
    private static final String SELECT_BY_DESSERT = "SELECT order_desserts.order_desserts_id, order_desserts.dessert_count, " +
            "order_desserts.orders_orders_id, order_desserts.desserts_dessert_id, \n" +
            "desserts.description, desserts.cost, desserts.name, desserts.type, order_desserts.cakes_cakes_id, cakes.cost, " +
            "cakes.design_description, cakes.weight, cakes.stuffings_stuffings_id, \n" +
            "stuffings.stuffing, stuffings.description FROM order_desserts \n" +
            "LEFT JOIN desserts ON order_desserts.desserts_dessert_id = desserts.dessert_id \n" +
            "LEFT JOIN cakes ON order_desserts.cakes_cakes_id = cakes.cakes_id \n" +
            "LEFT JOIN stuffings ON cakes.stuffings_stuffings_id = stuffings.stuffings_id " +
            "WHERE  order_desserts.desserts_dessert_id = ?";
    private static final String UPDATE_COUNT = "UPDATE order_desserts SET dessert_count = ? WHERE order_desserts_id = ?";
    private static final String UPDATE_ORDER = "UPDATE order_desserts SET orders_orders_id = ? WHERE order_desserts_id = ?";
    private static final String UPDATE_CAKE = "UPDATE order_desserts SET cakes_cakes_id = ? WHERE order_desserts_id = ?";
    private static final String UPDATE_DESSERT = "UPDATE order_desserts SET desserts_dessert_id = ? WHERE order_desserts_id = ?";


    @Override
    public List<OrderDessert> findAll() throws DaoException  {
        List<OrderDessert> orderDesserts = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_ORDER_DESSERTS);
            orderDesserts = createOrderDessertsList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);
        }

        return orderDesserts;
    }

    @Override
    public boolean delete(OrderDessert orderDessert) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE_ORDER_DESSERT);
            statement.setInt(1, orderDessert.getOrderDessertId());
            int flag = statement.executeUpdate();
            if (flag == 0) return false;
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        return true;
    }

    @Override
    public boolean deleteByOrder(Order order) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE_ORDER_DESSERT);
            statement.setInt(1, order.getOrderId());
            int flag = statement.executeUpdate();
            if (flag == 0) return false;
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        return true;
    }

    @Override
    public boolean create(OrderDessert orderDessert) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;

        try {
            if (orderDessert.getDessert() != null){
                statement = connection.prepareStatement(CREATE_ODER_DESSERT);
                statement.setInt(1, orderDessert.getDessertCount());
                statement.setInt(2, orderDessert.getOrder().getOrderId());
                statement.setInt(3, orderDessert.getDessert().getDessertId());
            }
            else {
                if (orderDessert.getCake() != null){
                    statement = connection.prepareStatement(CREATE_ODER_CAKE);
                    statement.setInt(1, orderDessert.getDessertCount());
                    statement.setInt(2, orderDessert.getOrder().getOrderId());
                   statement.setInt(3, orderDessert.getCake().getCakeId());
                }
                else {
                    return false;
                }
            }
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
    public List<OrderDessert> findById(int orderDessertId) throws DaoException {
        List<OrderDessert> orderDesserts = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setInt(1, orderDessertId);
            ResultSet resultSet = statement.executeQuery();
            orderDesserts = createOrderDessertsList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);
        }

        return orderDesserts;
    }

    @Override
    public List<OrderDessert> findByCount(int count) throws DaoException {
        List<OrderDessert> orderDesserts = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(SELECT_BY_COUNT);
            statement.setInt(1, count);
            ResultSet resultSet = statement.executeQuery();
            orderDesserts = createOrderDessertsList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);
        }

        return orderDesserts;
    }

    @Override
    public List<OrderDessert> findByOrder(Order order) throws DaoException {
        List<OrderDessert> orderDesserts = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(SELECT_BY_ORDER);
            statement.setInt(1, order.getOrderId());
            ResultSet resultSet = statement.executeQuery();
            orderDesserts = createOrderDessertsList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);
        }

        return orderDesserts;
    }

    @Override
    public List<OrderDessert> findByCake(Cake cake) throws DaoException {
        List<OrderDessert> orderDesserts = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(SELECT_BY_CAKE);
            statement.setInt(1, cake.getCakeId());
            ResultSet resultSet = statement.executeQuery();
            orderDesserts = createOrderDessertsList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);
        }

        return orderDesserts;
    }

    @Override
    public List<OrderDessert> findByDessert(Dessert dessert) throws DaoException {
        List<OrderDessert> orderDesserts = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(SELECT_BY_DESSERT);
            statement.setInt(1, dessert.getDessertId());
            ResultSet resultSet = statement.executeQuery();
            orderDesserts = createOrderDessertsList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);
        }

        return orderDesserts;
    }

    private List<OrderDessert> createOrderDessertsList(ResultSet resultSet) throws DaoException {
        List<OrderDessert> orderDesserts = new ArrayList<>();

        try {
            while (resultSet.next()) {
                int orderDessertId = resultSet.getInt(1);
                int orderDessertCount = resultSet.getInt(2);
                int orderId = resultSet.getInt(3);

                if (resultSet.getString(4) != null){
                    int dessertId = resultSet.getInt(4);
                    String dessertDescription = resultSet.getString(5);
                    double dessertCost = resultSet.getDouble(6);
                    String dessertName = resultSet.getString(7);
                    String dessertType = resultSet.getString(8);
                    Dessert dessert = new Dessert(dessertId, dessertDescription, dessertCost, dessertName, dessertType);
                    orderDesserts.add(new OrderDessert(orderDessertId, orderDessertCount, new Order(orderId), dessert));
                }
                else {
                    if (resultSet.getString(9) != null){
                        int cakeId = resultSet.getInt(9);
                        double cakeCost = resultSet.getDouble(10);
                        String cakeDesignDescription = resultSet.getString(11);
                        double cakeWeight = resultSet.getDouble(12);
                        Stuffing stuffing = new Stuffing(resultSet.getInt(13), resultSet.getString(14), resultSet.getString(15));
                        Cake cake = new Cake(cakeId, cakeCost, cakeDesignDescription, cakeWeight, stuffing);
                        orderDesserts.add(new OrderDessert(orderDessertId, orderDessertCount, new Order(orderId), cake));
                    }
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        return orderDesserts;
    }

    @Override
    public boolean updateCount(int count, int orderDessertId) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;

        try{
            statement = connection.prepareStatement(UPDATE_COUNT);
            statement.setInt(1, count);
            statement.setInt(2, orderDessertId);

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
    public boolean updateOrder(Order order, int orderDessertId) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;

        try{
            statement = connection.prepareStatement(UPDATE_ORDER);
            statement.setInt(1, order.getOrderId());
            statement.setInt(2, orderDessertId);

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
    public boolean updateCake(Cake cake, int orderDessertId) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;

        try{
            statement = connection.prepareStatement(UPDATE_CAKE);
            statement.setInt(1, cake.getCakeId());
            statement.setInt(2, orderDessertId);

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
    public boolean updateDessert(Dessert dessert, int orderDessertId) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;

        try{
            statement = connection.prepareStatement(UPDATE_DESSERT);
            statement.setInt(1, dessert.getDessertId());
            statement.setInt(2, orderDessertId);

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
