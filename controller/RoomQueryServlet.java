package uuu.resort.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uuu.resort.domain.Room;
import uuu.resort.domain.ResortException;
import uuu.resort.model.ManageRoomService;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "RoomQueryServlet", urlPatterns = {"/query_room.do"})
public class RoomQueryServlet extends HttpServlet {

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
         
        List<String> errors = new ArrayList<>();
        request.setCharacterEncoding("UTF-8");
        
        //1. 讀取並檢查請求中的表單資料
//        HttpSession session = request.getSession();
        DATE search_startdate = request.getParameter("inputday1");
        String search_enddate = request.getParameter("inputday2");
        String room_id = request.getParameter("room_type");

        if (searchdate == null || searchdate.length() == 0) {
            errors.add("必須輸入搜尋日期");
        }

        if (room_id == null || room_id.length() == 0) {
            errors.add("必須輸入訂房房碼");
        }

        if (errors.size() == 0) {
            //2. 呼叫商業邏輯
            try {
                    ManageRoomService service = new ManageRoomService();
                    Room r1 = service.get(room_id);
                
                    if (r1 != null) {
                   
                }
            } catch (ResortException ex) {
                System.out.println("無法訂房搜尋: " + ex);
                if (ex.getCause() != null) {
                    this.log("無法訂房搜尋", ex);
                    errors.add("無法訂房搜尋!");
                } else {
                    errors.add(ex.getMessage());
                }
            }
        }

        //3.2 內部轉交給/login.jsp
        request.setAttribute("errors", errors);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/room_query_list.jsp");
        dispatcher.forward(request, response);
        return;
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
