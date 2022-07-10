package com.finalwebproject.pastrtyshop.controller.command.impl;

import com.finalwebproject.pastrtyshop.controller.Router;
import com.finalwebproject.pastrtyshop.controller.command.Command;
import com.finalwebproject.pastrtyshop.entity.Client;
import com.finalwebproject.pastrtyshop.entity.Order;
import com.finalwebproject.pastrtyshop.entity.OrderDessert;
import com.finalwebproject.pastrtyshop.entity.OrderStatus;
import com.finalwebproject.pastrtyshop.exception.CommandException;
import com.finalwebproject.pastrtyshop.exception.ServiceException;
import com.finalwebproject.pastrtyshop.service.ClientService;
import com.finalwebproject.pastrtyshop.service.DessertsService;
import com.finalwebproject.pastrtyshop.service.impl.ClientServiceImpl;
import com.finalwebproject.pastrtyshop.service.impl.DessertsServiceImpl;
import com.finalwebproject.pastrtyshop.validator.Validator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.finalwebproject.pastrtyshop.controller.LinksToPages.MAIN_PAGE;
import static com.finalwebproject.pastrtyshop.controller.LinksToPages.BASKET_PAGE;
import static com.finalwebproject.pastrtyshop.controller.Parameters.*;
import static com.finalwebproject.pastrtyshop.controller.Parameters.INVALID_SECOND_NAME;

public class CreateOrderCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        ClientService clientService = new ClientServiceImpl();
        DessertsService dessertsService = new DessertsServiceImpl();
        Router router = new Router();
        List<OrderDessert> orderDesserts = (ArrayList<OrderDessert>) session.getAttribute(LIST_OF_DESSERTS);
        double totalPrice = 0;
        int orderId;
        Order order = null;
        for (OrderDessert orderDessert : orderDesserts){
            if (orderDessert.getDessert() != null) {
                totalPrice = orderDessert.getDessertCount() * orderDessert.getDessert().getCost() + totalPrice;
            }
            else {
                totalPrice = orderDessert.getCake().getCost() + totalPrice;
            }
        }

        Client client = (Client) session.getAttribute(CLIENT);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;

        try {
            date = simpleDateFormat.parse(request.getParameter(DATE));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (client != null){
            order = new Order(totalPrice, client, OrderStatus.PROCESSED, date);
        }
        else {
            Validator validator = new Validator();
            Map<String, String> clientData = new HashMap<>();
            clientData.put(FIRST_NAME, request.getParameter(FIRST_NAME));
            clientData.put(SECOND_NAME, request.getParameter(SECOND_NAME));
            clientData.put(PHONE_NUMBER, request.getParameter(PHONE_NUMBER));
            if (validator.checkOrderForm(clientData)) {
                order = new Order(totalPrice, OrderStatus.PROCESSED, date,clientData.get(FIRST_NAME) , clientData.get(SECOND_NAME), clientData.get(PHONE_NUMBER));
            }
            else {
                for (String key : clientData.keySet()){
                    String value = clientData.get(key);
                    switch (value){
                        case INVALID_FIRST_NAME -> request.setAttribute(INVALID_FIRST_NAME, true);
                        case INVALID_SECOND_NAME -> request.setAttribute(INVALID_SECOND_NAME, true);
                        case INVALID_PHONE_NUMBER -> request.setAttribute(INVALID_PHONE_NUMBER, true);
                    }
                }
                router.setPagePath(BASKET_PAGE);
                return router;
            }
        }
        try {
            orderId = clientService.createOrder(order);
            order.setOrderId(orderId);
            for (OrderDessert orderDessert : orderDesserts){
                orderDessert.setOrder(order);
                if (orderDessert.getCake() != null){
                    orderDessert.getCake().setCakeId(dessertsService.createCake(orderDessert.getCake()));
                }
                dessertsService.createOrderDessert(orderDessert);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        session.setAttribute(LIST_OF_DESSERTS, null);
        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(MAIN_PAGE);
        return router;
    }
}
