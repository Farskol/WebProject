package com.finalwebproject.pastrtyshop.dao;

import com.finalwebproject.pastrtyshop.entity.Cake;
import com.finalwebproject.pastrtyshop.entity.Dessert;
import com.finalwebproject.pastrtyshop.entity.Order;
import com.finalwebproject.pastrtyshop.entity.OrderDessert;
import com.finalwebproject.pastrtyshop.exception.DaoException;

import java.util.List;

public interface OrderDessertDao extends BaseDao<OrderDessert>{
    List<OrderDessert> findById (int OrderDessertId) throws DaoException;
    List<OrderDessert> findByCount (int count) throws DaoException;
    List<OrderDessert> findByOrder (Order order) throws DaoException;
    List<OrderDessert> findByCake (Cake cake) throws DaoException;
    List<OrderDessert> findByDessert (Dessert dessert) throws DaoException;

    boolean deleteByOrder(Order order) throws DaoException;
    boolean updateCount (int count, int orderDessertId) throws DaoException;
    boolean updateOrder (Order order, int orderDessertId) throws DaoException;
    boolean updateCake (Cake cake, int orderDessertId) throws DaoException;
    boolean updateDessert (Dessert dessert, int orderDessertId) throws DaoException;

}
