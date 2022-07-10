package com.finalwebproject.pastrtyshop.pool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;

public class CustomConnectionPool  {
    private static final Logger logger = LogManager.getLogger();
    private BlockingQueue<ProxyConnection> freeConnections;
    private Queue<ProxyConnection> busyConnections;
    private static ReentrantLock lock = new ReentrantLock();

    private final static int POOL_SIZE = 32;
    private final static String URL = "jdbc:mysql://localhost:3306/mydb";

    private CustomConnectionPool(){}

    private static class SingletonConnectionPool{
        private static CustomConnectionPool INSTANCE;
        private static boolean instanceCreated = false;
    }

    static public CustomConnectionPool getInstance(){
        if(!SingletonConnectionPool.instanceCreated){
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Properties properties = new Properties();
                properties.put("user","root");
                properties.put("password", "bkj85vso");
                properties.put("autoReconnect", "true");
                properties.put("characterEncoding","UTF-8");
                properties.put("useUnicode","true");

                CustomConnectionPool.lock.lock();
                SingletonConnectionPool.instanceCreated = true;
                SingletonConnectionPool.INSTANCE = new CustomConnectionPool();
                SingletonConnectionPool.INSTANCE.freeConnections = new LinkedBlockingDeque<>(POOL_SIZE);
                SingletonConnectionPool.INSTANCE.busyConnections = new ArrayDeque<>();

                for (int i = 0; i < POOL_SIZE; i++){
                    SingletonConnectionPool.INSTANCE.freeConnections.put(new ProxyConnection(DriverManager.getConnection(URL, properties)));
                }
            }
            catch (ClassNotFoundException e) {
                logger.log(Level.FATAL, e.getMessage());
            } catch (SQLException e) {
                logger.log(Level.ERROR, e.getMessage());
            } catch (InterruptedException e) {
                logger.log(Level.ERROR, e.getMessage());
            } finally {
                CustomConnectionPool.lock.lock();
            }
        }
        return SingletonConnectionPool.INSTANCE;
    }

    public Connection getConnection(){
        ProxyConnection proxyConnection = null;
        try{
            proxyConnection = freeConnections.take();
            busyConnections.offer(proxyConnection);
        }catch (InterruptedException e){
            logger.log(Level.ERROR, e.getMessage());
        }
        return proxyConnection;
    }

    public void releaseConnection(ProxyConnection connection){
        busyConnections.remove(connection);
        freeConnections.offer(connection);
    }

    public void destroyPool(){
        for (int i = 0; i < POOL_SIZE; i++){
            try {
                freeConnections.take().reallyClose();
            }
            catch (InterruptedException e){
                logger.log(Level.ERROR, e.getMessage());
            }
        }
        deregisterDrivers();
    }

    private void deregisterDrivers(){
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.log(Level.ERROR, e.getMessage());
            }
        });
    }
}
