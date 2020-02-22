package app.web.filter;

import javax.faces.context.FacesContext;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter({"/views/index.jsf", "/views/register.jsf", "/views/login.jsf"})
public class Authorization implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String id = (String) ((HttpServletRequest) servletRequest).getSession().getAttribute("userId");




        if (id != null){
            ((HttpServletResponse) servletResponse).sendRedirect("/views/home.jsf");
            return;
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}