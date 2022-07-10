package com.finalwebproject.pastrtyshop.controller.command.impl;

import com.finalwebproject.pastrtyshop.controller.Router;
import com.finalwebproject.pastrtyshop.controller.command.Command;
import com.finalwebproject.pastrtyshop.entity.Order;
import com.finalwebproject.pastrtyshop.entity.OrderStatus;
import com.finalwebproject.pastrtyshop.exception.CommandException;
import com.finalwebproject.pastrtyshop.exception.ServiceException;
import com.finalwebproject.pastrtyshop.service.ClientService;
import com.finalwebproject.pastrtyshop.service.impl.ClientServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.finalwebproject.pastrtyshop.controller.Parameters.ORDER_STATUS;
import static com.finalwebproject.pastrtyshop.controller.Parameters.ORDER;
import static com.finalwebproject.pastrtyshop.controller.LinksToPages.ORDER_EXTRA_PAGE;
import static com.finalwebproject.pastrtyshop.controller.Parameters.PROCESSED;
import static com.finalwebproject.pastrtyshop.controller.Parameters.PREPARING;
import static com.finalwebproject.pastrtyshop.controller.Parameters.READY;

public class ChangeOrderStatusCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Order order = (Order) session.getAttribute(ORDER);
        String orderStatus = request.getParameter(ORDER_STATUS);
        ClientService clientService =  new ClientServiceImpl();
        Router router = new Router();

        try {
            clientService.changeOrderStatus(orderStatus, order.getOrderId());
            if (orderStatus.equals(PROCESSED)){
                order.setOrderStatus(OrderStatus.PROCESSED);
            }
            else {
                if (orderStatus.equals(PREPARING)){
                    order.setOrderStatus(OrderStatus.PREPARING);
                }
                else {
                    if (orderStatus.equals(READY)){
                        order.setOrderStatus(OrderStatus.READY);
                    }
                }
            }
        }
        catch (ServiceException e){
            throw new CommandException(e);
        }

        session.setAttribute(ORDER, order);
        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(ORDER_EXTRA_PAGE);
        return router;
    }
}
