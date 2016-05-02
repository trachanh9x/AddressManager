/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package am;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author viha1
 */
public class ConnectToDatabase {
    private Connection conn;
    private Statement stat;
    private PreparedStatement ps;
    private ResultSet rs;
    public ConnectToDatabase(){
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/vietnam?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true", "root", "123456");
            stat = conn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public Connection getConnection(){
        return conn;
    }
    public ResultSet getRS(String sql){
        try {
            rs = stat.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
        
    }
    public void update(String sql) throws SQLException{
        stat.executeUpdate(sql);
    }
    public PreparedStatement getPS(String sql) throws SQLException{
        ps = null;
        ps = conn.prepareStatement(sql);
        return ps;
    }
    public void close() throws SQLException{
        conn.close();
    }
    
}
