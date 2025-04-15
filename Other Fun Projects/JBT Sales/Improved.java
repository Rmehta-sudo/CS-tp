import com.mysql.jdbc.Statement;

import java.sql.ResultSet;
import java.util.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Improved {


    public static void main(String[] args) {


        Scanner in = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int maha_bool;
        while (true)
        {
            System.out.println("Do you want to \n (1) input data \n (2) view all data \n (3) Any comments \n (4) quit :) ");
            int whatdo = in.nextInt();
            if (whatdo == 1) {
                while (true) {
                    System.out.println("Was the sale in or out of maharashtra? \n (1) for in maharashtra \n (2) for not in maharashtra");
                    maha_bool = in.nextInt();
                    if( maha_bool != 1 && maha_bool != 2) {
                        System.out.println("Please choose 1 or 2");
                    } else break;

                }

                ImprovedSale sale = new ImprovedSale(maha_bool) ;
                sale.input();
                sale.process();

                dbtest pro = new dbtest();
                pro.createConnection();

                {
                    System.out.println("Inserting....");

                    if (sale.maha) {
                        int IGST_val = (int) (sale.IGST * sale.value*sale.quantity);
                        int CGST_val = (int) (sale.CGST * sale.value*sale.quantity);
                        int SGST_val = (int) (sale.SGST * sale.value*sale.quantity);

                        IGST_val = 0;
                        sale.total = sale.value*sale.quantity + IGST_val + CGST_val + SGST_val;

                        try {
                            Class.forName("com.mysql.jdbc.Driver");
                            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_db?useSSL=false", "root", "root");
                            System.out.println("Database Connection Successful");
                            Statement smt = (Statement) con.createStatement();
                            String todo = "INSERT INTO my_table (GRADE,Date, Quantity_Sold, Rate,Value, IGST,SGST,CGST,TOTAL_GST,GRAND_TOTAL,SENT_TO)  VALUES ('"+sale.grade+"','"+sale.dateof.toString()+"','"+String.valueOf(sale.quantity)+"','"+String.valueOf(sale.value)+"','"+String.valueOf(sale.value*sale.quantity)+"','"+String.valueOf(IGST_val)+"','"+String.valueOf(SGST_val)+"','"+String.valueOf(CGST_val)+"','"+String.valueOf(IGST_val+CGST_val+SGST_val)+"','"+(sale.total)+"','"+sale.sento+"')";
                            //         System.out.println(todo);
                            smt.execute(todo);
                            System.out.println("Insert Successful");

                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(dbtest.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(dbtest.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (sale.maha == false) {

                        int IGST_val = (int) (sale.IGST * sale.value *sale.quantity);
                        int SGST_val = 0;
                        int CGST_val = 0;
                        sale.total = sale.value*sale.quantity + IGST_val + CGST_val + SGST_val;

                        try {
                            Class.forName("com.mysql.jdbc.Driver");
                            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_db?useSSL=false", "root", "root");
                            System.out.println("Database Connection Successful");
                            Statement smt = (Statement) con.createStatement();
                            String todo = "INSERT INTO my_table (GRADE,Date, Quantity_Sold, Rate,Value, IGST,SGST,CGST,TOTAL_GST,GRAND_TOTAL,SENT_TO)  VALUES ('"+sale.grade+"','"+sale.dateof.toString()+"','"+String.valueOf(sale.quantity)+"','"+String.valueOf(sale.value)+"','"+String.valueOf(sale.value*sale.quantity)+"','"+String.valueOf(IGST_val)+"','"+String.valueOf(SGST_val)+"','"+String.valueOf(CGST_val)+"','"+String.valueOf(IGST_val+CGST_val+SGST_val)+"','"+(sale.total)+"','"+sale.sento+"')";
                            smt.execute(todo);
                            System.out.println("Insert Successful");

                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(dbtest.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(dbtest.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                } // Display inserted Data


            } // Take user input , then display it then store it in the DB
            else if (whatdo == 2)
            {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_db?useSSL=false", "root", "root");
                    System.out.println("Database Connection Successful");
                    Statement smt = (Statement) con.createStatement();
                    ResultSet rs = smt.executeQuery("SELECT * from my_table");
                    while(rs.next())
                    {
                        System.out.print(rs.getString("GRADE")+"    ");
                        System.out.print(rs.getString("Date")+"    ");
                        System.out.print(rs.getInt("Quantity_Sold")+"    ");
                        System.out.print(rs.getInt("Rate")+"    ");
                        System.out.print(rs.getInt("IGST")+"    ");
                        System.out.print(rs.getInt("SGST")+"    ");
                        System.out.print(rs.getInt("CGST")+"    ");
                        System.out.print(rs.getInt("TOTAL_GST")+"    ");
                        System.out.print(rs.getInt("GRAND_TOTAL")+"    ");
                        System.out.print(rs.getInt("SENT_TO")+"    ");

                        System.out.println();
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(dbtest.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(dbtest.class.getName()).log(Level.SEVERE, null, ex);
                }
            } //  Showing the data in bad tabular format
            else if(whatdo == 4){
                System.out.println("Ok then ... ENJOYYY   :)");
                break;

            } // to quit
            else if(whatdo == 3){
				System.out.println("Enter your comments - less than 120 words.   :)");
                Scanner take = new Scanner(System.in);
                String comm = take.nextLine();
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_db?useSSL=false", "root", "root");
                    System.out.println("Database Connection Successful");
                    Statement smt = (Statement) con.createStatement();
                    String todo = "INSERT INTO my_table (comments)  VALUES ('"+comm+"')";
                    smt.execute(todo);
                    System.out.println("Insert Successful");

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(dbtest.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(dbtest.class.getName()).log(Level.SEVERE, null, ex);
                }

            } // to add comments

        }

    }
}


//Database work


class dbtest
{
    public static void main(String[] args)
    {
        dbtest pro = new dbtest();
        pro.createConnection();


    }
    void createConnection()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_db?useSSL=false","root","root");
            System.out.println("Database Connection Successful");

        }
        catch(ClassNotFoundException ex )
        {
            Logger.getLogger(dbtest.class.getName()).log(Level.SEVERE,null,ex);
        }catch(SQLException ex )
        {
            Logger.getLogger(dbtest.class.getName()).log(Level.SEVERE,null,ex);
        }
    }


}

//the Sale class



class ImprovedSale {
    static boolean maha = false;
    static int quantity = 0;
    static long value = 0;
    static final double IGST = 0.18;
    static final double CGST = 0.09;
    static final double SGST = 0.09;
    static final String[] gradeList = {"MRS FLEXOLITE 1302","UV JBT 2801", "WB JBT 3407", "WB JBT 3408","MRS FLEXOLITE 1301","MRS FLEXOLITE 1406","MRS FLEXOLITE 1605","MRS FLEXOLITE 1201","UV JBT 2511","UV JBT 2802","UV JBT 2406","UV JBT 2512","UV JBT 2401","UV JBT 2414","UV JBT 2409",};

    static Date dateof;
    static String grade;
    static String sento;
    static long total;
    Scanner in = new Scanner(System.in);
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


    ImprovedSale(int x) // constructor to reduce code in Input.java
    {
        if (x == 1) maha = true;
        else if (x == 2) maha = false;
    }

    void process() {
        System.out.println("Processing....");
        if (maha == true) {
            int CGST_val = (int) (CGST * value*quantity);
            int SGST_val = (int) (SGST * value*quantity);
            int total_GST = CGST_val + SGST_val;
            total = value*quantity + total_GST;


            System.out.println("The product is a " + grade + " product. ");
            System.out.println("Date : " + dateof);
            System.out.println("Quantity Sold : " + quantity + " kg");
            System.out.println("Rate : " + value);
			System.out.println("Value : " + value*quantity);
            System.out.println("IGST : " + 0);
            System.out.println("SGST : " + SGST_val);
            System.out.println("CGST : " + CGST_val);
            System.out.println("Total GST : " + total_GST);
            System.out.println("Grand Total : " + total);
            System.out.println("Sold To : " + sento);

            System.out.println("\n\n  Done... Tada");

        } else if (maha == false) {

            int IGST_val = (int) (IGST * value*quantity);
            int total_GST = IGST_val;
            total = value + IGST_val;

            System.out.println("The product is a " + grade + " product. ");
            System.out.println("Date : " + dateof);
            System.out.println("Quantity Sold : " + quantity + " kg");
            System.out.println("Rate : " + value);
			System.out.println("Value : " + value*quantity);
            System.out.println("IGST : " + IGST_val);
            System.out.println("SGST : " + 0);
            System.out.println("CGST : " + 0);
            System.out.println("Total GST : " + total_GST);
            System.out.println("Grand Total : " + total);
            System.out.println("Sold to : " + sento);
            System.out.println("\n\n  Done... Tada");
        }
    } // take user input

    void input() {
        int i = 0;
        String lengtho = "\n";
        while (i<gradeList.length)
        {
            lengtho += gradeList[i] +  "\n";
            i++;
        }
System.out.print("Please enter year :");

             int y = in.nextInt();
             System.out.print("Please enter month :");

             int m = in.nextInt();
             System.out.print("Please enter date :");

             int d = in.nextInt();
            dateof = new Date(y,m-1,d);
        while (true) {
            try {
                System.out.print("Please enter Grade: ");
                grade = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            List<String> list = Arrays.asList(gradeList);

            if (list.contains(grade)) {
                break;

            } else System.out.println("Please enter from " + lengtho);
        }

        System.out.print("Please enter quantity :");
        quantity = in.nextInt();
        System.out.print("Please enter Rate :");
        value = in.nextLong();
        System.out.print("Please enter Sold to :");
        sento = in.nextLine();


        System.out.println("processing.....");

    } // input into db method
}

/**
 * Project README.txt
 *Hi!
 * This a great java project .
 * I uses MySQL along with JDBC to
 * (1) input data everyday and enter put it all into a single object - 'sale'
 * (2) Then input into a Database called my_DB into my_table table.
 *
 * Improved Sale and Improved.java ARE new concise files. original project : 480 lines
 * improved : 260
 * Functionality for checking whether grade is correct 
				the above was done using loop and array to list and checking simple
 * Exit and loop function
 * Rate instead of value function
 * Read me improved
 * Sent_to functionality for which client sent to
 * Database connection SSL=false
 * redundancy of GST declarations removed from ImprovedSale.java
 * user input and display converted into sale methods to removed extra lines of code
 * braces arranged properly and extra looping introduced for lesser lines
 * unnecessary variable declarations removed
 * Learn query export to excel in csv file
 * added comments column to db
 * cleaned database
 * added function to allow to set date 
 * added rate and value columns to database
 * create classpath and added the required jar path file to it
 * Put all of them into a single file i.e. all classes and readme ... this one :)😄😅
 * Created runThis.bat for ease of running with one click only 
 * Did one exammple of COMPLETE FUNCTIONALITY
 * added sys.ot.pln prompt for comments
 
 *
 * todo remaining if possible and needed:
 * displaying query results in java
 * convert into a project folder and move it to another laptop by adding the dependencies -- DONE
 * changing mysql root password
 * todo ended
 *
 * ************************************************************************************
 *
 * HOW TO USE:
 * --->ENTERING DATA
 *  IMP : First open mysql workbench and click on this instance , enter password (=root)
 * (1)Double Click on project_1
 * (2)Double Click on src
 * (3)Double click on Improved
 * (4)Wait for 5 secs after open ... then look for a green arrow along the line numbers
 * (5)Click Green button
 * (6)Wait for 10 secs
 *
 *
 *
 *
 * --->VIEW DATA
 * OPEN MYSQL WORKBENCH
 *
 * JUST OPEN QUERIES FOLDER IN JBT SALES AND CLICK ON THE REQUIERED QUERY
 
 
 
 
 
 * (1) ALL THE DATA
 * New Query Tab ---  CTRL +T
 * type:
 * use my_db;
 * SELECT * from my_table
 * (then click the lightning button , the first One above the coding area)
 * ----------------------------------------------------------------------------
 * (2)A PARTICULAR GRADE :
 * New Query Tab ---  CTRL +T
 * type:
 * use my_db;
 * SELECT * from my_table WHERE GRADE =  'flexolite'
 *
 * (make sure  you have single quotes around the word)
 * (then click the lightning button , the first One above the coding area)
 *
 * (3) A PARTICULAR MONTH :
 * New Query Tab ---  CTRL +T
 * type:
 * use my_db;
 * SELECT * from my_table WHERE Date LIKE '%Jun%'
 * (Just write the first three letters of month with first letter in caps)
 * (make sure  you have single quotes around the word)
 * (then click the lightning button , the first One above the coding area)
 *
 *
 *
 * --->EXPORTING TO EXCEL IN .CSV FILE
 *
 *
 *
 *
 * *********************************************************************************
 *
 * Main project explanation
 * class ImprovedSale
 * it contains the code to
 * (1)create the sale object which will hold all the values and parts of the sale like date, grade, quantity , etc.
 * (2)It has the method to take user input
 * (3)It has the method to display / print user input.
 *
 * class Improved
 * it contains code to :
 * Find whether IGST has to applied and then respectively enter it into the database
 *
 * class dbtest
 * it contains the code to create the connection to the database
 *
 * ********************************************************************************
 * --------------------------------------------------------------------------------
 
 runThis.bat 
cd ProgramFiles
javac Improved.java
java Improved.java
pause
 */