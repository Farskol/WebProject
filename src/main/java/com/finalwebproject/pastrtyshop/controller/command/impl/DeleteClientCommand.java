package com.finalwebproject.pastrtyshop.controller.command.impl;

import com.finalwebproject.pastrtyshop.controller.Router;
import com.finalwebproject.pastrtyshop.controller.command.Command;
import com.finalwebproject.pastrtyshop.exception.CommandException;
import com.finalwebproject.pastrtyshop.exception.ServiceException;
import com.finalwebproject.pastrtyshop.service.ClientService;
import com.finalwebproject.pastrtyshop.service.impl.ClientServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import static com.finalwebproject.pastrtyshop.controller.Parameters.CLIENT_ID;
import static com.finalwebproject.pastrtyshop.controller.LinksToPages.CLIENTS_ADMIN_PAGE;
public class DeleteClientCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ClientService clientService = new ClientServiceImpl();
        int clientId = Integer.parseInt(request.getParameter(CLIENT_ID));
        Router router = new Router();

        try {
            clientService.deleteClient(clientId);
        }
        catch (ServiceException e){
            throw new CommandException(e);
        }

        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(CLIENTS_ADMIN_PAGE);
        return router;
    }
}
