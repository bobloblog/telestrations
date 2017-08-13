import java.awt.Graphics;
import java.awt.Color;

/**
 *This class creates a single java.awt.Graphics oval that is filled white.
 * 
 * @author Jeffrey Scott
 */
class Eraser extends Tools
{
    private int x = 0, y = 0;
    private int width = 25;
    
    /**
     * This constructer sets the x and y values of the eraser.
     */
    public Eraser(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * This method allows the width of the eraser to be altered.
     * @param width The new width.
     */
    public void changewidth(int width)
    {
        this.width = width;
    }
    
    /**
     * This method MUST be called before the eraser is actually drawn (call within paint() method).
     * @Param g A Graphics component.
     */
    public void paintObject(Graphics g){
        g.setColor(Color.WHITE);
        g.fillOval(x - (width / 2), y - (width / 2), width, width);  
    }
}