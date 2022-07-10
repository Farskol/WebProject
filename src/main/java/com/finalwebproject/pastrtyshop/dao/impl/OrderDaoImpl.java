package com.finalwebproject.pastrtyshop.dao.impl;

import com.finalwebproject.pastrtyshop.dao.ClientDao;
import com.finalwebproject.pastrtyshop.dao.OrderDao;
import com.finalwebproject.pastrtyshop.entity.*;
import com.finalwebproject.pastrtyshop.exception.DaoException;
import com.finalwebproject.pastrtyshop.pool.CustomConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    private static final Logger logger = LogManager.getLogger();

    private static final String SELECT_ALL_ORDERS = "SELECT orders.orders_id, orders.order_date, orders.total_price, " +
            "orders.orders_status, orders.in_what_date, orders.first_name, orders.phone_number, orders.second_name, orders.clients_client_id, " +
            "clients.login, clients.password, clients.first_name, clients.second_name, clients.email, clients.phone_number, clients.status, " +
            "clients.role, clients.discounts_discounts_id, discounts.value, discounts.discount_name FROM orders " +
            "LEFT JOIN clients ON orders.clients_client_id = clients.client_id " +
            "LEFT JOIN discounts ON  clients.discounts_discounts_id = discounts.discounts_id";
    private static final String DELETE_ORDER = "DELETE FROM orders WHERE orders_id = ?";
    private static final String CREATE_ORDER_WITH_CLIENT = "INSERT INTO orders (orders_id, order_date, total_price, orders_status, in_what_date, " +
            "first_name, phone_number, second_name, clients_client_id)\n" +
            "VAlUES (NULL, NOW(), ?, ?, ?, (SELECT first_name FROM clients WHERE client_id = ?), " +
            "(SELECT phone_number FROM clients WHERE client_id = ?), (SELECT second_name FROM clients WHERE client_id = ?), ?);\n";
    private static final String CREATE_ORDER_WITHOUT_CLIENT = "INSERT INTO orders (orders_id, order_date, total_price, orders_status, in_what_date, " +
            "first_name, phone_number, second_name, clients_client_id)\n" +
            "VAlUES (NULL, NOW(), ?, ?, ?, ?, ?, ?, NULL);\n";
    private static final String SELECT_BY_ID = "SELECT orders.orders_id, orders.order_date, orders.total_price, " +
            "orders.orders_status, orders.in_what_date, orders.first_name, orders.phone_number, orders.second_name, orders.clients_client_id, " +
            "clients.login, clients.password, clients.first_name, clients.second_name, clients.email, clients.phone_number, clients.status, " +
            "clients.role, clients.discounts_discounts_id, discounts.value, discounts.discount_name FROM orders " +
            "LEFT JOIN clients ON orders.clients_client_id = clients.client_id " +
            "LEFT JOIN discounts ON  clients.discounts_discounts_id = discounts.discounts_id WHERE orders.orders_id = ?";
    private static final String SELECT_BY_ORDER_DATE = "SELECT orders.orders_id, orders.order_date, orders.total_price, " +
            "orders.orders_status, orders.in_what_date, orders.first_name, orders.phone_number, orders.second_name, orders.clients_client_id, " +
            "clients.login, clients.password, clients.first_name, clients.second_name, clients.email, clients.phone_number, clients.status, " +
            "clients.role, clients.discounts_discounts_id, discounts.value, discounts.discount_name FROM orders " +
            "LEFT JOIN clients ON orders.clients_client_id = clients.client_id " +
            "LEFT JOIN discounts ON  clients.discounts_discounts_id = discounts.discounts_id WHERE orders.order_date = ?";
    private static final String SELECT_BY_TOTAL_PRICE = "SELECT orders.orders_id, orders.order_date, orders.total_price, " +
            "orders.orders_status, orders.in_what_date, orders.first_name, orders.phone_number, orders.second_name, orders.clients_client_id, " +
            "clients.login, clients.password, clients.first_name, clients.second_name, clients.email, clients.phone_number, clients.status, " +
            "clients.role, clients.discounts_discounts_id, discounts.value, discounts.discount_name FROM orders " +
            "LEFT JOIN clients ON orders.clients_client_id = clients.client_id " +
            "LEFT JOIN discounts ON  clients.discounts_discounts_id = discounts.discounts_id WHERE orders.total_price = ?";
    private static final String SELECT_BY_CLIENT = "SELECT orders.orders_id, orders.order_date, orders.total_price, " +
            "orders.orders_status, orders.in_what_date, orders.first_name, orders.phone_number, orders.second_name, orders.clients_client_id, " +
            "clients.login, clients.password, clients.first_name, clients.second_name, clients.email, clients.phone_number, clients.status, " +
            "clients.role, clients.discounts_discounts_id, discounts.value, discounts.discount_name FROM orders " +
            "LEFT JOIN clients ON orders.clients_client_id = clients.client_id " +
            "LEFT JOIN discounts ON  clients.discounts_discounts_id = discounts.discounts_id WHERE orders.clients_client_id = ?";
    private static final String SELECT_BY_ORDER_STATUS = "SELECT orders.orders_id, orders.order_date, orders.total_price, " +
            "orders.orders_status, orders.in_what_date, orders.first_name, orders.phone_number, orders.second_name, orders.clients_client_id, " +
            "clients.login, clients.password, clients.first_name, clients.second_name, clients.email, clients.phone_number, clients.status, " +
            "clients.role, clients.discounts_discounts_id, discounts.value, discounts.discount_name FROM orders " +
            "LEFT JOIN clients ON orders.clients_client_id = clients.client_id " +
            "LEFT JOIN discounts ON  clients.discounts_discounts_id = discounts.discounts_id WHERE orders.orders_status = ?";
    private static final String SELECT_BY_IN_WHAT_DATE = "SELECT orders.orders_id, orders.order_date, orders.total_price, " +
            "orders.orders_status, orders.in_what_date, orders.first_name, orders.phone_number, orders.second_name, orders.clients_client_id, " +
            "clients.login, clients.password, clients.first_name, clients.second_name, clients.email, clients.phone_number, clients.status, " +
            "clients.role, clients.discounts_discounts_id, discounts.value, discounts.discount_name FROM orders " +
            "LEFT JOIN clients ON orders.clients_client_id = clients.client_id " +
            "LEFT JOIN discounts ON  clients.discounts_discounts_id = discounts.discounts_id WHERE orders.in_what_date = ?";
    private static final String SELECT_BY_FIRST_NAME = "SELECT orders.orders_id, orders.order_date, orders.total_price, " +
            "orders.orders_status, orders.in_what_date, orders.first_name, orders.phone_number, orders.second_name, orders.clients_client_id, " +
            "clients.login, clients.password, clients.first_name, clients.second_name, clients.email, clients.phone_number, clients.status, " +
            "clients.role, clients.discounts_discounts_id, discounts.value, discounts.discount_name FROM orders " +
            "LEFT JOIN clients ON orders.clients_client_id = clients.client_id " +
            "LEFT JOIN discounts ON  clients.discounts_discounts_id = discounts.discounts_id WHERE orders.first_name = ?";
    private static final String SELECT_BY_SECOND_NAME = "SELECT orders.orders_id, orders.order_date, orders.total_price, " +
            "orders.orders_status, orders.in_what_date, orders.first_name, orders.phone_number, orders.second_name, orders.clients_client_id, " +
            "clients.login, clients.password, clients.first_name, clients.second_name, clients.email, clients.phone_number, clients.status, " +
            "clients.role, clients.discounts_discounts_id, discounts.value, discounts.discount_name FROM orders " +
            "LEFT JOIN clients ON orders.clients_client_id = clients.client_id " +
            "LEFT JOIN discounts ON  clients.discounts_discounts_id = discounts.discounts_id WHERE orders.second_name = ?";
    private static final String SELECT_BY_PHONE_NUMBER = "SELECT orders.orders_id, orders.order_date, orders.total_price, " +
            "orders.orders_status, orders.in_what_date, orders.first_name, orders.phone_number, orders.second_name, orders.clients_client_id, " +
            "clients.login, clients.password, clients.first_name, clients.second_name, clients.email, clients.phone_number, clients.status, " +
            "clients.role, clients.discounts_discounts_id, discounts.value, discounts.discount_name FROM orders " +
            "LEFT JOIN clients ON orders.clients_client_id = clients.client_id " +
            "LEFT JOIN discounts ON  clients.discounts_discounts_id = discounts.discounts_id WHERE orders.phone_number = ?";
    private static final String UPDATE_ORDER_DATE = "UPDATE orders SET order_date = ? WHERE orders_id = ?";
    private static final String UPDATE_TOTAL_PRICE = "UPDATE orders SET total_price = ? WHERE orders_id = ?";
    private static final String UPDATE_CLIENT = "UPDATE orders SET clients_client_id = ?, first_name = ?, second_name = ?, phone_number = ? " +
            "WHERE orders_id = ?";
    private static final String UPDATE_CLIENT_NULL = "UPDATE orders SET clients_client_id = NULL";
    private static final String UPDATE_ORDER_STATUS = "UPDATE orders SET orders_status = ? WHERE orders_id = ?";
    private static final String UPDATE_IN_WHAT_DATE = "UPDATE orders SET in_what_date = ? WHERE orders_id = ?";
    private static final String UPDATE_FIRST_NAME = "UPDATE orders SET first_name = ? WHERE orders_id = ?";
    private static final String UPDATE_SECOND_NAME = "UPDATE orders SET second_name = ? WHERE orders_id = ?";
    private static final String UPDATE_PHONE_NUMBER = "UPDATE orders SET phone_number = ? WHERE orders_id = ?";

    @Override
    public List<Order> findAll() throws DaoException {
        List<Order> orders = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        Statement statement = null;
        try{
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_ORDERS);
            orders = createOrderList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);

        }

        return orders;
    }

    @Override
    public boolean delete(Order order) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(DELETE_ORDER);
            statement.setInt(1, order.getOrderId());
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
    public boolean create(Order order) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            if (order.getClient() != null) {
                statement = connection.prepareStatement(CREATE_ORDER_WITH_CLIENT);
                statement.setDouble(1, order.getTotalPrice());
                statement.setString(2, order.getOrderStatus().toString());
                statement.setDate(3, new java.sql.Date(order.getInWhatDate().getTime()));
                statement.setInt(4, order.getClient().getClientId());
                statement.setInt(5, order.getClient().getClientId());
                statement.setInt(6, order.getClient().getClientId());
                statement.setInt(7, order.getClient().getClientId());
                int flag = statement.executeUpdate();
                if (flag == 0) {
                    return false;
                }
            }
            else {
                statement = connection.prepareStatement(CREATE_ORDER_WITHOUT_CLIENT);
                statement.setDouble(1, order.getTotalPrice());
                statement.setString(2, order.getOrderStatus().toString());
                statement.setDate(3, new java.sql.Date(order.getInWhatDate().getTime()));
                statement.setString(4, order.getFirstName());
                statement.setString(5, order.getPhoneNumber());
                statement.setString(6, order.getSecondName());
                int flag = statement.executeUpdate();
                if (flag == 0) {
                    return false;
                }
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
    public int createWithId(Order order) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        int lastId = 0;
        try{
            if (order.getClient() != null) {
                statement = connection.prepareStatement(CREATE_ORDER_WITH_CLIENT);
                statement.setDouble(1, order.getTotalPrice());
                statement.setString(2, order.getOrderStatus().toString());
                statement.setDate(3, new java.sql.Date(order.getInWhatDate().getTime()));
                statement.setInt(4, order.getClient().getClientId());
                statement.setInt(5, order.getClient().getClientId());
                statement.setInt(6, order.getClient().getClientId());
                statement.setInt(7, order.getClient().getClientId());
                statement.executeUpdate();

                statement = connection.prepareStatement("SELECT last_insert_id()");
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                lastId = resultSet.getInt(1);
            }
            else {
                statement = connection.prepareStatement(CREATE_ORDER_WITHOUT_CLIENT);
                statement.setDouble(1, order.getTotalPrice());
                statement.setString(2, order.getOrderStatus().toString());
                statement.setDate(3, new java.sql.Date(order.getInWhatDate().getTime()));
                statement.setString(4, order.getFirstName());
                statement.setString(5, order.getPhoneNumber());
                statement.setString(6, order.getSecondName());
                statement.executeUpdate();

                statement = connection.prepareStatement("SELECT last_insert_id()");
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                lastId = resultSet.getInt(1);
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

        return lastId;
    }

    @Override
    public List<Order> findById(int orderId) throws DaoException{
        List<Order> orders = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            orders = createOrderList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);

        }

        return orders;
    }

    @Override
    public List<Order> findByOrderDate(Date orderDate) throws DaoException {
        List<Order> orders = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SELECT_BY_ORDER_DATE);
            statement.setDate(1, new java.sql.Date(orderDate.getTime()));
            ResultSet resultSet = statement.executeQuery();
            orders = createOrderList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);

        }

        return orders;
    }

    @Override
    public List<Order> findByTotalPrice(double totalPrice) throws DaoException {
        List<Order> orders = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SELECT_BY_TOTAL_PRICE);
            statement.setDouble(1, totalPrice);
            ResultSet resultSet = statement.executeQuery();
            orders = createOrderList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);

        }

        return orders;
    }

    @Override
    public List<Order> findByClient(Client client) throws DaoException {
        List<Order> orders = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SELECT_BY_CLIENT);
            statement.setInt(1, client.getClientId());
            ResultSet resultSet = statement.executeQuery();
            orders = createOrderList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);

        }

        return orders;
    }

    @Override
    public List<Order> findByOrderStatus(OrderStatus orderStatus) throws DaoException {
        List<Order> orders = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SELECT_BY_ORDER_STATUS);
            statement.setString(1, orderStatus.toString());
            ResultSet resultSet = statement.executeQuery();
            orders = createOrderList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);

        }

        return orders;
    }

    @Override
    public List<Order> findByInWhatDate(Date inWhatDate) throws DaoException {
        List<Order> orders = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SELECT_BY_IN_WHAT_DATE);
            statement.setDate(1, new java.sql.Date(inWhatDate.getTime()));
            ResultSet resultSet = statement.executeQuery();
            orders = createOrderList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);

        }

        return orders;
    }

    @Override
    public List<Order> findByFirstName(String firstName) throws DaoException {
        List<Order> orders = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SELECT_BY_FIRST_NAME);
            statement.setString(1, firstName);
            ResultSet resultSet = statement.executeQuery();
            orders = createOrderList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);

        }

        return orders;
    }

    @Override
    public List<Order> findBySecondName(String secondName) throws DaoException {
        List<Order> orders = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SELECT_BY_SECOND_NAME);
            statement.setString(1, secondName);
            ResultSet resultSet = statement.executeQuery();
            orders = createOrderList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);

        }

        return orders;
    }

    @Override
    public List<Order> findByPhoneNumber(String phoneNumber) throws DaoException {
        List<Order> orders = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SELECT_BY_PHONE_NUMBER);
            statement.setString(1,phoneNumber);
            ResultSet resultSet = statement.executeQuery();
            orders = createOrderList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);

        }

        return orders;
    }

    private List<Order> createOrderList(ResultSet resultSet) throws DaoException {
        List<Order> orders = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                Date orderDate = resultSet.getDate(2);
                Double totalPrice = resultSet.getDouble(3);

                OrderStatus orderStatus;
                if (resultSet.getString(4).equals("PROCESSED")){
                    orderStatus = OrderStatus.PROCESSED;
                }
                else {
                    if (resultSet.getString(4).equals("PREPARING")){
                        orderStatus = OrderStatus.PREPARING;
                    }
                    else {
                        orderStatus = OrderStatus.READY;
                    }
                }

                Date inWhatDate = resultSet.getDate(5);
                String firstName = resultSet.getString(6);
                String phoneNumber = resultSet.getString(7);
                String secondName = resultSet.getString(8);

                Client client = null;
                if (resultSet.getString(9) != null) {
                    ClientStatus status;
                    if (resultSet.getString(16).equals("ONLINE")) {
                        status = ClientStatus.ONLINE;
                    } else {
                        if (resultSet.getString(16).equals("OFFLINE")) {
                            status = ClientStatus.OFFLINE;
                        } else {
                            status = ClientStatus.BANNED;
                        }
                    }
                    ClientRole role;
                    if (resultSet.getString(17).equals("ADMIN")) {
                        role = ClientRole.ADMIN;
                    } else {
                        role = ClientRole.CLIENT;
                    }
                    ClientDiscount discount = new ClientDiscount(resultSet.getInt(18), resultSet.getDouble(19), resultSet.getString(20));
                    if (discount.getValue() == 0){
                        discount.setValue(1);
                        discount.setNameOfDiscount("Нет скидки");
                    }
                    client = new Client(resultSet.getInt(9), resultSet.getString(10), resultSet.getString(11), resultSet.getString(12), resultSet.getString(13), resultSet.getString(14), resultSet.getString(15), role, status, discount);
                }
                orders.add(new Order(id, orderDate, totalPrice, client, orderStatus, inWhatDate, firstName, secondName, phoneNumber));
            }
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        return orders;
    }

    @Override
    public boolean updateOrderDate(Date orderDate, int orderId) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE_ORDER_DATE);
            statement.setDate(1, new java.sql.Date(orderDate.getTime()));
            statement.setInt(2, orderId);
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
    public boolean updateTotalPrice(double totalPrice, int orderId) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE_TOTAL_PRICE);
            statement.setDouble(1, totalPrice);
            statement.setInt(2, orderId);
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
    public boolean updateClient(Client client, int orderId) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            if (client != null) {
                statement = connection.prepareStatement(UPDATE_CLIENT);
                statement.setInt(1, client.getClientId());
                statement.setString(2, client.getFirstName());
                statement.setString(3, client.getSecondName());
                statement.setString(4, client.getPhoneNumber());
                statement.setInt(5, orderId);
            }
            else {
                statement = connection.prepareStatement(UPDATE_CLIENT_NULL);
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
    public boolean updateOrderStatus(OrderStatus orderStatus, int orderId) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE_ORDER_STATUS);
            statement.setString(1, orderStatus.toString());
            statement.setInt(2, orderId);
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
    public boolean updateInWhatDate(Date inWhatDate, int orderId) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE_IN_WHAT_DATE);
            statement.setDate(1, new java.sql.Date(inWhatDate.getTime()));
            statement.setInt(2, orderId);
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
    public boolean updateFirstName(String firstName, int orderId) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE_FIRST_NAME);
            statement.setString(1, firstName);
            statement.setInt(2, orderId);
            int flag = statement.executeUpdate();
            Order order = findById(orderId).get(0);
            if (order != null){
                ClientDao clientDao = new ClientDaoImpl();
                clientDao.updateFirstName(firstName,order.getClient().getClientId());
            }
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
    public boolean updateSecondName(String secondName, int orderId) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE_SECOND_NAME);
            statement.setString(1, secondName);
            statement.setInt(2, orderId);
            int flag = statement.executeUpdate();
            Order order = findById(orderId).get(0);
            if (order != null){
                ClientDao clientDao = new ClientDaoImpl();
                clientDao.updateSecondName(secondName,order.getClient().getClientId());
            }
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
    public boolean updatePhoneNumber(String phoneNumber, int orderId) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE_PHONE_NUMBER);
            statement.setString(1, phoneNumber);
            statement.setInt(2, orderId);
            int flag = statement.executeUpdate();
            Order order = findById(orderId).get(0);
            if (order != null){
                ClientDao clientDao = new ClientDaoImpl();
                clientDao.updateSecondName(phoneNumber,order.getClient().getClientId());
            }
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
