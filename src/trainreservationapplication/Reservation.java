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
    private int tNum;
     //static int reserveId = 1;
    //public String tName;
    private int PNRnumber;
    private int noOfTickets;
    private ArrayList<Person> passengers;
    private ArrayList<Integer> seats;
    private String date;
    private String start;
    private String destination;
    private float fare;
    private String cancelStatus = "No";
    private Transaction transaction ;
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

    public int gettNum() {
        return tNum;
    }

    public void settNum(int tNum) {
        this.tNum = tNum;
    }

    public int getPNRnumber() {
        return PNRnumber;
    }

    public void setPNRnumber(int PNRnumber) {
        this.PNRnumber = PNRnumber;
    }

    public int getNoOfTickets() {
        return noOfTickets;
    }

    public void setNoOfTickets(int noOfTickets) {
        this.noOfTickets = noOfTickets;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public float getFare() {
        return fare;
    }

    public void setFare(float fare) {
        this.fare = fare;
    }

    public String getCancelStatus() {
        return cancelStatus;
    }

    public void setCancelStatus(String cancelStatus) {
        this.cancelStatus = cancelStatus;
    }
    
    
    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public ArrayList<Person> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<Person> passengers) {
        this.passengers = passengers;
    }

    public ArrayList<Integer> getSeats() {
        return seats;
    }

    public void setSeats(ArrayList<Integer> seats) {
        this.seats = seats;
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
            System.out.println(train.seats.toString());
            
            result = 1;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return result;
    }
    

    
     
}
    
    

