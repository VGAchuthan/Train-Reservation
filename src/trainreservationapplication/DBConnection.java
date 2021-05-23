/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trainreservationapplication;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author User
 */
public class DBConnection {
    private static Connection con = null;
  
    static
    {
        String url = "jdbc:mysql://localhost:3306/train";
        String user = "root";
        String pass = "aChu@1998";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            con = DriverManager.getConnection(url, user, pass);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection()
    {
        return con;
    }
    
    
}
