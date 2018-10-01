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
import uuu.resort.domain.Promotion;
import uuu.resort.domain.Room;
import uuu.resort.domain.ResortException;

/**
 *
 * @author Angie Lin
 */
class RDBRoomsDAO implements DAOInterface<String, Room> {

    private static final String TABLE = "rooms";

//    private static final String INSERT_SQL_AUTO_ID = "INSERT INTO " + TABLE
//            + " (name, unit_price, date_thisMonth, date_nextMonth, url, id)"
//            + " VALUES(?,?,?,?,?)";

    private static final String INSERT_SQL = "INSERT INTO " + TABLE
            + " (id, name, unit_price, querydate, url, unoccupied, type, discount)"
            + " VALUES(?,?,?,?,?,?,?,?)";

    private static final String UPDATE_SQL = "UPDATE " + TABLE
            + " SET name=?, unit_price=?, querydate=?, url=?, unoccupied=?, type=?, discount=?"
            + " WHERE id=?";
    private static final String DELETE_SQL = "DELETE FROM " + TABLE + " WHERE id=?";

    private static final String SELECT_ALL_SQL = "SELECT id, name, unit_price, querydate, url, unoccupied, type, discount"
            + " FROM " + TABLE;

    private static final String SELECT_SQL = SELECT_ALL_SQL + " WHERE id=?";

    private static final String SELECT_BY_NAME = SELECT_ALL_SQL + " WHERE name LIKE ?";
    
    
    @Override
    public void insert(Room data) throws ResortException {
        if (data == null) {
            throw new IllegalArgumentException("新增房型不得為null!");
        }
        try (Connection connection = RDBConnection.getConnection(); //1. 取得連線
                PreparedStatement pstmt = connection.prepareStatement(INSERT_SQL);//2. 準備指令
                ) { //3.1 傳入參數
                pstmt.setString(1, data.getId());    
                pstmt.setString(2, data.getName());
                pstmt.setDouble(3, data instanceof Promotion ? ((Promotion) data).getListPrice() : data.getUnitPrice());
                java.sql.Date sqlDate = new java.sql.Date(data.getQuerydate().getTime());
                pstmt.setDate(4, sqlDate);
                System.out.println("房間日期: " + sqlDate);
                pstmt.setString(5, data.getUrl());
                pstmt.setBoolean(6,data.isUnoccupied());
                pstmt.setString(7, data.getClass().getSimpleName());
                if (data instanceof Promotion) {
                    pstmt.setInt(8, ((Promotion) data).getDiscount());
                } else {
                    pstmt.setInt(8, 0);
                }
                int rows = pstmt.executeUpdate();
                assert (rows == 1) : "新增房型資料結構有誤:" + data;
//            if (r.getId() <= 0) {
//                try (ResultSet rs = pstmt.getGeneratedKeys();) {
//                    if (rs.next()) {
//                        data.setId(rs.getInt(1));
//                    }
//                }
//            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "新增房型失敗: " + data, ex);
            throw new ResortException("新增房型失敗!", ex);
        }
    }
        

    @Override
    public void update(Room data) throws ResortException {
        if (data == null) {
            throw new IllegalArgumentException("修改房型不得為null!");
        }

        try (Connection connection = RDBConnection.getConnection(); //1. 取得連線
                PreparedStatement pstmt = connection.prepareStatement(UPDATE_SQL);) {
                pstmt.setString(1, data.getId());
                pstmt.setString(2, data.getName());
                pstmt.setDouble(3, data instanceof Promotion ? ((Promotion) data).getListPrice() : data.getUnitPrice());
                java.sql.Date sqlDate = new java.sql.Date(data.getQuerydate().getTime());
                pstmt.setDate(4, sqlDate);
                pstmt.setString(5, data.getUrl());
                pstmt.setBoolean(6,data.isUnoccupied());
                pstmt.setString(7, data.getClass().getSimpleName());
               
                if (data instanceof Promotion) {
                    pstmt.setInt(8, ((Promotion) data).getDiscount());
                } else {
                    pstmt.setInt(8, 0);
                    System.out.println("no discount");
                }
                int rows = pstmt.executeUpdate();
                assert (rows == 1) : "修改房型資料結構有誤:" + data;
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "修改房型失敗: " + data, ex);
            throw new ResortException("修改房型失敗!", ex);
        }
    }

    @Override
    public void delete(Room data) throws ResortException {
        if (data == null) {
            throw new IllegalArgumentException("刪除房型不得為null!");
        }

        try (Connection connection = RDBConnection.getConnection(); //1. 取得連線
                PreparedStatement pstmt = connection.prepareStatement(DELETE_SQL);) {
            pstmt.setString(1, data.getId());
            int rows = pstmt.executeUpdate();
            assert (rows == 1) : "刪除產品資料結構有誤:" + data;
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "刪除產品失敗: " + data, ex);
            throw new ResortException("刪除產品失敗!", ex);
        }
    }
   
    @Override
    public Room get(String key) throws ResortException {
        Room r = null;
        try (Connection c = RDBConnection.getConnection(); //1. 取得連線
                PreparedStatement pstmt = c.prepareStatement(SELECT_SQL);) { //2. 建立指令) 
            System.out.println(c.getCatalog());
            pstmt.setString(1, key);
            //3. 執行指令，取回結果
            try (ResultSet rs = pstmt.executeQuery();) {
                while (rs.next()) {
                    String type = rs.getString("type");
                    r = createRoom(type);
                    r.setId(rs.getString("id"));
                    r.setName(rs.getString("name"));
                    r.setUnitPrice(rs.getDouble("unit_price"));
                    r.setQuerydate(rs.getDate("querydate"));
                    r.setUrl(rs.getString("url"));
                    r.setUnoccupied(rs.getBoolean("unoccupied"));
                    
                    if (r instanceof Promotion) {
                        ((Promotion) r).setDiscount(rs.getInt("discount"));
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "查詢房型失敗: key-" + key, ex);
            throw new ResortException("查詢房型失敗!", ex);
        }
        return r;
    }

    private Room createRoom(String type) {
        Room c;
        if (type != null && type.length() > 0) {
            String className = Room.class.getName().replace("Room", type);
            System.out.println("className:" + className);
            try {
                c = (Room) Class.forName(className).newInstance();
                return c;
            } catch (Exception ex) {
                Logger.getLogger(RDBRoomsDAO.class.getName()).log(Level.INFO, "無法建立物件:" + className, ex);
            }
        }
        return c = new Room();
    }

    
    
    @Override
    public List<Room> getAll() throws ResortException {
        List<Room> list = new ArrayList<>();
        try (Connection connection = RDBConnection.getConnection(); //1. 取得連線
                PreparedStatement pstmt = connection.prepareStatement(SELECT_ALL_SQL); //2. 建立指令
                ResultSet rs = pstmt.executeQuery();) {//3. 執行指令，取回結果
            while (rs.next()) {
                   String type = rs.getString("type");
                   Room r = createRoom(type);
                   r.setId(rs.getString("id"));
                   r.setName(rs.getString("name"));
                   r.setUnitPrice(rs.getDouble("unit_price"));
                   r.setQuerydate(rs.getDate("querydate"));
                   r.setUrl(rs.getString("url"));
                   r.setUnoccupied(rs.getBoolean("unoccupied"));
                    
                    if (r instanceof Promotion) {
                        ((Promotion) r).setDiscount(rs.getInt("discount"));
                    }
                list.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "查詢全部房型失敗!", ex);
            throw new ResortException("查詢全部房型失敗!", ex);
        }
        return list;
    }

    public List<Room> getByName(String data) throws ResortException {
        List<Room> list = new ArrayList<>();
        try (Connection connection = RDBConnection.getConnection(); //1. 取得連線
                PreparedStatement pstmt = connection.prepareStatement(SELECT_BY_NAME);) { //2. 建立指令

            pstmt.setString(1, '%' + data + '%');
            try (ResultSet rs = pstmt.executeQuery();) {//3. 執行指令，取回結果
                while (rs.next()) {
                     String type = rs.getString("type");
                   Room r = createRoom(type);
                   r.setId(rs.getString("id"));
                   r.setName(rs.getString("name"));
                   r.setUnitPrice(rs.getDouble("unit_price"));
                   r.setQuerydate(rs.getDate("querydate"));
                   r.setUrl(rs.getString("url"));
                   r.setUnoccupied(rs.getBoolean("unoccupied"));
                    
                    if (r instanceof Promotion) {
                        ((Promotion) r).setDiscount(rs.getInt("discount"));
                    }
                    list.add(r);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "查詢房型(by Name)失敗!", ex);
            throw new ResortException("查詢房型(by Name)失敗!", ex);
        }
        return list;
    }

//    public static void main(String[] args) {
//        try {
//            RDBRoomsDAO dao = new RDBRoomsDAO();

//            Room p2 = new Room(5, "Acer八核心4G LTE 高效能智慧型手機 Acer Liquid X1", 7100);
//            p2.setDescription("5.7 吋 IPS 螢幕、1,280 x 720pixels 螢幕解析度，機身採輕薄設計，機身厚度為 8.5mm 僅 164g，採用 Gorilla Glass 3 高硬度玻璃與 Zero Air Gap 全貼合技術，其色澤、清晰度與飽和度更佳，並可大幅降低折射反光。");
//            p2.setUrl("http://cf-attach.i-sogi.com/tw/product/img/Acer_Liquid_X1_0602140002783_360x270.jpg");
//            try {
//                dao.insert(p2);
//            } catch (ResortException ex) {
//                dao.update(p2);
//                System.out.println("無法新增:" + (ex.getCause() == null ? "" : ex.getCause().getMessage()));
//            }
//
//            Outlet p3 = new Outlet(6, "ASUS ZenFone 2 Laser ZE500KL", 4700);
//            p3.setDescription("植入第四代康寧大猩猩玻璃，搭配 5 吋 720P HD IPS 面板觸控螢幕、1,280 x 720pixels 螢幕解析度，擁有清晰亮麗且細膩又不失真的畫面呈現，讓用戶在觀看影片、閱讀書籍、瀏覽網頁時都能得到絕佳的視覺體驗。");
//            p3.setUrl("http://cf-attach.i-sogi.com/tw/product/img/ASUS_ZenFone_2_Laser_ZE500KL_0731080831477_360x270.jpg");
//            try {
//                dao.insert(p3);
//            } catch (ResortException ex) {
//                dao.update(p3);
//                System.out.println("無法新增:" + (ex.getCause() == null ? "" : ex.getCause().getMessage()));
//            }

//            Room p = dao.("A1");
            //((Outlet)p).setDiscount(15);
//            p.setUnitPrice(3700);
//            dao.update(p);
//            List<Room> list = dao.getByName("樂木之家");
//            System.out.println("--------------\n查詢全部:\n" + list);
            // dao.delete(p);
//            System.out.println("--------------\n查詢全部:\n" + list.size());
            
//        } catch (ResortException ex) {
//            System.out.println(ex + ":" + ex.getCause() == null ? "" : ex.getCause().getMessage());
//        }
//    }
}
