/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trainreservationapplication;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;

/**
 *
 * @author User
 */
public class ReadFromTable implements Callable{

    String tablename;
    Connection con;
    ArrayList<HashMap<String, String>> result;
    HashMap<String , String> whereValues;

    public ReadFromTable(String tablename,HashMap<String,String> whereValues, Connection con) {
        this.tablename = tablename;
        this.whereValues = whereValues;
        this.con = con;
    }

    @Override
    public Object call() throws Exception {
        //System.out.println("IN call of thrad");
        String fetchSql;
        //whereValues.k
        String whereClause = "";
        for(HashMap.Entry entry : whereValues.entrySet()){
            whereClause +=entry.getKey()+" = "+entry.getValue();
        }
        if(whereValues.isEmpty() ||whereValues == null)
        {
              fetchSql = "SELECT * FROM "+this.tablename;
        }
        else
        {
             fetchSql =  "SELECT * FROM "+this.tablename+" WHERE "+whereClause;
        }
        //System.out.println(fetchSql);
        Statement stmt = this.con.createStatement();
        ResultSet rs = stmt.executeQuery(fetchSql);
        ResultSetMetaData resultMetaData = rs.getMetaData();
        int columnCount  = resultMetaData.getColumnCount();
        /*NOTE: below arraylist is used store group of rows fetched from table*/
        ArrayList<HashMap<String, String>> resultMapArray = new ArrayList<HashMap<String, String>>();
        
        while(rs.next()){
            /*NOTE: below hashmap contains each row info from table*/
           HashMap<String , String> resultMap = new HashMap<>(); 
        
            for(int i =1;i<=columnCount;i++){
                String columnName = resultMetaData.getColumnName(i);
                String columnValue = rs.getString(columnName);
                rs.getFetchSize();

                resultMap.put(columnName,columnValue);
            
            }
        resultMapArray.add(resultMap);
        }
        //System.out.println(resultMapArray);
        this.result = resultMapArray;
         
//        while(rs.next()){
//            System.out.println(rs);
//            this.result = rs;
//        }
        return this.result;
    }
    
    
}
