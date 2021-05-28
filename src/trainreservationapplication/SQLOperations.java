/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trainreservationapplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 *
 * @author User
 */
interface SQLOperationHandler{
    int write(String tablename, HashMap<String, String> writeValues,Connection con)throws Exception;
    ArrayList<HashMap<String, String>> fetchFromTable(String tablename,HashMap<String,String> whereValues, Connection con)throws Exception;
    int deleteFromTable(String tablename,HashMap<String,String> whereValues,Connection con)throws Exception;
    int queryNoOfEntries(String tablename);
}
class WriteToTable implements Runnable{
    String tablename;
    HashMap<String, String> writeValues; 
   // Connection conn = DBConnection.getConnection();
    Connection con = DBConnection.getConnection();
    
    
    int result = 0;

    public WriteToTable(String tablename, HashMap<String, String> writeValues, Connection con) {
        this.tablename = tablename;
        this.writeValues = writeValues;
        this.con = con;
    }
    
    
    @Override
    public void run()  {
        try{
        int index =1;
        int size = writeValues.size();
        String insertSql = "INSERT INTO "+tablename+"(";
        for(String key : writeValues.keySet())
        {
            insertSql+=key;
            if( index < size){
                insertSql+=", ";
            }
            //System.out.println(key + insertValues.size());
            index++;
        }
        insertSql+=") values ( ";
        index =1;
        
        for(String values : writeValues.values())
        {
            insertSql+="?";
            if( index < size){
                insertSql+=", ";
            }
           // System.out.println(values + insertValues.size());
            index++;
        }
        insertSql+=")";
        index =1;
        
        PreparedStatement pstmt =  con.prepareStatement(insertSql);
        for(String values : writeValues.values())
        {
            pstmt.setString(index, values);
            index++;
        }
        //rowid =
        System.out.println(insertSql);
        pstmt.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        
        
        
        
        
        //return this.result;
    }
    
}


public class SQLOperations implements SQLOperationHandler{

    @Override
    public int write(String tablename, HashMap<String, String> writeValues, Connection con) throws Exception{
        //Callable callable = new WriteToTable(tablename, writeValues, con);
        //FutureTask writeTask = new FutureTask(callable);
        Thread writeThread = new Thread(new WriteToTable(tablename, writeValues, con));
        writeThread.start();
        //return (int)writeTask.get();
        return 1;
    }
    @Override
    public ArrayList<HashMap<String, String>> fetchFromTable(String tablename,HashMap<String,String> whereValues, Connection con)throws Exception{
        Callable callable = new ReadFromTable(tablename, whereValues, con);
        FutureTask fetchTask = new FutureTask(callable);
        //ReadFromTable read = new ReadFromTable(tablename, whereValues, con);
        Thread fetchThread = new Thread(fetchTask);
        fetchThread.start();
        return (ArrayList<HashMap<String, String>>) fetchTask.get();
    }

    @Override
    public int deleteFromTable(String tablename, HashMap<String, String> whereValues,Connection con) throws Exception {
        Callable callable = new DeleteFromTable(tablename, whereValues,con);
        FutureTask deleteTask = new FutureTask(callable);
        Thread deleteThread = new Thread(deleteTask);
        deleteThread.start();
        return (int)deleteTask.get();
    }
    

    @Override
    public int queryNoOfEntries(String tablename) {
        int count = 0;
        
        try{
            String query = "SELECT COUNT(*)FROM "+tablename;
            Statement stmt = DBConnection.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                count = rs.getInt(1);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return count;
    }
    
    
   
    
}
