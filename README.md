==========Author Information==========

Author: Quinn Meiszinger
UserID: qmeiszin
Class: CIS*2430 - Object Oriented Programming
Professor: Fei Song
Project: A3


==========Project Description==========

In this project, we were tasked to design
an "ePortfolio" capable storing and
performing operations on both stocks and
mutual funds. We were required to design
classes for stocks and mutual funds that
contained variable such as "symbol", "name",
"quantity", "price", and "bookValue". We
were responsible for making sure all
necessary calculations were done correctly
and also calculate the gain of each
investment.
	For the stock class, we were tasked
with managing two seperate ArrayLists to
hold Stocks and MutualFunds. Along with
storing them, we had to design methods to
buy, sell, update price, calculate total
gains, and search through all available
investments.
	Finally, we had to create a main that
allowed the user to interact with each of
these elements and work with an investment
portfolio.

==========REVISION FOR 2.0==========
For project A2, we were tasked to update out
pre-exisiting ePortfolio with further functionality.
	We had to create a superclass called 
"Investment" that "Stock" and "MutualFund" inherit 
from. Due to this, we were able to combine both 
Stocks and MutualFunds into one ArrayList and cut 
down on the pre-existing code. 
	Along with this, we were
also tasked with creating an I/O system for the
program that allows reading and writing to a 
user specified file. This effectively allows
the saving of data across multiple sessions
using ePortfolio. 
	Finally, we were tasked with
creating a HashMap to store the keywords in 
each Investment name to the indices where they
exist. This allowed us to further refine the
search functionality of the program and create
a more efficient search algorithm.

==========REVISION FOR 3.0==========
For project A3, we were tasked to further update
out pre-exisiting ePortfolio with a GUI and
further functionality.
	We had to create a functioning GUI to
interact with each component of ePortfolio.
Using java Swing, we created multiple UI's
to buy, sell, update, calculate gains, and 
search through various investments.
	Along with this, we were tasked
with fine tuning out program to include
proper exception handling for constructors,
mutators, and input exception. Also, we has
to implement privacy leak protection, abstract
classes and method, and effectively utilize
polymorphism.


==========Assumptions and Limitations==========

For my code, I am assuming the user is
generally entering only the word needed for an
action. I have error checked user input
wherever possible, and have prompted the user to
try again should their input by invalid. However,
the user still needs to enter the right key words
to perform the action. There are a few spots in 
the program where I could not find a way to 
properly error check. For example, when entering 
quantity, the program asks for an integer and I
found no way to stop the user from entering a
double or string and crashing the program. The same
limitation can be found in the search function where
the user can enter a string of letters for the price
range, and the program will attempt to parse it as a
double and crash.

==========REVISION FOR V2.0==========
The program no longer crashes when user enters text input
for a numeric field. I have used NumberFormatException
to handle all numeric fields and prompt the user
to try again should they enter invalid input. Same goes
for the price range field in the search function, the
program can now handle invalid input and default
priceRangeFound to true.

==========REVISION FOR 3.0==========
The program still no longer crashes when text input
is entered for a numeric field. Various forms of 
exception handling have been implemented to make
error checking robust and complete. Along with this,
the UI greatly simplifies user input and
exception handling.


==========Building and Running==========

Building:
	- Make sure you are in the directory that contains the package "ePortfolio"
	- RUN: javac ./ePortfolio/PortfolioGUI.java ./ePortfolio/Investment.java ./ePortfolio/Stock.java ./ePortfolio/MutualFund.java
	- This will successfully compile all the neccessary .class files needed to run
	
Running
	- Make sure you are in the directory that contains the package "ePortfolio"
	- RUN: java ePortfolio.PortfolioGUI <filename>
	- This will launch the program. From here interact with the GUI


==========Program Testing==========

===PORTFOLIOGUI===

PortfolioGUI.updateWordHash()
	- Testing hash when program loads investments from file (hash successfully generated)
	- Testing hash when a new Stock is bought (hash successfully updated)
	- Testing hash when a new MutualFund is bough (hash successfully updated)
	- Testing hash when a Stock is fully sold (hash successfully updated)
	- Testing hash when a MutualFund is fully sold (hash successfully updated)
	
PortfolioGUI.buy();
	- Testing to buy a new Stock (successful)
	- Testing to buy a new MutualFund (successful)
	- Testing to buy more of an existing Stock (successful)
	- Testing to buy more of an existing MutualFund (successful)
	- Testing to buy another Stock (successful)
	- Testing to buy another MutualFund (successful)
	
	- Testing negative value for quantity (error generated, user prompted to try again)
	- Testing text input for quantity (error generated, user prompted to try again)
	- Testing negative value for price (error generated, user prompted to try again)
	- Testing text input for price (error generated, user prompted to try again)
	
	- User can select Stock or MutualFund from combo box (successful)
	- Buy button buys Investment with all specified fields (successful)
	- Reset button resets all fields and messages (successful)
	
Portfolio.sell();
	- Partially selling a Stock (successful)
	- Partially selling a MutualFund (successful)
	- Fully selling a Stock (successful)
	- Fully selling a MutualFund (successful)
	
	- Testing for symbol that doesn't exist (error generated, user prompted to try again)
	- Testing negative value for quantity (error generated, user prompted to try again)
	- Testing text input for quantity (error generated, user prompted to try again)
	- Testing to sell more quantity than is owned (error generated, user prompted to try again)
	- Testing negative value for price (error generated, user prompted to try again)
	- Testing text input for price (error generated, user prompted to try again)

	- Sell button sells Investment for specified quantity and price (successful)
	- Reset button resets all fields and messages (successful)
	
Portfolio.update();
	- Testing negative value for price (error generated, user prompted to try again)
	- Testing text input for price (error generated, user prompted to try again)
	- Testing no input for price (error generated, user prompted to try again)

	- Testing Prev button when first element is selected (error generated, user prompted to try again)
	- Testing Prev button when there is a previous element (successful)
	- Testing Next button when last element is selected (error generated, user prompted to try again)
	- Test Next button when there is a next element (successful)
	- Testing save button (successful)
	
Portfolio.getGains();
	- Program displays correct amount of gains (no user input for this function)
	- Program displays all individual gains (successful)
	
Portfolio.search();
	- Testing empty string for all value (all investments displayed successfully)
	- Searching for Stock by symbol (successful)
	- Searching for MutualFund by symbol (successful)
	- Searching for symbol that doesn't exist (successful)
	- Searching for single keyword (successful)
	- Searching for multiple keywords (successful)
	- Searching price range for exact value (successful)
	- Searching price range for equal to or greater than (successful)
	- Searching price range for equal to or less than (successful)
	- Searching price for valid price range (successful)
	- Testing negative value for low price (error generated, user prompted to try again)
	- Testing negative value for high price (error generated, user prompted to try again)
	- Searching for combination of symbol, keywords, and price range (successful)
	- Testing case insensitivity (successful)
	- Entering a string of characters for price range (error is thrown, price range defaulted to found)

Portfolio.readFile()
	- Testing with no input file (error thrown, no investment information loaded)
	- Testing with file name that does not exist (error thrown, no information loaded)
	- Testing with file name that does exist (file successfully read)

Portfolio.writeFile()
	- Testing with no output file (error thrown, investment information loaded into a default file "investments.txt")
	- Testing with file name that does not exist (investment information loaded into new file supplied by command line argument)
	- Testing with file name that does exist (investment information successfully saved)
	
===GUI===

	- Program accepts command line filename (successful)
	- Program exits if file cannot be read (successful)
	- Program defaults output to "investments.txt" if file cannot be read (successful)
	- Program creates new file if file is not found on startup (successful)
	- Program defaults output to "investments.txt" if file does not exist (successful)
	- Program accepts user input (successful)
	- Program shows command menu (successful)
	- Program shows "buy" command (successful)
	- Program shows "sell" command (successful)
	- Program shows "update" command (successful)
	- Program shows "get gains" command (successful)
	- Program shows "search" command (successful)
	- Program shows "quit" command (successful) 
	- Buy command shows buy menu (successful)
	- Sell command shows sell menu (successful)
	- Update command shows update menu (successful)
	- Get gains command shows get gains menu (successful)
	- Search command shows search menu (successful)
	- Quit command exits program and saves file (successful)
	- All commands print messages to JTextArea (successful)
	- Program terminates and writes when window closed (successful)
	
===STOCK===

Stock.buy()
	- Correct bookValue calculated (successful)
	- Correct gain calculated (successful)
	- Appropiate values changed (successful)

Stock.sell()
	- Correct bookValue calculated (successful)
	- Correct gain calculated (successful)
	- Appropiate values changed (successful)
	- Correct payment calculated if fully sold (successful)
	- Correct payment calculated if partially sold (successful)
	
Stock.setPrice()
	- Price update (successful)
	- bookValue updated (successful)
	
Stock.getGain()
	- Gain returned (successful)
	
Stock.toString()
	- String with information returned (successful)
	
===MUTUALFUND===

MutualFund.buy()
	- Correct bookValue calculated (successful)
	- Correct gain calculated (successful)
	- Appropiate values changed (successful)

MutualFund.sell()
	- Correct bookValue calculated (successful)
	- Correct gain calculated (successful)
	- Appropiate values changed (successful)
	- Correct payment calculated if fully sold (successful)
	- Correct payment calculated if partially sold (successful)
	
MutualFund.setPrice()
	- Price update (successful)
	- bookValue updated (successful)
	
MutualFund.getGains()
	- Gain returned (successful)
	
MutualFund.toString()
	- String with information returned (successful)

===INVESTMENT===

Investment()
	- Constructors work as intended 

Investment.setSymbol()
	- Set symbol (successful)
	- Empty string (error thrown)
	
Investment.setName()
	- Set name (successful)
	- Empty string (error thrown)
	
Investment.setQuantity()
	- Set quantity (successful)
	- Negative value (error thrown)

Investment.setPrice()
	- Set price (successful)
	- Negative value (error thrown)

Investment.setBookValue()
	- Set bookValue (successful)
	- Negative value (error thrown)

Investment.setGain()
	- Set gain (successful)
	- Invalid value (error thrown)

Investment.toString()
	- String with information returned (successful)
	
==========Further Improvements==========

I would ideally like to implement a more
efficient way of generating the HashMap. The 
way I have implemented is costly and could be
optimized much further.
	
	