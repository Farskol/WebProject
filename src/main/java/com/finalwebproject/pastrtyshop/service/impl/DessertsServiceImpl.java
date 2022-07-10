package com.finalwebproject.pastrtyshop.service.impl;

import com.finalwebproject.pastrtyshop.dao.CakeDao;
import com.finalwebproject.pastrtyshop.dao.DessertDao;
import com.finalwebproject.pastrtyshop.dao.OrderDessertDao;
import com.finalwebproject.pastrtyshop.dao.StuffingDao;
import com.finalwebproject.pastrtyshop.dao.impl.CakeDaoImpl;
import com.finalwebproject.pastrtyshop.dao.impl.DessertDaoImpl;
import com.finalwebproject.pastrtyshop.dao.impl.OrderDessertDaoImpl;
import com.finalwebproject.pastrtyshop.dao.impl.StuffingDaoImpl;
import com.finalwebproject.pastrtyshop.entity.Cake;
import com.finalwebproject.pastrtyshop.entity.Dessert;
import com.finalwebproject.pastrtyshop.entity.OrderDessert;
import com.finalwebproject.pastrtyshop.entity.Stuffing;
import com.finalwebproject.pastrtyshop.exception.DaoException;
import com.finalwebproject.pastrtyshop.exception.ServiceException;
import com.finalwebproject.pastrtyshop.service.DessertsService;
import com.finalwebproject.pastrtyshop.validator.Validator;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.StreamHandler;

import static com.finalwebproject.pastrtyshop.controller.Parameters.*;

public class DessertsServiceImpl implements DessertsService {
    @Override
    public List<Dessert> takeAllDesserts() throws ServiceException {
        DessertDao dessertDao = new DessertDaoImpl();
        List<Dessert> desserts = null;
        try {
            desserts = dessertDao.findAll();
        }
        catch (DaoException e){
            throw new ServiceException(e);
        }
        return desserts;
    }

    @Override
    public List<Stuffing> takeAllStuffings() throws ServiceException {
        StuffingDao stuffingDao = new StuffingDaoImpl();
        List<Stuffing> stuffings;
        try{
            stuffings = stuffingDao.findAll();
        }
        catch (DaoException e){
            throw new ServiceException(e);
        }
        return stuffings;
    }

    @Override
    public Dessert takeDessertById(int dessertId) throws ServiceException {
        DessertDao dessertDao = new DessertDaoImpl();
        Dessert dessert = null;

        try{
            if (!dessertDao.findById(dessertId).isEmpty()) {
                dessert = dessertDao.findById(dessertId).get(0);
            }
        }
        catch (DaoException e){
            throw new ServiceException(e);
        }
        return dessert;

    }

    @Override
    public boolean createOrderDessert(OrderDessert orderDessert) throws ServiceException {
        OrderDessertDao orderDessertDao = new OrderDessertDaoImpl();
        boolean flag = false;
        try {
            flag = orderDessertDao.create(orderDessert);
        }
        catch (DaoException e){
            throw new ServiceException(e);
        }
        return flag;
    }

    @Override
    public Stuffing takeStuffingById(int stuffingId) throws ServiceException {
        StuffingDao stuffingDao = new StuffingDaoImpl();
        Stuffing stuffing = null;
        try{
            if(!stuffingDao.findById(stuffingId).isEmpty()) {
                stuffing = stuffingDao.findById(stuffingId).get(0);
            }
        }
        catch (DaoException e){
            throw new ServiceException(e);
        }
        return stuffing;
    }

    @Override
    public int createCake(Cake cake) throws ServiceException {
        CakeDao cakeDao = new CakeDaoImpl();
        int cakeId = 0;
        try {
            cakeId = cakeDao.createWithId(cake);
        }
        catch (DaoException e){
            throw new ServiceException(e);
        }
        return cakeId;
    }

    @Override
    public boolean editDessert(Map<String, String> dessertData, int dessertId) throws ServiceException{
        boolean flag = true;
        Validator validator = new Validator();
        DessertDao dessertDao = new DessertDaoImpl();
    if (validator.checkDessertEditForm(dessertData)){
        try {
            if (!dessertData.get(DESSERT_NAME).isEmpty()){
                dessertDao.updateName(dessertData.get(DESSERT_NAME), dessertId);
            }
            if (!dessertData.get(DESSERT_DESCRIPTION).isEmpty()){
                dessertDao.updateDescription(dessertData.get(DESSERT_DESCRIPTION), dessertId);
            }
            if(!dessertData.get(TYPE_OF_DESSERT).isEmpty()){
                dessertDao.updateType(dessertData.get(TYPE_OF_DESSERT),dessertId);
            }
            if(!dessertData.get(COST_OF_DESSERT).isEmpty()){
                dessertDao.updateCost(Double.parseDouble(dessertData.get(COST_OF_DESSERT)),dessertId);
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
    public boolean createDessert(Map<String, String> dessertData) throws ServiceException {
        boolean flag = true;
        Validator validator = new Validator();
        DessertDao dessertDao = new DessertDaoImpl();

        if (validator.checkCreateDessertForm(dessertData)){
            try {
                Dessert dessert = new Dessert(
                        dessertData.get(DESSERT_DESCRIPTION),
                        Double.parseDouble(dessertData.get(COST_OF_DESSERT)),
                        dessertData.get(DESSERT_NAME),
                        dessertData.get(TYPE_OF_DESSERT)
                );

                dessertDao.create(dessert);
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
    public boolean deleteDessert(int dessertId) throws ServiceException {
        DessertDao dessertDao = new DessertDaoImpl();
        boolean flag = true;

        try {
            if(!dessertDao.delete(new Dessert(dessertId))){
                flag = false;
            }
            }
        catch (DaoException e){
            throw new ServiceException(e);
        }
        return flag;
    }

    @Override
    public boolean createStuffing(Map<String, String> stuffingData) throws ServiceException {
        StuffingDao stuffingDao = new StuffingDaoImpl();
        Validator validator = new Validator();
        boolean flag = true;
        try {
            if(validator.checkStuffingCreateForm(stuffingData)){
                stuffingDao.create(new Stuffing(stuffingData.get(STUFFING_NAME), stuffingData.get(STUFFING_DESCRIPTION)));
            }
            else {
                flag = false;
            }
        }
        catch (DaoException e){
            throw  new ServiceException(e);
        }
        return flag;
    }

    @Override
    public Optional<Stuffing> takeStuffing(int stuffingId) throws ServiceException {
        Optional<Stuffing> stuffing = Optional.empty();
        StuffingDao stuffingDao = new StuffingDaoImpl();

        try {
            if (!stuffingDao.findById(stuffingId).isEmpty())
            stuffing = Optional.of(stuffingDao.findById(stuffingId).get(0));
        }
        catch (DaoException e){
            throw new ServiceException(e);
        }
        return stuffing;
    }

    @Override
    public boolean editStuffing(Map<String, String> stuffingData, int stuffingId) throws ServiceException {
        StuffingDao stuffingDao = new StuffingDaoImpl();
        Validator validator = new Validator();
        boolean flag = true;

        try{
            if (validator.checkStuffingEditForm(stuffingData)){
                if (!stuffingData.get(STUFFING_NAME).isEmpty()) {
                    stuffingDao.updateStuffing(stuffingData.get(STUFFING_NAME), stuffingId);
                }
                if(!stuffingData.get(STUFFING_DESCRIPTION).isEmpty()) {
                    stuffingDao.updateDescription(stuffingData.get(STUFFING_DESCRIPTION), stuffingId);
                }
            }
            else {
                flag = false;
            }
        }
        catch (DaoException e){
            throw new ServiceException(e);
        }
        return flag;
    }

    @Override
    public boolean deleteStuffing(int stuffingId) throws ServiceException {
        StuffingDao stuffingDao = new StuffingDaoImpl();
        boolean flag = true;
        try {
            if(!stuffingDao.delete(new Stuffing(stuffingId))){
                flag = false;
            }
        }
        catch (DaoException e){
            throw new ServiceException(e);
        }
        return flag;
    }
}
