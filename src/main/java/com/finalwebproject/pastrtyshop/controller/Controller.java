package com.finalwebproject.pastrtyshop.controller;

import java.io.*;
import java.util.Optional;

import com.finalwebproject.pastrtyshop.controller.command.Command;
import com.finalwebproject.pastrtyshop.controller.factory.CommandType;
import com.finalwebproject.pastrtyshop.exception.CommandException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.finalwebproject.pastrtyshop.controller.Parameters.COMMAND;
import static com.finalwebproject.pastrtyshop.controller.LinksToPages.ERROR_500;

@WebServlet(urlPatterns = {"/controller"})
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();


    public void init() {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processingRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processingRequest(request, response);
    }

    private void processingRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String commandType = request.getParameter(COMMAND);
        Optional<Command> command = CommandType.provideCommand(commandType);
        try{
            Router router;
            if (command.isPresent()){
                router = command.get().execute(request);
                String page = router.getPagePath();
                if(router.getRouteType() == Router.RouteType.FORWARD){
                    request.getRequestDispatcher(page).forward(request,response);
                }
                else {
                    response.sendRedirect(page);
                }
            }
            else {
                response.sendRedirect(ERROR_500);
            }
        }
        catch (CommandException e){
            logger.error(e.getMessage());
            response.sendRedirect(ERROR_500);
        }
    }

    public void destroy() {
    }
}