package com.finalwebproject.pastrtyshop.controller.command.impl;

import com.finalwebproject.pastrtyshop.controller.Router;
import com.finalwebproject.pastrtyshop.controller.command.Command;
import com.finalwebproject.pastrtyshop.exception.CommandException;
import com.finalwebproject.pastrtyshop.exception.ServiceException;
import com.finalwebproject.pastrtyshop.service.ClientService;
import com.finalwebproject.pastrtyshop.service.DessertsService;
import com.finalwebproject.pastrtyshop.service.impl.ClientServiceImpl;
import com.finalwebproject.pastrtyshop.service.impl.DessertsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import static com.finalwebproject.pastrtyshop.controller.LinksToPages.STUFFING_ADMIN_PAGE;
import static com.finalwebproject.pastrtyshop.controller.LinksToPages.STUFFING_CREATE_PAGE;
import static com.finalwebproject.pastrtyshop.controller.Parameters.*;

public class CreateStuffingCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Map<String, String> stuffingData = new HashMap<>();
        DessertsService dessertsService = new DessertsServiceImpl();
        Router router = new Router();

        stuffingData.put(STUFFING_NAME, request.getParameter(STUFFING_NAME));
        stuffingData.put(STUFFING_DESCRIPTION, request.getParameter(STUFFING_DESCRIPTION));

        try {
            if(dessertsService.createStuffing(stuffingData)){
                router.setRouteType(Router.RouteType.REDIRECT);
                router.setPagePath(STUFFING_ADMIN_PAGE);
            }
            else {
                for (String key : stuffingData.keySet()){
                    String value = stuffingData.get(key);
                    switch (value){
                        case INVALID_STUFFING_NAME -> request.setAttribute(INVALID_STUFFING_NAME, true);
                        case INVALID_STUFFING_DESCRIPTION -> request.setAttribute(INVALID_STUFFING_DESCRIPTION, true);
                    }
                }
                router.setPagePath(STUFFING_CREATE_PAGE);
            }
        }
        catch (ServiceException e){
            throw new CommandException(e);
        }

        return router;
    }
}
