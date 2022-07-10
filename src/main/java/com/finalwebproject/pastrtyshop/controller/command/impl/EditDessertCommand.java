package com.finalwebproject.pastrtyshop.controller.command.impl;

import com.finalwebproject.pastrtyshop.controller.Router;
import com.finalwebproject.pastrtyshop.controller.command.Command;
import com.finalwebproject.pastrtyshop.entity.Dessert;
import com.finalwebproject.pastrtyshop.exception.CommandException;
import com.finalwebproject.pastrtyshop.exception.ServiceException;
import com.finalwebproject.pastrtyshop.service.DessertsService;
import com.finalwebproject.pastrtyshop.service.impl.DessertsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.finalwebproject.pastrtyshop.controller.Parameters.ID_OF_DESSERT;
import static com.finalwebproject.pastrtyshop.controller.Parameters.DESSERT;
import static com.finalwebproject.pastrtyshop.controller.LinksToPages.EDIT_PAGE;
public class EditDessertCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Router router = new Router();
        DessertsService dessertsService = new DessertsServiceImpl();
        Dessert dessert;
        int dessertId = Integer.parseInt(request.getParameter(ID_OF_DESSERT));

        try {
            dessert = dessertsService.takeDessertById(dessertId);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }


        session.setAttribute(DESSERT, dessert);
        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(EDIT_PAGE);

        return router;
    }
}
