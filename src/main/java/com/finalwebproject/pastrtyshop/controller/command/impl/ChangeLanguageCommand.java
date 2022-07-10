package com.finalwebproject.pastrtyshop.controller.command.impl;

import com.finalwebproject.pastrtyshop.controller.Router;
import com.finalwebproject.pastrtyshop.controller.command.Command;
import com.finalwebproject.pastrtyshop.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.finalwebproject.pastrtyshop.controller.Parameters.PAGE_LANGUAGE;
import static com.finalwebproject.pastrtyshop.controller.Parameters.LANGUAGE_RU;
import static com.finalwebproject.pastrtyshop.controller.Parameters.LANGUAGE_EN;
import static com.finalwebproject.pastrtyshop.controller.Parameters.CURRENT_PAGE;

public class ChangeLanguageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String pageLanguage;
        Router router = new Router();

        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath((String) session.getAttribute(CURRENT_PAGE));
        if (session.getAttribute(PAGE_LANGUAGE) != null){
            pageLanguage = (String)session.getAttribute(PAGE_LANGUAGE);
        }
        else {
            session.setAttribute(PAGE_LANGUAGE,LANGUAGE_EN);
            return router;
        }

        if(pageLanguage.equals(LANGUAGE_RU)){
            session.setAttribute(PAGE_LANGUAGE,LANGUAGE_EN);
        }
        else {
            session.setAttribute(PAGE_LANGUAGE,LANGUAGE_RU);
        }

        return router;
    }
}
