package com.finalwebproject.pastrtyshop.service.impl;

import com.finalwebproject.pastrtyshop.dao.ClientDao;
import com.finalwebproject.pastrtyshop.dao.DiscountDao;
import com.finalwebproject.pastrtyshop.dao.OrderDao;
import com.finalwebproject.pastrtyshop.dao.OrderDessertDao;
import com.finalwebproject.pastrtyshop.dao.impl.ClientDaoImpl;
import com.finalwebproject.pastrtyshop.dao.impl.DiscountDaoImpl;
import com.finalwebproject.pastrtyshop.dao.impl.OrderDaoImpl;
import com.finalwebproject.pastrtyshop.dao.impl.OrderDessertDaoImpl;
import com.finalwebproject.pastrtyshop.entity.*;
import com.finalwebproject.pastrtyshop.exception.DaoException;
import com.finalwebproject.pastrtyshop.exception.ServiceException;
import com.finalwebproject.pastrtyshop.service.ClientService;
import com.finalwebproject.pastrtyshop.validator.Validator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.finalwebproject.pastrtyshop.controller.Parameters.*;

public class ClientServiceImpl implements ClientService {

    @Override
    public boolean clientRegistration(Map<String, String> clientData) throws ServiceException{
        ClientDao clientDao = new ClientDaoImpl();
        try{
            Validator validator = new Validator();
            boolean flag = true;
            if (!validator.checkRegistration(clientData)){
                return false;
            }
            if(!clientDao.findByLogin(clientData.get(LOGIN)).isEmpty()){
                clientData.put(LOGIN,NOT_UNIQ_LOGIN);
                flag = false;
            }
            if (!clientDao.findByEmail(clientData.get(EMAIL)).isEmpty()){
                clientData.put(EMAIL, NOT_UNIQ_EMAIL);
                flag = false;
            }
            if (!clientDao.findByPhoneNumber(PHONE_NUMBER).isEmpty()){
                clientData.put(PHONE_NUMBER, NOT_UNIQ_PHONE_NUMBER);
                flag = false;
            }
            if (!flag){
                return false;
            }

            Client client = new Client(clientData.get(LOGIN), clientData.get(PASSWORD), clientData.get(FIRST_NAME),
                    clientData.get(SECOND_NAME), clientData.get(EMAIL), clientData.get(PHONE_NUMBER), ClientRole.CLIENT, ClientStatus.OFFLINE);

            if (!clientDao.create(client)){
                return false;
            }
            return flag;
        }
        catch (DaoException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Client> login(String login, String password) throws ServiceException{
        ClientDao clientDao = new ClientDaoImpl();
        Optional<Client> client = Optional.empty();
        try {
            List<Client> arrayList = clientDao.findBYLoginAndPassword(login, password);
            if (!arrayList.isEmpty()) {
                client = Optional.of(arrayList.get(0));
                if (client.get().getStatus() != ClientStatus.BANNED) {
                    clientDao.updateStatus(ClientStatus.ONLINE, client.get().getClientId());
                    client.get().setStatus(ClientStatus.ONLINE);
                }
            }
        }
        catch (DaoException e){
            throw new ServiceException(e);
        }
        return client;
    }

    @Override
    public boolean singOut(int clientId) throws ServiceException {
        ClientDao clientDao = new ClientDaoImpl();

        try{
            if (clientDao.updateStatus(ClientStatus.OFFLINE, clientId)){
                return true;
            }
        }
        catch (DaoException e){
            throw new ServiceException(e);
        }
        return false;
    }

    @Override
    public int createOrder(Order order) throws ServiceException {
        OrderDao orderDao = new OrderDaoImpl();
        int orderId;
        try{
            orderId = orderDao.createWithId(order);
        }
        catch (DaoException e){
            throw new ServiceException(e);
        }
        return orderId;
    }

    @Override
    public List<Order> takeAllOrders() throws ServiceException {
        OrderDao orderDao = new OrderDaoImpl();
        List<Order> orderList;

        try{
            orderList = orderDao.findAll();
        }
        catch (DaoException e){
            throw new ServiceException(e);
        }
        return orderList;
    }

    @Override
    public boolean deleteOrder(int orderId) throws ServiceException {
        OrderDao orderDao = new OrderDaoImpl();
        boolean flag = true;
        Order order = new Order(orderId);
        try{
            if (!orderDao.delete(order)) {
                flag = false;
            }

        }
        catch (DaoException e){
            throw new ServiceException(e);
        }
        return flag;
    }

    @Override
    public Optional <Order> takeOrder(int orderId) throws ServiceException {
        OrderDao orderDao = new OrderDaoImpl();
        Optional<Order> order = Optional.empty();

        try {
            List<Order> orderList = orderDao.findById(orderId);
            if(!orderList.isEmpty()){
                order = Optional.of(orderList.get(0));
            }
        }
        catch (DaoException e){
            throw new ServiceException(e);
        }
        return order;
    }

    @Override
    public List<OrderDessert> takeOrderDesserts(int orderId) throws ServiceException {
        OrderDessertDao orderDessertDao = new OrderDessertDaoImpl();
        List<OrderDessert> orderDessertList;
        try {
            orderDessertList = orderDessertDao.findByOrder(new Order(orderId));
        }
        catch (DaoException e){
            throw new ServiceException(e);
        }
        return orderDessertList;
    }

    @Override
    public boolean changeOrderStatus(String orderStatus, int orderId) throws ServiceException {
        OrderDao orderDao = new OrderDaoImpl();
        boolean flag = true;
        try {
            if (orderStatus.equals(PROCESSED)){
                orderDao.updateOrderStatus(OrderStatus.PROCESSED, orderId);
            }
            else {
                if (orderStatus.equals(PREPARING)){
                    orderDao.updateOrderStatus(OrderStatus.PREPARING, orderId);
                }
                else {
                    if (orderStatus.equals(READY)){
                        orderDao.updateOrderStatus(OrderStatus.READY, orderId);
                    }
                    else {
                        flag = false;
                    }
                }
            }

        }
        catch (DaoException e){
            throw new ServiceException(e);
        }
        return flag;
    }

    @Override
    public List<Client> takeAllClients() throws ServiceException {
        ClientDao clientDao = new ClientDaoImpl();
        List<Client> clientList;

        try{
            clientList = clientDao.findAll();
        }
        catch (DaoException e){
            throw new ServiceException(e);
        }

        return clientList;
    }

    @Override
    public boolean deleteClient(int clientId) throws ServiceException {
        boolean flag = true;
        ClientDao clientDao = new ClientDaoImpl();

        try {
            if (!clientDao.delete(new Client(clientId))){
                flag = false;
            }
        }
        catch (DaoException e){
            throw new ServiceException(e);
        }
        return flag;
    }

    @Override
    public Optional<Client> takeClient(int clientId) throws ServiceException {
        ClientDao clientDao = new ClientDaoImpl();
        Optional<Client> client = Optional.empty();
        try {
            List<Client> arrayList = clientDao.findById(clientId);
            if (!arrayList.isEmpty()) {
                client = Optional.of(arrayList.get(0));
            }
        }
        catch (DaoException e){
            throw new ServiceException(e);
        }
        return client;
    }

    @Override
    public List<Order> takeClientOrders(int clientId) throws ServiceException {
        OrderDao orderDao = new OrderDaoImpl();
        List<Order> orderList;
        try {
            orderList = orderDao.findByClient(new Client(clientId));
        }
        catch (DaoException e){
            throw new ServiceException(e);
        }
        return orderList;
    }

    @Override
    public boolean changeClientRole(ClientRole clientRole, int clientId) throws ServiceException {
        ClientDao clientDao = new ClientDaoImpl();
        boolean flag = true;

        try {
            if(!clientDao.updateRole(clientRole, clientId)){
                flag = false;
            }
        }
        catch (DaoException e){
            throw new ServiceException(e);
        }
        return flag;
    }

    @Override
    public List<ClientDiscount> takeAllDiscounts() throws ServiceException {
        List<ClientDiscount> clientDiscountList;
        DiscountDao discountDao = new DiscountDaoImpl();

        try{
            clientDiscountList = discountDao.findAll();
        }
        catch (DaoException e){
            throw new ServiceException(e);
        }
        return clientDiscountList;
    }

    @Override
    public boolean changeClientDiscount(ClientDiscount clientDiscount, int clientId) throws ServiceException {
        ClientDao clientDao = new ClientDaoImpl();
        boolean flag = true;

        try {
            if (!clientDao.updateDiscount(clientDiscount, clientId)){
                flag = false;
            }
        }
        catch (DaoException e){
            throw new ServiceException(e);
        }
        return flag;
    }

    @Override
    public Optional<ClientDiscount> takeClientDiscount(int discountId) throws ServiceException {
        Optional<ClientDiscount> clientDiscount = Optional.empty();
        DiscountDao discountDao = new DiscountDaoImpl();

        try{
            List<ClientDiscount> discountList = discountDao.findById(discountId);
            if(!discountList.isEmpty()){
                clientDiscount = Optional.of(discountList.get(0));
            }
        }
        catch (DaoException e){
            throw new ServiceException(e);
        }
        return clientDiscount;
    }

    @Override
    public boolean banClient(Client client) throws ServiceException {
        ClientDao clientDao = new ClientDaoImpl();
        boolean flag = true;

        try {
            if (client.getStatus() != ClientStatus.BANNED) {
                clientDao.updateStatus(ClientStatus.BANNED, client.getClientId());
                client.setStatus(ClientStatus.BANNED);
            }
            else {
                if (client.getStatus() == ClientStatus.BANNED){
                    clientDao.updateStatus(ClientStatus.OFFLINE, client.getClientId());
                    client.setStatus(ClientStatus.OFFLINE);
                }
                else {
                    flag = false;
                }
            }
        }
        catch (DaoException e){
            throw new ServiceException(e);
        }

        return flag;
    }

    @Override
    public boolean editDiscount(Map<String, String> discountData, int discountId) throws ServiceException {
        boolean flag = true;
        Validator validator = new Validator();
        DiscountDao discountDao = new DiscountDaoImpl();

        if(validator.checkDiscountEditForm(discountData)){
            try {
                if (!discountData.get(DISCOUNT_NAME).isEmpty()){
                    discountDao.updateDiscountName(discountData.get(DISCOUNT_NAME), discountId);
                }
                if (!discountData.get(DISCOUNT_VALUE).isEmpty()){
                    double value = 1-(Double.parseDouble(discountData.get(DISCOUNT_VALUE))/100);
                    discountDao.updateValue(value, discountId);
                }
            }
            catch (DaoException e){
                throw new ServiceException(e);
            }
        }
        else {
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean createDiscount(Map<String, String> discountData) throws ServiceException {
        boolean flag = true;
        Validator validator = new Validator();
        DiscountDao discountDao = new DiscountDaoImpl();

        if(validator.checkCreateDiscountForm(discountData)){
            try {
                double value = 1-(Double.parseDouble(discountData.get(DISCOUNT_VALUE))/100);

                ClientDiscount discount = new ClientDiscount(value, discountData.get(DISCOUNT_NAME));
                discountDao.create(discount);
            }
            catch (DaoException e){
                throw new ServiceException(e);
            }
        }
        else {
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean deleteDiscount(int discountId) throws ServiceException {
        boolean flag = true;
        DiscountDao discountDao = new DiscountDaoImpl();

        try {
            if (!discountDao.delete(new ClientDiscount(discountId))){
                flag = false;
            }
        }
        catch (DaoException e){
            throw new ServiceException(e);
        }
        return false;
    }
}
