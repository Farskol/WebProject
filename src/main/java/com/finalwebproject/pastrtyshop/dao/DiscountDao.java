package com.finalwebproject.pastrtyshop.dao;

import com.finalwebproject.pastrtyshop.entity.ClientDiscount;
import com.finalwebproject.pastrtyshop.exception.DaoException;

import java.util.List;

public interface DiscountDao extends BaseDao <ClientDiscount>{

    List<ClientDiscount> findById(int discountId) throws DaoException;
    List<ClientDiscount> findByValue(double value) throws DaoException;
    List<ClientDiscount> findByNameOfDiscount(String nameOfDiscount) throws DaoException;

    boolean updateValue(double value, int discountId) throws DaoException;
    boolean updateDiscountName(String nameOfDiscount, int discountId) throws DaoException;
}
