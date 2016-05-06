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
 * Class to connect to database
 */
public class ConnectToDatabase {
    private Connection conn;
    private Statement stat;
    private PreparedStatement ps;
    private ResultSet rs;
    // connect to database
    public ConnectToDatabase(){
        try {
            // get connecttion to database
            // account: root
            // password: 123456
            conn = DriverManager.getConnection("jdbc:mysql://localhost/vietnam?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true", "root", "123456");
            stat = conn.createStatement(); // create new statement
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    // return connnection created
    public Connection getConnection(){
        return conn;
    }
    // execute select commant and return result set received from server
    public ResultSet getRS(String sql){
        try {
            rs = stat.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
        
    }
    // execute update and delete commant
    public void update(String sql) throws SQLException{
        stat.executeUpdate(sql);
    }
    // create new prepare statement and return it
    public PreparedStatement getPS(String sql) throws SQLException{
        ps = null;
        ps = conn.prepareStatement(sql);
        return ps;
    }
    //close the connection
    public void close() throws SQLException{
        conn.close();
    }
    
}
