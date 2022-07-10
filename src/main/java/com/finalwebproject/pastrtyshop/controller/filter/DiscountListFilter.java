package com.finalwebproject.pastrtyshop.controller.filter;

import com.finalwebproject.pastrtyshop.controller.command.Command;
import com.finalwebproject.pastrtyshop.controller.command.impl.TakeAllDessertsCommand;
import com.finalwebproject.pastrtyshop.controller.command.impl.TakeAllDiscountsCommand;
import com.finalwebproject.pastrtyshop.exception.CommandException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebFilter(urlPatterns = {"/jsp/pages/admin/clients_extra.jsp", "/jsp/pages/admin/discount_admin.jsp"})
public class DiscountListFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Command command = new TakeAllDiscountsCommand();
        try {
            command.execute(request);
        } catch (CommandException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
