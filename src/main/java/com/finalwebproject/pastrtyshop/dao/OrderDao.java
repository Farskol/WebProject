package com.finalwebproject.pastrtyshop.dao;

import com.finalwebproject.pastrtyshop.entity.Client;
import com.finalwebproject.pastrtyshop.entity.Order;
import com.finalwebproject.pastrtyshop.entity.OrderStatus;
import com.finalwebproject.pastrtyshop.exception.DaoException;

import java.util.Date;
import java.util.List;

public interface OrderDao extends BaseDao<Order>{
    int createWithId(Order order) throws DaoException;

    List<Order> findById (int orderId) throws DaoException;
    List<Order> findByOrderDate (Date orderDate) throws DaoException;
    List<Order> findByTotalPrice (double totalPrice) throws DaoException;
    List<Order> findByClient (Client client) throws DaoException;
    List<Order> findByOrderStatus (OrderStatus orderStatus) throws DaoException;
    List<Order> findByInWhatDate (Date inWhatDate) throws DaoException;
    List<Order> findByFirstName (String firstName) throws DaoException;
    List<Order> findBySecondName (String secondName) throws DaoException;
    List<Order> findByPhoneNumber (String phoneNumber) throws DaoException;

    boolean updateOrderDate (Date orderDate, int orderId) throws DaoException;
    boolean updateTotalPrice (double totalPrice, int orderId) throws DaoException;
    boolean updateClient (Client client, int orderId) throws DaoException;
    boolean updateOrderStatus (OrderStatus orderStatus, int orderId) throws DaoException;
    boolean updateInWhatDate (Date inWhatDate, int orderId) throws DaoException;
    boolean updateFirstName (String firstName, int orderId) throws DaoException;
    boolean updateSecondName (String secondName, int orderId) throws DaoException;
    boolean updatePhoneNumber (String phoneNumber, int orderId) throws DaoException;

}
