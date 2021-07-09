package it.unipi.dii.inginf.dsmt.fastdoku.fastdoku;

import it.unipi.dii.inginf.dsmt.fastdoku.bean.User;
import it.unipi.dii.inginf.dsmt.fastdoku.persistence.LevelDBUser;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

@WebServlet(name = "accessServlet", value = "/access-servlet")
public class AccessServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest (request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest (request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        LevelDBUser levelDBUser = LevelDBUser.getInstance();
        HttpSession session = request.getSession();

        //login
        if (request.getParameter("signIn") != null) {

            User user = levelDBUser.login(username, password);
            if (user != null) {
                session.setAttribute("loggedUser", user);
                goToPage("main.jsp", request, response);
            }
            else {
                session.setAttribute("error", "Username or password wrong");
                goToPage("index.jsp", request, response);
            }
        }
        else { // If the user has required a register operation
            if (levelDBUser.isRegistered(username)){ //The username is already in use
                PrintWriter out = response.getWriter();
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Username not valid! Username already in use.');");
                out.println("document.location.href='./signUp.jsp';");
                out.println("</script>");
                out.close();
            } else {
                levelDBUser.signin(username, password);
                session.setAttribute("loggedUser", new User(username, password));
                goToPage("main.jsp", request, response);
            }
        }
    }

    /**
     * Function that with the dispatcher send the user to another page
     * @param page          Page to show
     * @param request       Request object
     * @param response      Response object
     * @throws ServletException
     * @throws IOException
     */
    private void goToPage (String page, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);
        if (requestDispatcher != null)
            requestDispatcher.include(request, response);
    }

}