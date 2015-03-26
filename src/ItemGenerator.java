import java.util.*;
import java.io.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Generates a list of items from a text file and provides a method
 * to randomly generate an item 
 * 
 * @author Nicholas Grant
 */
public class ItemGenerator implements Serializable{
    /**List of items*/
    private ArrayList<Item> itemList;
    
    /**
     * Adds items to itemList using a text file with a list of items.
     */
    public ItemGenerator() {
        ArrayList<Item> items = new ArrayList<>();
        try {
            Scanner in = new Scanner( new File( "ItemList.txt" ));
            in.useDelimiter( "," );
            do {
                String itemName = in.next();
                int itemValue = Integer.parseInt( in.next() );
                
                items.add( new Item( itemName, itemValue ) );
            } while ( in.hasNext() );
            in.close();
        } catch ( FileNotFoundException fnf) {
            System.out.println("File was not found.");
        }
        itemList = items;
    }
    
    /**
     * Generates a random item from itemList
     * @return      random item in itemList
     */
    public Item generateItem() {
        Random randItem = new Random();
        return itemList.get( randItem.nextInt( 8 ) );
    }
}