/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uuu.resort.controller;

import java.io.IOException;
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
import uuu.resort.domain.Customer;
import uuu.resort.domain.ResortException;
import uuu.resort.model.CustomerService;

/**
 *
 * @author PattyTai
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login.do"})
public class LoginServlet extends HttpServlet {

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

        ServletContext context = this.getServletContext();

        List<String> errors = new ArrayList<>();

        //1. 讀取並檢查請求中的表單資料
        HttpSession session = request.getSession();
        String id = request.getParameter("id");
        String memory = request.getParameter("memory");
        String password = request.getParameter("password");
        String checkCode = request.getParameter("check_code");

        if (id == null || id.length() == 0) {
            errors.add("必須輸入帳號");
        }

        if (password == null || password.length() == 0) {
            errors.add("必須輸入密碼");
        }

//        if (checkCode == null || checkCode.length() == 0) {
//            errors.add("必須輸入驗證碼");
//        } else {
//            //檢查檢核碼是否符合圖片中內容
//            String oldCheckCode = (String) session.getAttribute("LoginImageCheckCodeServlet");
//            if (!checkCode.equalsIgnoreCase(oldCheckCode)) {
//                errors.add("驗證碼不正確");
//            } else {
//                session.removeAttribute("LoginImageCheckCodeServlet");
//            }
//        }

        if (errors != null && errors.size() == 0) {
            //2. 呼叫商業邏輯
            CustomerService service = new CustomerService();
            try {
                Customer c = service.login(id, password);
                System.out.println("c:" + c);
                if (c != null) {
                    ServletContext application = this.getServletContext();

                    //session.removeAttribute("LoginImageCheckCodeServlet");
                    //計算登入人次
                    Integer count = (Integer) application.getAttribute("app.login.count");
                    if (count == null) {
                        count = 30005;
                    } else {
                        count++;
                    }
                    application.setAttribute("app.login.count", count);
                    //session.setMaxInactiveInterval(10); //10 seconds

                    //add cookie
                    Cookie cookie = new Cookie("user_id", c.getId());
                    cookie.setMaxAge(0); //delete cookie
                    if (memory != null) {
                        cookie.setMaxAge(7 * 24 * 60 * 60); //7 days
                    }
                    response.addCookie(cookie);
                    System.out.println(cookie);

                    Cookie cookie2 = new Cookie("memory", "checked");
                    cookie2.setMaxAge(0); //delete cookie
                    if (memory != null) {
                        cookie2.setMaxAge(7 * 24 * 60 * 60); //7 days
                    }
                    response.addCookie(cookie2);

                    //3.1.old forward給首頁
                    //request.setAttribute("user", c);                   
                    //RequestDispatcher dispatcher = request.getRequestDispatcher("/");                    
                    //dispatcher.forward(request, response);      
                    //3.1 redirect給首頁
                    session.setAttribute("user", c);
                    response.sendRedirect(request.getContextPath() + "/login_ok.jsp");
                    return;
                }
            } catch (ResortException ex) {
                System.out.println("無法登入: " + ex);
                if (ex.getCause() != null) {
                    this.log("無法登入", ex);
                    errors.add("無法登入，請聯絡系統管理員!");
                } else {
                    errors.add(ex.getMessage());
                }
            }
        }

        //3.2 內部轉交給/login.jsp
        request.setAttribute("errors", errors);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
        dispatcher.forward(request, response);
        return;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * // * Handles the HTTP <code>GET</code> method. // * // * @param request
     * servlet request // * @param response servlet response // * @throws
     * ServletException if a servlet-specific error occurs // * @throws
     * IOException if an I/O error occurs //
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
