package com.finalwebproject.pastrtyshop.controller.command.impl;

import com.finalwebproject.pastrtyshop.controller.Router;
import com.finalwebproject.pastrtyshop.controller.command.Command;
import com.finalwebproject.pastrtyshop.entity.Client;
import com.finalwebproject.pastrtyshop.entity.Order;
import com.finalwebproject.pastrtyshop.exception.CommandException;
import com.finalwebproject.pastrtyshop.exception.ServiceException;
import com.finalwebproject.pastrtyshop.service.ClientService;
import com.finalwebproject.pastrtyshop.service.impl.ClientServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import static com.finalwebproject.pastrtyshop.controller.Parameters.CLIENT_ID;
import static com.finalwebproject.pastrtyshop.controller.Parameters.CLIENT_EXTRA;
import static com.finalwebproject.pastrtyshop.controller.Parameters.ORDER_LIST;
import static com.finalwebproject.pastrtyshop.controller.LinksToPages.CLIENT_EXTRA_PAGE;

public class ClientExtraCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        int clientId = Integer.parseInt(request.getParameter(CLIENT_ID));
        ClientService clientService = new ClientServiceImpl();
        Router router = new Router();

        try {
            List<Order> orderList = clientService.takeClientOrders(clientId);
            Client client = clientService.takeClient(clientId).get();
            session.setAttribute(CLIENT_EXTRA, client);
            session.setAttribute(ORDER_LIST,orderList);
        }
        catch (ServiceException e){
            throw new CommandException(e);
        }

        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(CLIENT_EXTRA_PAGE);
        return router;
    }
}
