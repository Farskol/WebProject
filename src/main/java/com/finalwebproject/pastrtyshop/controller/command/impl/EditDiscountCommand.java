package com.finalwebproject.pastrtyshop.controller.command.impl;

import com.finalwebproject.pastrtyshop.controller.Router;
import com.finalwebproject.pastrtyshop.controller.command.Command;
import com.finalwebproject.pastrtyshop.entity.ClientDiscount;
import com.finalwebproject.pastrtyshop.exception.CommandException;
import com.finalwebproject.pastrtyshop.exception.ServiceException;
import com.finalwebproject.pastrtyshop.service.ClientService;
import com.finalwebproject.pastrtyshop.service.impl.ClientServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.finalwebproject.pastrtyshop.controller.Parameters.ID_OF_DISCOUNT;
import static com.finalwebproject.pastrtyshop.controller.Parameters.DISCOUNT;
import static com.finalwebproject.pastrtyshop.controller.LinksToPages.DISCOUNT_EDIT_PAGE;

public class EditDiscountCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        ClientService clientService = new ClientServiceImpl();
        int discountId = Integer.parseInt(request.getParameter(ID_OF_DISCOUNT));
        Router router = new Router();

        try {
            ClientDiscount clientDiscount = clientService.takeClientDiscount(discountId).get();
            session.setAttribute(DISCOUNT, clientDiscount);
        }
        catch (ServiceException e){
            throw  new CommandException(e);
        }

        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(DISCOUNT_EDIT_PAGE);
        return router;
    }
}
