package com.finalwebproject.pastrtyshop.controller.command.impl;

import com.finalwebproject.pastrtyshop.controller.Router;
import com.finalwebproject.pastrtyshop.controller.command.Command;
import com.finalwebproject.pastrtyshop.exception.CommandException;
import com.finalwebproject.pastrtyshop.exception.ServiceException;
import com.finalwebproject.pastrtyshop.service.ClientService;
import com.finalwebproject.pastrtyshop.service.impl.ClientServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import static com.finalwebproject.pastrtyshop.controller.LinksToPages.DISCOUNT_ADMIN_PAGE;
import static com.finalwebproject.pastrtyshop.controller.LinksToPages.DISCOUNT_CREATE_PAGE;
import static com.finalwebproject.pastrtyshop.controller.Parameters.*;
import static com.finalwebproject.pastrtyshop.controller.Parameters.EDIT_DISCOUNT_FORM_IS_NULL;

public class CreateDiscountCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Map<String, String> discountData = new HashMap<>();
        ClientService clientService = new ClientServiceImpl();
        Router router = new Router();

        discountData.put(DISCOUNT_NAME, request.getParameter(DISCOUNT_NAME));
        discountData.put(DISCOUNT_VALUE, request.getParameter(DISCOUNT_VALUE));

        try {
            if(clientService.createDiscount(discountData)){
                router.setRouteType(Router.RouteType.REDIRECT);
                router.setPagePath(DISCOUNT_ADMIN_PAGE);
            }
            else {
                for (String key : discountData.keySet()){
                    String value = discountData.get(key);
                    switch (value){
                        case INVALID_DISCOUNT_NAME -> request.setAttribute(INVALID_DISCOUNT_NAME, true);
                        case INVALID_DISCOUNT_VALUE -> request.setAttribute(INVALID_DISCOUNT_VALUE, true);
                    }
                }
                router.setPagePath(DISCOUNT_CREATE_PAGE);
            }
        }
        catch (ServiceException e){
            throw new CommandException(e);
        }

        return router;
    }

}
