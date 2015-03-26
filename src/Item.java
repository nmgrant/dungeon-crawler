import java.io.Serializable;

/**
 * A representation of an item with a gold value
 * 
 * @author Nicholas Grant
 */
public class Item implements Serializable {
    /**Item's name*/
    private String name;
    /**Item's gold value*/
    private int goldValue;
    
    /**
     * Item constructor
     * @param n     item's name
     * @param v     item's gold value
     */
    public Item( String n, int v ) {
        name = n;
        goldValue = v;
    }
    
    /**
     * Returns the item's name
     * @return
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns the item's gold value
     * @return
     */
    public int getValue() {
        return goldValue;
    }
    
}
