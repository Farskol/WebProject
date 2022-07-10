package com.finalwebproject.pastrtyshop.controller.command.impl;

import com.finalwebproject.pastrtyshop.controller.Router;
import com.finalwebproject.pastrtyshop.controller.command.Command;
import com.finalwebproject.pastrtyshop.entity.ClientDiscount;
import com.finalwebproject.pastrtyshop.exception.CommandException;
import com.finalwebproject.pastrtyshop.exception.ServiceException;
import com.finalwebproject.pastrtyshop.service.ClientService;
import com.finalwebproject.pastrtyshop.service.impl.ClientServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

import static com.finalwebproject.pastrtyshop.controller.LinksToPages.DISCOUNT_EDIT_PAGE;
import static com.finalwebproject.pastrtyshop.controller.Parameters.*;
import static com.finalwebproject.pastrtyshop.controller.LinksToPages.DISCOUNT_ADMIN_PAGE;

public class EditDiscountParametersCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Map<String, String> discountData = new HashMap<>();
        ClientService clientService = new ClientServiceImpl();
        ClientDiscount clientDiscount = (ClientDiscount) session.getAttribute(DISCOUNT);
        Router router = new Router();

        discountData.put(DISCOUNT_NAME, request.getParameter(DISCOUNT_NAME));
        discountData.put(DISCOUNT_VALUE, request.getParameter(DISCOUNT_VALUE));

        try {
            if(clientService.editDiscount(discountData, clientDiscount.getDiscountId())){
                router.setRouteType(Router.RouteType.REDIRECT);
                router.setPagePath(DISCOUNT_ADMIN_PAGE);
                session.setAttribute(DISCOUNT, null);
            }
            else {
                for (String key : discountData.keySet()){
                    String value = discountData.get(key);
                    switch (value){
                        case INVALID_DISCOUNT_NAME -> request.setAttribute(INVALID_DISCOUNT_NAME, true);
                        case INVALID_DISCOUNT_VALUE -> request.setAttribute(INVALID_DISCOUNT_VALUE, true);
                        case EDIT_DISCOUNT_FORM_IS_NULL -> request.setAttribute(EDIT_DISCOUNT_FORM_IS_NULL, true);
                    }
                }
                router.setPagePath(DISCOUNT_EDIT_PAGE);
            }
        }
        catch (ServiceException e){
            throw new CommandException(e);
        }

        return router;
    }
}
