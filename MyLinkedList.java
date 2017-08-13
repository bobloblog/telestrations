import java.util.NoSuchElementException;

/**
 * A custom version of a LinkedList.  This class does not implement a list, and therefore does not inherit any methods.  WYSIWYG.
 * 
 * @author Jeffrey Scott
 */
public class MyLinkedList
{
    private Node head = null, tail = null;
    private int size;
    /**
     * The default contructor for MyList.
     */
    public MyLinkedList()
    {
        size = 0;
    }
    
    public MyLinkedList(int nSize)
    {
        size = 0;
        for(int i = 0; i < nSize; i++)
            add(null);
    }
    
    /**
     * Adds the object to the end of the list.
     * 
     * @param The object to be added.
     */
    public boolean add(Object obj)
    {
        if(size <= 0)
        {
            head = new Node(obj);
            tail = head;
        }
        else
        {
            tail.setNext(new Node(obj));
            tail = tail.getNext();
        }
        size++;
        return true;
    }
    
    /**
     * Insterts the object into the specified locaion, and moves all other nodes back.
     * 
     * @param The slot number.
     * @param The object to be added.
     */
    public void add(int slot, Object obj)
    {
        if(size == 0 || slot >= size || slot < 0)
            throw new IndexOutOfBoundsException();
        
        if(slot == 0)
        {
            addFirst(obj);
            return;
        }
        if(slot == size - 1)
        {
            addLast(obj);
            return;
        }
        Node temp = head;
        for(int i = 0; i < slot - 1; i++)
            temp = temp.getNext();
            
        Node tempNext = temp.getNext();
        temp.setNext(new Node(obj));
        temp = temp.getNext();
        temp.setNext(tempNext);
        
        if(slot == size - 1)
            tail = temp;
        if(slot == 0)
            head = temp;
        
        size++;
    }
    
    /**
     * Insterts the object into the first locaion, and moves all other nodes back.
     * 
     * @param The object to be added.
     */
    public void addFirst(Object obj)
    {
        Node oldHead = head;
        head = new Node(obj);
        head.setNext(oldHead);
        size++;
    }
    
    /**
     * Insterts the object into the last location.
     * 
     * @param The object to be added.
     */
    public void addLast(Object obj)
    {
        this.add(obj);
    }
    
    /**
     * Clears the LinkedList.
     */
    public void clear()
    {
        head = null;
        tail = null;
        size = 0;
    }
    
    /**
     * Searches the list for the specified object.
     * 
     * @param The object to compare to.
     * @return True if the object is found, false if it is not.
     */
    public boolean Contains(Object obj)
    {
        Node temp = head;
        for(int i = 0; i < size; i++)
        {
            if(obj == null && temp.getData() == null)
                return true;
            if(temp.getData().equals(obj))
                return true;
            temp = temp.getNext();
        }
        return false;
    }
    
    /**
     * Gets the object at the specified location.
     * 
     * @param The slot number.
     * @return The object that the slot number.
     */
    public Object get(int slot)
    {
        if(size == 0 || slot >= size || slot < 0)
            throw new IndexOutOfBoundsException();
        else
        {
            Node temp = head;
            for(int i = 0; i < slot; i++)
                temp = temp.getNext();
            
            return temp.getData();
        }
    }
    
    /**
     * Gets the object at the specified location.
     * 
     * @param The slot number.
     * @return The object that the slot number.
     */
    public Object getFirst()
    {
        if(size <= 0)
            throw new NoSuchElementException("The list is empty.");
        return head.getData();
    }
    
    /**
     * Gets the object at the specified location.
     * 
     * @param The slot number.
     * @return The object that the slot number.
     */
    public Object getLast()
    {
        if(size <= 0)
            throw new NoSuchElementException("The list is empty.");
        return tail.getData();
    }
    
    /**
     * Returns the first slot number that is equal to the specified object
     * 
     * @param The object to be compared.
     * @return The first slot number that contains the object.
     */
    public int indexOf(Object obj)
    {
        Node temp = head;
        for(int i = 0; i < size - 1; i++)
        {
            if((temp.getData()).equals(obj))
                return i;
            temp = temp.getNext();
        }
        return -1;
    }
    
    /**
     * Returns the last slot number that is equal to the specified object
     * 
     * @param The object to be compared.
     * @return The last slot number that contains the object.
     */
    public int lastIndexOf(Object obj)
    {
        Node temp = head;
        int slot = -1;
        for(int i = 0; i < size - 1; i++)
        {
            if((temp.getData()).equals(obj))
                slot = i;
            temp = temp.getNext();
        }
        return slot;
    }
    
    /**
     * Gets the object at the first location, pushes all other nodes forward, and returns its value.
     * 
     * @return The first object.
     */
    public Object remove()
    {          
        return removeFirst();    
    }
    
    /**
     * Removes the object at the specified location and returns its value.
     * 
     * @param The slot number.
     * @return The object at the slot number.
     */
    public Object remove(int slot)
    {
        if(size == 0 || slot >= size || slot < 0)
            throw new IndexOutOfBoundsException();
            
        if(slot == 0)
        {
            Object tempData = head.getData();
            head = head.getNext();
            size--;
            return tempData;
        }
            
        Node temp = head;
        for(int i = 0; i < slot - 1; i++)
            temp = temp.getNext();
        if(slot == size - 1)
        {
            Object tempData = temp.getNext().getData();
            tail = temp;
            tail.setNext(null);
            size--;
            return tempData;
        }
        Object tempData = temp.getNext().getData();
        temp.setNext(temp.getNext().getNext());
        size--;
        return tempData;
    }
    
    /**
     * Removes the first occurance of the specified object.
     * 
     * @param The object
     */
    public void remove(Object obj)
    {            
        Node temp = head;
        if(temp.getData().equals(obj))
            remove(0);
        for(int i = 0; i < size - 1; i++)
        {
            if(temp.getNext().getData().equals(obj))
                remove(i + 1);
            temp = temp.getNext();
        }
    }
    
    /**
     * Gets the object at the first location, pushes all other nodes forward, and returns its value.
     * 
     * @return The first object.
     */
    public Object removeFirst()
    {
        if(size <= 0)
            throw new NoSuchElementException("The list is empty.");
            
        Object tempData = head.getData();
        head = head.getNext();
        size--;
        return tempData;
    }
    
    /**
     * Removes the object at the last location and returns its value.
     * 
     * @return The last object.
     */
    public Object removeLast()
    {
        if(size <= 0)
            throw new NoSuchElementException("The list is empty.");
            
        Object tempData = tail.getData();
        size--;
        Node temp = head;
        for(int i = 0; i < size - 1; i++)
            temp = temp.getNext();
        temp.setNext(null);
        tail = temp;
        return tempData;
    }
    
    /**
     * Sets the data of the specified node with a given object, and returns the old value.
     * 
     * @param The slot number.
     * @param The object to pass.
     * @return The old value of the slot.
     */
    
    public Object set(int slot, Object obj)
    {
        if(size <= 0)
            throw new IndexOutOfBoundsException();
            
        Node temp = head;
        for(int i = 0; i < slot; i++)
            temp = temp.getNext();
        Object oldData = temp.getData();
        temp.setData(obj);
        
        return oldData;
    }
    
    /**
     * Gets the size of the list.
     * 
     * @return The size of the list.
     */
    public int size()
    {
        return size;   
    }
}