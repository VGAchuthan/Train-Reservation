/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trainreservationapplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author User
 */
class TrainSchedule{
    private static HashMap<Integer, HashMap<Integer, Train>> trainSchedule = new HashMap<Integer, HashMap<Integer, Train>>();
    public TrainSchedule(){
        Date date = new Date();
        int today = date.getDate();
        
        for(int day = 0; day< 3;day++){
            Long seconds = date.getTime()+(day*24*60*60*1000);
            //date.
            Date tmr = new Date(seconds);
            //System.out.println(tmr);
            TrainSchedule.trainSchedule.put(tmr.getDate(), new TrainList().getTrainList());
           // System.out.println(TrainSchedule.trainSchedule.get(tmr));
        }
        //System.out.println(date.getDate());
        
        
    }
    public static Train getTrain(int date, int trainNumber){
        return TrainSchedule.trainSchedule.get(date).get(trainNumber);
            

        }
    
    public static void printTrainSchedule(){
        for(HashMap.Entry m : trainSchedule.entrySet())
        {
            System.out.println(m.getKey() + " " + m.getValue());
        }
    }
}

class TrainList{
    private  HashMap<Integer, Train> listOfTrains = new HashMap<>();
    public TrainList(){
        //System.out.println("in train list");
        for(int number =1; number <=4;number++){
            Train train = new Train(number);
            listOfTrains.put(number, train);
            
        }
    }
    public HashMap<Integer, Train> getTrainList(){
        return listOfTrains;
    }
    public Train getTrain(int trainNumber){
        return listOfTrains.get(trainNumber - 1);
    }
    
    //public static 
}

class Train {
    
    //private static Train instance = null; 
    public  HashMap<Integer, Person> seatToPassengerMap = new HashMap<>();
    int trainNumber;
    int availableSeats;
    int[] seats; 
    ArrayList<String> stoppings;

    public Train(int trainNumber){
        this.availableSeats = 72;
        this.seats = new int[72];
        this.trainNumber = trainNumber;
        this.stoppings = this.getStoppings(trainNumber);
        
    }
    public Train(){
        
    }
    

    public int getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public int[] getSeats() {
        return seats;
    }

    public void setSeats(int[] seats) {
        this.seats = seats;
    }
    
    private ArrayList<String> getStoppings(int trainNumber){
        
        ArrayList<String> stoppings = new ArrayList<>();
        switch(trainNumber){
            case 1:{
                stoppings.add("Madurai");
                stoppings.add("Dindugal");
                stoppings.add("Trichy");
                stoppings.add("Ariyalur");
                stoppings.add("Chengalpattu");
                stoppings.add("Tambaram");
                stoppings.add("Chennai Egmore");
                return stoppings;
            }
            case 2:{
                stoppings.add("Chennai Egmore");
                stoppings.add("Tambaram");
                stoppings.add("Chengalpattu");
                stoppings.add("Ariyalur");
                stoppings.add("Trichy");
                stoppings.add("Dindugal");
                stoppings.add("Madurai");
                return stoppings;
            }
            case 3:{
                stoppings.add("Chennai Egmore");
                stoppings.add("Tambaram");
                stoppings.add("Chengalpattu");
                stoppings.add("Ariyalur");
                stoppings.add("Trichy");
                stoppings.add("Pudhukkottai");
                stoppings.add("Karaikudi");
                return stoppings;
                
            }
            case 4:{
                stoppings.add("Chennai Central");
                stoppings.add("Arakonam");
                stoppings.add("Jolarpet");
                stoppings.add("Salem");
                stoppings.add("Erode");
                stoppings.add("Tripur");
                stoppings.add("Coimbatore");
                return stoppings;
                
            }
        }
        
        return null;
    }
    
    
    
//    public static Train getInstance(int trainNumber){
//        
//        if(listOfTrains.containsKey(trainNumber)){
//            return listOfTrains.get(trainNumber);
//        }
//        else listOfTrains.put(trainNumber, new Train(trainNumber));
//        return listOfTrains.get(trainNumber);
//    }
    
    public void printAllSeatDetails(){
        
        for(HashMap.Entry m : seatToPassengerMap.entrySet())
        {
            System.out.println(m.getKey() + " " + m.getValue());
        }
    }

    @Override
    public String toString() {
        return "Train{" + "trainNumber=" + trainNumber + ", availableSeats=" + availableSeats + ", seats=" + seats + '}';
    }
    
    
}
