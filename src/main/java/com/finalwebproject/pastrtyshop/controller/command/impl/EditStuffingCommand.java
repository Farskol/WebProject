package com.finalwebproject.pastrtyshop.controller.command.impl;

import com.finalwebproject.pastrtyshop.controller.Router;
import com.finalwebproject.pastrtyshop.controller.command.Command;
import com.finalwebproject.pastrtyshop.entity.Stuffing;
import com.finalwebproject.pastrtyshop.exception.CommandException;
import com.finalwebproject.pastrtyshop.exception.ServiceException;
import com.finalwebproject.pastrtyshop.service.DessertsService;
import com.finalwebproject.pastrtyshop.service.impl.DessertsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.finalwebproject.pastrtyshop.controller.Parameters.STUFFING;
import static com.finalwebproject.pastrtyshop.controller.LinksToPages.STUFFING_EDIT_PAGE;

import static com.finalwebproject.pastrtyshop.controller.Parameters.STUFFING_ID;
public class EditStuffingCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        DessertsService dessertsService = new DessertsServiceImpl();
        int stuffingId = Integer.parseInt(request.getParameter(STUFFING_ID));
        Router router = new Router();

        try {
            Stuffing stuffing = dessertsService.takeStuffing(stuffingId).get();
            session.setAttribute(STUFFING, stuffing);
        }
        catch (ServiceException e){
            throw new CommandException(e);
        }

        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(STUFFING_EDIT_PAGE);
        return router;
    }
}
