/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uuu.resort.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import uuu.resort.domain.Customer;
import uuu.resort.domain.Booking;
import uuu.resort.domain.BookingItem;
//import uuu.resort.domain.PaymentType;
//import uuu.resort.domain.ShippingType;
import uuu.resort.domain.ResortException;

/**
 *
 * @author PattyTai
 */
public class RDBOrdersDAO implements DAOInterface<Integer, Booking> {

    private static final String TABLE = "orders";

    private static final String INSERT_SQL_AUTO_ID = "INSERT INTO " + TABLE
            + " (customer_id, order_date, payment_fee, payment_note,"
            + " VALUES(?,?,?,?)";
//            + " shipping_type, shipping_fee, shipping_note,"
//            + " receiver_name, receiver_email,receiver_phone,shipping_address)"
//            + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String INSERT_SQL_ORDER_ITEM = "INSERT INTO order_items"
            + " (order_id, product_id, price, discount)"
            + " VALUES(?,?,?,?)";

//            + " (order_id, product_id, price, discount, quantity)"
//            + " VALUES(?,?,?,?,?)";

//    private static final String SELECT_ALL_COLUMN_SQL = "SELECT orders.id, customer_id, name, order_date,"
//            + " payment_type, payment_fee, shipping_type, shipping_fee, orders.status";
          
    private static final String SELECT_ALL_COLUMN_SQL = "SELECT orders.id, customer_id, name, order_date"
//            + " payment_type, payment_fee, shipping_type, shipping_fee, orders.status";
            + " payment_type, orders.status";

    private static final String SELECT_ALL_SQL_FOR_CUSTOMER = SELECT_ALL_COLUMN_SQL
            + ", SUM(price*(100-order_items.discount)/100*quantity) as total_amount"
            + " FROM " + TABLE
            + " JOIN customers ON orders.customer_id=customers.id"
            + " JOIN order_items ON orders.id=order_items.order_id"
            + " WHERE customer_id=? GROUP BY orders.id";

    private static final String SELECT_SQL = SELECT_ALL_COLUMN_SQL
            + " FROM " + TABLE
            + " JOIN order_items ON orders.id=order_items.order_id"
            + " JOIN rooms ON order_items.room_id = rooms.id"
//            + " JOIN products ON order_items.product_id = products.id"
            + " WHERE id=?";

    @Override
    public void insert(Booking data) throws ResortException {
        if (data == null || data.getOrderItemList() == null) {
            throw new IllegalArgumentException("新增訂單及明細不得為null!");
        }

        try (Connection connection = RDBConnection.getConnection(); //1. 取得連線
                PreparedStatement pstmt
                = //2.1建立指令
                connection.prepareStatement(INSERT_SQL_AUTO_ID, Statement.RETURN_GENERATED_KEYS);) {
            connection.setAutoCommit(false);//begin tran
            try {
                pstmt.setString(1, data.getCustomer().getId());
                pstmt.setTimestamp(2, new java.sql.Timestamp(data.getOrderTime().getTime()));
//                pstmt.setInt(3, data.getPaymentType().ordinal());
//                pstmt.setDouble(4, data.getPaymentFee());
//                pstmt.setString(5, data.getPaymentNote());
//                pstmt.setInt(6, data.getShippingType().ordinal());
//                pstmt.setDouble(7, data.getShippingFee());
//                pstmt.setString(8, data.getShippingNote());
//                pstmt.setString(9, data.getReceiverName());
//                pstmt.setString(10, data.getReceiverEmail());
//                pstmt.setString(11, data.getReceiverPhone());
//                pstmt.setString(12, data.getShippingAddress());

                int rows = pstmt.executeUpdate();
                assert (rows == 1) : "新增訂單資料結構有誤:" + data;
                try (ResultSet rs = pstmt.getGeneratedKeys();) {
                    if (rs.next()) {
                        data.setId(rs.getInt(1));
                    }
                }
                try (PreparedStatement pstmt2 = connection.prepareStatement(INSERT_SQL_ORDER_ITEM);) {
                    for (BookingItem item : data.getOrderItemList()) {
                        pstmt2.setInt(1, data.getId());
                        pstmt2.setString(2, item.getRoom().getName());
                        pstmt2.setDouble(3, item.getPrice());
                        pstmt2.setInt(4, item.getDiscount());
//                        pstmt2.setInt(5, item.getQuantity());
                        pstmt2.executeUpdate();
                    }
                }
                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
                throw ex;
            }finally{
                 connection.setAutoCommit(true); //for connection pool
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "新增訂單失敗: " + data, ex);
            throw new ResortException("新增訂單失敗!", ex);
        }
    }

    @Override
    public void update(Booking data) throws ResortException {
        throw new UnsupportedOperationException("不支援訂單修改"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Booking data) throws ResortException {
        throw new UnsupportedOperationException("不支援訂單刪除"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Booking get(Integer key) throws ResortException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Booking> getAll() throws ResortException {
        throw new UnsupportedOperationException("不支援查詢全部的訂單刪除");  //To change body of generated methods, choose Tools | Templates.
    }

    List<Booking> getOrdersByCustomers(Customer c) throws ResortException {
        List<Booking> list = new ArrayList<>();
        try (Connection connection = RDBConnection.getConnection(); //1. 取得連線
                PreparedStatement pstmt = connection.prepareStatement(SELECT_ALL_SQL_FOR_CUSTOMER); //2. 建立指令
                ) {
            pstmt.setString(1, c.getId());            
            try(ResultSet rs = pstmt.executeQuery()){
                while (rs.next()) {
                    Booking o = new Booking();
                    o.setId(rs.getInt("id"));
                    o.setCustomer(c);
                    o.setOrderTime(rs.getTimestamp("order_date"));
//                    o.setPaymentType(PaymentType.values()[rs.getInt("payment_type")]);
//                    o.setPaymentFee(rs.getDouble("payment_fee"));
//                    o.setShippingType(ShippingType.values()[rs.getInt("shipping_type")]);
//                    o.setShippingFee(rs.getDouble("shipping_fee"));
//                    o.setStatus(Booking.Status.values()[rs.getInt("status")]);
                    o.setTotalAmount(rs.getDouble("total_amount"));                    
                    list.add(o);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "查詢客戶歷史訂單失敗!", ex);
            throw new ResortException("查詢客戶歷史訂單失敗!", ex);
        }
        return list;
    }
}
