package ePortfolio;

import java.io.EOFException;

/**
 * Represents an investment
 * @author Quinn Meiszinger
 * @version 3.0
 * @since 2.0
 */
public abstract class Investment {

    //Class variables to keep track of necessary data
    private String symbol;
    private String name;
    private int quantity;
    private double price;
    private double bookValue;
    private double gain;

    /**
     * Constructor for the Investment class using appropiate data
     * @param symbol The symbol of the Investment
     * @param name The name of the investment
     * @param quantity The quantity of the investment
     * @param price The price of the investment 
     */
    public Investment(String symbol, String name, int quantity, double price) throws Exception
    {

        if(symbol == null || symbol.equals("") || symbol.equals("[ ]+"))
        {//Throw exception if symbol is empty
            throw new Exception("[EXCEPTION]: No input for symbol");
        }
        else
        {//Set symbol if valid
            this.symbol = symbol;
        }

        if(name == null || name.equals("") || name.equals("[ ]+"))
        {//Throw exception if name is empty
            throw new Exception("[EXCEPTION]: No input for name");
        }
        else
        {//Set name if valid
            this.name = name;
        }

        if(quantity <= 0)
        {//Throw exception if quantity less than or equal to 0
            throw new Exception("[EXCEPTION]: Quantity less than or equal to 0");
        }
        else
        {//Set quantity if valid
            this.quantity = quantity;
        }
        
        if(price < 0)
        {//Throw exception if price less than 0
            throw new Exception("[EXCEPTION]: Price less than 0");
        }
        else
        {//Set price if valid
            this.price = price;
        }

        //Default bookValue and gain
        bookValue = 0;
        gain = 0;

    }

    /**
     * No argument constructor for the Investment class
     */
    public Investment()
    {
        symbol = "";
        name = "";
        quantity = 0;
        price = 0;
        bookValue = 0;
        gain = 0;
    }

    /**
     * Copy constructor for Investment
     * @param investment Other Investment to copy from
     */
    public Investment(Investment investment)
    {
        symbol = investment.getSymbol();
        name = investment.getName();
        quantity = investment.getQuantity();
        price = investment.getPrice();
        bookValue = investment.getBookValue();
        gain = investment.getGain();
    }

    /**
     * Abstract method for buy()
     */
    public abstract void buy(int quantity, double price);

    /**
     * Abstract method for sell()
     * @return payment for amount sold
     */
    public abstract double sell(int quantity, double price);

    /**
     * Mutator method for Investment symbol
     * @param symbol The new symbol of the Investment
     * @return A boolean value indiciating whether the operation was successful
     */
    public boolean setSymbol(String symbol) throws Exception
    {//Mutator method for symbol

        if(symbol.equals("") || symbol == null)
        {//Check if user string is empty of equals null, if so return false
            throw new Exception("[EXCEPTION]: Input for symbol is empty");
        }
        else
        {//If string is valid, set the symbol and return true
            this.symbol = symbol;
            return true;
        }

    }

    /**
     * Mutator method for Investment name
     * @param name The new name of the Investment
     * @return A boolean value indiciating whether the operation was successful
     */
    public boolean setName(String name) throws Exception
    {//Mutator method for name

        if(name.equals("") || name == null)
        {//Check if user string is empty of null, if so return false
            throw new Exception("[EXCEPTION]: Input for name is empty");
        }
        else
        {//If string is valid, set the symbol and return true
            this.name = name;
            return true;
        }

    }

    /**
     * Mutator method for Investment quantity
     * @param quantity The new quantity of the Investment
     * @return A boolean value indicating whether the operation was successful
     */
    public boolean setQuantity(int quantity) throws Exception
    {//Mutator method for quantity

        if(quantity >= 0)
        {//If user input is valid (greater than or equal to 0) set quantity and return true
            this.quantity = quantity;
            return true;
        }
        else
        {//If user input is invalid return false
            throw new Exception("[EXCEPTION]: Input for quantity less than 0");
        }

    }

    /**
     * Mutator method for Investment price
     * @param price The new price of the Investment
     * @return A boolean value indicating whether the operation was successful
     */
    public boolean setPrice(double price) throws Exception
    {//Mutator method for price

        if(price >= 0.0)
        {//If user input is valid (greater than or equal to 0) set price and return true
            this.price = price;
            return true;
        }
        else
        {//If user input is invalid return false
            throw new Exception("[EXCEPTION]: Input for price is less than 0");
        }

    }

    /**
     * Mutator method for Investment bookValue
     * @param bookValue The new bookValue of the Investment
     * @return A boolean value indicating whether the operation was successful
     */
    public boolean setBookValue(double bookValue) throws Exception
    {//Mutator method for bookValue

        if(bookValue >= 0.0)
        {//If user input is valid (greater than or equal to 0) set bookValue and return true
            this.bookValue = bookValue;
            return true;
        }
        else
        {//If user input is invalid return false
            throw new Exception("[EXCEPTION]: Book value less than 0");
        }

    }

    /**
     * Mutator method for Investment gain
     * @param gain The new gain of the Investment
     * @return A boolean value indicating whether the operation was successful
     */
    public boolean setGain(double gain) throws Exception
    {//Mutator method for gain

        try
        {//Attempt to set gain and return true if successful
            this.gain = gain;
            return true;  
        }
        catch(NumberFormatException e)
        {//Catch number format exception and return false
            throw new Exception("[EXCEPTION]: NumberFormatException");
        }

    }

    /**
     * Accessor method for symbol
     * @return The symbol of the Investment
     */
    public String getSymbol()
    {//Accessor method for symbol
        return( new String(symbol) );
    }

    /**
     * Accessor method for name
     * @return The name of the Investment
     */
    public String getName()
    {//Accessor method for name
        return( new String(name) );
    }

    /**
     * Accessor method for quantity
     * @return The quantity of the Investment
     */
    public int getQuantity()
    {//Accessor method for quantity
        return quantity;
    }

    /**
     * Accessor method for price
     * @return The price of the Investment
     */
    public double getPrice()
    {//Accessor method for price
        return price;
    }

    /**
     * Accessor method for bookValue
     * @return The book value of the Investment
     */
    public double getBookValue()
    {//Accessor method for bookValue
        return bookValue;
    }

    /**
     * Accessor method for gain
     * @return The gain of the Investment
     */
    public double getGain()
    {//Accessor method for gain
        return gain;
    }

    /**
     * Returns a string containing all the information of the Investment
     */
    public String toString()
    {//Return a string containing all the information in the Investment

        return "symbol: " + symbol + '\n' +
               "name: " + name + '\n' +
               "quantity: " + quantity + '\n' +
               "price: $" + (double)Math.round(price * 100) / 100 + '\n' +
               "bookValue: $" + (double)Math.round(bookValue * 100) / 100 + '\n' +
               "gain: $" + (double)Math.round(gain * 100) / 100 + '\n';

    }

    /**
     * Checks for equality against another instance of Investment
     * @param otherObject The other Investment to check for equality
     * @return Returns a boolean value indicating whether the operation was successful
     */
    public boolean equals(Object otherObject)
    {//Check if this Investment is equal to another Investment

        if(otherObject == null)
        {//If other object is null return false
            return false;
        }
        else if(getClass() != otherObject.getClass())
        {//If there is a class type mismatch, return false
            return false;
        }
        else
        {//Check if each variable is equal to each other and return the boolean value

            //Cast other object to Investment
            Investment other = (Investment)otherObject;

            //Check all values and return boolean value
            return symbol.equals(other.symbol) &&
                   name.equals(other.name) &&
                   quantity == other.quantity &&
                   price == other.price &&
                   bookValue == other.bookValue &&
                   gain == other.gain;

        }
    }


}
