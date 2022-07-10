package com.finalwebproject.pastrtyshop.service;

import com.finalwebproject.pastrtyshop.entity.*;
import com.finalwebproject.pastrtyshop.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ClientService {
    boolean clientRegistration(Map<String, String> mapData) throws ServiceException;
    Optional<Client> login (String login, String password) throws ServiceException;
    boolean singOut (int clientId) throws ServiceException;
    int createOrder (Order order) throws ServiceException;
    List<Order> takeAllOrders() throws ServiceException;
    boolean deleteOrder(int orderId) throws ServiceException;
    Optional <Order> takeOrder (int orderId) throws ServiceException;
    List<OrderDessert> takeOrderDesserts (int orderId)  throws ServiceException;
    boolean changeOrderStatus(String orderStatus, int orderId) throws ServiceException;
    List<Client> takeAllClients() throws ServiceException;
    boolean deleteClient(int clientId) throws ServiceException;
    Optional<Client> takeClient (int clientId) throws ServiceException;
    List<Order> takeClientOrders (int clientId) throws ServiceException;
    boolean changeClientRole (ClientRole clientRole, int clientId) throws ServiceException;
    List<ClientDiscount> takeAllDiscounts() throws ServiceException;
    boolean changeClientDiscount(ClientDiscount clientDiscount, int clientId) throws ServiceException;
    Optional<ClientDiscount> takeClientDiscount(int discountId) throws ServiceException;
    boolean banClient(Client client) throws ServiceException;
    boolean editDiscount(Map<String, String> discountData, int discountId) throws ServiceException;
    boolean createDiscount(Map<String, String> discountData) throws ServiceException;
    boolean deleteDiscount(int discountId) throws ServiceException;

}
