
package Controller;

import Account.AccountDAO;
import Account.AccountDTO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;


public class LoginServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String URL = "login.jsp";
        try{
           String username = request.getParameter("txtUsername");
           String password = request.getParameter("txtPassword");
           AccountDAO dao = new AccountDAO();
           AccountDTO account = null;
            try {
               account = dao.getAccount(username, password);
            } catch (SQLException | ClassNotFoundException ex) {             
            }
            if (account == null){
                request.setAttribute("incorrectUsernamePassword", true);
              // thuoc tinh ma luu ben trong requestscope no chi ton tai ngan han, tu controller len view la het
            } else {
                HttpSession session = request.getSession();
              // lay ra session hien tai, con neu khong co thi tao moi
                // vung nho lon hon vung nho cu rat nhieu, no duy tri tu luc cac ban deploy ung dung cho toi het luon
                session.setAttribute("account", account);
                URL = "welcome.jsp";
            }
            
            
        }finally{
            RequestDispatcher rd = request.getRequestDispatcher(URL);
            rd.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
