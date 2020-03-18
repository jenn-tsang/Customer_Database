/* The Graphical User Interface class.
  Programmed by Jennifer Tsang on May 28, 2019 - June 12, 2019
  This program creates a GUI to display buttons, images, and texts for visual appeal*/

//import statements
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI implements ActionListener
{
    //Variable Declaration
    private JButton display, add, remove, sort, exit; //create buttons
    CustomerDatabase cd = new CustomerDatabase (); //creates CustomerDatabase

    public GUI ()
    {
	//assigns buttons
	display = new JButton ("Display Records");
	add = new JButton ("Insert Customer");
	remove = new JButton ("Remove Customer");
	sort = new JButton ("Sort Records");
	exit = new JButton ("Exit Program");

	//changes the background and text colour of each button to red and white, respectively
	display.setOpaque (true);
	display.setBorderPainted (false);
	display.setBackground (Color.red);
	display.setForeground (Color.white);
	add.setOpaque (true);
	add.setBorderPainted (false);
	add.setBackground (Color.red);
	add.setForeground (Color.white);
	remove.setOpaque (true);
	remove.setBorderPainted (false);
	remove.setBackground (Color.red);
	remove.setForeground (Color.white);
	sort.setOpaque (true);
	sort.setBorderPainted (false);
	sort.setBackground (Color.red);
	sort.setForeground (Color.white);
	exit.setOpaque (true);
	exit.setBorderPainted (false);
	exit.setBackground (Color.red);
	exit.setForeground (Color.white);

	//registers when certain buttons are clicked
	display.addActionListener (this);
	add.addActionListener (this);
	remove.addActionListener (this);
	sort.addActionListener (this);
	exit.addActionListener (this);

	//Creates label and displays title using a created font
	JLabel title = new JLabel (" Hello valued customer! Welcome to The Tsang Travel Agency! :)");
	Font f1 = new Font ("Lucida Calligraphy", Font.BOLD, 19);
	title.setFont (f1);
	title.setOpaque (true);
	title.setBackground (Color.darkGray); //background color
	title.setForeground (Color.white); //changes text color

	//Adds gif image of a plane taking off
	JLabel image = new JLabel (); //creates label
	ImageIcon img = new ImageIcon ("plane.gif");
	image.setIcon (img);

	//creates label with text that says the corporation name
	JLabel corpName = new JLabel ("      Brought to you by ® TSA - Tsang Sky Agency");

	//adds image, buttons, and texts into JPanel
	JPanel panel = new JPanel (); //creates new panel
	panel.add (corpName);
	panel.add (title);
	panel.add (image);
	panel.add (display);
	panel.add (add);
	panel.add (remove);
	panel.add (sort);
	panel.add (exit);
	panel.setBackground (Color.pink);

	//sets JFrame and makes it visible
	JFrame frame = new JFrame ("The Tsang Travel Agency");
	frame.setContentPane (panel);
	frame.setBounds (820, 100, 730, 600);
	frame.setVisible (true);
	frame.setResizable (false);
	frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

	//sets dimensions for buttons, text, and the image
	corpName.setBounds (230, 15, 280, 20);
	title.setBounds (0, 70, 720, 50);
	image.setBounds (35, 0, 800, 800);
	display.setBounds (530, 165, 150, 30);
	add.setBounds (530, 240, 150, 30);
	remove.setBounds (530, 315, 150, 30);
	sort.setBounds (530, 390, 150, 30);
	exit.setBounds (530, 465, 150, 30);

    } //constructor


    //if certain buttons are clicked, the specific function the button is supposed to do, will run
    public void actionPerformed (ActionEvent e)
    {
	if (e.getSource () == display) //displays records when display button is clicked
	    cd.displayRecord ();
	else if (e.getSource () == add) //adds customer into the database when add button is clicked
	    cd.addCustomer ();
	else if (e.getSource () == remove) //deletes a customer from database when remove button is clicked
	    cd.deleteCustomer ();
	else if (e.getSource () == sort) //sorts records depending on customer's choice when sort button is clicked
	    cd.sort ();
	else if (e.getSource () == exit) //stores and exits program when exit button is clicked
	    cd.store ();
    }
} //GUI class
