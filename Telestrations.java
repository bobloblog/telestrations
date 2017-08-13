import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.Random;

/**
 * This class is the backbone of the game.  It handles the players, the rounds, all of the image, word, and guess data, the dialogs, the timers, the buttons, the menu bar... you get the point. 
 * This code is commented in more detail throughout because of it's natural volume and complexity.
 * 
 *   Definitions for the purposes of this code:
 *   image- A MyLinkedList of Tool objects that, when painted, will ultimately create an image.
 *   guess- A String that represents what a user guessed that its corresponding image was.
 *   word- A String that represents what its corresponding image was supposed to be
 *   playerOffset- The game is supposed to be played by a group of people sitting in a circle.  To mimick the way the information moves around a circle, a playerOffset is calculated for each player
 *   every round to determine what data they are working with.
 * 
 * @author Jeffrey Scott
 */
class Telestrations extends Window implements ActionListener
{   
    private DrawingWindow currentDW;
    private GuessingWindow currentGW;
    private ViewingWindow currentVW;
    private DataObject[][] playerData;//2D array holds player data of data objects.  playerData[represents each player][represents data from each round].
    private boolean isDrawing = true, inGallery = false;//Identifies if the screen is currently in drawing (used to build screen)--Identifies if the screen is currently in the gallery (used to stop timers). 
    private JButton finished;//The finished button added at the bottom of every drawing or guessing window.
    private JMenuBar menu;
    private JMenu file;//The file menu on the menu bar
    private Timer timer, timerViewer;//The game timer--The timer that updates the countdown on the screen
    private int numPlayers, currentPlayer = 1, lastPlayer = 1, windowRound = 1, round = 1, delay = 60000;//number of players--the current player--the player before the current player--how many times the
                                                                                                         //screen has changed(DW to VW, vice-versa)--the current round(every DW + VW)--time in each round(ms).
    private long lastTime;//This is used by the timers to represent the system time (ms) when the timer was started.
    
    /**
     * The default and only constructor.  This creates the basis for the entire game (basically, everything metioned above).
     */
    public Telestrations()
    {                        
        //Prompts user for the number of players
        Object[] possibilities = {"2", "3", "4", "5", "6", "7", "8"};
        String s = (String)JOptionPane.showInputDialog(f, "How many players?", "Setup", JOptionPane.PLAIN_MESSAGE, javax.swing.UIManager.getIcon("OptionPane.questionIcon"), possibilities, "2");
        //frame, message, title, type of dialog, icon, choices (from above), default choice.
        //reads the users choice, closese if canceled or exited.
        if ((s != null) && (s.length() > 0))
        {
            try{numPlayers = Integer.parseInt(s);}
            catch(Exception e){}
        }
        else
            System.exit(0);
        //gets the words and sets them to each player
        ArrayList<String> words = getWords();
        playerData = new DataObject[numPlayers][numPlayers];
        for(int i = 0; i < playerData.length; i++)
            for(int j = 1; j < playerData[0].length; j++)
            {
                playerData[i][0] = new DataObject(words.get(i), words.get(i));
                playerData[i][j] = new DataObject();
            }      
        //sets up the timer.
        ActionListener timerAction = new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                if(!inGallery)
                {
                    f.setTitle("Telestrations! " + "Player " + currentPlayer + " -- Time Expired!" );//updates the title with the time expired message.
                    java.awt.Toolkit.getDefaultToolkit().beep();//beeps if time runs out (nifty little feature).
                    manageRound();
                    lastTime = System.currentTimeMillis() + delay;
                }
            }
        };
        //sets up the timer updater timer.
        lastTime = System.currentTimeMillis() + delay + 1000;
        ActionListener timeViewer = new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {   
                if(!inGallery)
                     f.setTitle("Telestrations! " + "Player " + currentPlayer + " -- " + (int)(((lastTime) - (System.currentTimeMillis())) / 1000) + " seconds left.");//updates title w/ time left.
            }
        };
        //The following code sets up everything in the window will use including the menu bar and it's menus and items, the "Finished" button mentioned above, and prepares the timers.
        menu = new JMenuBar();
        file = new JMenu("File");
        menu.add(file);
        JMenuItem exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
        exit.addActionListener(this);
        exit.setActionCommand("exit");
        file.add(exit);
        f.setJMenuBar(menu);
        
        finished = new JButton("Finished");
        finished.addActionListener(this);
        finished.setActionCommand("finished");
        f.add(finished, BorderLayout.SOUTH);
        
        timer = new Timer(delay, timerAction);
        timer.setRepeats(false);
        timerViewer = new Timer(1000, timeViewer);
        timerViewer.setRepeats(true);
        //finally shows the window, and begins the game in the drawing mode.
        f.setVisible(true);  
        isDrawing();
    }
    
    /**
     * Adds an image to the player data based on the current player, last player, playerOffset, and round.
     */
    public void addImage(MyLinkedList image)
    {
        playerData[playerOffset(lastPlayer) - 1][round - 1].setImage(image);
    }
    
    /**
     * Sets the guess of the corresponding DataObject and sets the word of the next.
     * @param guess A String of the guess.
     */
    public void addGuess(String guess)
    {
        playerData[reverseOffset() - 1][round - 2].setGuess(guess);//sets the guess of the new
        playerData[reverseOffset() - 1][round - 1].setWord(guess);//sets the word of the original
    }
    
    /**
     * Gets the last image drawn by the player before the current player.
     * @return A MyLinkedList of the image.
     */
    public MyLinkedList getLastImage()
    {
        return playerData[lastPlayer - 1][round - 2].getImage();
    }
    
    /**
     * This method rebuilds the screen to prepare it for a drawing round.
     */
    public void isDrawing()
    {        
        //halts the timers and clears the screen; gets the last image drawn.
        timer.stop();
        timerViewer.stop();
        try{addImage(currentDW.getImage()); f.remove(currentDW);}
        catch(Exception e){}
        try{f.remove(currentGW);}
        catch(Exception e){}
        currentDW = null;
        
        f.repaint();
        //Creates and displays the continue dialog.
        if(windowRound <= numPlayers && isDrawing)
            JOptionPane.showMessageDialog(f, "It is player " + currentPlayer + "'s turn.\n\nClick OK to continue", "Player " + currentPlayer + ":", JOptionPane.INFORMATION_MESSAGE);
        //Sets up the window for the drawing round.
        if(round != 1)
            currentDW = new DrawingWindow("It is Player " + currentPlayer + "'s turn to draw the word \"" + playerData[playerOffset(currentPlayer) - 1][round - 2].getGuess() + ":\"");
        else
            currentDW = new DrawingWindow("It is Player " + currentPlayer + "'s turn to draw the word \"" + playerData[playerOffset(currentPlayer) - 1][round - 1].getGuess() + ":\"");
        f.add(currentDW, BorderLayout.CENTER);//adds the DW to the middle of the window.
        //prepares the screen to be shown and shows it.
        f.pack();
        f.validate();
        f.setVisible(true);
        //restarts the timers.
        timer.restart();
        lastTime = System.currentTimeMillis() + delay + 1000;
        timerViewer.restart();
    } 
    
    /**
     * This method rebuilds the screen to prepare it for a guessing round (during which the user will be typing. Hence: isTyping()).
     */
    public void isTyping()
    {
        //halts the timers and clears the screen; gets the last guess.
        timer.stop();
        timerViewer.stop();
        try{if(currentGW.getGuess() != null){addGuess(currentGW.getGuess());} f.remove(currentGW);}
        catch(Exception e){} 
        try{f.remove(currentDW);}
        catch(Exception e){}   
        currentGW = null;
        
        f.repaint();
        //Creates and displays the continue dialog.
        if(windowRound <= numPlayers && !isDrawing)
            JOptionPane.showMessageDialog(f, "It is player " + currentPlayer + "'s turn.\n\nClick OK to continue", "Player " + currentPlayer + ":", JOptionPane.INFORMATION_MESSAGE);
        //Sets up the window for the guessing round.
        currentGW = new GuessingWindow(getLastImage(), "It is Player " + currentPlayer + "'s turn to guess the word.");
        f.add(currentGW, BorderLayout.CENTER);
        //prepares the screen to be shown and shows it.
        f.pack();
        f.validate();
        f.setVisible(true);
        //restarts the timers
        timer.restart();
        lastTime = System.currentTimeMillis() + delay + 1000;
        timerViewer.restart();
    }
    
    /**
     * This method rebuilds the screen to prepare it for the ViewingScreen.  This screen is the last screen setup in the program (Hence: isDone()).
     */
    public void isDone()
    {
        //halts the timers and clears the screen; gets the last image drawn or guess.
        timer.stop();
        timerViewer.stop();
        f.setTitle("Telestrations Gallery!");
        inGallery = true;//sets indicators that the game is in the gallery to keep timers from updating, and the screen from changing.
        isDrawing = false;
        try{addImage(currentDW.getImage()); addGuess(currentGW.getGuess());} catch(Exception e){}
        try{f.remove(currentGW); f.remove(currentDW);} catch(Exception e){}
        try{f.remove(finished);}catch(Exception e){}
        currentDW = null;
        currentGW = null;
        
        f.repaint();
        //creates the navigation buttons for the gallery
        JButton previous = new JButton("Previous");
        previous.addActionListener(this);
        previous.setActionCommand("previous");
        f.add(previous, BorderLayout.PAGE_START);
       
        JButton next = new JButton("Next");
        next.addActionListener(this);
        next.setActionCommand("next");
        f.add(next, BorderLayout.PAGE_END);
        //Sets up the window for the viewing.
        currentVW = new ViewingWindow(playerData);
        f.add(currentVW, BorderLayout.CENTER);
        //prepares the screen to be shown and shows it.
        f.pack();
        f.validate();
        f.setVisible(true);
    }
    
    /**
     * This is the action listener for the game. It handles the menu bar and all buttons on the frame.
     * @param e An ActionEvent.
     */
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().compareTo("finished") ==0)
            manageRound();
        if(e.getActionCommand().compareTo("next") ==0)  
            currentVW.cycle("next");//cycles the gallery to the next image. See ViewingWindow API.
        if(e.getActionCommand().compareTo("previous") ==0)
            currentVW.cycle("previous");//cycles the gallery to the previous image. See ViewingWindow API.
        if(e.getActionCommand().compareTo("exit") ==0) 
            System.exit(0);
    }
    
    /**
     * This method manages timers and determines if the round is over or if the game is over, and calls the necessary methods to prepare the screen.
     */
    public void manageRound()
    {
        //updates timers and current player and last player information for the round, then calls either the next part of the round, or recalls the necessary window.
        lastTime = System.currentTimeMillis() + delay + 1000;
        lastPlayer = currentPlayer;
        if(currentPlayer >= numPlayers)
        {
            currentPlayer = 1;
            switchScreens();
        }
        else
            currentPlayer++;
        if(isDrawing)
            isDrawing();
        else
            isTyping();
    }
    
    /**
     * This method calls the necessary methods to prepare the screen for each round.
     */
    private void switchScreens()
    {   
        if((windowRound + 1) > numPlayers)
        {
            windowRound++;
            isDone();
        }
        else if(isDrawing)
        {
            try{addImage(currentDW.getImage());}//gets the last image drawn, updates round info
            catch(Exception f){}
            round++;
            windowRound++;
            isTyping();
            isDrawing = false;
        }
        else if(!isDrawing)
        {
            try{addGuess(currentGW.getGuess());}//gets the last guess, updates round info
            catch(Exception f){}
            windowRound++;
            isDrawing();
            isDrawing = true;
        }
    }
    
    /**
     * This method gets an ArrayList of words to be used by the players.  The words come from the file "words.txt." This file can be edited. To add or remove words, simply type them on a new line 
     * (or remove the line they are on entirely) and update the number on the first line (which represents the number of words in the file).  The list does NOT have to be in alphabetical order, that's 
     * just the way the words came.
     * @return An ArrayList of Strings that contains the words.
     */
    public ArrayList<String> getWords()
    {
        ArrayList<String> strs = new ArrayList<String>();//The words themselves
        ArrayList<Integer> ints = new ArrayList<Integer>();//The line value that the words are on
        Random rand = new Random();
        int numWords;//The number of words in the list.  Found by parsing the first line of the file
        
        //pulls the words from the file
        String temp;
        try
        {
            FileInputStream fstream = new FileInputStream("words.txt");
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            temp = br.readLine();
            numWords = Integer.parseInt(temp);
            
            for(int i = 0; i < numPlayers; i++)
                ints.add((int)(rand.nextInt(numWords + 1) + 1));
            for(int i = 0; i < numPlayers; i++)
            {
                for(int j = 0; (temp = br.readLine()) != null; j++)
                    if(j == ints.get(i))
                        strs.add(temp);
                fstream = new FileInputStream("words.txt");
                in = new DataInputStream(fstream);
                br = new BufferedReader(new InputStreamReader(in));
                temp = br.readLine();
            }            
            fstream.close();
        }
        catch(Exception e){JOptionPane.showMessageDialog(f, "The file \"words.txt\" could not be found.", "Error", JOptionPane.ERROR_MESSAGE); System.exit(1);}
        
        return strs;
    }
    
    /**
     * This method calculates the playerOffset (as defined at the beginning of this document).
     * @param curPlayer The current player.
     * @return an int value representing the playerOffset.
     */
    public int playerOffset(int curPlayer)
    {
        if((curPlayer + (round - 1)) <= numPlayers)
            return curPlayer + (round - 1);
        else//if has to jump from the last player to the first.
            return (curPlayer + (round - 1)) / numPlayers;
    }
    
    /**
     * This calculates the reverse of the playerOffset (instead of x forward, it's x back).
     * @return The reverseOffset.
     */
    public int reverseOffset()
    {
        if((currentPlayer - round) > 0)
            return currentPlayer - round;
        else//if has to jump from the first player to the last.
            return numPlayers + (currentPlayer - round);
    }
    
    /**
     * Sets the prefered size of the frame and its components.
     */
    public Dimension getPreferredSize()
    {
        return new Dimension(15,150);
    } 
}