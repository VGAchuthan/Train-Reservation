/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trainreservationapplication;

import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.Callable;

/**
 *
 * @author User
 */
public class DeleteFromTable implements Callable{
    String tableName;
    HashMap<String, String> whereValues ;
    Connection con ;
    int result;

    public DeleteFromTable(String tableName, HashMap<String, String> whereValues, Connection con) {
        this.tableName = tableName;
        this.whereValues = whereValues;
        this.con = con;
    }
    

    @Override
    public Object call() throws Exception {
        String deleteSql = "DELETE FROM "+tableName;
        
        if(whereValues != null)
        {
            //for( ;0==0 ;);
            deleteSql+=" WHERE ";
            for(HashMap.Entry m : whereValues.entrySet())
            {
                deleteSql+=m.getKey().toString()+" = \'"+m.getValue()+"\'";
            }
            
        }
        System.out.println(deleteSql);
        Statement stmt = con.createStatement();
        this.result = stmt.executeUpdate(deleteSql);
        return this.result;
    }
    
    
}
