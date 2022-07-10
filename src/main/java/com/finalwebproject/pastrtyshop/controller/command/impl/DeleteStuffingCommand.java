package com.finalwebproject.pastrtyshop.controller.command.impl;

import com.finalwebproject.pastrtyshop.controller.Router;
import com.finalwebproject.pastrtyshop.controller.command.Command;
import com.finalwebproject.pastrtyshop.exception.CommandException;
import com.finalwebproject.pastrtyshop.exception.ServiceException;
import com.finalwebproject.pastrtyshop.service.DessertsService;
import com.finalwebproject.pastrtyshop.service.impl.DessertsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import static com.finalwebproject.pastrtyshop.controller.Parameters.STUFFING_ID;
import static com.finalwebproject.pastrtyshop.controller.LinksToPages.STUFFING_ADMIN_PAGE;
public class DeleteStuffingCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        DessertsService dessertsService = new DessertsServiceImpl();
        int stuffingId = Integer.parseInt(request.getParameter(STUFFING_ID));
        Router router = new Router();

        try {
            dessertsService.deleteStuffing(stuffingId);
        }
        catch (ServiceException e){
            throw new CommandException(e);
        }
        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(STUFFING_ADMIN_PAGE);
        return router;
    }
}
