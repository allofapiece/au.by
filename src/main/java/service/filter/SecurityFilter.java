package service.filter;

import entity.Role;
import entity.User;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class SecurityFilter implements Filter {
    private PermissionStore store;
    private static final Logger LOG = Logger.getLogger(SecurityFilter.class);

    public SecurityFilter() {
        store = new PermissionStore();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.info("Security filter is init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String command = servletRequest.getParameter("command");
        if (command == null) {
            RequestDispatcher dispatcher = servletRequest.getRequestDispatcher("/jsp/main.jsp");
            dispatcher.forward(servletRequest, servletResponse);
        } else {
            User user = (User) request.getSession().getAttribute("user");

            if (user == null) {
                user = new User();
                user.addRole(Role.ANONYMOUS);
            }

            if (permission(user.getRoles(), command)) {
                filterChain.doFilter(request, response);
            } else {
                RequestDispatcher dispatcher = servletRequest.getRequestDispatcher("/jsp/user/login.jsp");
                dispatcher.forward(servletRequest, servletResponse);
            }
        }
    }

    @Override
    public void destroy() {
        LOG.info("Security filter is destroy");
    }

    public boolean permission(List<Role> roles, String command) {
        for (Role role : roles) {
            if (store.getAuthorizedRoles(command).contains(role)) {
                return true;
            }
        }

        return false;
    }

}
