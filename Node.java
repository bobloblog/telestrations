/**
 * Nodes to be used by MyList
 * 
 * @author Jeffrey Scott
 */
public class Node
{
    private Object data = null;
    private Node next = null;
    
    /**
     * The constructor for the Node.
     * 
     * @param The data for the node.
     */
    public Node(Object obj)
    {
        data = obj;
        if(obj == null)
            data = null;
    }
    
    /**
     * Gets the data stored in the node.
     * 
     * @return Retrusn the data.
     */
    public Object getData()
    {
        if(data == null)
            return null;
        return data;
    }
    
    /**
     * Sets the data of the node.
     * 
     * @param The data for the node.
     */
    public void setData(Object obj)
    {
        data = obj;
        if(obj == null)
            data = null;
    }

    /**
     * Gets the next node.
     * 
     * @return The next node.
     */
    public Node getNext()
    {
        if(next == null)
            return null;
        return next;
    }
    
    /**
     * Sets the next node that this node is looking at.
     * 
     * @param The next node.
     */
    public void setNext(Node node)
    {
        if(node == null)
            next = null;
        next = node;
    }
}