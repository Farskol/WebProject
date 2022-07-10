package com.finalwebproject.pastrtyshop.dao;

import com.finalwebproject.pastrtyshop.entity.Cake;
import com.finalwebproject.pastrtyshop.entity.Stuffing;
import com.finalwebproject.pastrtyshop.exception.DaoException;

import java.util.List;

public interface CakeDao extends BaseDao<Cake> {
    int createWithId(Cake cake) throws DaoException;

    List<Cake> findById(int id) throws DaoException;
    List<Cake> findByCost(double cost) throws DaoException;
    List<Cake> findByWeight(double weight) throws DaoException;
    List<Cake> findByStuffing(Stuffing stuffing) throws DaoException;

    boolean updateCost(double cost, int cakeId) throws DaoException;
    boolean updateWeight(double weight, int cakeId) throws DaoException;
    boolean updateStuffing(Stuffing stuffing, int cakeId) throws DaoException;
    boolean updateDesignDescription(String description, int cakeId) throws DaoException;
}
