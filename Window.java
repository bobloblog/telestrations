import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Window sets up, well, the window.  All it really does is create an empty frame and call Telestrations. Not much to it.
 * 
 * @author Jeffrey Scott
 */
public class Window
{    
    protected static JFrame f;
    
    /**
     * Call this to start Telestrations in it's entirety.
     */
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                createAndShowGUI(); 
            }
        });
    }

    private static void createAndShowGUI()
    {
        f = new JFrame("Telestrations!");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
        catch (Exception e) {}
        f.setSize(506, 557);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        new Telestrations();
    } 
}