/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trainreservationapplication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author User
 */
public class Reservation {
     int tNum;
     static int reserveId = 1;
    //public String tName;
     int PNRnumber;
     int noOfTickets;
     ArrayList<Person> passengers;
     ArrayList<Integer> seats;
     String date;
     String start;
     String destination;
     float fare;
     String cancelStatus = "No";
     public static HashMap<Integer, Reservation> listOfReservation = new HashMap<>();
     
     public Reservation(){
         
     }

    public Reservation(int noOfTickets,ArrayList<Person> passengers, ArrayList<Integer> seats, int trainnumber,String start, String destination,float fare, String date) {
        this.noOfTickets = noOfTickets;
        this.passengers = passengers;
        this.seats = seats;
        this.tNum = trainnumber;
        this.PNRnumber = this.getNextPNRNumber();
        this.start = start;
        this.destination = destination;
        this.fare = fare;
        this.date = date;
        //addToReservationList();
        Reservation.listOfReservation.put(this.PNRnumber,this);
    }

    @Override
    public String toString() {
        return "Reservation{" + "tNum=" + tNum + ", PNRnumber=" + PNRnumber + ", noOfTickets=" + noOfTickets + ", passengers=" + passengers + ", seats=" + seats + ", date=" + date + ", start=" + start + ", destination=" + destination + ", fare=" + fare + ", cancelStatus=" + cancelStatus + '}';
    }
    
    //private void addToReservationList();

    

    
    private int getNextPNRNumber(){
        SQLOperationHandler dbhandler = new SQLOperations();
        return dbhandler.queryNoOfEntries("train.reservation") + 1;
    }
    
    public int cancelBooking(int pnrNumber){
        //int result = removeSeatsFromBooking(Reservation.listOfReservation.get(pnrNumber));
        return removeSeatsFromBooking(Reservation.listOfReservation.get(pnrNumber)); 
        
    }
    private int removeSeatsFromBooking(Reservation reservation){
        int result = 0;
        Train train = TrainSchedule.getTrain(LocalDate.parse(reservation.date), reservation.tNum);
        try{
            reservation.seats.forEach((seat) -> {
            train.seats[seat] = 0;
            train.seatToPassengerMap.remove(seat);
         });
            System.out.println("DElete afetr");
            System.out.println(train.seats);
            
            result = 1;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return result;
    }
    

    
     
}
    
    

