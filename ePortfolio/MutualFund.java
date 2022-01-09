package ePortfolio;

/** 
 * Represents a Mutual Fund
 * @author Quinn Meiszinger
 * @version 3.0
 * @since 1.0
 */
public class MutualFund extends Investment
{

    /**
     * Constructor for the MutualFund class using appropiate user data
     * @param symbol The symbol of the MutualFund
     * @param name The name of the MutualFund
     * @param quantity The quantity (integer) of the MutualFund
     * @param price The price (double) of the MutualFund
     */
    public MutualFund(String symbol, String name, int quantity, double price) throws Exception
    {//Constructor that takes required data and calculates bookvalue and gain

        super(symbol, name, quantity, price);
        setBookValue(price * quantity);
        setGain( (quantity * price - 45.00) - getBookValue() );

    }

    /**
     * No argument constructor for MutualFund
     */
    public MutualFund()
    {//No argument constructor

        super();

    }

    /**
     * Copy constructor for MutualFund
     * @param mutualFund Other MutualFund to copy from
     */
    public MutualFund(MutualFund mutualFund)
    {//Copy constructor

        super(mutualFund);
        try
        {
            setBookValue(mutualFund.getPrice() * mutualFund.getQuantity());
            setGain( (mutualFund.getQuantity() * mutualFund.getPrice() - 45.00) - getBookValue() );
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }   

    }

    /**
     * Buys a specific quantity of this MutualFund for a specific price
     * @param quantity The quantity (integer) the user wishes to buy
     * @param price The price (double) the user wishes to buy at
     */
    public void buy(int quantity, double price)
    {//Method for buying more of this specfic mutual fund

        try
        {

            //Update quantity and price to their new values
            setQuantity( getQuantity() + quantity );
            setPrice(price);

            //Calculate the new book value
            setBookValue( getBookValue() + (price * quantity) );

            //Calculate the gain of the mutual fund
            setGain( (getQuantity() * getPrice() - 45.0) - getBookValue() );

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return;
        }

    }
    
    /**
     * Sells a specific quantity of this MutualFund for a specific price
     * @param quantity The quantity (integer) the user wishes to sell
     * @param price The price (double) the user wishes to sell at
     * @return Returns the amount the MutualFund sold for
     */
    public double sell(int quantity, double price)
    {//Method for selling this specific mutual fund

        try
        {

            if(getQuantity() - quantity <= 0.0)
            {//If the user is selling all of the mutual fund, calculate gain and return the payment

                double payment = quantity * price - 45.0;
                setGain( payment - getBookValue() );
                return payment;

            }

            //Calculate the new book value
            setBookValue( getBookValue() * ( ( (double)getQuantity() - (double)quantity ) / (double)getQuantity() ) );

            //Update quantity and price variables
            setQuantity( getQuantity() - quantity );
            setPrice(price);

            //Calculate the new gain
            setGain( (getQuantity() * getPrice() - 45.0) - getBookValue() );

            //Calculate and return the payment
            return quantity * price - 45.0;

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return 0.0;
        }

    }

    /**
     * Mutator method for quantity
     * @param quantity The new quantity of the MutualFund
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
            setBookValue( getQuantity() * getPrice() );
            setGain( (getQuantity() * getPrice() - 45.0) - getBookValue() );
            return true;
        }

    }

    /**
     * Mutator method for price
     * @param price The new price of the MutualFund
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
            super.setPrice(price);
            setGain( (getQuantity() * getPrice() - 45.0) - getBookValue() );
            return true;
        }

    }

    /**
     * Returns a string containing all information of the MutualFund
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
     * Checks for equality against another instance of MutualFund
     * @param otherObject The other MutualFund to check for equality
     * @return A boolean value indicating whether they are equal or not
     */
    public boolean equals(Object otherObject)
    {//Check if this mututal fund is equal to another mutual fund

        if(otherObject == null)
        {//If other is null, return false
            return false;
        }
        else if(getClass() != otherObject.getClass())
        {//If there is a class type mismatch, return false
            return false;
        }
        else
        {//Check if each variable is equal to each other and return the boolean value

            //Cast other object to MutualFund
            MutualFund other = (MutualFund)otherObject;

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