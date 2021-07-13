package it.unipi.dii.inginf.dsmt.fastdoku.fastdoku;

import it.unipi.dii.inginf.dsmt.fastdoku.bean.User;
import it.unipi.dii.inginf.dsmt.fastdoku.persistence.LevelDBUser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "deleteServlet", value = "/delete-servlet")
public class DeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest (request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest (request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        LevelDBUser levelDBUser = LevelDBUser.getInstance();
        HttpSession session = request.getSession();
    //    levelDBUser.deleteUser(((User)session.getAttribute("loggedUser")).getUsername());
        PrintWriter out = response.getWriter();
        out.println("<script type=\"text/javascript\">");
        out.println("alert('User delete!');");
        out.println("document.location.href='./index.jsp';");
        out.println("</script>");
        out.close();
        session.invalidate();
        goToPage("index.jsp", request, response);
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
