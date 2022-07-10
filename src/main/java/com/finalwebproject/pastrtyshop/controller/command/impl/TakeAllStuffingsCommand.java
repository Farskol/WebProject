package com.finalwebproject.pastrtyshop.controller.command.impl;

import com.finalwebproject.pastrtyshop.controller.Router;
import com.finalwebproject.pastrtyshop.controller.command.Command;
import com.finalwebproject.pastrtyshop.entity.Stuffing;
import com.finalwebproject.pastrtyshop.exception.CommandException;
import com.finalwebproject.pastrtyshop.exception.ServiceException;
import com.finalwebproject.pastrtyshop.service.DessertsService;
import com.finalwebproject.pastrtyshop.service.impl.DessertsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static com.finalwebproject.pastrtyshop.controller.Parameters.STUFFING_LIST;
import static com.finalwebproject.pastrtyshop.controller.LinksToPages.CAKE_PAGE;

public class TakeAllStuffingsCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        DessertsService dessertsService = new DessertsServiceImpl();
        List<Stuffing> stuffings;
        try {
            stuffings = dessertsService.takeAllStuffings();
        }
        catch (ServiceException e){
            throw new CommandException(e);
        }
        request.setAttribute(STUFFING_LIST, stuffings);
        router.setPagePath(CAKE_PAGE);
        return null;
    }
}
