package it.unipi.dii.inginf.dsmt.fastdoku.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

/**
 * This filter has the task of checking that the
 * username and password match some patterns.
 * It is sufficient that only one of the pattern
 * is not match to prevent registration.
 */
@WebFilter(
        filterName = "MatchPatternFilter",
        servletNames = {"accessServlet"},
        urlPatterns = {"/access-servlet"}
)
public class MatchPatternFilter implements Filter {
    private ServletContext context;

    public void init(FilterConfig fConfig) throws ServletException {
        this.context = fConfig.getServletContext();
        this.context.log("Match pattern filter initialized");
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repeatpassword = request.getParameter("repeatpassword");

        PrintWriter out = response.getWriter();
        if (request.getParameter("signUp") != null) {

            if (!Pattern.matches("^[a-zA-Z0-9_.]*$", username)) {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Username not valid! Please use alphanumeric chars, underscore and dot.');");
                out.println("document.location.href='./signUp.jsp';");
                out.println("</script>");
            } else if(password.length() < 6) {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Password not valid! Password too short.');");
                out.println("document.location.href='./signUp.jsp';");
                out.println("</script>");
            } else if(!password.equals(repeatpassword)) {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Password not valid! Make sure the password is correct.');");
                out.println("document.location.href='./signUp.jsp';");
                out.println("</script>");
            } else {
                chain.doFilter(req, resp);
            }
        } else if(request.getParameter("signIn") == null) {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Access Denied! If you want to continue log in.');");
            out.println("document.location.href='./index.jsp';");
            out.println("</script>");
        }else {
            chain.doFilter(req, resp);
        }

        out.close();
    }

    public void destroy() {
        //close resources
    }
}
