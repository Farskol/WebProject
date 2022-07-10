package com.finalwebproject.pastrtyshop.controller.command.impl;

import com.finalwebproject.pastrtyshop.controller.Router;
import com.finalwebproject.pastrtyshop.controller.command.Command;
import com.finalwebproject.pastrtyshop.exception.CommandException;
import com.finalwebproject.pastrtyshop.exception.ServiceException;
import com.finalwebproject.pastrtyshop.service.DessertsService;
import com.finalwebproject.pastrtyshop.service.impl.DessertsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import static com.finalwebproject.pastrtyshop.controller.Parameters.ID_OF_DESSERT;
import static com.finalwebproject.pastrtyshop.controller.LinksToPages.DESSERT_ADMIN_PAGE;
public class DeleteDessertCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        DessertsService dessertsService = new DessertsServiceImpl();
        Router router = new Router();

        try {
            int dessertId = Integer.parseInt(request.getParameter(ID_OF_DESSERT));
            dessertsService.deleteDessert(dessertId);
        }
        catch (ServiceException e){
            throw new CommandException(e);
        }

        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(DESSERT_ADMIN_PAGE);
        return router;
    }
}
