package com.finalwebproject.pastrtyshop.controller.command.impl;

import com.finalwebproject.pastrtyshop.controller.Router;
import com.finalwebproject.pastrtyshop.controller.command.Command;
import com.finalwebproject.pastrtyshop.entity.Dessert;
import com.finalwebproject.pastrtyshop.entity.Order;
import com.finalwebproject.pastrtyshop.exception.CommandException;
import com.finalwebproject.pastrtyshop.exception.ServiceException;
import com.finalwebproject.pastrtyshop.service.ClientService;
import com.finalwebproject.pastrtyshop.service.impl.ClientServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static com.finalwebproject.pastrtyshop.controller.LinksToPages.ORDERS_ADMIN_PAGE;
import static com.finalwebproject.pastrtyshop.controller.Parameters.ORDER_LIST;

public class TakeAllOrdersCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ClientService clientService = new ClientServiceImpl();
        Router router = new Router();
        List<Order> orderList;
        try {
            orderList = clientService.takeAllOrders();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        request.setAttribute(ORDER_LIST,orderList);
        router.setPagePath(ORDERS_ADMIN_PAGE);
        return router;
    }
}
