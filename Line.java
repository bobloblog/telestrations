import java.awt.Graphics;
import java.awt.Color;

/**
 *This class creates a single java.awt.Graphics line.
 * 
 * @author Jeffrey Scott
 */
class Line extends Tools
{
    private int bX = 0;
    private int bY = 0;
    private int eX = 0;
    private int eY = 0;
    
    /**
     * The constructor for the line that takes the endpoints as parameters.
     * @param beginX The x value of the first endpoint.
     * @param beginY The y value of the first endpoint.
     * @param endX The x value of the second endpoint.
     * @param endY The y value of the second endpoint.
     */
    public Line(int beginX, int beginY, int endX, int endY)
    {
        bX = beginX;
        bY = beginY;
        eX = endX;
        eY = endY;
    }

    /**
     * This method MUST be called before the line is actually drawn (call within paint() method).
     * @param g A Graphics component.
     */
    public void paintObject(Graphics g){
        g.setColor(Color.BLACK);
        g.drawLine(bX,bY,eX,eY);  
    }
}