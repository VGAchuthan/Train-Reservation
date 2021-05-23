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
public class User {
    private static ArrayList<User> users = new ArrayList<>();
    private String email, password;
    public static User currentUser;
    
    public User(){
        users.add(new User("vgachu19","aChu"));
        users.add(new User("admin","admin"));
        users.add(new User("vijay","vijay1998"));
       // users.add(new User("vgachu19","aChu"));
        
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public static int getUser(String name, String password){
        int result = 0;
        for(User user : User.users){
            //System.out.print(user);
            if(user.email.equals(name) && user.password.equals(password)){
                User.currentUser = user;
                //result = 1;
                return 1;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "User{" + "email=" + email + ", password=" + password + '}';
    }
    
    
}
