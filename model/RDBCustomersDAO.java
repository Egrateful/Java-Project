/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uuu.resort.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import uuu.resort.domain.*;

/**
 *
 * @author PattyTai
 */
public class RDBCustomersDAO implements DAOInterface<String, Customer> {

    private static final String INSERT_SQL = "INSERT INTO customers "
            + "(id,name,gender,password,email,birthday,phone,address,married,blood_type,type,discount) "
            + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String UPDATE_SQL = "UPDATE customers "
            + "SET name=?,gender=?,password=?,email=?,birthday=?,phone=?,address=?,married=?,blood_type=?,type=?,discount=? "
            + "WHERE id=?";

    private static final String DELETE_SQL = "DELETE FROM customers WHERE id=?";

    private static final String SELECT_ALL_SQL
            = "SELECT id,name,gender,password,email,birthday,phone,address,married,blood_type,type,discount,status "
            + "FROM customers";
    private static final String sql
            = SELECT_ALL_SQL + " WHERE id = ?"; // AND password='123456'";

    @Override
    public void insert(Customer c) throws ResortException {
        if (c == null) {
            throw new ResortException("無法新增null客戶資料");
        }

        try (Connection connection = RDBConnection.getConnection(); //2. 建立連線
                PreparedStatement pstmt = connection.prepareStatement(INSERT_SQL); //3. 準備指令
                ) {
            //3.1 傳入參數
            pstmt.setString(1, c.getId());
            pstmt.setString(2, c.getName());
            pstmt.setString(3, String.valueOf(c.getGender()));
            pstmt.setString(4, c.getPassword());
            pstmt.setString(5, c.getEmail());
            pstmt.setDate(6, c.getBirthday() != null ? new java.sql.Date(c.getBirthday().getTime()) : null);
            pstmt.setString(7, c.getPhone());
            pstmt.setString(8, c.getAddress());
            pstmt.setBoolean(9, c.isMarried());
            pstmt.setString(10, c.getBloodType() != null ? c.getBloodType().name() : null);
            pstmt.setString(11, c.getClass().getSimpleName());
            pstmt.setInt(12, c instanceof VIP ? ((VIP) c).getDiscount() : 0);

            //4. 執行指令
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("新增客戶失敗: " + c + "發生" + ex);
            throw new ResortException("新增客戶失敗: " + c, ex);
        }
    }

    @Override
    public void update(Customer c) throws ResortException {
        if (c == null) {
            throw new ResortException("無法修改null客戶資料");
        }

        try (Connection connection = RDBConnection.getConnection(); //2. 建立連線
                PreparedStatement pstmt = connection.prepareStatement(UPDATE_SQL); //3. 準備指令
                ) {
            //3.1 傳入參數            
            pstmt.setString(1, c.getName());
            pstmt.setString(2, String.valueOf(c.getGender()));
            pstmt.setString(3, c.getPassword());
            pstmt.setString(4, c.getEmail());
            pstmt.setDate(5, c.getBirthday() != null ? new java.sql.Date(c.getBirthday().getTime()) : null);
            pstmt.setString(6, c.getPhone());
            pstmt.setString(7, c.getAddress());
            pstmt.setBoolean(8, c.isMarried());
            pstmt.setString(9, c.getBloodType() != null ? c.getBloodType().name() : null);
            pstmt.setString(10, c.getClass().getSimpleName());
            pstmt.setInt(11, c instanceof VIP ? ((VIP) c).getDiscount() : 0);
            pstmt.setString(12, c.getId());

            //4. 執行指令
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("修改客戶失敗: " + c + "發生" + ex);
            throw new ResortException("修改客戶失敗: " + c, ex);
        }
    }

    @Override
    public void delete(Customer c) throws ResortException {
        if (c == null) {
            throw new ResortException("無法刪除null客戶資料");
        }

        try (Connection connection = RDBConnection.getConnection(); //2. 建立連線
                PreparedStatement pstmt = connection.prepareStatement(DELETE_SQL); //3. 準備指令
                ) {
            //3.1 傳入參數            
            pstmt.setString(1, c.getId());

            //4. 執行指令
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("刪除客戶失敗: " + c + "發生" + ex);
            throw new ResortException("刪除客戶失敗: " + c, ex);
        }
    }

    private Customer createCustomer(String t) {
        Customer cust = null;
        if ("VIP".equals(t)) {
            cust = new VIP();
        } else {
            cust = new Customer();
        }
        return cust;
    }

    @Override
    public Customer get(String id) throws ResortException {
        Customer cust = null;
        try (Connection c = RDBConnection.getConnection();//2. 建立連線
                PreparedStatement stmt = c.prepareStatement(sql);//3. 建立指令物件
                ) {
            System.out.println(c.getCatalog());

            stmt.setString(1, id); //3.2 傳入?參數的值
            try (ResultSet rs = stmt.executeQuery();) {//4. 執行指令
                //5.處理Resultset
                while (rs.next()) {
                    try {
                        String t = rs.getString("type");
                        cust = this.createCustomer(t);
                        if (cust instanceof VIP) {
                            ((VIP) cust).setDiscount(rs.getInt("discount"));
                        }
                        cust.setId(rs.getString("id"));
                        cust.setName(rs.getString("name"));
                        cust.setPassword(rs.getString("password"));
                        cust.setGender(rs.getString("gender").charAt(0));
                        cust.setEmail(rs.getString("email"));
                        cust.setBirthday(rs.getDate("birthday"));
                       cust.setPhone(rs.getString("phone"));
                        cust.setAddress(rs.getString("address"));
                        cust.setMarried(rs.getBoolean("married"));

                        String bType = rs.getString("blood_type");
                        if (bType != null && (bType = bType.trim()).length() > 0) {
                            cust.setBloodType(BloodType.valueOf(bType));
                        }
                        System.out.println("cust:" + cust);
                    } catch (ResortException ex) {
                        System.out.println(ex);
                        throw new ResortException("查詢客戶建立指派客戶物件資料失敗", ex);
                    }
                }
            }
            //c.close();                  
        } catch (SQLException ex) {
            Logger.getLogger(RDBCustomersDAO.class.getName()).log(Level.SEVERE, "查詢客戶失敗", ex);
            throw new ResortException("查詢客戶失敗", ex);
        }

        return cust;
    }

    @Override
    public List<Customer> getAll() throws ResortException {
        List<Customer> customers = new ArrayList<>();

        //JDBC 查詢customers的所有資料>...
        try (Connection c = RDBConnection.getConnection();//2. 建立連線
                PreparedStatement stmt = c.prepareStatement(SELECT_ALL_SQL);//3. 建立指令物件
                ) {
            //System.out.println(c.getCatalog());
            //stmt.setString(1, id); //3.2 傳入?參數的值
            try (ResultSet rs = stmt.executeQuery();) {//4. 執行指令
                //5.處理Resultset
                while (rs.next()) {
                    try {
                        String t = rs.getString("type");
                        Customer cust = this.createCustomer(t);
                        if (cust instanceof VIP) {
                            ((VIP) cust).setDiscount(rs.getInt("discount"));
                        }
                        cust.setId(rs.getString("id"));
                        cust.setName(rs.getString("name"));
                        cust.setPassword(rs.getString("password"));
                        cust.setGender(rs.getString("gender").charAt(0));
                        cust.setEmail(rs.getString("email"));
                        cust.setBirthday(rs.getDate("birthday"));
                        cust.setPhone(rs.getString("phone"));
                        cust.setAddress(rs.getString("address"));
                        cust.setMarried(rs.getBoolean("married"));

                        String bType = rs.getString("blood_type");
                        if (bType != null && (bType = bType.trim()).length() > 0) {
                            cust.setBloodType(BloodType.valueOf(bType));
                        }
                        System.out.println("cust:" + cust);
                        customers.add(cust);
                    } catch (ResortException ex) {
                        System.out.println(ex);
                        throw new ResortException("查詢全部客戶建立指派客戶物件資料失敗", ex);
                    }
                }
            }
            //c.close();                  
        } catch (SQLException ex) {
            Logger.getLogger(RDBCustomersDAO.class.getName()).log(Level.SEVERE, "查詢全部客戶失敗", ex);
            throw new ResortException("查詢全部客戶失敗", ex);
        }
        return customers;
    }

}
