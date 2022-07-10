package com.finalwebproject.pastrtyshop.controller.command.impl;

import com.finalwebproject.pastrtyshop.controller.Router;
import com.finalwebproject.pastrtyshop.controller.command.Command;
import com.finalwebproject.pastrtyshop.entity.OrderDessert;
import com.finalwebproject.pastrtyshop.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static com.finalwebproject.pastrtyshop.controller.LinksToPages.BASKET_PAGE;
import static com.finalwebproject.pastrtyshop.controller.Parameters.LIST_OF_DESSERTS;
import static com.finalwebproject.pastrtyshop.controller.Parameters.COUNT;

public class DeleteDessertInListCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Router router = new Router();

        List<OrderDessert> orderDessertsList = (ArrayList<OrderDessert>) session.getAttribute(LIST_OF_DESSERTS);
        orderDessertsList.remove(Integer.parseInt(request.getParameter(COUNT)));

        session.setAttribute(LIST_OF_DESSERTS,orderDessertsList);
        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(BASKET_PAGE);

        return router;
    }
}
