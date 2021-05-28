/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trainreservationapplication;

import java.util.HashMap;

/**
 *
 * @author User
 */
interface TransactionHandlerMessenger{
    int addTransaction(Transaction transaction);
    int deleteTransaction(int PNRnumber);
}
public class TransactionHandler implements TransactionHandlerMessenger {
    DBHandlerMessenger handler = new DBHandler();
    private HashMap<Integer, Transaction> transactionMap = new HashMap<>();

    @Override
    public int addTransaction(Transaction transaction) {
        transactionMap.put(transaction.getPNRnumber(), transaction);
        return handler.addToTransactionTable(transaction);
       
    }

    @Override
    public int deleteTransaction(int PNRnumber) {
        
        try{
            transactionMap.remove(PNRnumber);
           // return handler.deleteFromTable(PNRnumber);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return 1;
        
        
    }
    
    
}
class Transaction{
    private int PNRnumber;
    private int transactionId;
    private String CardNumber;
    private String MobileNumber;
    private float amount;

    public Transaction() {
    }

    public Transaction(String CardNumber, String MobileNumber, int PNRnumber, float amount) {
        this.transactionId = getNextTransactionId();
        this.CardNumber = CardNumber;
        this.MobileNumber = MobileNumber;
        this.PNRnumber = PNRnumber;
        this.amount = amount;
    }

    public int getPNRnumber() {
        return PNRnumber;
    }

    public void setPNRnumber(int PNRnumber) {
        this.PNRnumber = PNRnumber;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    
    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String CardNumber) {
        this.CardNumber = CardNumber;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String MobileNumber) {
        this.MobileNumber = MobileNumber;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    
    
    private int getNextTransactionId(){
        SQLOperationHandler sqlHandler = new SQLOperations();
        
        return sqlHandler.queryNoOfEntries("train.transactions");
    }
    
    
}
