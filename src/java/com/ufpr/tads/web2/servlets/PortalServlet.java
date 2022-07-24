/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import exceptions.DAOException;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.ArrayList;
import model.Usuario;
import model.UsuarioDAO;
import model.ConnectionFactory;

/**
 *
 * @author eduar
 */
@WebServlet(name = "PortalServlet", urlPatterns = {"/PortalServlet"})
public class PortalServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession(true);
        
        String logado = (String) session.getAttribute("logado");
        
        if(logado == null){
            request.setAttribute("msg", "Usuario não logado");
            request.setAttribute("page", "index.html");
            
            RequestDispatcher rd = getServletContext().
                    getRequestDispatcher("/ErroServlet");
            rd.forward(request, response); 
            
        }else{
            
            List<Usuario> usuarios =  new ArrayList<>();

            try(ConnectionFactory factory = new ConnectionFactory()){

                UsuarioDAO dao = new UsuarioDAO(factory.getConnection());
                usuarios = dao.buscarTodos();
            }catch(DAOException e){
                System.out.println("#### ERRO DE DAO: "+ e.getMessage());
                e.printStackTrace();
            }
        
            try ( PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                
                
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet PortalServlet</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println("<form action=\"./CadastrarUsuarioServlet\" method=\"post\">");
                out.println("<label for='nome'>Nome:</label><br>");
                out.println("<input type='text' id='nome' name='nome' value=''><br>");
                out.println("<label for='login'>Login:</label><br>");
                out.println("<input type='text' id='login' name='login' value=''><br>");
                out.println("<label for='senha'>Senha:</label><br>");
                out.println("<input type'text' id='senha' name='senha' value=><br><br>");
                out.println("<input type='submit' value='Salvar'>");
                out.println("</form>");
                out.println("<table>");
                out.println("<tr>");
                out.println("<th>"+"Nome"+"</th>");
                out.println("<th>"+"Login"+"</th>");
                out.println("<th>"+"Senha"+"</th>");
                out.println("</tr>");
                for(Usuario usuario : usuarios){
                    out.println("<tr>");
                    out.println("<td>"+usuario.getNome()+"</td>");
                    out.println("<td>"+usuario.getLogin()+"</td>");
                    out.println("<td>"+usuario.getSenha()+"</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
                out.println("<a href='./LogoutServlet'>Sair</a>");
                out.println("</body>");
                out.println("</html>");
            }
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
