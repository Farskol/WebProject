package com.finalwebproject.pastrtyshop.service;

import com.finalwebproject.pastrtyshop.entity.Cake;
import com.finalwebproject.pastrtyshop.entity.Dessert;
import com.finalwebproject.pastrtyshop.entity.OrderDessert;
import com.finalwebproject.pastrtyshop.entity.Stuffing;
import com.finalwebproject.pastrtyshop.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DessertsService {
List<Dessert> takeAllDesserts() throws ServiceException;
List<Stuffing> takeAllStuffings() throws ServiceException;
Dessert takeDessertById(int dessertId) throws ServiceException;
boolean createOrderDessert(OrderDessert orderDessert) throws ServiceException;
Stuffing takeStuffingById(int stuffingId) throws ServiceException;
int createCake(Cake cake) throws ServiceException;
boolean editDessert(Map<String, String> dessertData, int dessertId) throws ServiceException;
boolean createDessert(Map<String, String> dessertData) throws ServiceException;
boolean deleteDessert(int dessertId) throws ServiceException;
boolean createStuffing(Map<String, String> stuffingData) throws ServiceException;
Optional<Stuffing> takeStuffing(int stuffingId) throws ServiceException;
boolean editStuffing(Map<String, String> stuffingData, int stuffingId) throws ServiceException;
boolean deleteStuffing(int stuffingId) throws ServiceException;
}
