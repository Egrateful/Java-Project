/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uuu.resort.model;

import java.sql.*;
import java.util.ResourceBundle;
import uuu.resort.domain.ResortException;

/**
 *
 * @author PattyTai
 */
public class RDBConnection {    
    private static final String driver;//= "com.mysql.jdbc.Driver";
    private static final String url;// = "jdbc:mysql://localhost:3306/resort?zeroDateTimeBehavior=convertToNull";
    private static final String userid;// = "root";
    private static final String password;// = "1234";

    static{
        ResourceBundle bundle = ResourceBundle.getBundle("uuu.resort.model.JDBCSettings");
        
        String dr = bundle.getString("jdbc.driver");
        if(dr!=null){
            driver = dr;
        }else{
            driver = "com.mysql.jdbc.Driver";            
        }
        
        String ur = bundle.getString("jdbc.url");
        if(ur!=null){
            url = ur;
        }else{
            url="jdbc:mysql://localhost:3306/resort?zeroDateTimeBehavior=convertToNull";
        }
        
        String id = bundle.getString("jdbc.userid");
        if(id!=null){
            userid = id;
        }else{
            userid="root";
        }

        String pwd = bundle.getString("jdbc.password");
        if(pwd!=null){
            password = pwd;
        }else{
            password="1234";
        }

    }
    
    public static Connection getConnection() throws ResortException {
        Connection c = null;
        try {
            //1. load JDBC Driver
            Class.forName(driver);

            //2. 建立連線
            try {
                c = DriverManager.getConnection(url, userid, password);
                return c;
            } catch (SQLException ex) {
                System.out.println("無法建立連線");
                throw new ResortException("無法建立MySQL JDBC連線", ex);
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("無法載入Driver: " + driver);
            throw new ResortException("無法載入Driver: " + driver, ex);
        }
    }
}
