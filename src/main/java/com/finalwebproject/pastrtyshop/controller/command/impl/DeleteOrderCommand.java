package com.finalwebproject.pastrtyshop.controller.command.impl;

import com.finalwebproject.pastrtyshop.controller.Router;
import com.finalwebproject.pastrtyshop.controller.command.Command;
import com.finalwebproject.pastrtyshop.exception.CommandException;
import com.finalwebproject.pastrtyshop.exception.ServiceException;
import com.finalwebproject.pastrtyshop.service.ClientService;
import com.finalwebproject.pastrtyshop.service.impl.ClientServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import static com.finalwebproject.pastrtyshop.controller.Parameters.ORDER_ID;
import static com.finalwebproject.pastrtyshop.controller.LinksToPages.ORDERS_ADMIN_PAGE;
public class DeleteOrderCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ClientService clientService = new ClientServiceImpl();
        int orderId = Integer.parseInt(request.getParameter(ORDER_ID));
        Router router = new Router();
        try {
            clientService.deleteOrder(orderId);
        }
        catch (ServiceException e){
            throw new CommandException(e);
        }

        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(ORDERS_ADMIN_PAGE);
        return router;
    }
}
