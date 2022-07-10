package com.finalwebproject.pastrtyshop.controller.command.impl;

import com.finalwebproject.pastrtyshop.controller.Router;
import com.finalwebproject.pastrtyshop.controller.command.Command;
import com.finalwebproject.pastrtyshop.exception.CommandException;
import com.finalwebproject.pastrtyshop.exception.ServiceException;
import com.finalwebproject.pastrtyshop.service.ClientService;
import com.finalwebproject.pastrtyshop.service.impl.ClientServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.finalwebproject.pastrtyshop.controller.Parameters.ORDER_ID;
import static com.finalwebproject.pastrtyshop.controller.Parameters.ORDER;
import static com.finalwebproject.pastrtyshop.controller.Parameters.ORDER_DESSERT_LIST;
import static com.finalwebproject.pastrtyshop.controller.LinksToPages.ORDER_EXTRA_PAGE;

public class OrderExtraCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        int orderId = Integer.parseInt(request.getParameter(ORDER_ID));
        ClientService clientService = new ClientServiceImpl();
        Router router = new Router();

        try{
            session.setAttribute(ORDER,clientService.takeOrder(orderId).get());
            session.setAttribute(ORDER_DESSERT_LIST,clientService.takeOrderDesserts(orderId));
        }
        catch (ServiceException e){
            throw new CommandException(e);
        }

        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(ORDER_EXTRA_PAGE);
        return router;
    }
}
