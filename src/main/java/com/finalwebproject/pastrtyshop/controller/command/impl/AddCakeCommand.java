package com.finalwebproject.pastrtyshop.controller.command.impl;

import com.finalwebproject.pastrtyshop.controller.Router;
import com.finalwebproject.pastrtyshop.controller.command.Command;
import com.finalwebproject.pastrtyshop.entity.Cake;
import com.finalwebproject.pastrtyshop.entity.OrderDessert;
import com.finalwebproject.pastrtyshop.entity.Stuffing;
import com.finalwebproject.pastrtyshop.exception.CommandException;
import com.finalwebproject.pastrtyshop.exception.ServiceException;
import com.finalwebproject.pastrtyshop.service.DessertsService;
import com.finalwebproject.pastrtyshop.service.impl.DessertsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static com.finalwebproject.pastrtyshop.controller.Parameters.LIST_OF_DESSERTS;
import static com.finalwebproject.pastrtyshop.controller.Parameters.WEIGHT;
import static com.finalwebproject.pastrtyshop.controller.Parameters.DESCRIPTION;
import static com.finalwebproject.pastrtyshop.controller.Parameters.STUFFING;
import static com.finalwebproject.pastrtyshop.controller.LinksToPages.CAKE_PAGE;

public class AddCakeCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Router router = new Router();
        List<OrderDessert> orderDessertsList;
        Cake cake;
        DessertsService dessertsService = new DessertsServiceImpl();

        if(session.getAttribute(LIST_OF_DESSERTS) == null){
            orderDessertsList = new ArrayList<>();
        }
        else {
            orderDessertsList = (ArrayList<OrderDessert>) session.getAttribute(LIST_OF_DESSERTS);
        }
        double weight = Double.parseDouble(request.getParameter(WEIGHT));
        double cost = 30 * weight;
        try {
            Stuffing stuffing = dessertsService.takeStuffingById(Integer.parseInt(request.getParameter(STUFFING)));
            cake = new Cake(cost, request.getParameter(DESCRIPTION), weight, stuffing);
        }
        catch (ServiceException e){
            throw new CommandException(e);
        }
        OrderDessert orderDessert = new OrderDessert(1,cake);
        orderDessertsList.add(orderDessert);
        session.setAttribute(LIST_OF_DESSERTS,orderDessertsList);

        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(CAKE_PAGE);
        return router;
    }
}
