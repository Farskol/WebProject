package com.finalwebproject.pastrtyshop.controller.command.impl;

import com.finalwebproject.pastrtyshop.controller.Router;
import com.finalwebproject.pastrtyshop.controller.command.Command;
import com.finalwebproject.pastrtyshop.entity.Client;
import com.finalwebproject.pastrtyshop.entity.ClientDiscount;
import com.finalwebproject.pastrtyshop.exception.CommandException;
import com.finalwebproject.pastrtyshop.exception.ServiceException;
import com.finalwebproject.pastrtyshop.service.ClientService;
import com.finalwebproject.pastrtyshop.service.impl.ClientServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import static com.finalwebproject.pastrtyshop.controller.Parameters.CLIENT_DISCOUNT;
import static com.finalwebproject.pastrtyshop.controller.Parameters.CLIENT_EXTRA;
import static com.finalwebproject.pastrtyshop.controller.LinksToPages.CLIENT_EXTRA_PAGE;

public class ChangeClientDiscountCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        ClientService clientService = new ClientServiceImpl();
        Router router = new Router();
        Client client = (Client) session.getAttribute(CLIENT_EXTRA);

        try {
            if (!request.getParameter(CLIENT_DISCOUNT).isEmpty()){
                Optional<ClientDiscount> clientDiscount = clientService.takeClientDiscount(Integer.parseInt(request.getParameter(CLIENT_DISCOUNT)));
                clientService.changeClientDiscount(clientDiscount.get(), client.getClientId());
                client.setDiscount(clientDiscount.get());
                session.setAttribute(CLIENT_EXTRA,client);
            }
            else {
                clientService.changeClientDiscount(null, client.getClientId());
                client.setDiscount(new ClientDiscount(0,1,"Нет скидки"));
            }
        }
        catch (ServiceException e){
            throw new CommandException(e);
        }

        router.setRouteType(Router.RouteType.REDIRECT);
        router.setPagePath(CLIENT_EXTRA_PAGE);
        return router;
    }
}
