package ePortfolio;

import java.io.*;
import java.text.NumberFormat;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * GUI for purchasing, selling, and searching investments
 * @author Quinn Meiszinger
 * @version 3.0
 * @since 3.0
 */
public class PortfolioGUI extends JFrame
{

    //Variable for initial GUI width and height
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    //Text fields for input
    private JComboBox<String> typeBox;
    private JTextField symbolField;
    private JTextField nameField;
    private JTextField quantityField;
    private JTextField priceField;
    private JTextField nameKeywordsField;
    private JTextField lowPriceField;
    private JTextField highPriceField;

    //Main text area
    private JTextArea messageArea;

    //Global variable for storing the update index
    private int updateIndex = 0;

    //Variable for storing Investments
    private ArrayList<Investment> investmentList;

    //Variable for storing the wordMap
    private HashMap<String, ArrayList<Integer> > wordMap;

    //Variable for storing fileName to read/write to
    private String IOfileName;

    /**
     * No-Argument Constructor
     */
    public PortfolioGUI()
    {//Constructor for GUI
        super();
        prepareGUI();
        investmentList = new ArrayList<Investment>();
        wordMap = new HashMap<String, ArrayList<Integer> >();
    }

    /**
     * Copy Constructor
     * @param portfolioGUI A pre-exisiting PortfolioGUI to copy from
     */
    public PortfolioGUI(PortfolioGUI portfolioGUI)
    {
        super();
        prepareGUI();
        investmentList = new ArrayList<Investment>(portfolioGUI.getInvestmentList());
        wordMap = new HashMap<String, ArrayList<Integer> >(portfolioGUI.getWordMap());
    }

    /**
     * Checks if a String is empty
     * @param string The string to check if empty
     * @return A boolean expression indicating whether the String was empty
     */
    private boolean isEmpty(String string)
    {
        
        if(string == null ||string.equals("") || string.equals("[ ]+"))
        {//If string is empty, return true
            return true;
        }
        else
        {//If not, return false
            return false;
        }

    }

    /**
     * Searches all investments given a symbol
     * @param list The Investment list to search through
     * @param symbol The symbol to search for
     * @return The index of the Investment in the array, if not found -1 it returned
     */
    private int searchInvestmentList(ArrayList<Investment> list, String symbol)
    {//Method for searching through all investments given a symbol

        for(int i = 0; i < list.size(); i++)
        {//For loop for iterating through each investment

            //Temporary variable for storing each investment
            Investment temp = list.get(i);

            if (symbol.equalsIgnoreCase( temp.getSymbol() ) )
            {//Checks for equality, if a match is found, return the index in the list
                return i;
            }

        }

        //If the symbol is not found, return -1
        return -1;

    }

    /**
     * Method for updating the entire word hash
     */
    private void updateWordHash()
    {

        //Clear old map or values
        wordMap.clear();

        //Create string array to hold all Investment names
        String[] investmentNames = new String[investmentList.size()];

        for(int i = 0; i < investmentList.size(); i++)
        {//For loop for iterating through each investment

            //Declare temporary investment to store each investment
            Investment temp = investmentList.get(i);

            //Push each name to investmentNames
            investmentNames[i] = temp.getName();

        }
        
        for(String investment : investmentNames)
        {//For loop for iterating through each investment name

            //Split investment name into individual words
            String[] words = investment.toLowerCase().split("[ .,!?]+");

            for(String word : words)
            {//For loop for iterating through each word in investment name

                //Create new arraylist for storing the indices where the key word exists
                ArrayList<Integer> indices = new ArrayList<Integer>();

                //Counter variable
                int i = 0;

                for(Investment temp : investmentList)
                {//Iterate through each investment in the list

                    if(temp.getName().toLowerCase().contains(word))
                    {//If key word is in investment name, add index to indices
                        indices.add(i);
                    }

                    //Increment counter
                    i++;

                }

                //Map key word to indices where it exists
                wordMap.put(word, indices);

            }

        }

    }

    /**
     * Anonymous inner class for showing the BuyInvestmentUI
     */
    private class ShowBuyInvestmentUI implements ActionListener
    {
        
        /**
         * Action performed on click
         */
        public void actionPerformed(ActionEvent event)
        {   

            //Remove all items from content pane
            getContentPane().removeAll();

            //Create new panel with GridBag layout
            JPanel buyInvestmentUI = new JPanel(new GridBagLayout());
            buyInvestmentUI.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            buyInvestmentUI.setBackground(Color.LIGHT_GRAY);
            
            //Create new GridBagConstraints object
            GridBagConstraints c = new GridBagConstraints();

            //========== TOP ROW (TITLE) ===========
            c.anchor = GridBagConstraints.LINE_END;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.00f;
            c.weighty = 0.05f;
            c.gridwidth = 2;
            c.insets = new Insets(0, 10, 0, 0);

            //Create title label
            JLabel titleLabel = new JLabel("Buying an Investment");
            titleLabel.setFont(titleLabel.getFont().deriveFont(18.0f));
            c.gridx = 0;
            c.gridy = 0;
            buyInvestmentUI.add(titleLabel, c);

            //========== FIRST COLUMN (LABELS)==========
            c.anchor = GridBagConstraints.EAST;
            c.fill = GridBagConstraints.NONE;
            c.weightx = 0.15f;
            c.weighty = 0.50f;
            c.gridwidth = 1;
            c.insets = new Insets(10, 10, 10, 10);

            //Create type label
            JLabel typeLabel = new JLabel("Type:");
            c.gridx = 0;
            c.gridy = 1;
            buyInvestmentUI.add(typeLabel, c);

            //Create symbol label
            JLabel symbolLabel = new JLabel("Symbol:");
            c.gridx = 0;
            c.gridy = 2;
            buyInvestmentUI.add(symbolLabel, c);

            //Create name label
            JLabel nameLabel = new JLabel("Name:");
            c.gridx = 0;
            c.gridy = 3;
            buyInvestmentUI.add(nameLabel, c);

            //Create quantity label
            JLabel quantityLabel = new JLabel("Quantity:");
            c.gridx = 0;
            c.gridy = 4;
            buyInvestmentUI.add(quantityLabel, c);

            //Create price label
            JLabel priceLabel = new JLabel("Price:");
            c.gridx = 0;
            c.gridy = 5;
            buyInvestmentUI.add(priceLabel, c);

            //=========== SECOND COLUMN (FIELDS)===========
            c.anchor = GridBagConstraints.WEST;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.75f;
            c.weighty = 0.50f;
            c.insets = new Insets(0, 0, 0, 50);

            //Create type field
            String[] typeArray = {"Stock", "Mutual Fund"};
            typeBox = new JComboBox<String>(typeArray);
            c.gridx = 1;
            c.gridy = 1;
            buyInvestmentUI.add(typeBox, c);

            //Create symbol field
            symbolField = new JTextField(10);
            c.gridx = 1;
            c.gridy = 2;
            buyInvestmentUI.add(symbolField, c);

            //Create name field
            nameField = new JTextField(10);
            c.gridx = 1;
            c.gridy = 3;
            buyInvestmentUI.add(nameField, c);

            //Create quantity field
            quantityField = new JTextField(10);
            c.gridx = 1;
            c.gridy = 4;
            buyInvestmentUI.add(quantityField, c);

            //Create price field
            priceField = new JTextField(10);
            c.gridx = 1;
            c.gridy = 5;
            buyInvestmentUI.add(priceField, c);

            //========== THIRD COLUMN (BUTTONS)==========
            c.fill = GridBagConstraints.BOTH;
            c.anchor = GridBagConstraints.CENTER;
            c.weightx = 0.10f;
            c.weighty = 0.50f;
            c.insets = new Insets(50, 25, 50, 25);

            //Create reset button
            JButton resetButton = new JButton("Reset");
            resetButton.setBackground(Color.WHITE);
            ShowBuyInvestmentUI resetListener = new ShowBuyInvestmentUI();
            resetButton.addActionListener(resetListener);

            //Create buy button
            JButton buyButton = new JButton("Buy");
            buyButton.setBackground(Color.WHITE);
            BuyInvestment buyInvestmentListener = new BuyInvestment();
            buyButton.addActionListener(buyInvestmentListener);

            //Create new gridLayout to store buttons
            GridLayout gridLayout = new GridLayout(2, 1);
            gridLayout.setVgap(75);
            JPanel buttonPanel = new JPanel(gridLayout);
            buttonPanel.setBackground(Color.LIGHT_GRAY);
            buttonPanel.add(buyButton);
            buttonPanel.add(resetButton);

            //Add buttonPanel to UI
            c.gridx = 2;
            c.gridy = 1;
            c.gridheight = 5;
            buyInvestmentUI.add(buttonPanel, c);

            //========== BOTTOM ROW (LABEL) =========
            c.fill = GridBagConstraints.NONE;
            c.anchor = GridBagConstraints.LAST_LINE_START;
            c.weightx = 0.00f;
            c.weighty = 0.00f;
            c.gridheight = 1;
            c.insets = new Insets(10, 10, 0, 0);

            //Create new title for message area
            JLabel messageAreaLabel = new JLabel("Messages");
            c.gridx = 0;
            c.gridy = 6;
            buyInvestmentUI.add(messageAreaLabel, c);

            //======== BOTTOM ROW (TEXTAREA) ==========
            c.fill = GridBagConstraints.BOTH;
            c.anchor = GridBagConstraints.PAGE_END;
            c.weightx = 0.00f;
            c.weighty = 0.75f;
            c.gridheight = 1;
            c.insets = new Insets(10, 10, 10, 10);
            
            //Create new text area
            messageArea = new JTextArea(8, 20);
            messageArea.setEditable(false);

            //Create new scroll pane to scroll horizontally and vertically
            JScrollPane messageAreaScroll = new JScrollPane(messageArea);
            messageAreaScroll.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            messageAreaScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            messageAreaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            c.gridx = 0;
            c.gridy = 7;
            c.gridheight = 1;
            c.gridwidth = 3;
            buyInvestmentUI.add(messageAreaScroll, c);

            //========= BORDERS ==========
            c.fill = GridBagConstraints.BOTH;
            c.anchor = GridBagConstraints.CENTER;
            c.weightx = 0.00f;
            c.weighty = 0.00f;
            c.gridheight = 1;
            c.insets = new Insets(0, 0, 0, 0);

            //Create border to surround the labels and fields
            JPanel fieldBorder = new JPanel(null);
            fieldBorder.setOpaque(false);
            fieldBorder.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 2;
            c.gridheight = 6;
            buyInvestmentUI.add(fieldBorder, c);

            //Create border to surround the buttons
            JPanel buttonBorder = new JPanel(null);
            buttonBorder.setOpaque(false);
            buttonBorder.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
            c.gridx = 2;
            c.gridy = 0;
            c.gridwidth = 1;
            c.gridheight = 6;
            buyInvestmentUI.add(buttonBorder, c);

            //========= FINALIZATION OF UI ==========
            add(buyInvestmentUI);
            validate();

        }

    }

    /**
     * Anonymous inner class for buying investments
     */
    private class BuyInvestment implements ActionListener
    {

        /**
         * Action performed on click
         */
        public void actionPerformed(ActionEvent event)
        {

            //Strings for holding raw input of text fields
            String rawType = typeBox.getSelectedItem().toString();
            String rawSymbol = symbolField.getText();
            String rawName = nameField.getText();
            String rawQuantity = quantityField.getText();
            String rawPrice = priceField.getText();

            //Variable for holding the final parsed values
            String type;
            String symbol;
            String name;
            int quantity;
            double price;

            if(isEmpty(rawType))
            {//if no input is entered for type, throw an error and prompt the user to try again
                messageArea.append("[ERROR]: No string entered for type, please try again\n");
                return;
            }

            if(isEmpty(rawSymbol))
            {//If no input is entered for symbol, throw an error and prompt the user to try again
                messageArea.append("[ERROR]: No string entered for symbol, please try again\n");
                return;
            }

            if(isEmpty(rawName))
            {//If no input entered for name, throw an error and prompt the user to try again
                messageArea.append("[ERROR]: No string entered for name, please try again\n");
                return;
            }

            if(isEmpty(rawQuantity))
            {//If no input entered for quantity, throw an error and prompt the user to try again
                messageArea.append("[ERROR]: No string entered for quantity, please try again\n");
                return;
            }

            if(isEmpty(rawPrice))
            {//If no input entered for price, throw an error and prompt the user to try again
                messageArea.append("[ERROR]: No string entered for price, please try again\n");
                return;
            }

            try
            {//Attempt to set type, symbol, name, quantity, and price
                type = new String(rawType);
                symbol = new String(rawSymbol);
                name = new String(rawName);
                quantity = Integer.parseInt(rawQuantity);
                price = Double.parseDouble(rawPrice);
            }
            catch(NumberFormatException e)
            {//If there is a NumberFormatException, throw an error and prompt the user to try again
                messageArea.append("[ERROR]: Could not parse quantity or price, please try again\n");
                return;
            }
            catch(InputMismatchException e)
            {//If there is an InputMismatchException, throw an error and prompt the user to try again
                messageArea.append("[ERROR]: Input type mismatch, please try again\n");
                return;
            }

            if(quantity <= 0)
            {//If quantity is invalid, throw an error and prompt the user to try again
                messageArea.append("[ERROR]: Quantity cannot be less than 0, please try again\n");
                return;
            }

            if(price < 0)
            {//If price is invalid, throw an error and prompt the user to try again
                messageArea.append("[ERROR]: Price cannot be less than 0, please try again\n");
                return;
            }

            //Create variable and search investmentList for symbol
            int index = searchInvestmentList(investmentList, symbol);

            if(index != -1)
            {//If investment exists, make sure it doesn't exist alreayd as opposite type

                if(type.equalsIgnoreCase("Stock"))
                {//If user is attempting to buy stock

                    //Get investment from list
                    Investment temp = investmentList.get(index);

                    if(temp instanceof MutualFund)
                    {//If Investment exists as MutualFund, throw an error and prompt user to try again
                        messageArea.append("[ERROR]: " + symbol + " already exists as mutual fund");
                        return;
                    }

                }
                else if(type.equalsIgnoreCase("Mutual Fund"))
                {//If user is attempting to buy mutual fund

                    //Get investment from list
                    Investment temp = investmentList.get(index);

                    if(temp instanceof Stock)
                    {//If Investment exists as Stock, throw an error and prompt the user to try again
                        messageArea.append("[ERROR]: " + symbol + " already exists as stock");
                        return;
                    }

                }

            }

            if(index != -1)
            {//If Investment already exists

                //Get investment from list
                Investment temp = investmentList.get(index);

                //Buy more of the investment
                temp.buy(quantity, price);
                
                //Store investment back in list
                investmentList.set(index, temp);

                //Update hash
                updateWordHash();

                //Inform user if operation was successful
                messageArea.append(type + " successfully bought\n");
                return;

            }
            else
            {//If investment does not exist

                Investment temp;
                if(type.equalsIgnoreCase("Stock"))
                {//Buy a stock if user specified stock

                    try
                    {
                        temp = new Stock(symbol, name, quantity, price);
                    }
                    catch(Exception e)
                    {
                        messageArea.append("[ERROR]: Exception " + e.getMessage() + " thrown in Stock constructor\n");
                        return;
                    }
                    
                }
                else if(type.equalsIgnoreCase("Mutual Fund"))
                {//Buy a mutual fund is user specified mutual fund

                    try
                    {
                        temp = new MutualFund(symbol, name, quantity, price);
                    }
                    catch(Exception e)
                    {
                        messageArea.append("[ERROR]: Exception " + e.getMessage() + " thrown in MutualFund constructor\n");
                        return;
                    }
                    
                }
                else
                {//Return if somehow neither were chosen
                    return;
                }

                //Add investment to list
                investmentList.add(temp);

                //Update hash
                updateWordHash();

                //Inform user that operation was successful
                messageArea.append(type + " successfully bought\n");

            }

        }

    }

    /**
     * Anonymous inner class for showing SellInvestmentUI
     */
    private class ShowSellInvestmentUI implements ActionListener
    {

        /**
         * Action performed on click
         */
        public void actionPerformed(ActionEvent event)
        {    
            
            //Remove all previous items from frame
            getContentPane().removeAll();

            //Create new panel with GridBag layout
            JPanel sellInvestmentUI = new JPanel(new GridBagLayout());
            sellInvestmentUI.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            sellInvestmentUI.setBackground(Color.LIGHT_GRAY);

            //Create new GridBagContraints object
            GridBagConstraints c = new GridBagConstraints();

            //========== TOP ROW (TITLE) ==========
            c.anchor = GridBagConstraints.LINE_END;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.00f;
            c.weighty = 0.05f;
            c.gridwidth = 2;
            c.insets = new Insets(0, 10, 0, 0);

            //Create new title label
            JLabel titleLabel = new JLabel("Selling an Investment");
            titleLabel.setFont(titleLabel.getFont().deriveFont(18.0f));
            c.gridx = 0;
            c.gridy = 0;
            sellInvestmentUI.add(titleLabel, c);

            //========== FIRST COLUMN ==========
            c.anchor = GridBagConstraints.EAST;
            c.fill = GridBagConstraints.NONE;
            c.weightx = 0.15f;
            c.weighty = 0.50f;
            c.gridwidth = 1;
            c.insets = new Insets(10, 10, 10, 10);

            //Create symbol label
            JLabel symbolLabel = new JLabel("Symbol:");
            c.gridx = 0;
            c.gridy = 1;
            sellInvestmentUI.add(symbolLabel, c);

            //Create quantity label
            JLabel quantityLabel = new JLabel("Quantity:");
            c.gridx = 0;
            c.gridy = 2;
            sellInvestmentUI.add(quantityLabel, c);

            //Create price label
            JLabel priceLabel = new JLabel("Price:");
            c.gridx = 0;
            c.gridy = 3;
            sellInvestmentUI.add(priceLabel, c);

            //=========== SECOND COLUMN ===========
            c.anchor = GridBagConstraints.WEST;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.75f;
            c.weighty = 0.50f;
            c.insets = new Insets(0, 0, 0, 50);

            //Create symbol field
            symbolField = new JTextField(10);
            c.gridx = 1;
            c.gridy = 1;
            sellInvestmentUI.add(symbolField, c);

            //Create quantity field
            quantityField = new JTextField(10);
            c.gridx = 1;
            c.gridy = 2;
            sellInvestmentUI.add(quantityField, c);

            //Create price field
            priceField = new JTextField(10);
            c.gridx = 1;
            c.gridy = 3;
            sellInvestmentUI.add(priceField, c);

            //========== THIRD COLUMN ==========
            c.fill = GridBagConstraints.BOTH;
            c.anchor = GridBagConstraints.CENTER;
            c.weightx = 0.10f;
            c.weighty = 0.50f;
            c.insets = new Insets(50, 25, 50, 25);

            //Create reset button
            JButton resetButton = new JButton("Reset");
            resetButton.setBackground(Color.WHITE);
            ShowSellInvestmentUI resetListener = new ShowSellInvestmentUI();
            resetButton.addActionListener(resetListener);

            //Create sell button
            JButton sellButton = new JButton("Sell");
            sellButton.setBackground(Color.WHITE);
            SellInvestment sellInvestmentListener = new SellInvestment();
            sellButton.addActionListener(sellInvestmentListener);

            //Create new gridLayout to store buttons
            GridLayout gridLayout = new GridLayout(2, 1);
            gridLayout.setVgap(75);
            JPanel buttonPanel = new JPanel(gridLayout);
            buttonPanel.setBackground(Color.LIGHT_GRAY);
            buttonPanel.add(sellButton);
            buttonPanel.add(resetButton);

            //Add buttonPanel to layout
            c.gridx = 2;
            c.gridy = 1;
            c.gridheight = 4;
            sellInvestmentUI.add(buttonPanel, c);

            //========== BOTTOM ROW (LABEL) =========
            c.fill = GridBagConstraints.NONE;
            c.anchor = GridBagConstraints.LAST_LINE_START;
            c.weightx = 0.00f;
            c.weighty = 0.00f;
            c.gridheight = 1;
            c.insets = new Insets(10, 10, 0, 0);

            //Create title for message area
            JLabel messageAreaLabel = new JLabel("Messages");
            c.gridx = 0;
            c.gridy = 4;
            sellInvestmentUI.add(messageAreaLabel, c);

            //======== BOTTOM ROW (TEXTAREA) ==========
            c.fill = GridBagConstraints.BOTH;
            c.anchor = GridBagConstraints.PAGE_END;
            c.weightx = 0.00f;
            c.weighty = 0.75f;
            c.insets = new Insets(10, 10, 10, 10);

            //Create new message area
            messageArea = new JTextArea(8, 20);
            messageArea.setEditable(false);

            //Create new scroll pane for horizontal and vertical scrolling
            JScrollPane messageAreaScroll = new JScrollPane(messageArea);
            messageAreaScroll.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            messageAreaScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            messageAreaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            c.gridx = 0;
            c.gridy = 5;
            c.gridheight = 1;
            c.gridwidth = 3;
            sellInvestmentUI.add(messageAreaScroll, c);

            //========== BORDERS ==========
            c.fill = GridBagConstraints.BOTH;
            c.anchor = GridBagConstraints.CENTER;
            c.weightx = 0.00f;
            c.weighty = 0.00f;
            c.gridheight = 1;
            c.insets = new Insets(0, 0, 0, 0);

            JPanel fieldBorder = new JPanel(null);
            fieldBorder.setOpaque(false);
            fieldBorder.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 2;
            c.gridheight = 4;
            sellInvestmentUI.add(fieldBorder, c);

            JPanel buttonBorder = new JPanel(null);
            buttonBorder.setOpaque(false);
            buttonBorder.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
            c.gridx = 2;
            c.gridy = 0;
            c.gridwidth = 1;
            c.gridheight = 4;
            sellInvestmentUI.add(buttonBorder, c);

            //========= FINALIZATION OF UI ==========
            add(sellInvestmentUI);
            validate();

        }

    }

    /**
     * Anonynmous inner class for selling investments
     */
    private class SellInvestment implements ActionListener
    {

        /**
         * Action performed on click
         */
        public void actionPerformed(ActionEvent event)
        {

            //Variables for storing raw input from text fields
            String rawSymbol = symbolField.getText();
            String rawQuantity = quantityField.getText();
            String rawPrice = priceField.getText();

            //Variables for storing parsed values
            String symbol;
            int quantity;
            double price;

            if(isEmpty(rawSymbol))
            {//If no input is entered for symbol, throw an error and prompr user to try again
                messageArea.append("[ERROR]: No string entered for symbol, please try again\n");
                return;
            }

            if(isEmpty(rawQuantity))
            {//If no input is entered for quantity, throw an error and prompt user to try again
                messageArea.append("[ERROR]: No string entered for quantity, please try again\n");
                return;
            }

            if(isEmpty(rawPrice))
            {//If no input entered for price, throw an error and prompt user to try again
                messageArea.append("[ERROR]: No string entered for price, please try again\n");
                return;
            }

            try
            {//Attempt to set symbol, quantity and price
                symbol = new String(rawSymbol);
                quantity = Integer.parseInt(rawQuantity);
                price = Double.parseDouble(rawPrice);
            }
            catch(NumberFormatException e)
            {//If NumberFormatException, throw an error and prompt the user to try again
                messageArea.append("[ERROR]: Could not parse quantity or price, please try again\n");
                return;
            }
            catch(InputMismatchException e)
            {//If inputMisatch, throw an error and prompt the user to try again
                messageArea.append("[ERROR]: Input type mismatch, please try again\n");
                return;
            }

            if(quantity <= 0)
            {//If quantity invalid, throw an error and prompt the user to try again
                messageArea.append("[ERROR]: Quantity cannot be less than 0, please try again\n");
                return;
            }

            if(price < 0)
            {//If price invalid, throw an error and prompt the user to try again
                messageArea.append("[ERROR]: Price cannot be less than 0, please try again\n");
                return;
            }

            //Search investmentList for symbol
            int index = searchInvestmentList(investmentList, symbol);

            if(index == -1)
            {//If Investment not found, throw an error and prompt the user to try again
                messageArea.append("[ERROR]: No such investment exists, please try again\n");
                return;
            }
            else
            {

                //Variable for holding investments
                Investment temp = investmentList.get(index);

                //Variable for keeping track of previous quantity
                int totalQuantity = temp.getQuantity();

                if(quantity > totalQuantity)
                {//Check if quantity being sold is more than total quantity
                    messageArea.append("[ERROR]: Cannot sell more of an investment than is owned, please try again\n");
                    return;
                }

                //Variable for keeping track of payment
                double payment = 0;

                //Sell investment
                payment = temp.sell(quantity, price);

                //Print payment and gain to UI
                messageArea.append(quantity + " units of " + temp.getSymbol() + " sold for $" + (double)Math.round(payment * 100) / 100 + "\n");
                messageArea.append("Gain was $" + (double)Math.round(temp.getGain() * 100) / 100 + "\n");

                if( (totalQuantity - quantity) <= 0)
                {//If all of the investment was sold, remove from list

                    //Remove investment
                    investmentList.remove(index);

                    //Update hash
                    updateWordHash();

                }
                else
                {//Set investment back into list
                    investmentList.set(index, temp);
                }

                //Return from method
                return;

            }

        }

    }

    /**
     * Anonymous inner class for showing the UpdateInvestmentUI
     */
    private class ShowUpdateInvestmentUI implements ActionListener
    {

        /**
         * Action performed on click
         */
        public void actionPerformed(ActionEvent event)
        {

            //Remove all items from content pane
            getContentPane().removeAll();

            //Create new panel with GridBag layout
            JPanel updateInvestmentUI = new JPanel(new GridBagLayout());
            updateInvestmentUI.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            updateInvestmentUI.setBackground(Color.LIGHT_GRAY);

            //Create new GridBagConstraints object
            GridBagConstraints c = new GridBagConstraints();

            //========== TOP ROW (TITLE) ==========
            c.anchor = GridBagConstraints.LINE_END;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.00f;
            c.weighty = 0.05f;
            c.gridwidth = 2;
            c.insets = new Insets(0, 10, 0, 0);

            //Create new title label
            JLabel titleLabel = new JLabel("Updating Investments");
            titleLabel.setFont(titleLabel.getFont().deriveFont(18.0f));
            c.gridx = 0;
            c.gridy = 0;
            updateInvestmentUI.add(titleLabel, c);

            //========== FIRST COLUMN (LABELS) ==========
            c.anchor = GridBagConstraints.EAST;
            c.fill = GridBagConstraints.NONE;
            c.weightx = 0.15f;
            c.weighty = 0.50f;
            c.gridwidth = 1;
            c.insets = new Insets(10, 10, 10, 10);

            //Create symbol label
            JLabel symbolLabel = new JLabel("Symbol:");
            c.gridx = 0;
            c.gridy = 1;
            updateInvestmentUI.add(symbolLabel, c);

            //Create name label
            JLabel nameLabel = new JLabel("Name: ");
            c.gridx = 0;
            c.gridy = 2;
            updateInvestmentUI.add(nameLabel, c);

            //Create price label
            JLabel priceLabel = new JLabel("Price:");
            c.gridx = 0;
            c.gridy = 3;
            updateInvestmentUI.add(priceLabel, c);

            //========== SECOND COLUMN (FIELDS) ==========
            c.anchor = GridBagConstraints.WEST;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.75f;
            c.weighty = 0.50f;
            c.insets = new Insets(0, 0, 0, 50);

            //Create symbol field
            symbolField = new JTextField(10);
            symbolField.setEditable(false);
            c.gridx = 1;
            c.gridy = 1;
            updateInvestmentUI.add(symbolField, c);

            //Create name field
            nameField = new JTextField(10);
            nameField.setEditable(false);
            c.gridx = 1;
            c.gridy = 2;
            updateInvestmentUI.add(nameField, c);

            //Create price field
            priceField = new JTextField(10);
            c.gridx = 1;
            c.gridy = 3;
            updateInvestmentUI.add(priceField, c);

            //========== THIRD COLUMN (BUTTON FIELD) ==========
            c.fill = GridBagConstraints.BOTH;
            c.anchor = GridBagConstraints.CENTER;
            c.weightx = 0.10f;
            c.weighty = 0.50f;
            c.insets = new Insets(50, 25, 50, 25);

            //Create prev button
            JButton prevButton = new JButton("Prev");
            prevButton.setBackground(Color.WHITE);
            PrevInvestment prevInvestmentListener = new PrevInvestment();
            prevButton.addActionListener(prevInvestmentListener);

            //Create next button
            JButton nextButton = new JButton("Next");
            nextButton.setBackground(Color.WHITE);
            NextInvestment nextInvestmentListener = new NextInvestment();
            nextButton.addActionListener(nextInvestmentListener);

            //Create save button
            JButton saveButton = new JButton("Save");
            saveButton.setBackground(Color.WHITE);
            UpdateInvestments updateInvestmentsListener = new UpdateInvestments();
            saveButton.addActionListener(updateInvestmentsListener);

            //Create new gridLayout to hold buttons
            GridLayout gridLayout = new GridLayout(3, 1);
            gridLayout.setVgap(25);
            JPanel buttonPanel = new JPanel(gridLayout);
            buttonPanel.setBackground(Color.LIGHT_GRAY);
            buttonPanel.add(prevButton);
            buttonPanel.add(nextButton);
            buttonPanel.add(saveButton);

            //Add buttonPanel to UI
            c.gridx = 2;
            c.gridy = 1;
            c.gridheight = 3;
            updateInvestmentUI.add(buttonPanel, c);

            //========== BOTTOM ROW (LABEL) =========
            c.fill = GridBagConstraints.NONE;
            c.anchor = GridBagConstraints.LAST_LINE_START;
            c.weightx = 0.00f;
            c.weighty = 0.00f;
            c.gridheight = 1;
            c.insets = new Insets(10, 10, 0, 0);

            //Create messageArea title
            JLabel messageAreaLabel = new JLabel("Search Results");
            c.gridx = 0;
            c.gridy = 4;
            updateInvestmentUI.add(messageAreaLabel, c);

            //========== BOTTOM ROW (TEXTAREA) ==========
            c.fill = GridBagConstraints.BOTH;
            c.anchor = GridBagConstraints.PAGE_END;
            c.weightx = 0.00f;
            c.weighty = 0.75f;
            c.gridheight = 1;
            c.insets = new Insets(10, 10, 10, 10);

            //Create new messageArea
            messageArea = new JTextArea(8, 20);
            messageArea.setEditable(false);

            //Create new scroll pane for horizontal and vertical scrolling
            JScrollPane messageAreaScroll = new JScrollPane(messageArea);
            messageAreaScroll.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            messageAreaScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            messageAreaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            c.gridx = 0;
            c.gridy = 5;
            c.gridheight = 1;
            c.gridwidth = 3;
            updateInvestmentUI.add(messageAreaScroll, c);

            //========== BORDERS ==========
            c.fill = GridBagConstraints.BOTH;
            c.anchor = GridBagConstraints.CENTER;
            c.weightx = 0.00f;
            c.weighty = 0.00f;
            c.gridheight = 1;
            c.insets = new Insets(0, 0, 0, 0);

            //Create border to surround labels and fields
            JPanel fieldBorder = new JPanel(null);
            fieldBorder.setOpaque(false);
            fieldBorder.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 2;
            c.gridheight = 4;
            updateInvestmentUI.add(fieldBorder, c);

            //Create border to surround buttons
            JPanel buttonBorder = new JPanel(null);
            buttonBorder.setOpaque(false);
            buttonBorder.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
            c.gridx = 2;
            c.gridy = 0;
            c.gridwidth = 1;
            c.gridheight = 4;
            updateInvestmentUI.add(buttonBorder, c);

            //========== FINALIZATION OF UI ==========
            if(investmentList.size() > 0)
            {//If list is not empty, display information
                symbolField.setText(investmentList.get(updateIndex).getSymbol());
                nameField.setText(investmentList.get(updateIndex).getName());
                priceField.setText(String.valueOf(investmentList.get(updateIndex).getPrice()));
            }
            else
            {//If list is empty, display default information
                symbolField.setText("No Investments in Database");
                nameField.setText("No Investments in Database");
                priceField.setText("No Investments in Database");
            }

            add(updateInvestmentUI);
            validate();

        }

    }

    /**
     * Anonymous inner class for advancing to next investment in the UpdateInvestmentUI
     */
    private class NextInvestment implements ActionListener
    {

        /**
         * Action performed on click
         */
        public void actionPerformed(ActionEvent event)
        {

            if((updateIndex + 1) >= investmentList.size())
            {//If there is no next investment, throw an error and prompt the user to try again
                messageArea.append("[ERROR]: No next investment in list, please try again\n");
                return;
            }
            else
            {

                //Increment updateIndex
                updateIndex++;

                //Update relevant fields
                symbolField.setText(investmentList.get(updateIndex).getSymbol());
                nameField.setText(investmentList.get(updateIndex).getName());
                priceField.setText(String.valueOf(investmentList.get(updateIndex).getPrice()));

                //Return from method
                return;

            }

        }

    }

    /**
     * Anonymous inner class for selecting the next investment in the UpdateInvestmentUI
     */
    private class PrevInvestment implements ActionListener
    {

        /**
         * Action performed on click
         */
        public void actionPerformed(ActionEvent event)
        {

            if((updateIndex - 1) < 0)
            {//If there is no previous investment, throw an error and prompt the user to try again
                messageArea.append("[ERROR]: No previous investment in list, please try again\n");
                return;
            }
            else
            {//If there is a previous investment

                //Decrement updateIndex
                updateIndex--;

                //Update relevant fields
                symbolField.setText(investmentList.get(updateIndex).getSymbol());
                nameField.setText(investmentList.get(updateIndex).getName());
                priceField.setText(String.valueOf(investmentList.get(updateIndex).getPrice()));

                //Return from method
                return;

            }

        }

    }

    /**
     * Anonymous inner class for updating investment price
     */
    private class UpdateInvestments implements ActionListener
    {

        /**
         * Action performed on click
         */
        public void actionPerformed(ActionEvent event)
        {

            if(investmentList.size() < 1)
            {//If list is emtpy, throw an error and prompt the user to try again
                messageArea.append("[ERROR]: No investments in the database, please try again\n");
                return;
            }

            //Variables for holding the raw input from text fields
            String rawPrice = priceField.getText();

            //Variables for holding the parsed values
            double price;

            if(isEmpty(rawPrice))
            {//If rawPrice is empty, throw an error and prompt user to try again
                messageArea.append("[ERROR]: No string entered for price, please try again\n");
                return;
            }

            try
            {//Parse price from test field
                price = Double.parseDouble(rawPrice);
            }
            catch(NumberFormatException e)
            {//Catch NumberFormatException, if caught throw an error and prompt user to try again
                messageArea.append("[ERROR]: Could not parse price, please try again\n");
                return;
            }

            //Get investment and set price
            Investment temp = investmentList.get(updateIndex);

            //Set price
            try
            {
                temp.setPrice(price);
            }
            catch(Exception e)
            {
                messageArea.append("[ERROR]: exception " + e.getMessage() + " throw in setPrice method\n");
                return;
            }
            

            //Set investment back into list 
            investmentList.set(updateIndex, temp);

            //Inform user that price was successfully updated
            messageArea.append("Price successfully updated\n");
            return;

        }

    }

    /**
     * Anonymous inner class for showing the InvestmentGainsUI
     */
    private class ShowInvestmentGainsUI implements ActionListener
    {

        /**
         * Action performed on click
         */
        public void actionPerformed(ActionEvent event)
        {

            //Remove all items from content pane
            getContentPane().removeAll();

            //Create new panel with GridBag layout
            JPanel investmentGainsUI = new JPanel(new GridBagLayout());
            investmentGainsUI.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            investmentGainsUI.setBackground(Color.LIGHT_GRAY);

            //Create new GridBagConstraints object
            GridBagConstraints c = new GridBagConstraints();

            //========== TOP ROW (TITLE) ============
            c.anchor = GridBagConstraints.LINE_END;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.00f;
            c.weighty = 0.05f;
            c.gridwidth = 2;
            c.insets = new Insets(0, 10, 0, 0);

            //Create title label
            JLabel titleLabel = new JLabel("Getting Total Gain");
            titleLabel.setFont(titleLabel.getFont().deriveFont(18.0f));
            c.gridx = 0;
            c.gridy = 0;
            investmentGainsUI.add(titleLabel, c);
            
            //========== FIRST COLUMN (LABELS) ==========
            c.anchor = GridBagConstraints.EAST;
            c.fill = GridBagConstraints.NONE;
            c.weightx = 0.15f;
            c.weighty = 0.50f;
            c.gridwidth = 1;
            c.insets = new Insets(10, 10, 10, 10);

            //Create label for totalGain field
            JLabel totalGainLabel = new JLabel("Total Gain:");
            c.gridx = 0;
            c.gridy = 1;
            investmentGainsUI.add(totalGainLabel, c);
            
            //========== SECOND COLUMN (FIELDS) ==========
            c.anchor = GridBagConstraints.WEST;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.75f;
            c.weighty = 0.50f;
            c.insets = new Insets(0, 0, 0, 500);

            //Create field to display total gain
            JTextField totalGainField = new JTextField(10);
            totalGainField.setEditable(false);
            c.gridx = 1;
            c.gridy = 1;
            investmentGainsUI.add(totalGainField, c);

            //========== BOTTOM ROW (LABEL) =========
            c.fill = GridBagConstraints.NONE;
            c.anchor = GridBagConstraints.LAST_LINE_START;
            c.weightx = 0.00f;
            c.weighty = 0.00f;
            c.gridheight = 1;
            c.insets = new Insets(10, 10, 0, 0);

            //Create new label for message area
            JLabel messageAreaLabel = new JLabel("Individual Gains");
            c.gridx = 0;
            c.gridy = 2;
            investmentGainsUI.add(messageAreaLabel, c);

            //========== BOTTOM ROW (TEXT AREA) ==========
            c.fill = GridBagConstraints.BOTH;
            c.anchor = GridBagConstraints.PAGE_END;
            c.weightx = 0.00f;
            c.weighty = 0.75f;
            c.gridheight = 1;
            c.insets = new Insets(10, 10, 10, 10);

            //Create new message area for output
            messageArea = new JTextArea(8, 20);
            messageArea.setEditable(false);

            //Create new scroll pane for horizontal and vertical scrolling
            JScrollPane messageAreaScroll = new JScrollPane(messageArea);
            messageAreaScroll.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            messageAreaScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            messageAreaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            c.gridx = 0;
            c.gridy = 3;
            c.gridheight = 1;
            c.gridwidth = 2;
            investmentGainsUI.add(messageAreaScroll, c);

            //========== BORDERS =========
            c.fill = GridBagConstraints.BOTH;
            c.anchor = GridBagConstraints.CENTER;
            c.weightx = 0.00f;
            c.weighty = 0.00f;
            c.gridheight = 1;
            c.insets = new Insets(0, 0, 0, 0);

            //Create new panel to create border between top and bottom of panel
            JPanel topBottomBorder = new JPanel(null);
            topBottomBorder.setOpaque(false);
            topBottomBorder.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 2;
            c.gridheight = 2;
            investmentGainsUI.add(topBottomBorder, c);

            //========== FINALIZATION OF UI ==========
            //Variable for keeping track of totalGains
            double totalGains = 0.0;

            for(Investment temp : investmentList)
            {//Iterate through each Investment in investmentList

                //Append individual gain to messageArea and add gain to total gains
                messageArea.append(temp.getSymbol() + " gain is: " + (double)Math.round(temp.getGain() * 100) / 100 + "\n");
                totalGains += temp.getGain();  

            }

            //Set text of totalGainField
            totalGainField.setText(String.valueOf((double)Math.round(totalGains * 100) / 100));

            add(investmentGainsUI);
            validate();

        }

    }

    /**
     * Anonymous inner class for showing the SearchInvestmentUI
     */
    private class ShowSearchInvestmentsUI implements ActionListener
    {

        /**
         * Action performed on click
         */
        public void actionPerformed(ActionEvent event)
        {

            //Remove all items from content pane
            getContentPane().removeAll();

            //Create new panel with GridBag layout
            JPanel searchInvestmentUI = new JPanel(new GridBagLayout());
            searchInvestmentUI.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            searchInvestmentUI.setBackground(Color.LIGHT_GRAY);

            //Create new GridBagConstraints object
            GridBagConstraints c = new GridBagConstraints();

            //========== TOP ROW (TITLE) ==========
            c.anchor = GridBagConstraints.LINE_END;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.00f;
            c.weighty = 0.05f;
            c.gridwidth = 2;
            c.insets = new Insets(0, 10, 0, 0);

            //Create title label for panel
            JLabel titleLabel = new JLabel("Searching Investments");
            titleLabel.setFont(titleLabel.getFont().deriveFont(18.0f));
            c.gridx = 0;
            c.gridy = 0;
            searchInvestmentUI.add(titleLabel, c);

            //========== FIRST COLUMN (LABELS) ==========
            c.anchor = GridBagConstraints.EAST;
            c.fill = GridBagConstraints.NONE;
            c.weightx = 0.15f;
            c.weighty = 0.50f;
            c.gridwidth = 1;
            c.insets = new Insets(10, 10, 10, 10);

            //Create symbol label
            JLabel symbolLabel = new JLabel("Symbol:");
            c.gridx = 0;
            c.gridy = 1;
            searchInvestmentUI.add(symbolLabel, c);

            //Create nameKeywords label
            JLabel nameKeywordsLabel = new JLabel("Name Keywords:");
            c.gridx = 0;
            c.gridy = 2;
            searchInvestmentUI.add(nameKeywordsLabel, c);

            //Create lowPrice label
            JLabel lowPriceLabel = new JLabel("Low Price:");
            c.gridx = 0;
            c.gridy = 3;
            searchInvestmentUI.add(lowPriceLabel, c);

            //Create highPrice label
            JLabel highPriceLabel = new JLabel("High Price");
            c.gridx = 0;
            c.gridy = 4;
            searchInvestmentUI.add(highPriceLabel, c);

            //========== SECOND COLUMN (FIELDS) ==========
            c.anchor = GridBagConstraints.WEST;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.75f;
            c.weighty = 0.50f;
            c.insets = new Insets(0, 0, 0, 50);

            //Create symbol field
            symbolField = new JTextField(10);
            c.gridx = 1;
            c.gridy = 1;
            searchInvestmentUI.add(symbolField, c);

            //Create nameKeywords field
            nameKeywordsField = new JTextField(10);
            c.gridx = 1;
            c.gridy = 2;
            searchInvestmentUI.add(nameKeywordsField, c);

            //Create lowPrice field
            lowPriceField = new JTextField(10);
            c.gridx = 1;
            c.gridy = 3;
            searchInvestmentUI.add(lowPriceField, c);

            //Create highPrice field
            highPriceField = new JTextField(10);
            c.gridx = 1;
            c.gridy = 4;
            searchInvestmentUI.add(highPriceField, c);

            //========== THIRD COLUMN (BUTTONS) ==========
            c.fill = GridBagConstraints.BOTH;
            c.anchor = GridBagConstraints.CENTER;
            c.weightx = 0.10f;
            c.weighty = 0.50f;
            c.insets = new Insets(50, 25, 50, 25);

            //Create reset button
            JButton resetButton = new JButton("Reset");
            resetButton.setBackground(Color.WHITE);
            ShowSearchInvestmentsUI resetListener = new ShowSearchInvestmentsUI();
            resetButton.addActionListener(resetListener);

            //Create search button
            JButton searchButton = new JButton("Search");
            searchButton.setBackground(Color.WHITE);
            SearchInvestments searchInvestmentsListener = new SearchInvestments();
            searchButton.addActionListener(searchInvestmentsListener);

            //Create new gridLayout and add reset and search buttons
            GridLayout gridLayout = new GridLayout(2, 1);
            gridLayout.setVgap(75);
            JPanel buttonPanel = new JPanel(gridLayout);
            buttonPanel.setBackground(Color.LIGHT_GRAY);
            buttonPanel.add(searchButton);
            buttonPanel.add(resetButton);

            //Add button panel to UI
            c.gridx = 2;
            c.gridy = 1;
            c.gridheight = 4;
            searchInvestmentUI.add(buttonPanel, c);

            //========== BOTTOM ROW (LABEL) =========
            c.fill = GridBagConstraints.NONE;
            c.anchor = GridBagConstraints.LAST_LINE_START;
            c.weightx = 0.00f;
            c.weighty = 0.00f;
            c.gridheight = 1;
            c.insets = new Insets(10, 10, 0, 0);

            //Create label for title of message area
            JLabel messageAreaLabel = new JLabel("Search Results");
            c.gridx = 0;
            c.gridy = 6;
            searchInvestmentUI.add(messageAreaLabel, c);

            //========== BOTTOM ROW (TEXTAREA) ==========
            c.fill = GridBagConstraints.BOTH;
            c.anchor = GridBagConstraints.PAGE_END;
            c.weightx = 0.00f;
            c.weighty = 0.75f;
            c.gridheight = 1;
            c.insets = new Insets(10, 10, 10, 10);

            //Create new message area
            messageArea = new JTextArea(8, 20);
            messageArea.setEditable(false);

            //Create new scroll pane for horizontal and vertical scrolling
            JScrollPane messageAreaScroll = new JScrollPane(messageArea);
            messageAreaScroll.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            messageAreaScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            messageAreaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            c.gridx = 0;
            c.gridy = 7;
            c.gridheight = 1;
            c.gridwidth = 3;
            searchInvestmentUI.add(messageAreaScroll, c);
            
            //========== BORDERS ==========
            c.fill = GridBagConstraints.BOTH;
            c.anchor = GridBagConstraints.CENTER;
            c.weightx = 0.00f;
            c.weighty = 0.00f;
            c.gridheight = 1;
            c.insets = new Insets(0, 0, 0, 0);

            //Create JPanel for border around labels and fields
            JPanel fieldBorder = new JPanel(null);
            fieldBorder.setOpaque(false);
            fieldBorder.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 2;
            c.gridheight = 5;
            searchInvestmentUI.add(fieldBorder, c);
            
            //Create JPanel for border atound buttons
            JPanel buttonBorder = new JPanel(null);
            buttonBorder.setOpaque(false);
            buttonBorder.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
            c.gridx = 2;
            c.gridy = 0;
            c.gridwidth = 1;
            c.gridheight = 5;
            searchInvestmentUI.add(buttonBorder, c);

            //========== FINALIZATION OF UI ==========
            add(searchInvestmentUI);
            validate();

        }

    }

    /**
     * Anonymous inner class for searching investments
     */
    private class SearchInvestments implements ActionListener
    {

        /**
         * Action performed on click
         */
        public void actionPerformed(ActionEvent event)
        {

            //Variable for holding raw strings from text field
            String rawSymbol = symbolField.getText();
            String rawNameKeywords = nameKeywordsField.getText();
            String rawLowPrice = lowPriceField.getText();
            String rawHighPrice = highPriceField.getText();

            //Variables for holding parsed input
            String symbol = new String(rawSymbol);
            String[] nameKeywords;
            double lowPrice;
            double highPrice;

            try
            {//Attempt to parse high and low prices

                if(!isEmpty(rawLowPrice))
                {//If input entered for low-price, attempt to parse
                    lowPrice = Double.parseDouble(rawLowPrice);
                    if(lowPrice < 0)
                    {
                        messageArea.append("[ERROR]: Low price less than 0, please try again\n");
                        return;
                    } 
                }
                else
                {//If no input entered for low-price, set to default 0.0
                    lowPrice = -1;
                }

                if(!isEmpty(rawHighPrice))
                {//If input entered for high-price, attempt to parse
                    highPrice = Double.parseDouble(rawHighPrice); 
                    if(highPrice < 0)
                    {
                        messageArea.append("[ERROR]: High price less than 0, please try again\n");
                        return;
                    }
                }
                else
                {//If no input entered for high-price, set to default 0.0
                    highPrice = -1;
                }
                      
            }
            catch(NumberFormatException e)
            {//Catch NumberFormatException, if caught throw an error and return from method
                messageArea.append("[ERROR]: Could not parse high or low price, please try again\n");
                return;
            }

            //Boolean expressions used to evaluate whether the Investment meets the criteria
            boolean symbolFound = true;
            boolean keywordsFound = true;
            boolean priceRangeFound = true;

            //String to hold delimiters
            String delimiters = "[ ]+";

            //Declare ArrayList to store the indices to search through
            ArrayList<Integer> indices = new ArrayList<Integer>();

            if(isEmpty(rawNameKeywords))
            {//If no input entered for name keywords

                for(int i = 0; i < investmentList.size(); i++)
                {//Push all indices in investmentList to indices
                    indices.add(i);
                }

                //Default keywordFound to true
                keywordsFound = true;

            }
            else
            {//If input entered for name keywords

                //Split raw input into individual words
                nameKeywords = rawNameKeywords.toLowerCase().split(delimiters);

                for(int i = 0; i < investmentList.size(); i++)
                {//Push all indices in investmentList to indices
                    indices.add(i);
                }

                for(String word : nameKeywords)
                {//Iterate though each word in words

                    //Create new ArrayList to store the intersection of all the wordMaps
                    ArrayList<Integer> intersection = new ArrayList<Integer>();

                    //Create new ArrayList to store the wordMap
                    ArrayList<Integer> temp = new ArrayList<Integer>();

                    //Get wordList using search word as key
                    temp = wordMap.get(word);

                    if(temp != null)
                    {//Check if temp is not equal to null or empty before checking

                        for(int i : temp)
                        {//Iterate through each integer in the wordMap

                            if(indices.contains(i))
                            {//If any value in the word map intersects with the pre-exisiting indices, add it to intersection
                                intersection.add(i);
                            }

                        }

                    }

                    //Set indices to calculated intersection
                    indices = intersection;

                }

                if(!indices.isEmpty())
                {//If keyword(s) are found, set keywordsFound to true
                    keywordsFound = true;
                }

            }

            for(int i : indices)
            {//Iterate through each number in indices

                //Get Investment from Investment list
                Investment temp = investmentList.get(i);

                if(isEmpty(symbol))
                {//If symbol is empty, default symbolFound to true
                    symbolFound = true;
                }
                else
                {//If symbol is not empty, check for equality

                    if(symbol.equalsIgnoreCase( temp.getSymbol() ))
                    {//If symbols are equal, set symbolFound to true
                        symbolFound = true;
                    }
                    else
                    {//If symbols are not equal, set symbolFound to false
                        symbolFound = false;
                    }

                }

                if(isEmpty(rawLowPrice) && isEmpty(rawHighPrice))
                {//If both low and high prices are empty, default priceRangeFound to true
                    priceRangeFound = true;
                }
                else if(isEmpty(rawLowPrice))
                {//If low price is empty, check if price is less than or equal to high price
                    priceRangeFound = temp.getPrice() <= highPrice;
                }
                else if(isEmpty(rawHighPrice))
                {//If high price is empty, check if price is greater than or equal to low price
                    priceRangeFound = temp.getPrice() >= lowPrice;
                }
                else
                {//If neither low or high price is empty, check if price is in between them
                    priceRangeFound = temp.getPrice() >= lowPrice && temp.getPrice() <= highPrice;
                }

                if(symbolFound && keywordsFound && priceRangeFound)
                {//Check if the stock matches the search criteria, if so print to screen
                
                    if(temp instanceof Stock)
                    {//If investment is a Stock, print header for formatting
                        messageArea.append("========STOCK========\n");
                    }
                    else if(temp instanceof MutualFund)
                    {//If investment is a MutualFund, print header for formatting
                        messageArea.append("========MUTUAL FUND========\n");
                    }
                    else
                    {//If neither, do nothing

                    }

                    //Print the investment to the screen
                    messageArea.append(temp.toString() + "\n");

                }

            }

        }      

    }

    /**
     * Method for preparing the GUI home screen, commands menu, and window options
     */
    private void prepareGUI()
    {

        //Set title, size, layout, and default close operation
        setTitle("ePortfolio");
        setSize(WIDTH, HEIGHT);
        setLayout( new GridLayout(1, 1) );

        //New quit listener for when window closes
        Quit quitListener = new Quit();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new Quit());

        //Set resizble true and set color to LIGHT_GRAY
        setResizable(true);
        getContentPane().setBackground(Color.LIGHT_GRAY);
        
        //========== INITIAL PANEL ==========
        JPanel initialPanel = new JPanel(new GridLayout(2, 1));
        initialPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 100));
        initialPanel.setBackground(Color.LIGHT_GRAY);

        //New label with welcome message, set font to 20pt
        JLabel welcomeLabel = new JLabel("Welcome to ePortfolio");
        welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(20.0f));

        //New text area with instruction message, set font same as welcome label
        JTextArea instructionArea = new JTextArea(3, 10);
        instructionArea.setFont(welcomeLabel.getFont());
        instructionArea.setOpaque(false);
        instructionArea.setBackground(new Color(0, 0, 0, 0));
        instructionArea.setEditable(false);
        instructionArea.setLineWrap(true);
        instructionArea.setText("Choose a command from the \"Commands\" menu to buy or sell an investment, update prices for all investments, get gain for the portfolio, search for relevant investments, or quit the program.");

        //Add welcome message and instruction to intial panel
        initialPanel.add(welcomeLabel);
        initialPanel.add(instructionArea);

        //Add intial panel to screen
        add(initialPanel);

        //Create new menu for holding commands
        JMenu commandMenu = new JMenu("Commands");

        //========== BUY MENU ==========
        JMenuItem buyInvestment = new JMenuItem("Buy Investment");
        ShowBuyInvestmentUI showBuyInvestmentUIListener = new ShowBuyInvestmentUI();
        buyInvestment.addActionListener(showBuyInvestmentUIListener);
        commandMenu.add(buyInvestment);

        //========== SELL MENU ==========
        JMenuItem sellInvestment = new JMenuItem("Sell Investment");
        ShowSellInvestmentUI showSellInvestmentUIListener = new ShowSellInvestmentUI();
        sellInvestment.addActionListener(showSellInvestmentUIListener);
        commandMenu.add(sellInvestment);

        //========== UPDATE MENU ==========
        JMenuItem updateInvestment = new JMenuItem("Update Investment");
        ShowUpdateInvestmentUI showUpdateInvestmentUIListener = new ShowUpdateInvestmentUI();
        updateInvestment.addActionListener(showUpdateInvestmentUIListener);
        commandMenu.add(updateInvestment);

        //========== TOTAL GAIN MENU ==========
        JMenuItem getTotalGain = new JMenuItem("Get Total Gain");
        ShowInvestmentGainsUI showInvestmentGainsUIListener = new ShowInvestmentGainsUI();
        getTotalGain.addActionListener(showInvestmentGainsUIListener);
        commandMenu.add(getTotalGain);

        //========== SEARCH MENU ==========
        JMenuItem searchInvestments = new JMenuItem("Search Investments");
        ShowSearchInvestmentsUI showSearchInvestmentsUIListener = new ShowSearchInvestmentsUI();
        searchInvestments.addActionListener(showSearchInvestmentsUIListener);
        commandMenu.add(searchInvestments);
        
        //========== QUIT MENU ==========
        JMenuItem quit = new JMenuItem("Quit Program");
        quit.addActionListener(quitListener);
        commandMenu.add(quit);

        //Create menu bar and add all menu choices
        JMenuBar bar = new JMenuBar();
        bar.add(commandMenu);
        
        //Set menu bar and validate program
        setJMenuBar(bar);
        validate();

    }

    /**
     * Method for reading all Investment information to a file
     * @param fileName A string representing the name of the file to read from
     * @return A boolean value indicating whether the read was successful
     */
    public boolean readFile(String fileName)
    {

        //Create inputStream variable
        Scanner inputStream = null;

        try
        {//Attempt to open supplied file name
            inputStream = new Scanner(new FileInputStream(fileName));
        }
        catch(FileNotFoundException e)
        {//Catch FileNotFoundException and throw an error and exit method
            System.out.println("     File " + fileName + " was not found or could not be opened");
            return false;
        }

        while(inputStream.hasNextLine())
        {

            //Declare variables to store information of investment
            String type;
            String symbol;
            String name;
            int quantity;
            double price;
            double bookValue;

            //Declare variables to store file information
            String temp;
            String[] splitString;

            try
            {//Try to get file input

                //Get line containing investment type
                temp = inputStream.nextLine();
                splitString = temp.split("\"");
                type = splitString[1];

                //Get line containing symbol
                temp = inputStream.nextLine();
                splitString = temp.split("\"");
                symbol = splitString[1];

                //Get line containing symbol
                temp = inputStream.nextLine();
                splitString = temp.split("\"");
                name = splitString[1];

                //Get line containing quantity
                temp = inputStream.nextLine();
                splitString = temp.split("\"");
                quantity = Integer.parseInt(splitString[1]);

                //Get line containing price
                temp = inputStream.nextLine();
                splitString = temp.split("\"");
                price = Double.parseDouble(splitString[1]);

                //Get line containing bookValue
                temp = inputStream.nextLine();
                splitString = temp.split("\"");
                bookValue = Double.parseDouble(splitString[1]);

                //Get newline at end of investment
                inputStream.nextLine();

                if(type.equalsIgnoreCase("stock"))
                {//If type is Stock, create a new stock with file information and push to list

                    //Create new Stock
                    Stock tempStock;
                    try
                    {
                        tempStock = new Stock(symbol, name, quantity, price);
                    }
                    catch(Exception e)
                    {
                        messageArea.append("[ERROR]: Exception " + e.getMessage() + " throw in Stock constructor\n");
                        return false;
                    }
                    
                    investmentList.add(tempStock);

                }
                else if(type.equalsIgnoreCase("mutualfund"))
                {//If type is MutualFund, create a new mutual fund with file information and push to list

                    //Create new MutualFund
                    MutualFund tempMutualFund;
                    try
                    {
                        tempMutualFund = new MutualFund(symbol, name, quantity, price);
                    }
                    catch(Exception e)
                    {
                        messageArea.append("[ERROR]: Exception " + e.getMessage() + " thrown in MutualFund exception\n");
                        return false;
                    }
                    
                    investmentList.add(tempMutualFund);
                    
                }

            }
            catch(ArrayIndexOutOfBoundsException e)
            {//If at any time a string is not split corretly, exit program and throw an error
                System.out.println("     [ERROR]: File could not be read successfully, exiting program...");
                System.exit(0);
            }
            catch(NumberFormatException e)
            {//If at any time a number cannot be parsed, exit program and throw an error
                System.out.println("     [ERROR]: Could not parse value, exiting program...");
                System.exit(0);
            }
            
        }

        //Update wordMap
        updateWordHash();

        //Close input stream
        inputStream.close();

        //Inform user operation was successful
        System.out.println();
        System.out.println("     File " + fileName + " successfully read");
        System.out.println();

        //Return true
        return true;

    }

    /**
     * Anonymous inner class for quiting the program
     */
    private class Quit implements WindowListener, ActionListener
    {

        public void windowOpened(WindowEvent event)
        {

        }

        /**
         * On window close, write all Investment information to file and quit program
         */
        public void windowClosing(WindowEvent event)
        {

            //Create PrintWriter object
            PrintWriter outputStream = null;

            try
            {//Attempt to open the file name supplied to the function
                outputStream = new PrintWriter(new FileOutputStream(IOfileName));
            }
            catch(FileNotFoundException e)
            {//Inform user and exit program if file was not found
                System.out.println("     File " + IOfileName + " was not found or could not be opened, exiting program");
                System.exit(0);
            }
            catch(NullPointerException e)
            {//Inform user and exit program if there is a NULL pointer exception
                System.out.println("     File " + IOfileName + " was not found or could not be opened, exiting program");
                System.exit(0);
            }

            for(int i = 0; i < investmentList.size(); i++)
            {//For loop for iterating through each investment

                //Declare investment object and get the current investment from the list
                Investment temp = investmentList.get(i);

                if(temp instanceof Stock)
                {//If object is a Stock, write stock type to list
                    outputStream.println("type = \"stock\"");
                }
                else if(temp instanceof MutualFund)
                {//If object is a MutualFund, write mutual fund type to list
                    outputStream.println("type = \"mutualfund\"");
                }

                //Write investment information to file
                outputStream.println("symbol = \"" + temp.getSymbol() + "\"");
                outputStream.println("name = \"" + temp.getName() + "\"");
                outputStream.println("quantity = \"" + temp.getQuantity() + "\"");
                outputStream.println("price = \"" + temp.getPrice() + "\"");
                outputStream.println("bookValue = \"" + temp.getBookValue() + "\"");
                outputStream.println();

            }

            //Close outputStream and exit program
            outputStream.close();
            System.exit(0);

        }

        /**
         * Action performed on click
         * @param event
         */
        public void actionPerformed(ActionEvent event)
        {

            //Create PrintWriter object
            PrintWriter outputStream = null;

            try
            {//Attempt to open the file name supplied to the function
                outputStream = new PrintWriter(new FileOutputStream(IOfileName));
            }
            catch(FileNotFoundException e)
            {//Inform user and exit program if file was not found
                System.out.println("     File " + IOfileName + " was not found or could not be opened, exiting program");
                System.exit(0);
            }
            catch(NullPointerException e)
            {//Inform user and exit program if there is a NULL pointer exception
                System.out.println("     File " + IOfileName + " was not found or could not be opened, exiting program");
                System.exit(0);
            }

            for(int i = 0; i < investmentList.size(); i++)
            {//For loop for iterating through each investment

                //Declare investment object and get the current investment from the list
                Investment temp = investmentList.get(i);

                if(temp instanceof Stock)
                {//If object is a Stock, write stock type to list
                    outputStream.println("type = \"stock\"");
                }
                else if(temp instanceof MutualFund)
                {//If object is a MutualFund, write mutual fund type to list
                    outputStream.println("type = \"mutualfund\"");
                }

                //Write investment information to file
                outputStream.println("symbol = \"" + temp.getSymbol() + "\"");
                outputStream.println("name = \"" + temp.getName() + "\"");
                outputStream.println("quantity = \"" + temp.getQuantity() + "\"");
                outputStream.println("price = \"" + temp.getPrice() + "\"");
                outputStream.println("bookValue = \"" + temp.getBookValue() + "\"");
                outputStream.println();

            }

            //Close outputStream and exit program
            outputStream.close();
            System.exit(0);

        }

        public void windowClosed(WindowEvent event)
        {

        }

        public void windowIconified(WindowEvent event)
        {

        }

        public void windowDeiconified(WindowEvent event)
        {

        }

        public void windowActivated(WindowEvent event)
        {

        }

        public void windowDeactivated(WindowEvent event)
        {
            
        }

    }

    /**
     * Mutator method for IOfileName
     * @param string The string name of the I/O file to read/write to
     */
    public void setIOFileName(String string)
    {

        if(isEmpty(string))
        {//If string is empty, set to default file investments.txt
            IOfileName = new String("investments.txt");
        }
        else
        {//If string is not empty, set IOfileName
            IOfileName = new String(string);
        }

    }

    /**
     * Accessor for InvestmentList
     * @return Copy of InvestmentList
     */
    public ArrayList<Investment> getInvestmentList()
    {
        return new ArrayList<Investment>(investmentList);
    }

    /**
     * Accessor for WordMap
     * @return Copy of WordMap
     */
    public HashMap<String, ArrayList<Integer> > getWordMap()
    {
        return new HashMap<String, ArrayList<Integer> >(wordMap);
    }   

    /**
     * Convert PortfolioGUI to string and return
     */
    public String toString()
    {
        return new String(wordMap + "\n" + investmentList + "\n");
    }

    /**
     * Checks for equality against another PortfolioGUI
     */
    public boolean equals(Object otherObject)
    {

        if(otherObject == null)
        {//Check if otherObject is NULL, if so return false
            return false;
        }
        else if(otherObject.getClass() != getClass())
        {//Check if there is an Object type mismatch, if so return false
            return false;
        }
        else
        {//Check for equality

            //Cast otherObject as PortfolioGUI
            PortfolioGUI other = (PortfolioGUI)otherObject;

            //Return the boolean expressions of the investmentLists and wordMaps
            return investmentList.equals(other.getInvestmentList()) && wordMap.equals(other.getWordMap());

        }

    }

    /**
     * Main class reesponsible for creating PortfolioGUI
     * @param args Command Line-Arguments
     */
    public static void main(String[] args)
    {

        //Create new GUI from PortfolioGUI
        PortfolioGUI portfolioGUI = new PortfolioGUI();

        //Set GUI visible
        portfolioGUI.setVisible(true);

        try
        {//Attempt to read from a command line file

            if(args[0] == null || args[0].equals(""))
            {//If string is empty or null, throw an error to user, and set defaultFilename

                System.out.println("     [ERROR]: No file name entered, file will be saved to new file \"investments.txt\" upon program close");
                portfolioGUI.setIOFileName("investments.txt");
            }
            else
            {//If string exists, attempt to read from file and set IOfileName
                portfolioGUI.readFile(args[0]);
                portfolioGUI.setIOFileName(args[0]);
            }

        }
        catch(ArrayIndexOutOfBoundsException e)
        {//If no command line input was entered, throw an error to user and set default IOfileName
            System.out.println("     [ERROR]: No file name entered, file will be saved to new file \"investments.txt\" upon program close");
            portfolioGUI.setIOFileName("investments.txt");
        }

    }

}