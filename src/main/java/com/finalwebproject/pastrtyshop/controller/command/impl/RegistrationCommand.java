package com.finalwebproject.pastrtyshop.controller.command.impl;

import com.finalwebproject.pastrtyshop.controller.Router;
import com.finalwebproject.pastrtyshop.controller.command.Command;
import com.finalwebproject.pastrtyshop.exception.CommandException;
import com.finalwebproject.pastrtyshop.exception.ServiceException;
import com.finalwebproject.pastrtyshop.service.ClientService;
import com.finalwebproject.pastrtyshop.service.impl.ClientServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static com.finalwebproject.pastrtyshop.controller.Parameters.FIRST_NAME;
import static com.finalwebproject.pastrtyshop.controller.Parameters.SECOND_NAME;
import static com.finalwebproject.pastrtyshop.controller.Parameters.LOGIN;
import static com.finalwebproject.pastrtyshop.controller.Parameters.PASSWORD;
import static com.finalwebproject.pastrtyshop.controller.Parameters.PHONE_NUMBER;
import static com.finalwebproject.pastrtyshop.controller.Parameters.EMAIL;
import static com.finalwebproject.pastrtyshop.controller.LinksToPages.MAIN_PAGE;
import static com.finalwebproject.pastrtyshop.controller.LinksToPages.REGISTRATION_PAGE;
import static com.finalwebproject.pastrtyshop.controller.Parameters.INVALID_FIRST_NAME;
import static com.finalwebproject.pastrtyshop.controller.Parameters.INVALID_SECOND_NAME;
import static com.finalwebproject.pastrtyshop.controller.Parameters.INVALID_LOGIN;
import static com.finalwebproject.pastrtyshop.controller.Parameters.INVALID_PASSWORD;
import static com.finalwebproject.pastrtyshop.controller.Parameters.INVALID_EMAIL;
import static com.finalwebproject.pastrtyshop.controller.Parameters.INVALID_PHONE_NUMBER;
import static com.finalwebproject.pastrtyshop.controller.Parameters.NOT_UNIQ_LOGIN;
import static com.finalwebproject.pastrtyshop.controller.Parameters.NOT_UNIQ_EMAIL;
import static com.finalwebproject.pastrtyshop.controller.Parameters.NOT_UNIQ_PHONE_NUMBER;




public class RegistrationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ClientService clientService = new ClientServiceImpl();
        Map<String,String> clientData = new HashMap<>();
        clientData.put(FIRST_NAME, request.getParameter(FIRST_NAME));
        clientData.put(SECOND_NAME, request.getParameter(SECOND_NAME));
        clientData.put(LOGIN, request.getParameter(LOGIN));
        clientData.put(PASSWORD, request.getParameter(PASSWORD));
        clientData.put(PHONE_NUMBER, request.getParameter(PHONE_NUMBER));
        clientData.put(EMAIL, request.getParameter(EMAIL));
        Router router = new Router();
        try {
            if(clientService.clientRegistration(clientData)){
                router.setRouteType(Router.RouteType.REDIRECT);
                router.setPagePath(MAIN_PAGE);
            }
            else {
                for (String key : clientData.keySet()) {
                    String value = clientData.get(key);
                    switch (value) {
                        case INVALID_FIRST_NAME -> request.setAttribute(INVALID_FIRST_NAME, true);
                        case INVALID_SECOND_NAME -> request.setAttribute(INVALID_SECOND_NAME, true);
                        case INVALID_LOGIN -> request.setAttribute(INVALID_LOGIN,true);
                        case INVALID_PASSWORD -> request.setAttribute(INVALID_PASSWORD, true);
                        case INVALID_EMAIL -> request.setAttribute(INVALID_EMAIL,true);
                        case INVALID_PHONE_NUMBER -> request.setAttribute(INVALID_PHONE_NUMBER, true);
                        case NOT_UNIQ_LOGIN -> request.setAttribute(NOT_UNIQ_LOGIN, true);
                        case NOT_UNIQ_EMAIL -> request.setAttribute(NOT_UNIQ_EMAIL, true);
                        case NOT_UNIQ_PHONE_NUMBER -> request.setAttribute(NOT_UNIQ_PHONE_NUMBER, true);
                    }
                }
                router.setPagePath(REGISTRATION_PAGE);
            }
        }
        catch (ServiceException e){
            logger.log(Level.ERROR, e.getMessage());
            throw new CommandException(e);
        }
        return router;
    }
}
