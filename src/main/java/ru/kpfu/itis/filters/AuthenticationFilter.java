package ru.kpfu.itis.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Alisher Khabibullin (Alalaq) <alisher.khabibullin@gmail.com>
 */

@WebFilter(urlPatterns = {"/profiles/", "/profile/", "/settings/", "/personal_account/"}, filterName = "/AuthenticationFilter")
public class AuthenticationFilter implements Filter {
    private FilterConfig filterConfig;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        if (session.getAttribute("user") != null){
            chain.doFilter(req, resp);
        }
        else {
            resp.getWriter().print(Files.readString(Path.of("C:\\Users\\muzik\\Desktop\\Semester_work\\src\\main\\webapp\\WEB-INF\\views\\AuthenticationExc.jsp")));

        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy(){
        filterConfig = null;
    }

}
