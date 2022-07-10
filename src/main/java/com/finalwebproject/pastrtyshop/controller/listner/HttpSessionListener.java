package com.finalwebproject.pastrtyshop.controller.listner;

import com.finalwebproject.pastrtyshop.pool.CustomConnectionPool;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;

@WebListener
public class HttpSessionListener implements jakarta.servlet.http.HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        CustomConnectionPool.getInstance();
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        CustomConnectionPool.getInstance().destroyPool();
    }
}
