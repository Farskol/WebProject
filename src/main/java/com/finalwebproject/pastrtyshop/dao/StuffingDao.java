package com.finalwebproject.pastrtyshop.dao;

import com.finalwebproject.pastrtyshop.entity.Stuffing;
import com.finalwebproject.pastrtyshop.exception.DaoException;

import java.util.List;

public interface StuffingDao extends BaseDao<Stuffing>{
    List<Stuffing> findById(int stuffingId) throws DaoException;
    List<Stuffing> findByStuffing(String stuffing) throws DaoException;

    boolean updateStuffing(String stuffing, int stuffingId) throws DaoException;
    boolean updateDescription(String description, int stuffingId) throws DaoException;
}
