import java.awt.Graphics;

/**
 * A tool is an object that must be able to be painted to a Graphics component.
 */
public abstract class Tools
{
    /**
     * This method MUST be called before any tool is actually drawn (call within paint() method).
     * @param g A Graphics component.
     */
    public abstract void paintObject(Graphics g);
}