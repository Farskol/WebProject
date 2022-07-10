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

import java.util.HashMap;
import java.util.Map;

import static com.finalwebproject.pastrtyshop.controller.LinksToPages.EDIT_PAGE;
import static com.finalwebproject.pastrtyshop.controller.LinksToPages.DESSERT_ADMIN_PAGE;
import static com.finalwebproject.pastrtyshop.controller.Parameters.*;

public class EditDessertParametersCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Map<String,String> dessertData = new HashMap<>();
        DessertsService dessertsService = new DessertsServiceImpl();
        Router router = new Router();
        Dessert dessert = (Dessert) session.getAttribute(DESSERT);
        dessertData.put(DESSERT_NAME, request.getParameter(DESSERT_NAME));
        dessertData.put(DESSERT_DESCRIPTION, request.getParameter(DESSERT_DESCRIPTION));
        dessertData.put(TYPE_OF_DESSERT, request.getParameter(TYPE_OF_DESSERT));
        dessertData.put(COST_OF_DESSERT, request.getParameter(COST_OF_DESSERT));
        try {
            if(dessertsService.editDessert(dessertData, dessert.getDessertId())){
                router.setRouteType(Router.RouteType.REDIRECT);
                router.setPagePath(DESSERT_ADMIN_PAGE);
                session.setAttribute(DESSERT, null);
            }
            else {
                for (String key : dessertData.keySet()){
                    String value = dessertData.get(key);
                    switch (value){
                        case EDIT_DESSERT_FORM_IS_NULL -> request.setAttribute(EDIT_DESSERT_FORM_IS_NULL, true);
                        case INVALID_DESSERT_NAME -> request.setAttribute(INVALID_DESSERT_NAME, true);
                        case INVALID_DESSERT_DESCRIPTION -> request.setAttribute(INVALID_DESSERT_DESCRIPTION, true);
                        case INVALID_DESSERT_TYPE -> request.setAttribute(INVALID_DESSERT_TYPE, true);
                        case INVALID_DESSERT_COST -> request.setAttribute(INVALID_DESSERT_COST, true);
                    }
                }
                router.setPagePath(EDIT_PAGE);
            }
        }
        catch (ServiceException e){
            throw new CommandException(e);
        }

        return router;
    }
}
