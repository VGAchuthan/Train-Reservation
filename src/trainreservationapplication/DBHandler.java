/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trainreservationapplication;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author User
 */
interface DBHandlerMessenger{
    int addToPassengersTable(int trainnumber, int pnrnumber,ArrayList<Person> passengers, ArrayList<Integer> seats)throws Exception;
    int addToReservationTable(int pnrnumber, int trainnumber, int passengersSize, ArrayList<String> routes, float fare, String date);
    int deleteFromTable(int pnrNumber) throws Exception;
    int addToTransactionTable(Transaction transactions);
    
}
public class DBHandler implements DBHandlerMessenger {
    Connection con = DBConnection.getConnection();
    SQLOperationHandler sqlHandler = new SQLOperations();

    @Override
    public int addToPassengersTable(int trainnumber, int pnrnumber,ArrayList<Person> passengers, ArrayList<Integer> seats)throws Exception {
        int result = 0;
        int index = 0;
        try{
        for(Person p :passengers )
        {
            HashMap<String, String> writeValues = new HashMap<>();
            writeValues.put("pnr_number", Integer.toString(pnrnumber));
            writeValues.put("train_number", Integer.toString(trainnumber));
            writeValues.put("name", p.getName());
            writeValues.put("age", Integer.toString(p.getAge()));
            writeValues.put("gender", p.getGender());
            writeValues.put("seat_no",Integer.toString(seats.get(index)));
            index++;
            //Vaigai.
            result = sqlHandler.write("train.passegers", writeValues, con);
        }}
        catch(Exception e){
            e.printStackTrace();
        }
        
        return result;
        //return ;
    
    }

    @Override
    public int deleteFromTable(int pnrNumber) throws Exception {
        HashMap<String, String> whereValues = new HashMap<>();
        whereValues.put("pnr_number",Integer.toString(pnrNumber));
        try{
            int result1 =this.sqlHandler.deleteFromTable("train.reservation", whereValues,con);
            int result2 =this.sqlHandler.deleteFromTable("train.passegers", whereValues, con);
            int result3 = this.sqlHandler.deleteFromTable("train.transactions", whereValues, con);
            if(result1 != 0 && result2 != 0){
                return 1;
            }
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
            return 0;
    }

    @Override
    public int addToReservationTable(int pnrnumber, int trainnumber, int passengersSize, ArrayList<String> routes, float fare,String date) {
        HashMap<String, String> writeValues = new HashMap<>();
        int result =0;
        try{
        writeValues.put("pnr_number", Integer.toString(pnrnumber));
        writeValues.put("train_number", Integer.toString(trainnumber));
        writeValues.put("no_of_tickets", Integer.toString(passengersSize));
        writeValues.put("start", routes.get(0));
        writeValues.put("destination", routes.get(1));
        writeValues.put("fare", Float.toString(fare));
        writeValues.put("date", date);
        
        return this.sqlHandler.write("train.reservation", writeValues, con);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return 0;

    }

    @Override
    public int addToTransactionTable(Transaction transactions) {
        HashMap<String, String> writeValues = new HashMap<>();
        int result =0;
        try{
        writeValues.put("card_number", transactions.getCardNumber());
        writeValues.put("mobile_number", transactions.getMobileNumber());
        writeValues.put("pnr_number", Integer.toString(transactions.getPNRnumber()));
        writeValues.put("amount", Float.toString(transactions.getAmount()));
        
        
        return this.sqlHandler.write("train.transactions", writeValues, con);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    
    }
    
    
    
    
}
