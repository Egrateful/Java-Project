/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uuu.totalbuy.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author PattyTai
 */
//@WebServlet(name = "imageCheckCodeServlet", urlPatterns = {"/images/check_code.jpg"})
public class ImageCheckCodeServlet extends HttpServlet {

    private int len = 6, width = 16 * 2 + 12 * len, height = 20;

    @Override
    public void init() {
        String len = this.getInitParameter("length");
        if (len != null && len.matches("\\d")) {
            this.len = Integer.parseInt(len);
            this.width = 16 * 2 + 12 * this.len;
        }
    }

    private BufferedImage generateImage(String rand, int width, int height) {
        //開始建立影像
//        BufferedImage img = ImageIO.read("");
//        int w = img.getWidth(null);
//        int h = img.getHeight(null);
//        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
//        Graphics g = bi.getGraphics();
//        g.drawImage(img, 0, 0, null);
//
        BufferedImage image
                = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();   //取得影像繪圖區

        g.setColor(getRandomColor(200, 250)); //設定繪圖區背景色

        g.fillRect(0, 0, width, height);  //在繪圖區畫個長方型

        g.setFont(new Font("Arial", Font.PLAIN, 18));//設定字體

        //產生干擾線讓影像略模糊不易識別
        Random random = new Random();
        g.setColor(getRandomColor(170, 210));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        //將認證數字顯示到影像
        g.setColor(getRandomColor(20, 140));
        //將認證數字顯示到影像
        g.drawString(rand, 16, 16);
        g.dispose();

        return image;
    }

    private Color getRandomColor(int fc, int bc) {
        //在參數設定的範圍內，隨機產生顏色
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

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
        //0. 根據請求來判斷是否更新亂數文字
        String rand = null;
        HttpSession session = request.getSession();//15章才介紹
        String s = request.getParameter("get");//更新圖片
        if (s == null) {
            rand = (String) session.getAttribute(this.getServletName());
        } else {
            session.removeAttribute(getServletName());
        }

        //1. 取得/產生亂數文字
        if (rand == null) {
            rand = "";
            Random r = new Random();
            for (int i = 0; i < len; i++) {
                int data = r.nextInt(35);
                rand += (data < 10 ? (char) (data + '0') : (char) (data - 10 + 'A'));
            }
            session.setAttribute(getServletName(), rand);
        }

        //2. 繪製圖片
        BufferedImage image = generateImage(rand, width, height);

        //3.產生http回應
        response.setContentType("image/jpeg");
        try (OutputStream out = response.getOutputStream()) {
            ImageIO.write(image, "JPEG", out);
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
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
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
