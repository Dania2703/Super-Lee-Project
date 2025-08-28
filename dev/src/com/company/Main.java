package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

import javax.swing.SwingUtilities;

    public class Main {
    

             public static void main(String[] args) throws SQLException, IOException {
                vmain();
             }
     public static void vmain() throws NumberFormatException, IOException{
                System.out.println("0 stock , 1 supplier");

                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
              int  menu = Integer.parseInt(reader.readLine());
              

                switch (menu) {
                            case 0:
                        try {
                            try {
                                Stockmain.mainMenu();
                            } catch (IllegalAccessException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        } catch (SQLException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                            //will be gui class for store,
                            //init here
                            break;
                            case 1:
                        try {
                            MainController.mainMenu();
                        } catch (IllegalAccessException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                            break;
                            //will be gui class for supps,
                            //init here
                         
                        }
            
             }
                 
    }