package ePortfolio;

/** 
 * Represents a stock
 * @author Quinn Meiszinger
 * @version 3.0
 * @since 1.0
 */
public class Stock extends Investment
{
     
    /**
     * Constructor for the Stock class using appropiate user data
     * @param symbol The symbol of the Stock
     * @param name The name of the Stock
     * @param quantity The quantity (integer) of the Stock
     * @param price The price (double) of the Stock
     */
    public Stock(String symbol, String name, int quantity, double price) throws Exception
    {//Constructor that takes required data and calculates bookValue and gain
        
        super(symbol, name, quantity, price);
        setBookValue( quantity * price + 9.99 );
        setGain( (quantity * price - 9.99) - getBookValue() );

    }

    /**
     * No argument constructor for Stock
     */
    public Stock()
    {//No argument constructor

       super();
         
    }

    /**
     * Copy constructor for Stock
     * @param stock Other Stock to copy from
     */
    public Stock(Stock stock)
    {//Copy constructor

        super(stock);
        try
        {
            setBookValue( stock.getQuantity() * stock.getPrice() + 9.99 );
            setGain( (stock.getQuantity() * stock.getPrice() - 9.99) - getBookValue() );
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Buys a specific quantity of this Stock for a specififc price
     * @param quantity The quantity (integer) the user wishes to buy
     * @param price The price (double) the user wishes to buy at
     */
    public void buy(int quantity, double price)
    {//Method for buying more of this specific stock

        try
        {

            //Update quantity and price to their new values
            setQuantity(getQuantity() + quantity);
            setPrice(price);

            //Calculate the new book value
            setBookValue(getBookValue() + quantity * price + 9.99);

            //Calculate the gain of the stock
            setGain( (quantity * price - 9.99) - getBookValue() );

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return;
        }

    }

    /**
     * Sells a specific quantity of this Stock for a specific price
     * @param quantity The quantity (integer) the user wishes to sell
     * @param price The price (double) the user wishes to sell at
     * @return Returns the amount the Stock sold for
     */
    public double sell(int quantity, double price)
    {//Method for selling this specific stock

        try
        {

            if(getQuantity() - quantity <= 0.0)
            {//If the user is selling all of the stock, calculate gain and return the payment

                double payment = (quantity * price - 9.99);
                setGain( payment - getBookValue() );
                return payment;

            }

            //Calculate the new book value
            setBookValue( getBookValue() * ( ( (double)getQuantity() - (double)quantity ) / (double)getQuantity() ) );

            //Update quantity and price variables
            setQuantity( getQuantity() - quantity );
            setPrice( price );

            //Calculate the new gain
            setGain( (getQuantity() * getPrice() - 9.99  - getBookValue()) );

            //Calculate and return the payment
            return quantity * price - 9.99;

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return 0.0;
        }

    }

    /**
     * Mutator method for quantity
     * @param quantity The new quantity of the Stock
     * @return A boolean value indicating whether the operation was successful
     */
    public boolean setQuantity(int quantity) throws Exception
    {//Mutator method for quantity

        if(quantity < 0)
        {//Check if user input is valid, if not return false
            throw new Exception("[EXCEPTION]: Quantity less than 0");
        }
        else
        {//If user input is valid, set quantity and return true
            super.setQuantity( quantity );
            setBookValue( getQuantity() * getPrice() + 9.99);
            setGain( (getQuantity() * getPrice() - 9.99) - getBookValue() );
            return true;
        }

    }

    /**
     * Mutator method for price
     * @param price The new price of the Stock
     * @return A boolean value indicating whether the operation was successful
     */
    public boolean setPrice(double price) throws Exception
    {//Mutator method for updating price

        if(price < 0.0)
        {//Check if user input is valid, if not return false
            throw new Exception("[EXCEPTION]: Price less than 0");
        }
        else
        {//If user input is valid, set price to new price and calculate the new gain
            super.setPrice( price );
            setGain( (getQuantity() * getPrice() - 9.99) - getBookValue() );
            return true;
        }

    }

    /**
     * Returns a string containing all information of the Stock
     */
    public String toString()
    {//Return a string containing all the information in the mututal fund

        return "symbol: " + getSymbol() + '\n' +
               "name: " + getName() + '\n' +
               "quantity: " + getQuantity() + '\n' +
               "price: $" + (double)Math.round(getPrice() * 100) / 100 + '\n' +
               "bookValue: $" + (double)Math.round(getBookValue() * 100) / 100 + '\n' +
               "gain: $" + (double)Math.round(getGain() * 100) / 100 + '\n';

    }

    /**
     * Checks for equality against another instance of Stock
     * @param otherObject The other Stock to check for equality
     * @return A boolean value indicating whether they are equal or not
     */
    public boolean equals(Object otherObject)
    {//Check if this stock is equal to another stock

        if(otherObject == null)
        {//If other is null, return false
            return false;
        }
        else if(getClass() != otherObject.getClass())
        {//If there is a class type mismatch, return false
            return false;
        }
        else
        {//Check if each variable is equal to each other and return the boolaen value

            //Cast other object as Stock
            Stock other = (Stock)otherObject;

            //Check all values and return boolean value
            return getSymbol().equals(other.getSymbol()) &&
                   getName().equals(other.getName()) &&
                   getQuantity() == other.getQuantity() &&
                   getPrice() == other.getPrice() &&
                   getBookValue() == other.getBookValue() &&
                   getGain() == other.getGain();

        }

    }
  
}