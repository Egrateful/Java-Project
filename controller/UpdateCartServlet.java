/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uuu.resort.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uuu.resort.domain.Customer;
import uuu.resort.domain.Room;
import uuu.resort.domain.ShoppingCart;

/**
 *
 * @author PattyTai
 */
@WebServlet(name = "UpdateCartServlet", urlPatterns = {"/update_cart.do"})
public class UpdateCartServlet extends HttpServlet {

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

        HttpSession session = request.getSession(false);
        if (session != null) {
            ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
            if (cart != null) {
//                Customer user = (Customer) session.getAttribute("user");
//                if (user != null) {
//                    cart.setCustomer(user);
//                }
                
                Set<Room> removedSet = new HashSet<>();
                for (Room p : cart.getKeySet()) {
                    String delete = request.getParameter("delete_" + p.getId());
                    if (delete != null) {
                        //cart.remove(p);
                        removedSet.add(p);
                    } else {
                        cart.update(p);
//                        String quantity = request.getParameter("quantity_" + p.getId());
//                        if(quantity!=null && quantity.matches("\\d+")){
//                            int q = Integer.parseInt(quantity);
//                            cart.update(p, q);
//                        }
                    }                    
                }
                
                for(Room p:removedSet){
                    cart.remove(p);
                }
            }
        }
        response.sendRedirect(request.getContextPath() + "/cart.jsp");
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
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
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
