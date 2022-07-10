package com.finalwebproject.pastrtyshop.dao.impl;

import com.finalwebproject.pastrtyshop.dao.ClientDao;
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

public class ClientDaoImpl implements ClientDao {
    private static final Logger logger = LogManager.getLogger();

    private static final String SELECT_ALL_CLIENTS = "SELECT clients.client_id, clients.login, password, clients.first_name," +
            " clients.second_name, clients.email, clients.phone_number, clients.status, clients.role, clients.discounts_discounts_id, " +
            "discounts.value, discounts.discount_name FROM clients\n" +
            "LEFT JOIN discounts ON  clients.discounts_discounts_id = discounts.discounts_id";
    private static final String CREATE_CLIENT = "INSERT INTO clients (client_id, login, password, first_name, second_name, email, phone_number, status, role)\n" +
            "VAlUES (NUll, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_CLIENT = "DELETE FROM clients WHERE client_id = ?";
    private static final String SELECT_BY_ID = "SELECT clients.client_id, clients.login, password, clients.first_name," +
            " clients.second_name, clients.email, clients.phone_number, clients.status, clients.role, clients.discounts_discounts_id, " +
            "discounts.value, discounts.discount_name FROM clients\n" +
            "LEFT JOIN discounts ON  clients.discounts_discounts_id = discounts.discounts_id WHERE client_id = ?";
    private static final String SELECT_BY_LOGIN = "SELECT clients.client_id, clients.login, password, clients.first_name," +
            " clients.second_name, clients.email, clients.phone_number, clients.status, clients.role, clients.discounts_discounts_id, " +
            "discounts.value, discounts.discount_name FROM clients\n" +
            "LEFT JOIN discounts ON  clients.discounts_discounts_id = discounts.discounts_id WHERE login = ?";
    private static final String SELECT_BY_FIRST_NAME = "SELECT clients.client_id, clients.login, password, clients.first_name," +
            " clients.second_name, clients.email, clients.phone_number, clients.status, clients.role, clients.discounts_discounts_id, " +
            "discounts.value, discounts.discount_name FROM clients\n" +
            "LEFT JOIN discounts ON  clients.discounts_discounts_id = discounts.discounts_id WHERE first_Name = ?";
    private static final String SELECT_BY_SECOND_NAME = "SELECT clients.client_id, clients.login, password, clients.first_name," +
            " clients.second_name, clients.email, clients.phone_number, clients.status, clients.role, clients.discounts_discounts_id, " +
            "discounts.value, discounts.discount_name FROM clients\n" +
            "LEFT JOIN discounts ON  clients.discounts_discounts_id = discounts.discounts_id WHERE second_name = ?";
    private static final String SELECT_BY_EMAIL = "SELECT clients.client_id, clients.login, password, clients.first_name," +
            " clients.second_name, clients.email, clients.phone_number, clients.status, clients.role, clients.discounts_discounts_id, " +
            "discounts.value, discounts.discount_name FROM clients\n" +
            "LEFT JOIN discounts ON  clients.discounts_discounts_id = discounts.discounts_id WHERE email = ?";
    private static final String SELECT_BY_PHONE_NUMBER = "SELECT clients.client_id, clients.login, password, clients.first_name," +
            " clients.second_name, clients.email, clients.phone_number, clients.status, clients.role, clients.discounts_discounts_id, " +
            "discounts.value, discounts.discount_name FROM clients\n" +
            "LEFT JOIN discounts ON  clients.discounts_discounts_id = discounts.discounts_id WHERE phone_number = ?";
    private static final String SELECT_BY_ROLE = "SELECT clients.client_id, clients.login, password, clients.first_name," +
            " clients.second_name, clients.email, clients.phone_number, clients.status, clients.role, clients.discounts_discounts_id, " +
            "discounts.value, discounts.discount_name FROM clients\n" +
            "LEFT JOIN discounts ON  clients.discounts_discounts_id = discounts.discounts_id WHERE role = ?";
    private static final String SELECT_BY_STATUS = "SELECT clients.client_id, clients.login, password, clients.first_name," +
            " clients.second_name, clients.email, clients.phone_number, clients.status, clients.role, clients.discounts_discounts_id, " +
            "discounts.value, discounts.discount_name FROM clients\n" +
            "LEFT JOIN discounts ON  clients.discounts_discounts_id = discounts.discounts_id WHERE staatus = ?";
    private static final String SELECT_BY_LOGIN_AND_PASSWORD = "SELECT clients.client_id, clients.login, password, clients.first_name," +
            " clients.second_name, clients.email, clients.phone_number, clients.status, clients.role, clients.discounts_discounts_id, " +
            "discounts.value, discounts.discount_name FROM clients\n" +
            "LEFT JOIN discounts ON  clients.discounts_discounts_id = discounts.discounts_id WHERE login = ? AND password = ?";
    private static final String UPDATE_LOGIN = "UPDATE clients SET login = ? WHERE client_id = ?";
    private static final String UPDATE_PASSWORD = "UPDATE clients SET password = ? WHERE client_id = ?";
    private static final String UPDATE_FIRST_NAME = "UPDATE clients SET first_name = ? WHERE client_id = ?";
    private static final String UPDATE_SECOND_NAME = "UPDATE clients SET second_name = ? WHERE client_id = ?";
    private static final String UPDATE_EMAIL = "UPDATE clients SET email = ? WHERE client_id = ?";
    private static final String UPDATE_PHONE_NUMBER = "UPDATE clients SET phone_number = ? WHERE client_id = ?";
    private static final String UPDATE_STATUS = "UPDATE clients SET status = ? WHERE client_id = ?";
    private static final String UPDATE_ROLE = "UPDATE clients SET role = ? WHERE client_id = ?";
    private static final String UPDATE_DISCOUNT = "UPDATE clients SET discounts_discounts_id = ? WHERE client_id = ?";
    private static final String UPDATE_DISCOUNT_NULL = "UPDATE clients SET discounts_discounts_id = NULL WHERE client_id = ?";

    @Override
    public List<Client> findAll() throws DaoException {
        List<Client> clients = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        Statement statement = null;
        try{
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_CLIENTS);
            clients = createClientList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);

        }

        return clients;
    }

    @Override
    public boolean delete(Client client) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(DELETE_CLIENT);
            statement.setInt(1, client.getClientId());
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
    public boolean create(Client client) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(CREATE_CLIENT);
            statement.setString(1, client.getLogin());
            statement.setString(2, client.getPassword());
            statement.setString(3, client.getFirstName());
            statement.setString(4, client.getSecondName());
            statement.setString(5, client.getEmail());
            statement.setString(6, client.getPhoneNumber());
            statement.setString(7, client.getStatus().toString());
            statement.setString(8, client.getRole().toString());
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
    public List<Client> findById(int clientId) throws DaoException {
        List<Client> clients = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setInt(1, clientId);
            ResultSet resultSet = statement.executeQuery();
            clients = createClientList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);

        }

        return clients;
    }

    @Override
    public List<Client> findByLogin(String login) throws DaoException {
        List<Client> clients = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SELECT_BY_LOGIN);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            clients = createClientList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);

        }

        return clients;
    }

    @Override
    public List<Client> findByFirstName(String firstName) throws DaoException {
        List<Client> clients = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SELECT_BY_FIRST_NAME);
            statement.setString(1, firstName);
            ResultSet resultSet = statement.executeQuery();
            clients = createClientList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);

        }

        return clients;
    }

    @Override
    public List<Client> findBySecondName(String secondName) throws DaoException {
        List<Client> clients = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SELECT_BY_SECOND_NAME);
            statement.setString(1, secondName);
            ResultSet resultSet = statement.executeQuery();
            clients = createClientList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);

        }

        return clients;
    }

    @Override
    public List<Client> findByEmail(String email) throws DaoException {
        List<Client> clients = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SELECT_BY_EMAIL);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            clients = createClientList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);

        }

        return clients;
    }

    @Override
    public List<Client> findByPhoneNumber(String phoneNumber) throws DaoException {
        List<Client> clients = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SELECT_BY_PHONE_NUMBER);
            statement.setString(1, phoneNumber);
            ResultSet resultSet = statement.executeQuery();
            clients = createClientList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);

        }

        return clients;
    }

    @Override
    public List<Client> findByRole(ClientRole role) throws DaoException {
        List<Client> clients = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SELECT_BY_ROLE);
            statement.setString(1, role.toString());
            ResultSet resultSet = statement.executeQuery();
            clients = createClientList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);

        }

        return clients;
    }

    @Override
    public List<Client> findByStatus(ClientStatus status) throws DaoException {
        List<Client> clients = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SELECT_BY_STATUS);
            statement.setString(1, status.toString());
            ResultSet resultSet = statement.executeQuery();
            clients = createClientList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);

        }

        return clients;
    }

    @Override
    public List<Client> findBYLoginAndPassword(String login, String password) throws DaoException {
        List<Client> clients = new ArrayList<>();

        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SELECT_BY_LOGIN_AND_PASSWORD);
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            clients = createClientList(resultSet);
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        finally {
            closeStatement(statement);
            closeConnection(connection);

        }

        return clients;
    }

    private List<Client> createClientList(ResultSet resultSet) throws DaoException {
        List<Client> clients = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int Id = resultSet.getInt(1);
                String ClientLogin = resultSet.getString(2);
                String password = resultSet.getString(3);
                String firstName = resultSet.getString(4);
                String secondName = resultSet.getString(5);
                String email = resultSet.getString(6);
                String phoneNumber = resultSet.getString(7);
                ClientStatus status;
                if (resultSet.getString(8).equals("ONLINE")) {
                    status = ClientStatus.ONLINE;
                } else {
                    if (resultSet.getString(8).equals("OFFLINE")) {
                        status = ClientStatus.OFFLINE;
                    } else {
                        status = ClientStatus.BANNED;
                    }
                }
                ClientRole role;
                if (resultSet.getString(9).equals("ADMIN")) {
                    role = ClientRole.ADMIN;
                } else {
                    role = ClientRole.CLIENT;
                }
                ClientDiscount discount = new ClientDiscount(resultSet.getInt(10), resultSet.getDouble(11), resultSet.getString(12));
                if (discount.getValue() == 0){
                    discount.setValue(1);
                    discount.setNameOfDiscount("Нет скидки");
                }
                clients.add(new Client(Id, ClientLogin, password, firstName, secondName, email, phoneNumber, role, status, discount));
            }
        }
        catch (SQLException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new DaoException(e);
        }
        return clients;
    }

    @Override
    public boolean updateLogin(String login, int clientId) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE_LOGIN);
            statement.setString(1, login);
            statement.setInt(2, clientId);
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
    public boolean updatePassword(String password, int clientId) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE_PASSWORD);
            statement.setString(1, password);
            statement.setInt(2, clientId);
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
    public boolean updateFirstName(String firstName, int clientId) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE_FIRST_NAME);
            statement.setString(1, firstName);
            statement.setInt(2, clientId);
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
    public boolean updateSecondName(String secondName, int clientId) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE_SECOND_NAME);
            statement.setString(1, secondName);
            statement.setInt(2, clientId);
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
    public boolean updateEmail(String email, int clientId) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE_EMAIL);
            statement.setString(1, email);
            statement.setInt(2, clientId);
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
    public boolean updatePhoneNumber(String phoneNumber, int clientId) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE_PHONE_NUMBER);
            statement.setString(1, phoneNumber);
            statement.setInt(2, clientId);
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
    public boolean updateRole(ClientRole role, int clientId) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE_ROLE);
            statement.setString(1, role.toString());
            statement.setInt(2, clientId);
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
    public boolean updateStatus(ClientStatus status, int clientId) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE_STATUS);
            statement.setString(1, status.toString());
            statement.setInt(2, clientId);
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
    public boolean updateDiscount(ClientDiscount discount, int clientId) throws DaoException {
        CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        try{
            if (discount != null) {
                statement = connection.prepareStatement(UPDATE_DISCOUNT);
                statement.setInt(1, discount.getDiscountId());
                statement.setInt(2, clientId);
            }
            else {
                statement = connection.prepareStatement(UPDATE_DISCOUNT_NULL);
                statement.setInt(1, clientId);
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


}
