package com.finalwebproject.pastrtyshop.dao;

import com.finalwebproject.pastrtyshop.entity.Dessert;
import com.finalwebproject.pastrtyshop.entity.Order;
import com.finalwebproject.pastrtyshop.exception.DaoException;

import java.util.List;

public interface DessertDao extends BaseDao<Dessert>{
    List<Dessert> findById(int dessertId) throws DaoException;
    List<Dessert> findByCost(double cost) throws DaoException;
    List<Dessert> findByName(String name) throws DaoException;
    List<Dessert> findByType(String type) throws DaoException;

    boolean updateCost(double cost, int dessertId) throws DaoException;
    boolean updateDescription(String description, int dessertId) throws DaoException;
    boolean updateName(String name, int dessertId) throws DaoException;
    boolean updateType(String type, int dessertId) throws DaoException;
}
