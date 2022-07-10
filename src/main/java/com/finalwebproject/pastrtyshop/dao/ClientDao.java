package com.finalwebproject.pastrtyshop.dao;

import com.finalwebproject.pastrtyshop.entity.Client;
import com.finalwebproject.pastrtyshop.entity.ClientDiscount;
import com.finalwebproject.pastrtyshop.entity.ClientRole;
import com.finalwebproject.pastrtyshop.entity.ClientStatus;
import com.finalwebproject.pastrtyshop.exception.DaoException;

import java.util.List;

public interface ClientDao extends BaseDao <Client>{
 List<Client> findById(int clientId) throws DaoException;
 List<Client> findByLogin(String login) throws DaoException;
 List<Client> findByFirstName(String firstName) throws DaoException;
 List<Client> findBySecondName(String secondName) throws DaoException;
 List<Client> findByEmail(String email) throws DaoException;
 List<Client> findByPhoneNumber(String PhoneNumber) throws DaoException;
 List<Client> findByRole(ClientRole role) throws DaoException;
 List<Client> findByStatus(ClientStatus status) throws DaoException;
 List<Client> findBYLoginAndPassword(String login, String password) throws DaoException;

 boolean updateLogin(String login, int clientId) throws DaoException;
 boolean updatePassword(String password, int clientId) throws DaoException;
 boolean updateFirstName(String firstName, int clientId) throws DaoException;
 boolean updateSecondName(String secondName, int clientId) throws DaoException;
 boolean updateEmail(String email, int clientId) throws DaoException;
 boolean updatePhoneNumber(String  phoneNumber, int clientId) throws DaoException;
 boolean updateRole(ClientRole role, int clientId) throws DaoException;
 boolean updateStatus(ClientStatus status, int clientId) throws DaoException;
 boolean updateDiscount(ClientDiscount discount, int clientId) throws DaoException;
}
