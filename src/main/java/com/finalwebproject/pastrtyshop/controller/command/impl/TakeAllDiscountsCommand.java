package com.finalwebproject.pastrtyshop.controller.command.impl;

import com.finalwebproject.pastrtyshop.controller.Router;
import com.finalwebproject.pastrtyshop.controller.command.Command;
import com.finalwebproject.pastrtyshop.entity.ClientDiscount;
import com.finalwebproject.pastrtyshop.exception.CommandException;
import com.finalwebproject.pastrtyshop.exception.ServiceException;
import com.finalwebproject.pastrtyshop.service.ClientService;
import com.finalwebproject.pastrtyshop.service.impl.ClientServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static com.finalwebproject.pastrtyshop.controller.LinksToPages.CLIENT_EXTRA_PAGE;
import static com.finalwebproject.pastrtyshop.controller.Parameters.DISCOUNT_LIST;
public class TakeAllDiscountsCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        List<ClientDiscount> clientDiscountList;
        ClientService clientService = new ClientServiceImpl();
        Router router = new Router();

        try {
            clientDiscountList = clientService.takeAllDiscounts();
            request.setAttribute(DISCOUNT_LIST,clientDiscountList);
        }
        catch (ServiceException e){
            throw new CommandException(e);
        }

        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(CLIENT_EXTRA_PAGE);
        return router;
    }
}
