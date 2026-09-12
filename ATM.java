import java.io.*;
import java.util.Scanner;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class ATM{

    public static void atm(int accountnumber, String name, int pin) throws IOException{
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        int balance = 0;

        System.out.println("====================");


        while(choice != 4){
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Exit");

            choice = sc.nextInt();

           switch(choice){
              case 1:

                    System.out.println("Enter the amount :" );
                    int depo = sc.nextInt();

                    //retriving data from file 
                    balance = checkaccount(accountnumber, pin );

                    balance += depo;
                    System.out.println("Amount deposited successfully. new balance is : " + balance);

                    //updating the balance 
                    update(accountnumber , name , pin, balance );

                break;



               case 2:
                    System.out.println("Enter withdrawal amount : \n");
                    int with = sc.nextInt();

                    balance = checkaccount(accountnumber, pin);
                    if(with <= balance){
                        balance -= with;
                        System.out.println("amount after withdrawal is :\n" + balance);


                     // upating the balance in accounts.txt
                        update(accountnumber, name, pin, balance);

                    }else{
                    System.out.println("insuffiecient balance \n");
                    } 

                break;


                case 3 : 
                    System.out.println("Current balance is :" + balance );
                    balance = checkaccount(accountnumber, pin);
                    System.out.println("Your balnce is "+ balance);

                break;

                case 4 :
                    System.out.println("Thank you for visiting our ATM");
                break;

                default :
                    System.out.println("Invalid choice. Please try again.");
                break;
            } 
        }
    }


    public static void createAccount()throws IOException{
        Scanner sc = new Scanner(System.in);
        int accountnumber = -1;
        int pin = -1;
    
        
                
        System.out.println("enter account number :" );
        accountnumber = sc.nextInt();
        sc.nextLine();
        
        System.out.println("Enter your name :");
        String name = sc.nextLine();

        System.out.println("Enter your pin :");
        pin = sc.nextInt();
        int balance = 0;

        
        FileWriter fw = new FileWriter("accounts.txt", true);

        fw.write(accountnumber + "|" + name + "|" + pin + "|" + balance + "\n");
        fw.close();
        System.out.println("Account created successfully!");
        
    }


    public static void update(int accountnumber,String name, int pin, int balance) throws IOException {
        Scanner sc = new Scanner(new File("accounts.txt"));
        ArrayList<String> lines = new ArrayList<>();
        boolean found = false;
        
        while(sc.hasNextLine()){
            lines.add(sc.nextLine());
        }
        sc.close();

        for(int i = 0; i < lines.size(); i++){
            String[] parts = lines.get(i).split("\\|");
            if ( parts[0].equals(Integer.toString(accountnumber)) && parts[2].equals(Integer.toString(pin))){
                String updated = accountnumber + "|" + name + "|" + pin + "|" + balance;
                lines.set(i, updated);
                found = true;
                break;
            }
        }
        if(!found){
            System.out.println("account not found. invalid Acoount number or pin number  ");
            return;
        }

        // rewritng whole data (updated ) to file 
        FileWriter fw = new FileWriter("accounts.txt");
        for(int i =0; i< lines.size(); i++){
            fw.write(lines.get(i) + "\n");
        }
        fw.close();

        System.out.println("Balance has been updated !");

    }


    public static int checkaccount(int accountnumber, int pin) throws IOException{
        Scanner sc = new Scanner(new File("accounts.txt"));
        int balance = -1;

        
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            String[] details = line.split("\\|");
            int accNum = Integer.parseInt(details[0]);
            int pinNum = Integer.parseInt(details[2]);
           
            if(accountnumber == accNum && pin == pinNum){
                System.out.println("account found");
                String name = details[1];
                System.out.println("Name: " + name);
                balance = Integer.parseInt(details[3]);
                break;
            }
        }
        sc.close();
        if(balance == -1){
            System.out.println("Account not found");
        }
        return balance;
    }
    


    // main function 
    public static void main(String args[]) throws IOException {
        Scanner sc = new Scanner(System.in);

        int choice1 = -1;
        System.out.print("Welcome to AtM simulation\n\n");

        while(choice1 != 3){
            System.out.println("==========================\n\n");
            System.out.println("1. Login account");
            System.out.println("2. Create account ");
            System.out.println("3. Exit");
            choice1 = sc.nextInt();


            
            switch(choice1 ){
                case 1: 
                    System.out.println("Enter Account Number : ");
                    int acc = sc.nextInt();
                    System.out.println("Enter the pin : ");
                    int p = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter name :");
                    String name = sc.nextLine();

                    int balance = checkaccount(acc, p);
                    if (balance != -1 ){
                        atm(acc, name, p);
                    }
                break;

                case 2:

                    createAccount();

                break;

                case 3:
                    break;

            }
        }
    }
}
