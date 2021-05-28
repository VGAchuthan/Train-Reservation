/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trainreservationapplication;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class TrainReservationApplication {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        fetchFromDB();
        Scanner sc = new Scanner(System.in);
        Connection c = DBConnection.getConnection();
        
        ReservationHandlerMessenger handler = new ReservationHandler();
        int choice;
        int continuation;
        int result =0;
        User currentUser ;//= User.currentUser;
        Date date = new Date();//date.
        System.out.println(date + " " +date.getDate());
        
       /* do{
            System.out.println("Enter Login Credentials");
            System.out.println("Enter Username");
            String username = sc.next();
            System.out.println("Enter Password");
            String password = sc.next();
            User  user = new User();
            currentUser = User.currentUser;
            result = User.getUser(username, password);
            if(result == 0){
                System.out.println("Enter Valid Credentials");
            }
        }while(result != 1);*/
        do{
        System.out.println("1.Book Ticekt\t2.Cancel Booking\t3.Train Seating Details\n4.Logout");
        choice = sc.nextInt();
        switch(choice){
            case 1:{
                //Date date = new Date();
                
                LocalDate selectedDate = selectDate();
                //System.out.println(LocalDate.parse(selectedDate.toString()));
                System.out.println("Selected date "+ selectedDate.getDayOfMonth());
                int trainNumber = getTrainNumber();
                Train train = TrainSchedule.getTrain(selectedDate, trainNumber);
                System.out.println(train);
                System.out.println("Available Seats"+train.availableSeats);
                ArrayList<String> routes = getSourceAndDestionation(train); 
                System.out.println("routees "+routes);
                
                ArrayList<Person> listOfPassengers = new ArrayList<>();
                
                System.out.println("Enter Number of Passengers");
                int passengerCount = sc.nextInt();
                if(passengerCount <= train.availableSeats){
                    for(int count = 1; count <=passengerCount;count++)
                    { //System.out.println("")
                        System.out.println("Enter Name");
                        String name = sc.next();
                        System.out.println("Enter Age");
                        int age = sc.nextInt();
                        System.out.println("Enter Gender");
                        String gender = sc.next();
                        Person passenger = new Person(name, age, gender);
                        listOfPassengers.add(passenger);
                    }
                    float fare = calculateFare(routes,train.stoppings) * passengerCount;
                    System.out.println("fare : "+fare);
                    int status = handler.bookTickets(selectedDate,train,listOfPassengers, routes, fare);
                    if(status == 1){
                        System.out.println("Booked Successfully");
                    }
                    else{
                        System.out.println("Booking Failed");
                    }
                    
                }
                else{
                    System.out.println("Tickets Not Available For All Passengers");
                }
                
                
                break;
            }
            case 2:{
                System.out.println("Enter PNR number to Cancel Booking");
                int pnrNumber = sc.nextInt();
                
                int deleteStatus = handler.cancelTickets(pnrNumber);
                if(deleteStatus != 0){
                    System.out.println("Cancel Successfully");
                }
                else
                {
                    System.out.println("Cancel Un Successfully");
                }
                break;
            }
            case 3:
            {
                LocalDate queryDate = selectDate();
                int querytrainNumber = getTrainNumber();
                System.out.println(queryDate + " " +querytrainNumber);
                TrainSchedule.getTrain(queryDate, querytrainNumber).printAllSeatDetails();
                
                break;
            }
            case 4:{
                currentUser = null;
                break;
                
            }
            
        }
        System.out.println("press 1 to add another booking");
        continuation = sc.nextInt();
        }while(continuation == 1);
        
    }
    private static int getTrainNumber(){
        int trainNumber;
        Scanner sc = new Scanner(System.in);
        System.out.println("1.Vaigai\t2.Pandian\t3.Pallavan\t4.Pothigai");
        trainNumber = sc.nextInt();
        switch(trainNumber){
            case 1:{
                return 1;
                //break;
            }
            case 2:{
                return 2;
            }
            case 3:{
                return 3;
            }
            case 4:{
                return 4;
            }
            default:{
                System.out.println("Choose Valid Value");
                break;
            }       
            
        }
        return 0;        
    }
    
    private static LocalDate selectDate(){
        ArrayList<LocalDate> listOfDate = new ArrayList<>();
        System.out.println("Select  Date");
        //Date date = new Date();
        //Calendar c = new GregorianCalendar();
        Calendar c = Calendar.getInstance();
        //Calendar.//c.
        LocalDate localDate = LocalDate.now();
        //LocalDate time = new LocalDate();
        System.out.println("Local date:"+localDate);
        //int today = date.getDate();
        //int month = date.getMonth();
        //int index;
        for(int day = 0; day<3;day++){
           // Long seconds = date.getTime()+(day*24*60*60*1000);
            LocalDate tempDate = localDate.plusDays(day);
            //System.out.println(tempDate);
            listOfDate.add(tempDate);
            System.out.print(day+1+". "+tempDate+"\t");
            
        }
        
        
        System.out.println();
        Scanner sc = new Scanner(System.in);
        int selectDate = sc.nextInt();
        switch(selectDate){
            case 1:{
                return listOfDate.get(0);//.getDate();
            }
            case 2:{
                return listOfDate.get(1);//.getDate();
            }
            case 3:{
                return listOfDate.get(2);//.getDate();
            }
            case 4:{
                return listOfDate.get(3);//.getDate();
            }
        }
        
        return localDate;
    }
    private static ArrayList<String> getSourceAndDestionation(Train train){
        ArrayList<String> route = new ArrayList<String>();
        Scanner sc = new Scanner(System.in);
        int index=1;
        
        System.out.println("Enter Starting Point");
        String source = selectStation(train.stoppings.subList(0, train.stoppings.size() -1 ));
        System.out.println("Enter Destination Point");
        String destination = selectStation(train.stoppings.subList(train.stoppings.indexOf(source) + 1, train.stoppings.size()));
        route.add(source);
        route.add(destination);

        
        return route;
        
    }
    private static String selectStation(List<String> stationList) {
        int station;
        int index = 1;
        Scanner sc = new Scanner(System.in);
        for(String stations : stationList){
            System.out.print(index+"."+stations+"\t");
            index++;
            
        }
        System.out.println();
        station = sc.nextInt();
        return  stationList.get( station -1);
        
    }
    private static float calculateFare(ArrayList<String> routes, ArrayList<String> stationList){
        float fare = 0f;
        int stoppingCounts = stationList.indexOf(routes.get(1)) - stationList.indexOf(routes.get(0)) ;
        
        return stoppingCounts * 17.5f;
    }
    private static void fetchFromDB(){
        TrainSchedule ts = new TrainSchedule();
        FetchFromDB fetch = new FetchFromDB();
        Thread t = new Thread(fetch);
        t.start();
    }
}

