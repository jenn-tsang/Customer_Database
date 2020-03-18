/*The CustomerRecord Class.
  Programmed by Jennifer Tsang on May 28, 2019 - June 12, 2019.
  This program defines and encapsulates a customer's first name, last name, address,
  telephone number, age, and income. It makes all fields accessible through instance methods.*/

public class CustomerRecord
{
    //variable declaration
    private String firstName, lastName, address, telephone, age, income;

    //constructor
    public CustomerRecord (String firstName, String lastName, String address, String telephone, String age, String income)
    {
	//gets input and assigns it to the variables
	this.firstName = firstName;
	this.lastName = lastName;
	this.address = address;
	this.telephone = telephone;
	this.age = age;
	this.income = income;
    }


    //returns first name of customer
    public String getFirstName ()
    {
	return firstName;
    }


    //returns last name of customer
    public String getLastName ()
    {
	return lastName;
    }


    //returns address of customer
    public String getAddress ()
    {
	return address;
    }


    //returns telephone number of customer
    public String getTelephone ()
    {
	return telephone;
    }


    //returns age of customer
    public String getAge ()
    {
	return age;
    }


    //returns income of customer
    public String getIncome ()
    {
	return income;
    }
} // CustomerRecord class
