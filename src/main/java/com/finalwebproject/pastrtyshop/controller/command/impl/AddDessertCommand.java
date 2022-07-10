package com.finalwebproject.pastrtyshop.controller.command.impl;

import com.finalwebproject.pastrtyshop.controller.Router;
import com.finalwebproject.pastrtyshop.controller.command.Command;;
import com.finalwebproject.pastrtyshop.entity.Dessert;
import com.finalwebproject.pastrtyshop.entity.OrderDessert;
import com.finalwebproject.pastrtyshop.exception.CommandException;
import com.finalwebproject.pastrtyshop.exception.ServiceException;
import com.finalwebproject.pastrtyshop.service.DessertsService;
import com.finalwebproject.pastrtyshop.service.impl.DessertsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static com.finalwebproject.pastrtyshop.controller.Parameters.LIST_OF_DESSERTS;
import static com.finalwebproject.pastrtyshop.controller.LinksToPages.DESSERT_PAGE;
import static com.finalwebproject.pastrtyshop.controller.Parameters.DESSERT_COUNT;
import static com.finalwebproject.pastrtyshop.controller.Parameters.ID_OF_DESSERT;

public class AddDessertCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Router router = new Router();
        DessertsService dessertsService = new DessertsServiceImpl();
        Dessert dessert;
        List<OrderDessert> orderDessertsList;
        Integer dessertId = Integer.parseInt(request.getParameter(ID_OF_DESSERT));
        Integer count = Integer.parseInt(request.getParameter(DESSERT_COUNT));

        if(session.getAttribute(LIST_OF_DESSERTS) == null){
            orderDessertsList = new ArrayList<>();
        }
        else {
           orderDessertsList = (ArrayList<OrderDessert>) session.getAttribute(LIST_OF_DESSERTS);
        }

        try {
            if ((dessert = dessertsService.takeDessertById(dessertId)) != null) {
                orderDessertsList.add(new OrderDessert(count, dessert));
                session.setAttribute(LIST_OF_DESSERTS, orderDessertsList);
            }
        }
        catch (ServiceException e){
            throw new CommandException(e);
        }
        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(DESSERT_PAGE);
        return router;
    }
}
