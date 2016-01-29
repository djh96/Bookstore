/*Daniel Hodgson
CS 0401
This code will represent a bookstore that sells books, bookmarks, and paintings of books.
It will have features such as changing order, seeing current order, checking out, and paying.
*/
import java.util.Scanner; //Imports the scanner class
import java.io.*;         //Imports the input output wildcard
public class Bookstore    //Creates class called Bookstore
{
  //The following declarations declare private variables outside of the main method so they can be used throughout all of the methods
  private static String personalize = "";   //Personalize variable will store a string if the user wants to personalize their book
  private static int counter1 = 0, counter2 = 0;    //Counter variables to ensure that inventory and buyBooks are matched together
  private static int personalizeCounter = 0;      //Counter to tell the checkout method if the user personalized their book
  private static int books = 0;   //Stores number of books bought
  private static int bookmarks = 0;   //Stores number of bookmarks bought
  private static int paintings = 0;   //Stores number of paintings bought
  private static int customer = 1;    //Stores what number each customer is
  private static double discount = 0.0;   //Stores the discount
  private static double tax = 0.0;        //Stores the tax
  private static double bookCost = 0.0;   //Stores the cost of the books
  private static double bookmarkCost = 0.0;   //Stores the cost of the bookmarks
  private static double paintingsCost = 0.0;  //Stores the cost of the paintings
  private static double totalCost = 0.0;      //Stores the total cost
  private static double userPaid = 0.0;   //Stores how much money the user is paying with

  //Main method, throws IOException for files
  public static void main(String[] args)  throws IOException
  {
    System.out.println("Welcome to the bookstore!");  //Prints a greeting to the user
    runProgram();   //Calls the method that will run the bulk of the program
  }
  //showMenu will print out the menu and then go back to the runProgram method
  public static void showMenu()
  {
    //Prints items and costs on the menu
  	System.out.println("We sell: ");
  	System.out.println("Books for $5.00 each");
  	System.out.println("Bookmarks for $1.00 each, or a pack of six for $5.00");
  	System.out.println("Paintings of Books for $100.00 each");
    runProgram();   //Calls the method that will run the bulk of the program
  }
  //buyBooks method will allow the user to buy books
  public static void buyBooks()
  {
    counter1++; //First part of counter

    //Catches any exceptions thrown by inventory
    try
    {
      inventory(); //Calls the inventory method
    }
    catch(IOException e)
    {

    }
    
   	Scanner keyboard = new Scanner(System.in);    //Creates scanner object called keyboard
    if(counter1 == counter2)  //If the counters are equal, that means there is a book that can be personalized. If they aren't then there is no book to be personalizes
    {
          //Asks the user if they want to personalize the book and tells them the appropriate response
      System.out.println("Would you like to personalize a book for an additional $1.00? You may only have one. \nType yes if you do.");
      String input = keyboard.nextLine();   //Stores keyboard input that will be yes if the user wants to or anything else if not

      if(input.charAt(0) == 'y' || input.charAt(0) == 'Y')    //If the first letter of the user's input is y, lower or upper case
      {
        System.out.println("What would you like it to say?"); //Asks what the personal message will be.
        personalize = keyboard.nextLine();    //Stores the personal message
        personalizeCounter = 1;   //Sets counter so later the program will know there is a personalization
      }
    }
    //Sets the counters back to 0
    counter1 = 0;
    counter2 = 0;
    runProgram();   //Calls the method that will run the bulk of the program
  }
  //buyBookmarks method will allow the user to buy bookmarks
  public static void buyBookmarks()
  {
  	System.out.println("How many bookmarks would you like to buy?"); //Asks the user how many bookmarks
  	Scanner keyboard = new Scanner(System.in);    //Creates scanner object called keyboard
    bookmarks = keyboard.nextInt();   //Stores the number of bookmarks the user wants to buy into bookmarks
    if(bookmarks < 0)   //If the user tries to buy a negative number of bookmarks
    {
      System.out.println("You can't buy negative bookmarks!");  //Tells the user that is not a valid input
      buyBookmarks();   //Calls buyBookmarks method that will let the user re-enter the number of bookmarks they want to buy
    }
    runProgram();   //Calls the method that will run the bulk of the program
  }
  //buyPaintings method will allow the user to buy paintings
  public static void buyPaintings()
  {
  	System.out.println("How many paintings would you like to buy?");   //Asks how many paintings the user wants to buy
  	Scanner keyboard = new Scanner(System.in);    //Creates scanner object called keyboard
  	paintings = keyboard.nextInt();    //Stores the number of paintings the user wants to buy into paintings
    if(paintings < 0)   //If the user tries to buy a negative number of paintings
    {
      System.out.println("You can't buy negative paintings!");    //Tells the user that is not a valid input
      buyPaintings();   //Calls buyPaintings method that will let the user re-enter the number of paintings they want to buy
    }
    runProgram();   //Calls the method that will run the bulk of the program
  }
  //calculateCost method will calculate the total cost
  public static void calculateCost()
  {
    bookCost = books * 5.00;    //Books are $5.00
    paintingsCost = paintings * 100.00;   //Paintings are $100.00
    if(personalizeCounter == 1) //If the user personalize a book
    {
      bookCost += 1.00;    //The total book cost will go up one dollar
    }

    if(bookmarks >=6)   //If there are enough bookmarks to make a pack
    {
      int packs = bookmarks/6;  //Store number of packs as an integer so it rounds down to an integer number of packs
      bookmarkCost = (packs*5.00)+((bookmarks-(packs*6) * 1.00));   //The number of packs are $5.00 each, then the remaining bookmarks are $1.00 each
    }
    else    //If there are not enough bookmarks to make a pack
    {
      bookmarkCost = 1.00 * bookmarks;  //Each bookmark is $1.00
    }

    totalCost = (bookCost + bookmarkCost + paintingsCost);  //Total cost equals cost of books, bookmarks, and paintings combined
    checkIfThird();   //Calls method to check if this is a third customer
  }
  //checkIfThird method will keep a running total of what number customer the program is on and give the user a discount if they are one of every third customer
  public static void checkIfThird()
  {
    System.out.println("We are running a promotion that every third customer gets 10% off!");   //Tell the user about the promotion

    if(customer % 3 == 0)   //If this is customer's number is divisible by 3
    {
      totalCost *= .90; //90% discount on the total cost
      System.out.println("You won a 10% discount!");  //Tell the user they won the discount
      discount = (totalCost/.90)-totalCost;   //Calculate the amount of the discount
      System.out.printf("You get a discount of $%.2f (before tax)\n", discount);    //Tell the user their discount
      //Calculate the new prices of each item ordered
      bookmarkCost *=.90;
      bookCost *=.90;
      paintingsCost *=.90;
    }
    else  //If the user is not a third customer
    {
      System.out.println("You did not get a discount! Better luck next time!");   //Tell the user they did not get 
    }
    customer++;   //Increase the number that the next customer will be
  }
  //moreCustomer method will ask if there are more customers
  public static void moreCustomer()
  {
    //Asks the user if there are more customers and tells them valid inputs
    System.out.println("Are there any more customers in line?");
    System.out.println("If there are, please type 1, otherwise type 2");

    Scanner keyboard = new Scanner(System.in);    //Creates scanner object called keyboard
    int decision = keyboard.nextInt();            //User's decision is stores from the keyboard
    if(decision == 1)   //If the user says there is another customer
    {
      //Reset the number of books, bookmarks, paintings, and the personalizeCounter
      books = 0;
      bookmarks = 0;
      paintings = 0;
      personalizeCounter = 0;
      runProgram();   //Calls the method that will run the program again
    }
    else if(decision == 2)    //If the user says there are no more customers in line
    {
      System.out.println("Thanks for shopping!");   //Thanks the user for shopping
      System.exit(0);   //Exits the program
    }
    else    //If the user did not enter a valid number
    {
      System.out.println("That is not a 1 or 2, please type 1 or 2");     //Tell the user what a valid response is
      moreCustomer();   //Repeats the method so the user can put in a valid input
    }
  }
  //runProgram method is helpful because it can be accessed and rerun from any other method unlike main.  It will give the user options of what they can do
  public static void runProgram()
  {
    //Tells the user their options
    System.out.println("1 - Buy something");
    System.out.println("2 - See current order");
    System.out.println("3 - See menu");
    System.out.println("4 - Checkout");

    Scanner keyboard = new Scanner(System.in);    //Creates scanner object called keyboard
    int input = keyboard.nextInt();               //Input will be stored from the keyboard

    if(input == 1)  //If the user wants to buy somthing
    {
      buySomething();   //The user will be able to buy something
    }
    else if(input == 2)   //If the user wants to see the current order
    {
      currentOrder();   //Shows the user their current order
    }
    else if(input == 3)   //If the user wants to see the menu
    {
      showMenu();     //Shows the user the menu
    }
    else if(input == 4)   //If the user wants to checkout
    {
      checkOut();   //The user will be able to checkout
    }
    else    //If the user gives an invalid input
    {
      System.out.println("That is not a valid option.  Please type a valid option");  //Tells the user 
      runProgram();   //Calls the method that will let the user enter a valid input again
    }
  }
  //currentOrder method prints the current order to the user
  public static void currentOrder()
  {
    System.out.println("You currently have " + books + " books, " + bookmarks +
      " bookmarks, and " + paintings + " paintings.");  //Prings the current order
    runProgram();   //Calls the method that will run the bulk of the program
  }
  //checkOut method will let the user check out
  public static void checkOut()
  {
    calculateCost();    //Calculate the cost to be displayed in the receipt
    bookwormCard();     //Ask if the user has a bookworm card

    tax = (totalCost*1.07)-totalCost;   //Calculates the tax

    System.out.println("Here is your order:\n");    //Prints where the order begins
    if(!(books == 0))   //If there were books bought
    {
      System.out.printf(books + " books for a cost of $%.2f.\n", bookCost);   //Displays the number of books and book cost
      if(personalizeCounter == 1)   //If the the user personalized a book
      {
        System.out.println("Including one personalized book with the message:\n");    //Prompts the personalized message
        System.out.println(personalize + "\n");   //Prints the personalized message
      }
    }
    if(!(bookmarks == 0))   //If bookmarks were bought
    {
      System.out.printf(bookmarks + " bookmarks for a cost of $%.2f.\n", bookmarkCost); //Display the number of bookmarks and bookmark cost
    }
    if(!(paintings == 0))   //If paintings were bought
    {
      System.out.printf(paintings + " paintings for a cost of $%.2f.\n", paintingsCost);    //Display the number of paintings and paintings
    }        
    System.out.printf("Your subtotal is $%.2f\n", totalCost);     //Displays the subtotal
    System.out.printf("Your tax is $%.2f\n", tax);                //Displays the tax

    totalCost += tax;   //Add the tax to the total cost

    System.out.printf("Your total cost is $%.2f\n\n", totalCost);   //Displays the total cost

    pays();   //Lets the user pay
    moreCustomer();   //Calls method to see if there are more customers
  }
  //pays method will let the user pay
  public static void pays()
  {
    System.out.println("How much money are you paying with?");    //Asks the user how much money they are paying with
    Scanner keyboard = new Scanner(System.in);    //Creates scanner object called keyboard
    userPaid = keyboard.nextDouble();             //userPaid represents the keyboard input of how much the user is paying with
    double change = userPaid-totalCost;           //Calculates the change

    while((totalCost-userPaid)>0.01 || (totalCost>userPaid))    //While the what the user paid with is not enough and greater than the margin of 1 cent
    {
      System.out.println("Not enough money - please re-enter");     //Tell the user they didn't pay with enough money
      userPaid = keyboard.nextDouble();   //Let the user re enter a value
      change = userPaid-totalCost;        //Calculates the change
    }
    if(totalCost>userPaid)    //If the user paid with not enough money but was less than the margin of 1 cent, the change is negligible so it is 0.
    {
      change = 0.0;
    }
    System.out.printf("You paid with $%.2f so your change is $%.2f.\n", userPaid, change);  //Print out how much the user paid with and the change
  }
  //bookwormCard method ask the user if they have a bookworm card
  public static void bookwormCard()
  {
    System.out.println("Do you have a Bookworm Card? Type yes if you do.");   //Asks the user if they have a bookworm card
    Scanner keyboard = new Scanner(System.in);    //Creates scanner object called keyboard
    String input = keyboard.nextLine();           //Input is what the user types

    if(input.charAt(0) == 'y' || input.charAt(0) == 'Y')    //If the first letter of the user's input is y, lower or upper case (if the user said yes)
    {
      System.out.println("Thank you, a 25% discount has been applied.");    //Tell the user they got 25% off
      totalCost *= .75;   //Give the user the 25% discount
      
      discount += (totalCost/.75)-totalCost;    //Calculate the discount
      System.out.printf("You get a discount of $%.2f\n", discount);   //Tell the user what their discount was

      //Calculate the discounts for each item bought
      bookmarkCost *=.75;
      bookCost *=.75;
      paintingsCost *=.75;
    } 
    discount = 0.0;  //Reset the discount for the next user after it has been displayed
  }
  //buySometing method lets the user buy something
  public static void buySomething()
  {
      Scanner keyboard = new Scanner(System.in);    //Creates scanner object called keyboard

      //Ask the user what they want to buy and give them options
      System.out.println("What would you like to buy?");
      System.out.println("1 - Books");
      System.out.println("2 - Bookmarks");
      System.out.println("3 - Paintings");
      
      int decision = keyboard.nextInt();  //Stores what the user wants to buy in a variable

      if(decision == 1)   //If the user wants to buy books
      {
        buyBooks();   //Call the method to let the user buy books
      }
      else if(decision == 2)    //If the user wants to buy bookmarks
      {
        buyBookmarks();   //Let the user buy bookmarks
      }
      else if(decision == 3)    //If the user wants to buy paintings
      {
        buyPaintings();   //Let the user buy paintings
      }
      else    //If the user enters an invalid input
      {
        System.out.println("That is not a valid option.  Please type a valid option");    //Tell the user their input is invalid
        buySomething();   //Let the user buy something again
      }
  }
  //inventory method will show the user the inventory and let them buy books
  public static void inventory() throws IOException
  {
    //Ask the user if they want to see the inventory and teh appropriate responses
    System.out.println("Would you like to see our inventory? Type yes if you do.");
    System.out.println("Note: Saying anything else will not let you buy a book.");
    Scanner keyboard = new Scanner(System.in);    //Creates scanner object called keyboard
    String input = keyboard.nextLine();           //Stores the user's input

    File myFile = new File("Bookstore.txt");        //Open the file "Bookstore.txt"
    Scanner inputFile = new Scanner(myFile);        //Create scanner object called inputFile that will take input from the file
    String str = "";                                //Create a string to hold and then display books
    if(input.charAt(0) == 'y' || input.charAt(0) == 'Y')    //If the first letter of the user's input is y, lower or upper case
    {
      while(inputFile.hasNext())                   //While the file has another line, it will repeat
      {
        str = inputFile.nextLine();             //Stores each line as a String object
        System.out.println(str);                //Prints that string object
      }
      inputFile.close();  //Close the file
      
      myFile = new File("Bookstore.txt");         //Reopen the file to start it at the beginning
      inputFile = new Scanner(myFile);            //Reset inputFile to read input from myFile
      System.out.println("What book would you like to buy?");   //Ask the user what book they would like to buy
      input = keyboard.nextLine();    //Stores input from the user

      while(inputFile.hasNext())    //While there are more lines in the file OR for every line of the file
      {
        if(input.equalsIgnoreCase(inputFile.nextLine()))    //If what the user inputted for the book they want to buy is equal to a line in the file
        {
          System.out.println("How many copies would you like to buy?");   //Ask the user how many copies they want to buy
          int input2 = keyboard.nextInt();    //Store the user's input
          if(input2 >=0)    //If the user enters a feasible number of books
          {
            books += input2;    //Add that number of books to the book total
            counter2++;         //Make counter2 match counter1 
          }
          else if(input2 < 0)   //If the user tries to enter negative books
          {
            System.out.println("You can't buy negative books!");    //Tell the user they can't buy negative books
            inventory();    //Reset the method
          }
        }
      }
      if(counter1 != counter2)    //If the user entered a book not in the inventory, the counters will be off
      {
        System.out.println("Sorry, we don't have that book in stock."); //Tell the user that book is not in stock
        inventory();    //Reset the inventory method
      }
    }
    inputFile.close();    //Close the file
  }
}