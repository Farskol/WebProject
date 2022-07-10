package com.finalwebproject.pastrtyshop.controller.command.impl;

import com.finalwebproject.pastrtyshop.controller.Router;
import com.finalwebproject.pastrtyshop.controller.command.Command;
import com.finalwebproject.pastrtyshop.entity.Client;
import com.finalwebproject.pastrtyshop.entity.ClientRole;
import com.finalwebproject.pastrtyshop.exception.CommandException;
import com.finalwebproject.pastrtyshop.exception.ServiceException;
import com.finalwebproject.pastrtyshop.service.ClientService;
import com.finalwebproject.pastrtyshop.service.impl.ClientServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.finalwebproject.pastrtyshop.controller.Parameters.CLIENT_ROLE;
import static com.finalwebproject.pastrtyshop.controller.Parameters.ADMIN;
import static com.finalwebproject.pastrtyshop.controller.Parameters.CLIENT_EXTRA;
import static com.finalwebproject.pastrtyshop.controller.Parameters.CLIENT2;
import static com.finalwebproject.pastrtyshop.controller.LinksToPages.CLIENT_EXTRA_PAGE;

public class ChangeClientRoleCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        ClientService clientService = new ClientServiceImpl();
        String clientRole = request.getParameter(CLIENT_ROLE);
        Client client = (Client) session.getAttribute(CLIENT_EXTRA);
        Router router = new Router();

        try {
            if (clientRole.equals(ADMIN)){
                clientService.changeClientRole(ClientRole.ADMIN, client.getClientId());
                client.setRole(ClientRole.ADMIN);
            }
            else {
                if (clientRole.equals(CLIENT2)){
                    clientService.changeClientRole(ClientRole.CLIENT, client.getClientId());
                    client.setRole(ClientRole.CLIENT);
                }
            }
            session.setAttribute(CLIENT_EXTRA, client);
        }
        catch (ServiceException e){
            throw new CommandException(e);
        }

        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(CLIENT_EXTRA_PAGE);
        return router;
    }
}
