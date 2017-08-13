/**
 * A DataObject stores a LinkedList of lines that create an image, a String of the word the image is supposed to be, and a String of the guess of what the image is.
 * 
 * @author Jeffrey Scott
 */

public class DataObject
{
    private MyLinkedList imageData;
    private String guessData, wordData;

    /**
     * Constructor that sets all values to null.
     */
    public DataObject()
    {
        wordData = "";
        imageData = new MyLinkedList();
        guessData = "";
    }
    
    /**
     * Constructor that sets the word for the DataObject.
     * @param word The desired word.
     */
    public DataObject(String word)
    {
        wordData = word;
        imageData = new MyLinkedList();
        guessData = "";
    }
    
    /**
     * Constructor that sets the image for the DataObject.
     * @param image The desired image.
     */
    public DataObject(MyLinkedList image)
    {
        wordData = "";
        imageData = image;
        guessData = "";
    }
    
    /**
     * Constructor that sets the word and the image for the DataObject.
     * @param word The desired word.
     * @param image The desired image.
     */
    public DataObject(String word, MyLinkedList image)
    {
        wordData = word;
        imageData = image;
        guessData = "";
    }
    
    /**
     * Constructor that sets the word and the guess for the DataObject.
     * @param word The desired word.
     * @param guess The desired guess.
     */
    public DataObject(String word, String guess)
    {
        wordData = word;
        imageData = new MyLinkedList();
        guessData = guess;
    }
    
    /**
     * Constructor that sets the image and the guess for the DataObject.
     * @param word The desired image.
     * @param word The desired guess.
     */
    public DataObject(MyLinkedList image, String guess)
    {
        wordData = "";
        imageData = image;
        guessData = guess;
    }
    
    /**
     * Constructor that sets the word, the image, and the guess for the DataObject.
     * @param word The desired word.
     * @param image The desired image.
     * @param guess The desired guess.
     */
    public DataObject(String word, MyLinkedList image, String guess)
    {
        wordData = word;
        imageData = image;
        guessData = guess;
    }
        
    /**
     * Returns the String value of the guess.
     * @return The string value of the guess.
     */
    public String getGuess()
    {
        return guessData;   
    }
    
    /**
     * Returns a MyLinkedList of the image.
     * @return A MyLinkedList of the image.
     */
    public MyLinkedList getImage()
    {
        return imageData;
    }
    
    /**
     * Returns the String value of the word.
     * @return The String value of the word.
     */
    public String getWord()
    {
        return wordData;
    }
    
    /**
     * Sets the guess.
     * @param word The desired guess.
     */
    public void setGuess(String guess)
    {
        guessData = guess;
    }

    /**
     * Sets the image.
     * @param word The desired image.
     */
    public void setImage(MyLinkedList image)
    {
        imageData = image;
    }
    
    /**
     * Sets the word.
     * @param word The desired word.
     */
    public void setWord(String word)
    {
        wordData = word;
    }
}