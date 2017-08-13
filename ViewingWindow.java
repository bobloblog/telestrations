import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.BorderLayout;
import java.awt.event.*;

/**
 * The ViewingWindow, also known as a Gallery, is opened at the end of the game to show the users all of the drawings 
 * and guesses.
 * 
 * @author Jeffrey Scott
 */
public class ViewingWindow extends JPanel
{
    private DataObject[][] dataMass;
    private int beingViewed = 0, p = 0, r = 0;//p = player, r = round
    private JTextField textField, textFieldTwo;
    
    /**
     * This constructure sets the JPane do display all of the items of the ViewingWindow.
     * 
     * @param incData A 2D DataObject array arranged with the first array representing the players and the second
     * array representing the rounds.  The DataObjects do not have to be full.
     */
    public ViewingWindow(DataObject[][] incData)
    {
        super(new BorderLayout());
        
        textField = new JTextField("Word: ", 1);
        textField.setEditable(false);
        
        textFieldTwo = new JTextField("Guess: ", 1);
        textFieldTwo.setEditable(false);
        
        add(textField, BorderLayout.PAGE_START);
        add(textFieldTwo, BorderLayout.PAGE_END);
        
        dataMass = incData;
        
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.WHITE);
    }
    
    /**
     * This overrides the paint() method to draw the image in the DataObject, and to display the word and guess
     * on the screen.
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);   
        try
        {
            MyLinkedList temp = dataMass[p][r].getImage();
            for(int i = 0; i < temp.size(); i++)
                ((Tools)temp.get(i)).paintObject(g);
            textField.setText("Gallery:  \"" + dataMass[p][r].getWord() + "\"");
            textFieldTwo.setText("Guess:  \"" + dataMass[p][r].getGuess() + "\"");
        }
        catch(Exception e){}
    }  
    
    /**
    *This window does not include the Previous and Next buttons required for operation.  They must be placed
    *on the frame, not the JPanel. Calling this method with "next" or "previous" will act as an actionListener,
    *and perform the respective tasks.
    *@param actionCommand "next" or "previous" to perform respective tasks.
    */
    public void cycle(String actionCommand)
    {
        if(actionCommand.compareTo("next") ==0)
        {   
            r++;
            if(r >= dataMass[0].length - 1)
            {
                p++;
                if(p >= dataMass.length)
                {
                    p = dataMass.length - 1;
                    r--;
                }
                else
                    r = 0;
            }
            repaint();
        }
        if(actionCommand.compareTo("previous") ==0)
        {   
            r--;
            if(r < 0)
            {
                p--;
                if(p < 0)
                {
                    p = 0;
                    r++;
                }
                else
                    r = dataMass[0].length - 2;
            }
            repaint();    
        }
    }

    /**
     * Sets the prefered size of the panel and its components
     */
    public Dimension getPreferredSize()
    {
        return new Dimension(500,500);
    } 
}