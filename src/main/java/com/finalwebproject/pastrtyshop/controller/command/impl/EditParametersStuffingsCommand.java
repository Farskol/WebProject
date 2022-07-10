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

import java.util.HashMap;
import java.util.Map;

import static com.finalwebproject.pastrtyshop.controller.LinksToPages.STUFFING_ADMIN_PAGE;
import static com.finalwebproject.pastrtyshop.controller.LinksToPages.STUFFING_EDIT_PAGE;
import static com.finalwebproject.pastrtyshop.controller.Parameters.*;
import static com.finalwebproject.pastrtyshop.controller.Parameters.INVALID_STUFFING_DESCRIPTION;

public class EditParametersStuffingsCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Map<String, String> stuffingData = new HashMap<>();
        DessertsService dessertsService = new DessertsServiceImpl();
        Router router = new Router();

        stuffingData.put(STUFFING_NAME, request.getParameter(STUFFING_NAME));
        stuffingData.put(STUFFING_DESCRIPTION, request.getParameter(STUFFING_DESCRIPTION));

        Stuffing stuffing = (Stuffing) session.getAttribute(STUFFING);
        try {
            if(dessertsService.editStuffing(stuffingData, stuffing.getStuffingId())){
                router.setRouteType(Router.RouteType.REDIRECT);
                router.setPagePath(STUFFING_ADMIN_PAGE);
                session.setAttribute(STUFFING, null);
            }
            else {
                for (String key : stuffingData.keySet()){
                    String value = stuffingData.get(key);
                    switch (value){
                        case INVALID_STUFFING_NAME -> request.setAttribute(INVALID_STUFFING_NAME, true);
                        case INVALID_STUFFING_DESCRIPTION -> request.setAttribute(INVALID_STUFFING_DESCRIPTION, true);
                        case EDIT_STUFFING_FORM_IS_NULL -> request.setAttribute(EDIT_STUFFING_FORM_IS_NULL, true);
                    }
                }
                router.setPagePath(STUFFING_EDIT_PAGE);
            }
        }
        catch (ServiceException e){
            throw new CommandException(e);
        }

        return router;
    }
}
