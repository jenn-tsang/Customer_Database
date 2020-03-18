/*The CustomerDatabase class.
  Programmed by Jennifer Tsang on May 28,2019 - June 12, 2019.
  This program contains methods to add/delete a customer, sort the customers, display the customers, and read and
  write the customers onto a file.*/

//import statements
import java.io.*;
import javax.swing.*;
import java.util.*;

public class CustomerDatabase
{
    //variable declaration/assignment
    private CustomerRecord[] cr = new CustomerRecord [1000]; //creates a CustomerRecord array of 1000 elements
    private int index = -1;  //index of array

    //Construtor: When CustomerDatabase is created, the text file will be read and each line will become the parameters for the objects in the array
    public CustomerDatabase ()
    {
	//if file is not found, a message will show up indicating to the user that the file does not exist
	try
	{
	    //Declaration of objects/variables
	    BufferedReader br = new BufferedReader (new FileReader ("Customer Data.txt"));
	    StringTokenizer st;
	    String read = br.readLine (); //reads line

	    while (read != null) //reads until lines contain no info
	    {
		st = new StringTokenizer (read, "/"); //tokenizes line by "/"
		index++; //increase size every time new customer record is created
		//each new object in the array will contain the tokenized elements from the file
		cr [index] = new CustomerRecord (st.nextToken (), st.nextToken (), st.nextToken (), st.nextToken (), st.nextToken (), st.nextToken ());

		read = br.readLine ();
	    } // end while

	    br.close (); //closes file
	}
	catch (IOException e)
	{
	    JOptionPane.showMessageDialog (null, "File not found");
	}
    }


    //displays all assigned records in array
    public void displayRecord ()
    {
	String output = "";
	JTextArea ja = new JTextArea ();

	//adds info from each element in the array into the output
	for (int i = 0 ; i <= index ; i++)
	{
	    output += cr [i].getFirstName () + ", " + cr [i].getLastName () + ", " + cr [i].getAddress () + ", " + cr [i].getTelephone () + ", " + cr [i].getAge () + ", " + cr [i].getIncome () + "\n";
	}

	ja.setText (output); //set output to JTextArea object
	ja.setEditable (false);
	JOptionPane.showMessageDialog (null, ja); //prints array
    }


    //this method adds another customer into the customer records
    public void addCustomer ()
    {
	//calls helper method to get information about new customer
	String customerInfo = helpAddCustomerInput ();

	//tokenizes all information about the new customer and creates a temporary CustomerRecord with that info
	StringTokenizer st = new StringTokenizer (customerInfo, "/");
	CustomerRecord temp = new CustomerRecord (st.nextToken (), st.nextToken (), st.nextToken (), st.nextToken (), st.nextToken (), st.nextToken ());

	//creates a temporary index that holds the value of the length of the original array without the new customer
	int tempIndex = index;
	//increase index to for new customer
	index++;

	for (int i = index ; i >= 0 ; i--) //start backwards and go towards beginning of array
	{
	    //runs when the index reaches the end of the array and when the new customer's last name comes alphabetically before another customer already in the system
	    while (tempIndex >= 0 && temp.getLastName ().compareToIgnoreCase (cr [tempIndex].getLastName ()) < 0)
	    {
		cr [tempIndex + 1] = cr [tempIndex]; //pushes all customers that come alphabetically after the new customer down
		tempIndex--; //decrease tempIndex to look through each customer to see if the new customer comes before/after the customers in the array
	    } //end while
	} // end for

	//inserts the new customer into the correct position
	cr [tempIndex + 1] = temp;
    }


    //helper method that prompts user with all input necessary to create a new customer using the readLib class and separates them using a backslash
    public String helpAddCustomerInput ()
    {
	//letting user know what they are prompted for
	JOptionPane.showMessageDialog (null, "You will be prompted to enter information about the person you wish to add onto the system.");

	//creates prompts into string array
	String[] prompt = {"first name", "last name", "address", "telephone number", "age", "income"};
	String allInfo = "";

	//puts all input information into a string
	for (int i = 0 ; i < prompt.length ; i++)
	{
	    allInfo += ReadLib.readString (prompt [i]) + "/";
	}

	//returns string into addCustomer()
	return allInfo;
    }


    //this method deletes the customer from the system, if they are in the system
    public void deleteCustomer ()
    {
	//calls the help-a-method to get input for the customer that needs to be removed
	int custToDeleteIncdex = helpGetDeleteInput ();

	//if the customer index was unchanged (still -1), then the customer is not in the system
	if (custToDeleteIncdex == -1)
	    JOptionPane.showMessageDialog (null, "Customer is not found in the system."); //show client that user is not in system
	//if it changed, the customer is in the system, then it removes the customer
	else
	{
	    int pointer = -1; //declare & assign pointer

	    //creates temporary CustomerRecord of the customer that needs to be removed
	    CustomerRecord temp = cr [custToDeleteIncdex];

	    //for loop goes from beginning till end of array
	    for (int i = 0 ; i <= index ; i++)
	    {
		pointer = i; //pointer begins at the start of the array
		//if the last name of the customer that client wants to remove matches the array
		if (cr [pointer].getLastName ().equalsIgnoreCase (temp.getLastName ()))
		{
		    //for loop runs from the point where the last name matches and goes until the rest of the array
		    for (int j = pointer ; j < index ; j++)
		    {
			cr [pointer] = cr [pointer + 1]; //pushes all content of the array up, so that the name that needs to be removed is gone from the array
			pointer++; //increases pointer
		    } //inner for loop
		    cr [index] = null; //clears the info from last element
		    index--; //decreases index to indicate one less customer in the database
		    break; //exit loop once person is removed
		} //while
	    } //outer for loop
	} //else
    }


    //gets input of the last name of the customer to be removed from the system
    public int helpGetDeleteInput ()
    {
	String custLastName = ReadLib.readString ("the last name of the customer you wish to remove"); //get input
	int indexOfCustomer = -1; //index of the customer to be removed

	//checks through each element in the array to see if the inputted last name is in the system
	for (int i = 0 ; i <= index ; i++)
	{
	    //if the inputted last name is in the system, get the index of the customer and then leave the loop
	    if (custLastName.equalsIgnoreCase (cr [i].getLastName ()))
	    {
		indexOfCustomer = i;
		break;
	    } //end if
	} //end for

	//returns index of the customer
	return indexOfCustomer;
    }


    //this method prompts user to enter how they would like the database sorted - by age or income
    public void sort ()
    {
	String[] options = {"Age", "Income"};
	//prompt
	int input = JOptionPane.showOptionDialog (null, "Please select how you would like to sort the Customers", "Sorting Method", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options [0]);

	if (input == 0) //if age is chosen, array will sort by age
	    sortAge ();
	else //otherwise, array will sort by income
	    sortIncome ();
    }


    //this method creates a temporary array of the records, sorts them, and prints them
    public void sortAge ()
    {
	CustomerRecord[] temp = new CustomerRecord [index + 1]; //creates a temporary array of the same length of the original number of customer records
	System.arraycopy (cr, 0, temp, 0, index + 1); //copies array into temporary array

	CustomerRecord pivotForSorting = null; //creates pivot customer record for sorting method
	quickSortAgeHelp (pivotForSorting, temp, 0, index); //sorts records by age

	//Create JTextArea to display the sorted array
	JTextArea sortedAge = new JTextArea ();
	String output = "";
	//assigns contents of array to output string variable
	for (int i = 0 ; i <= index ; i++)
	{
	    output += temp [i].getFirstName () + ", " + temp [i].getLastName () + ", " + temp [i].getAddress () + ", " + temp [i].getTelephone () + ", " + temp [i].getAge () + ", " + temp [i].getIncome () + "\n";
	}
	sortedAge.setText (output); //setText to the contents of the array
	sortedAge.setEditable (false);
	JOptionPane.showMessageDialog (null, sortedAge); //display array sorted by age
    }


    //this method sorts the array by age using quick sort
    public void quickSortAgeHelp (CustomerRecord pivot, CustomerRecord[] a, int small, int big)
    {
	if (small < big)
	{
	    int left = small; //left = start of array
	    int right = big; //right = end of array
	    pivot = a [left]; //pivot customer record is the left element

	    while (left < right) //when left does not pass right marker, then do the rest
	    {
		//if age on the right is bigger than or equal to the pivoting customer record's age and right is still greater than left
		while (Integer.parseInt (a [right].getAge ()) >= Integer.parseInt (pivot.getAge ()) && right > left)
		{
		    right--; //decrease right pointer by 1
		}
		a [left] = a [right]; //replace the left customer record with the right customer record once the pivot's age is bigger than the right element

		//if age on the left is less than the pivoting customer record's age and right is still greater than left
		while (Integer.parseInt (a [left].getAge ()) < Integer.parseInt (pivot.getAge ()) && left < right)
		{
		    left++; //increase the left marker to continue checking subsequent elements
		}
		a [right] = a [left]; //once it isn't, then replace the right element with the contents of the left element
	    } //end of outer while loop

	    a [right] = pivot; //insert pivot point into where the pointers are
	    quickSortAgeHelp (pivot, a, small, left - 1); //recursive method - keeps sorting from the left side
	    quickSortAgeHelp (pivot, a, right + 1, big); //recursive method - keeps sorting from the right side
	}
    }


    //this method creates a temporary array of the records, sorts them, and prints them
    public void sortIncome ()
    {
	CustomerRecord[] temp = new CustomerRecord [index + 1]; //creates a temporary array of the same length of the original number of customer records
	System.arraycopy (cr, 0, temp, 0, index + 1); //copies array into temporary array

	CustomerRecord pivotForSorting = null; //creates pivot customer record for sorting method
	quickSortIncomeHelp (pivotForSorting, temp, 0, index); //sorts records by income

	JTextArea sortedIncome = new JTextArea ();
	String output = "";
	//assigns contents of array to output string variable
	for (int i = 0 ; i <= index ; i++)
	{
	    output += temp [i].getFirstName () + ", " + temp [i].getLastName () + ", " + temp [i].getAddress () + ", " + temp [i].getTelephone () + ", " + temp [i].getAge () + ", " + temp [i].getIncome () + "\n";
	}

	sortedIncome.setText (output); //setText to the contents of the array
	sortedIncome.setEditable (false);
	JOptionPane.showMessageDialog (null, sortedIncome); //display array sorted by income

    }


    //this method sorts the array by income using quick sort
    public void quickSortIncomeHelp (CustomerRecord pivot, CustomerRecord[] a, int small, int big)
    {
	if (small < big)
	{
	    int left = small; //left = start of array
	    int right = big; //right = end of array
	    pivot = a [left]; //pivot customer record is the left element

	    while (left < right) //when left does not pass right marker, then do the rest
	    {
		//if income on the right is bigger than or equal to the pivoting customer record's income and right is still greater than left
		while (Integer.parseInt (a [right].getIncome ()) >= Integer.parseInt (pivot.getIncome ()) && right > left)
		{
		    right--; //decrease right pointer by 1
		}
		a [left] = a [right]; //replace the left customer record with the right customer record once the pivot's age is bigger than the right element

		//if income on the left is less than the pivoting customer record's income and right is still greater than left
		while (Integer.parseInt (a [left].getIncome ()) < Integer.parseInt (pivot.getIncome ()) && left < right)
		{
		    left++; //increase left marker
		}
		a [right] = a [left]; //once it isn't, then replace the right element with the contents of the left element
	    } //end of outer while loop

	    a [right] = pivot; //insert pivot point into where the pointers are
	    quickSortIncomeHelp (pivot, a, small, left - 1); //recursive method - keeps sorting from the left side
	    quickSortIncomeHelp (pivot, a, right + 1, big); //recursive method - keeps sorting from the right side
	}
    }


    //this method stores the records by writing them back onto the original file
    public void store ()
    {
	String print = "";
	//if file is not found, "No file found" will be displayed onto screen
	try
	{
	    PrintWriter pw = new PrintWriter (new FileWriter ("Customer Data.txt")); //create new PrintWriter object
	    //adds customer record onto the string variable and writes each line individually onto the file
	    for (int i = 0 ; i <= index ; i++)
	    {
		print = cr [i].getFirstName () + "/" + cr [i].getLastName () + "/" + cr [i].getAddress () + "/" + cr [i].getTelephone () + "/" + cr [i].getAge () + "/" + cr [i].getIncome ();
		pw.println (print);
	    }

	    pw.close (); //closes file
	}
	catch (IOException e)
	{
	    JOptionPane.showMessageDialog (null, "No file found");
	}

	System.exit (0); //exit program
    }
} //CustomerDatabase class
