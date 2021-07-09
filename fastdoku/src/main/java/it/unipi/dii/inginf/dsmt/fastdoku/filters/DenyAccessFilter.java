package it.unipi.dii.inginf.dsmt.fastdoku.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This filter is used to avoid access if the user is not logged in.
 * If the user cannot access an alert message is visualized.
 */
@WebFilter(
        filterName = "DenyAccessFilter",
        servletNames = {"UpdatePointsServlet", "LogOutServlet"},
        urlPatterns = {"/main.jsp", "/sudoku.jsp"}
        )
public class DenyAccessFilter implements Filter {

    private ServletContext context;

    public void init(FilterConfig fConfig) throws ServletException {
        this.context = fConfig.getServletContext();
        this.context.log("Deny access filter initialized");
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;


        HttpSession session = request.getSession();
        if (session.getAttribute("loggedUser") != null)
            chain.doFilter(req, resp);
        else {
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Invalid access!');");
            out.println("document.location.href='./index.jsp';"); // forced logout
            out.println("</script>");
            out.close();
        }

    }

    public void destroy() {
        //close resources
    }

}
