package com.finalwebproject.pastrtyshop.controller.command;

import com.finalwebproject.pastrtyshop.controller.Router;
import com.finalwebproject.pastrtyshop.exception.CommandException;
import com.finalwebproject.pastrtyshop.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;

public interface Command {
    Router execute(HttpServletRequest request) throws CommandException;
}
