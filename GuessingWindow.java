import javax.swing.JPanel;
import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import java.util.ArrayList;
import javax.swing.JTextField;
import java.awt.event.*;
import javax.swing.event.*; 

/**
 * The GuessingWindow is opened at the second half of each round.  It displays the image, and creates a textBox that revices the users guess.  This class does not contain a "finished" button or a timer.
 * 
 * @author Jeffrey Scott
 */
public class GuessingWindow extends JPanel implements DocumentListener
{
    private MyLinkedList objs;
    private Graphics g;
    private JTextField textField, textFieldTwo;
    private String wordGuess;
    
    /**
     * This constructer creates a GuessingWindow using the default instructions.
     * @param image A MyLinkedList of tools that creates the image.
     */
    public GuessingWindow(MyLinkedList image)
    {        
        super(new BorderLayout());
        
        textField = new JTextField("Guess:", 1);
        textField.setEditable(false);
        add(textField, BorderLayout.NORTH);
        
        textFieldTwo = new JTextField("Type Guess Here", 1);
        textFieldTwo.getDocument().addDocumentListener(this);
        add(textFieldTwo, BorderLayout.SOUTH);
        
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.WHITE);
        
        objs = image;
        repaint();
    }
    
    /**
     * This constructer creates a GuessingWindow using custom instructions.
     * @param image A MyLinkedList of tools that creates the image.
     * @param instructions A String of instructions to be displayed at the top of the page.
     */
    public GuessingWindow(MyLinkedList image, String instructions)
    {        
        super(new BorderLayout());
        
        textField = new JTextField(instructions, 1);
        textField.setEditable(false);
        add(textField, BorderLayout.NORTH);
        
        textFieldTwo = new JTextField("Type Guess Here", 1);
        textFieldTwo.getDocument().addDocumentListener(this);
        add(textFieldTwo, BorderLayout.SOUTH);
        
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.WHITE);
        
        objs = image;
        repaint();
    }
    
    /**
     * This overrides the paint() method to draw the image in the MyLinkedList and to create the text boxes.
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);         
        if(objs != null)
            for(int i = 0; i < objs.size(); i++)
                ((Tools)objs.get(i)).paintObject(g);
    }  

    /**
     * Returns the guess that the user made.
     * @return A String with the guess.
     */
    public String getGuess()
    {
        return wordGuess;
    }
    
    /**
     * Sets the prefered size of the panel and its components.
     */
    public Dimension getPreferredSize()
    {
        return new Dimension(500,500);
    } 
    
    /**
     * An EventListener for the text boxes.
     * @param e A DocumentEvent,
     */
    public void insertUpdate(DocumentEvent e)
    {
        wordGuess = textFieldTwo.getText();
    }
    
    /**
     * An EventListener for the text boxes.
     * @param e A DocumentEvent,
     */
    public void removeUpdate(DocumentEvent e)
    {
        wordGuess = textFieldTwo.getText();
    }
    
    /**
     * An EventListener for the text boxes.
     * @param e A DocumentEvent,
     */
    public void changedUpdate(DocumentEvent e)
    {
        wordGuess = textFieldTwo.getText();
    }
}