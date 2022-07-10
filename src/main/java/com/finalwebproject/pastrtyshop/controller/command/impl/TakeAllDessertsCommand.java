package com.finalwebproject.pastrtyshop.controller.command.impl;

import com.finalwebproject.pastrtyshop.controller.Router;
import com.finalwebproject.pastrtyshop.controller.command.Command;
import com.finalwebproject.pastrtyshop.entity.Dessert;
import com.finalwebproject.pastrtyshop.exception.CommandException;
import com.finalwebproject.pastrtyshop.exception.ServiceException;
import com.finalwebproject.pastrtyshop.service.DessertsService;
import com.finalwebproject.pastrtyshop.service.impl.DessertsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import static com.finalwebproject.pastrtyshop.controller.Parameters.DESSERT_LIST;
import static com.finalwebproject.pastrtyshop.controller.LinksToPages.DESSERT_PAGE;

public class TakeAllDessertsCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        DessertsService dessertsService = new DessertsServiceImpl();
        Router router = new Router();
        List<Dessert> desserts = null;
        try {
            desserts = dessertsService.takeAllDesserts();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        request.setAttribute(DESSERT_LIST,desserts);
        router.setPagePath(DESSERT_PAGE);
        return router;
    }
}
