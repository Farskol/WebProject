package com.finalwebproject.pastrtyshop.controller.command.impl;

import com.finalwebproject.pastrtyshop.controller.Router;
import com.finalwebproject.pastrtyshop.controller.command.Command;
import com.finalwebproject.pastrtyshop.exception.CommandException;
import com.finalwebproject.pastrtyshop.exception.ServiceException;
import com.finalwebproject.pastrtyshop.service.ClientService;
import com.finalwebproject.pastrtyshop.service.impl.ClientServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import static com.finalwebproject.pastrtyshop.controller.Parameters.ID_OF_DISCOUNT;
import static com.finalwebproject.pastrtyshop.controller.LinksToPages.DISCOUNT_ADMIN_PAGE;
public class DeleteDiscountCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ClientService clientService = new ClientServiceImpl();
        Router router = new Router();

        try {
            int discountId = Integer.parseInt(request.getParameter(ID_OF_DISCOUNT));
            clientService.deleteDiscount(discountId);
        }
        catch (ServiceException e){
            throw new CommandException(e);
        }

        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(DISCOUNT_ADMIN_PAGE);

        return router;
    }
}
