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
public class FetchFromDB implements Runnable{
    DBHandlerMessenger dbHandler = new DBHandler();

    @Override
    public void run() {
        Reservation reservation = new Reservation();
       // System.out.println("IN FETCH FROM DB CALSS");
        LocalDate today = LocalDate.now();
        ArrayList<HashMap<String, String>> values = new ArrayList<>();
        values = this.dbHandler.fetchFromReservationTable();
        //values.forEach(action);
        /* NOTE: Fetch values from Reservation Tables*/
        for(HashMap<String, String> value : values){
            LocalDate date = LocalDate.parse(value.get("date"));
            //System.out.println("date comparisoin"+date.compareTo(today));\
            /*NOTE: If reserved date is greater than or equal to today date, then then values added in cache*/
            if(date.compareTo(today) >= 0 ){
            //System.out.println(value);
            reservation.setPNRnumber(Integer.valueOf(value.get("pnr_number")));
            reservation.settNum(Integer.valueOf(value.get("train_number")));
            reservation.setDate(value.get("date"));
            reservation.setNoOfTickets(Integer.valueOf(value.get("no_of_tickets")));
            reservation.setFare(Float.valueOf(value.get("fare")));
            reservation.setStart(value.get("start"));
            reservation.setDestination(value.get("destination"));
            //System.out.println("RESERVATION\n"+reservation);
            ArrayList<Person> passengerList = new ArrayList<>();
            ArrayList<Integer> seats = new ArrayList<>();
            //System.out.println("in fetch"+reservation.getDate() + " "+reservation.gettNum());
            
            Train train = TrainSchedule.getTrain(LocalDate.parse(reservation.getDate()), reservation.gettNum());
            /* NOTE : Fetch Values from Passenegers Table*/
            ArrayList<HashMap<String, String>> passengerValues = new ArrayList<>();
            passengerValues = this.dbHandler.fetchFromPassengerTable(reservation.getPNRnumber());
            for(HashMap<String, String> passengerValue : passengerValues){
                Person person = new Person();
                person.name = passengerValue.get("name");
                person.age = Integer.valueOf(passengerValue.get("age"));
                person.gender = passengerValue.get("gender");
                passengerList.add(person);
                seats.add(Integer.valueOf(passengerValue.get("seat_no")));
                train.allocateSeats(train,Integer.valueOf(passengerValue.get("seat_no")) );
                train.seatToPassengerMap.put(Integer.valueOf(passengerValue.get("seat_no")), person);
            }
            reservation.setPassengers(passengerList);
            reservation.setSeats(seats);
            Reservation.listOfReservation.put(Integer.valueOf(value.get("pnr_number")),reservation);
            /* NOTE: Fetch Values from Transaction Table*/
            ArrayList<HashMap<String, String>> transactionValues = new ArrayList<>();
            transactionValues = this.dbHandler.fetchFromTransactionTable(reservation.getPNRnumber());
            for(HashMap<String, String> transactionValue : transactionValues){
                Transaction transaction = new Transaction();
                transaction.setAmount(Float.valueOf(transactionValue.get("amount")));
                transaction.setCardNumber(transactionValue.get("card_number"));
                transaction.setMobileNumber(transactionValue.get("mobile_number"));
                transaction.setPNRnumber(Integer.valueOf(transactionValue.get("pnr_number")));
                transaction.setTransactionId(Integer.valueOf(transactionValue.get("transaction_id")));
                reservation.setTransaction(transaction);
                
            }
            
            
        } 
        }
        
    }
    
}
