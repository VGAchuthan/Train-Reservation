/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trainreservationapplication;

import java.util.ArrayList;

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
     String start;
     String destination;
     float fare;
     String cancelStatus = "No";
     
     public Reservation(){
         
     }

    public Reservation(int noOfTickets,ArrayList<Person> passengers, ArrayList<Integer> seats, int trainnumber,String start, String destination,float fare) {
        this.noOfTickets = noOfTickets;
        this.passengers = passengers;
        this.seats = seats;
        this.tNum = trainnumber;
        this.PNRnumber = this.getNextPNRNumber();
        this.start = start;
        this.destination = destination;
        this.fare = fare;
    }

    @Override
    public String toString() {
        return "Reservation{" + "tNum=" + tNum + ", PNRnumber=" + PNRnumber + ", noOfTickets=" + noOfTickets + ", passengers=" + passengers + ", seats=" + seats + ", start=" + start + ", destination=" + destination + ", fare=" + fare + ", cancelStatus=" + cancelStatus + '}';
    }

    
    private int getNextPNRNumber(){
        SQLOperationHandler dbhandler = new SQLOperations();
        return dbhandler.queryNoOfEntries("train.reservation") + 1;
    }

    
     
}
    
    

