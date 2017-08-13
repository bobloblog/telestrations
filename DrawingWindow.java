import java.awt.geom.Line2D;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The DrawingWindow is opened at the beginning of each round.  It creates a textbox displaying the word to be drawng and it saves the image as the user draws it.  This class does not contain a 
 * "finished" button or a timer.
 * 
 * @author Jeffrey Scott
 */
class DrawingWindow extends JPanel
{
    private int beginX = 0, beginY = 0, endX = 0, endY = 0;
    private Line line =  new Line(0, 0, 0, 0);
    private MyLinkedList objs;
    private Graphics g;
    private JTextField textField;
    
    /**
     * This constructer creates a DrawingWindow with default instructions that should contain the word to be drawn.
     * @param instructions A String of instructions to be displayed at the top of the page.
     */
    public DrawingWindow(String instructions)
    {
        super(new BorderLayout());
        
        textField = new JTextField(instructions, 1);
        textField.setEditable(false);
        add(textField, BorderLayout.NORTH);
        
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.WHITE);
        
        objs = new MyLinkedList();
        repaint();
        
        begin();
    }
    
    /*
     * This method handles the bulk of the class, collecting user input to create a MyLinkedLlist representation of the image.
     */
    private void begin()
    {
        addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                if(SwingUtilities.isLeftMouseButton(e))
                {
                    beginX = e.getX();
                    beginY = e.getY();
                    objs.add(new Line(beginX, beginY, beginX, beginY));
                }
                else if(SwingUtilities.isRightMouseButton(e))
                    objs.add(new Eraser(e.getX(), e.getY()));
                repaint();
            }
        });

        addMouseMotionListener(new MouseAdapter()
        {
            public void mouseDragged(MouseEvent e)
            {
                if(SwingUtilities.isLeftMouseButton(e))
                {
                    endX = e.getX();
                    endY = e.getY();    
                    objs.add(new Line(beginX, beginY, endX, endY));
                    beginX = e.getX();
                    beginY = e.getY();
                }
                else if(SwingUtilities.isRightMouseButton(e))
                    objs.add(new Eraser(e.getX(), e.getY()));
                repaint();
            }
        });
    }
    
    /**
     * This method returns the MyLinkedList representation of the image.
     * @return A MyLinkedList of tools that represents the image drawn.
     */
    public MyLinkedList getImage()
    {
        MyLinkedList temp = new MyLinkedList();
        for(int i = 0; i < objs.size(); i++)
            temp.add(objs.get(i));
            
        objs.clear();
        return temp;
    }

    /**
     * This overrides the paint() method to draw the image in the MyLinkedList and to create the text boxes.
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);        
        for(int i = 0; i < objs.size(); i++)
            ((Tools)objs.get(i)).paintObject(g);
    }  

    /**
     * Sets the prefered size of the panel and its components.
     */
    public Dimension getPreferredSize()
    {
        return new Dimension(500,500);
    }   
}