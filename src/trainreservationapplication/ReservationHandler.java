/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trainreservationapplication;
//import contactapplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author User
 */
interface ReservationHandlerMessenger{
    int bookTickets(int day,Train train,ArrayList<Person> passengerList, ArrayList<String> routes,float fare);
    int cancelTickets(int pnrNumber);
}
public class ReservationHandler implements ReservationHandlerMessenger{
    DBHandlerMessenger handler = new DBHandler();
    @Override
    public int bookTickets(int day,Train train,ArrayList<Person> passengerList, ArrayList<String> routes,float fare) {
        
        ArrayList<Integer> allocatedSeat = new ArrayList<>();
        allocatedSeat = this.setSeats(train.seats,train,passengerList);
        System.out.println(allocatedSeat);
        Reservation reserve = new Reservation(passengerList.size(),passengerList, allocatedSeat, train.trainNumber,routes.get(0), routes.get(1),fare);
        System.out.println(Reservation.reserveId);
        System.out.println(reserve);
        int p_result = writePassengerValues(reserve.tNum, reserve.PNRnumber,passengerList,allocatedSeat);
        System.out.println("Pass res"+p_result);
        int r_result = writeReservationValues(reserve.PNRnumber,reserve.tNum,passengerList.size(), routes, fare);
        
        train.setAvailableSeats(train.getAvailableSeats() - passengerList.size());
        
        System.out.println(train.availableSeats);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        
        return 1;//p_result && r_result;
    }

    @Override
    public int cancelTickets(int pnrNumber) {
        try{
            return this.handler.deleteFromTable(pnrNumber);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return 0;
        
    }
    private ArrayList<Integer> setSeats(int[] seats,Train train,ArrayList<Person> passengerList){
        int seatNo = 1;
        int passengerCount = passengerList.size();
        ArrayList<Integer> allocatedSeat = new ArrayList<>();
        
        for(Person p : passengerList ){
            System.out.println(p);
            if(p.age >=55)
            {
                for(int seat = 0;seat < 72;seat++)
                if(seats[seat] == 0&&((seat % 8) == 0 || (seat % 8)== 5 || (seat % 8) == 7 ))
                {
                    seats[seat] =1;
                    allocatedSeat.add(seat);
                    train.seatToPassengerMap.put(seat,p);
                    break;
                }
                
            }
            else{
                for(int seat = 0;seat < 72;seat++)
                if(seats[seat] == 0){
                    seats[seat] = 1;
                    allocatedSeat.add(seat);
                    train.seatToPassengerMap.put(seat,p);
                    break;
                }
                
            }
        //}
            
        }
        return allocatedSeat;
    }
    
    private int writePassengerValues(int trainnumber, int pnrnumber, ArrayList<Person> passengers, ArrayList<Integer> seats){
        
            try{
                return handler.addToPassengersTable(trainnumber, pnrnumber,  passengers,  seats);
            }
            catch(Exception e){
                e.printStackTrace();
            }
            
        
        return 0;
    }
    
    private int writeReservationValues(int pnrnumber, int trainnumber, int passengersSize,ArrayList<String> route, float fare){
        
        try{
            return this.handler.addToReservationTable(pnrnumber,  trainnumber,  passengersSize,route, fare);
        }
        catch(Exception e){
            e.printStackTrace();
        }
                
        
        return 0;
    }
}
