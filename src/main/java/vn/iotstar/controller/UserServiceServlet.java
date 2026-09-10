package vn.iotstar.controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import org.springframework.web.context.support.WebApplicationContextUtils;
import vn.iotstar.service.UserService;

/**
 * Gives container-created @WebServlet classes access to Spring-managed services.
 */
abstract class UserServiceServlet extends HttpServlet {
    protected UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = WebApplicationContextUtils
                .getRequiredWebApplicationContext(config.getServletContext())
                .getBean(UserService.class);
    }
}
