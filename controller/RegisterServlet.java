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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import uuu.resort.domain.BloodType;
import uuu.resort.domain.Customer;
import uuu.resort.domain.ResortException;
import uuu.resort.model.CustomerService;

/**
 *
 * @author PattyTai
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register.do"})
public class RegisterServlet extends HttpServlet {

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
        //1. 取得並檢查register.html中的表單資料
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        String gender = request.getParameter("gender");
        //System.out.println("gender: " + gender);
        String email = request.getParameter("email");
        String checkCode = request.getParameter("check_code");
        String birthday = request.getParameter("birthday");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String married = request.getParameter("married");
        String bloodType = request.getParameter("blood_type");
        
        if (id == null || (id = id.trim()).length() == 0) {
            errors.add("必須輸入會員帳號");
        }

        if (name == null || (name = name.trim()).length() == 0) {
            errors.add("必須輸入會員姓名");
        }

        if (password1 == null || (password1 = password1.trim()).length() == 0
                || password2 == null || (password2 = password2.trim()).length() == 0) {
            errors.add("必須輸入會員密碼與確認密碼");
        } else if (!password1.equals(password2)) {
            errors.add("必須會員密碼與確認密碼必須一致且大小寫相符");
        }

        if (gender == null || (gender = gender.trim()).length() == 0) {
            errors.add("必須輸入會員性別");
        }

        if (email == null || (email = email.trim()).length() == 0) {
            errors.add("必須輸入電子郵件");
        }

        if (checkCode == null || (checkCode = checkCode.trim()).length() == 0) {
            errors.add("必須輸入驗證碼");
        } else {
            //....
            
        }

        if (errors.size() == 0) {
            //2. 執行商業邏輯
            try {
                Customer c = new Customer();
                c.setId(id);
                c.setName(name);
                c.setPassword(password1);
                c.setGender(gender.charAt(0));
                c.setEmail(email);

                c.setBirthday(birthday);
                c.setPhone(phone);
                c.setAddress(address);
                c.setMarried(married != null);
                c.setBloodType(
                        bloodType == null || bloodType.length() == 0 ? null : BloodType.valueOf(bloodType));

                CustomerService service = new CustomerService();
                service.register(c);

                //3.1 forward to /register_ok.jsp
                request.setAttribute("customer", c);

                RequestDispatcher dispatcher
                        = request.getRequestDispatcher("/register_ok.jsp");
                dispatcher.forward(request, response);

                return;

            } catch (ResortException ex) {
                if (ex.getCause()!=null){
                    this.log("新增失敗", ex);
                    errors.add("新增失敗: " + ex.getCause().getMessage());
                }else{
                    errors.add(ex.toString());
                }
            }catch (Exception ex) {
                errors.add(ex.toString());
            }
        }

        //3.2 forward to /register.jsp
        request.setAttribute("errors", errors);

        RequestDispatcher dispatcher
                = request.getRequestDispatcher("/register.jsp");
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
