package com.finalwebproject.pastrtyshop.controller.command.impl;

import com.finalwebproject.pastrtyshop.controller.Router;
import com.finalwebproject.pastrtyshop.controller.command.Command;
import com.finalwebproject.pastrtyshop.entity.Client;
import com.finalwebproject.pastrtyshop.exception.CommandException;
import com.finalwebproject.pastrtyshop.exception.ServiceException;
import com.finalwebproject.pastrtyshop.service.ClientService;
import com.finalwebproject.pastrtyshop.service.impl.ClientServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.finalwebproject.pastrtyshop.controller.LinksToPages.MAIN_PAGE;
import static com.finalwebproject.pastrtyshop.controller.Parameters.CLIENT;

public class SignOutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        ClientService clientService = new ClientServiceImpl();

        try {
            Client client = (Client) session.getAttribute(CLIENT);
            clientService.singOut(client.getClientId());
        }
        catch (ServiceException e){
            throw new CommandException(e);
        }

        session.setAttribute(CLIENT, null);
        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(MAIN_PAGE);
        return router;
    }
}
