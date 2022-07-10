package com.finalwebproject.pastrtyshop.controller.command.impl;

import com.finalwebproject.pastrtyshop.controller.Router;
import com.finalwebproject.pastrtyshop.controller.command.Command;
import com.finalwebproject.pastrtyshop.entity.Client;
import com.finalwebproject.pastrtyshop.entity.ClientStatus;
import com.finalwebproject.pastrtyshop.exception.CommandException;
import com.finalwebproject.pastrtyshop.exception.ServiceException;
import com.finalwebproject.pastrtyshop.service.ClientService;
import com.finalwebproject.pastrtyshop.service.impl.ClientServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.finalwebproject.pastrtyshop.controller.LinksToPages.LOGIN_PAGE;
import static com.finalwebproject.pastrtyshop.controller.LinksToPages.MAIN_PAGE;
import static com.finalwebproject.pastrtyshop.controller.LinksToPages.MAIN_ADMIN_PAGE;

import java.util.Optional;

import static com.finalwebproject.pastrtyshop.controller.Parameters.*;

public class SignInCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException{
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        HttpSession session = request.getSession();
        Router router = new Router();
        Optional<Client> client;
        ClientService service = new ClientServiceImpl();
        try {
            client = service.login(login, password);
            if(client.isPresent()){
                Client alsoClient = client.get();
                switch (alsoClient.getRole()){
                    case ADMIN -> {
                        router.setPagePath(MAIN_ADMIN_PAGE);
                        session.setAttribute(CLIENT, alsoClient);
                    }
                    case CLIENT -> {
                        if (alsoClient.getStatus() == ClientStatus.BANNED){
                            request.setAttribute(CLIENT_STATUS_BANNED, true);
                            router.setPagePath(LOGIN_PAGE);
                        }
                        else {
                            session.setAttribute(CLIENT, alsoClient);
                            router.setPagePath(MAIN_PAGE);
                        }
                    }
                }
            }
            else{
                request.setAttribute(INCORRECT_LOGIN_OR_PASSWORD, true);
                router.setPagePath(LOGIN_PAGE);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new CommandException(e);
        }
        return router;
    }
}
