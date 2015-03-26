import java.util.*;
import java.io.Serializable;
import java.awt.Point;

/**
 * A representation of Character that represents the user
 * 
 * @author Nicholas Grant
 */
public class Hero extends Character implements Serializable {
    /**An array list of items (max 5)*/
    private ArrayList< Item > items;
    /**Hero's location*/
    private Point location;
    
    /**
     * Hero constructor
     * @param n         hero's name
     * @param q         hero's catchphrase
     * @param start     hero's starting location
     */
    public Hero( String n, String q, Point start ) {
        super ( n, q, 1, 10, 0 );
        items = new ArrayList<>( 5 );
        location = start;
    }
        
    /**
     * Returns the hero's items
     * @return      hero's inventory
     */
    public ArrayList<Item> getItems() {
        return items;
    }
    
    /**
     * Adds item to hero's inventory
     * @param i     item to add
     * @return      returns true if item added successfully
     */
    public boolean pickUpItem( Item i ) {
        items.add( i );
        return true;
    }
    
    /**
     * Removes item from hero's inventory once sold
     * @param i     item to remove
     */
    public void removeItem( Item i ) {
        items.remove( i );
    }
    
    /**
     * Removes item at an index from hero's inventory
     * @param index     index of item to remove
     */
    public void removeItem( int index ) {
        items.remove( index );
    }
    
    /**
     * Returns the hero's location
     * @return      hero location
     */
    public Point getLocation() {
        return location;
    }
    
    /**
     * Sets the hero's location
     * @param p     desired hero location
     */
    public void setLocation( Point p ) {
        location = p;
    }
    
    /**
     * Returns the state of the room to the north of the hero
     * @param l     current dungeon level
     * @return      state of room to the north of the hero
     */
    public char goNorth( Level l ) {
        if ( l.getRoom( location ) == 'm' ) {
            return 'm';
        }
        else if ( l.getRoom( location ) == 'i' ) {
            return 'i';
        }
        else if ( l.getRoom( location ) == 'f' ) {
            return 'f';
        }
        else {
            return 0;
        }
    }
    
    /**
     * Returns the state of the room to the south of the hero
     * @param l     current dungeon level
     * @return      state of room to the south of the hero
     */
    public char goSouth( Level l) {
        if ( l.getRoom( location ) == 'm' ) {
            return 'm';
        }
        else if ( l.getRoom( location ) == 'i' ) {
            return 'i';
        }
        else if ( l.getRoom( location ) == 'f' ) {
            return 'f';
        }
        else {
            return 0;
        }
    }
    
    /**
     * Returns the state of the room to the east of the hero
     * @param l     current dungeon level
     * @return      state of room to the east of the hero
     */
    public char goEast( Level l ) {
        if ( l.getRoom( location ) == 'm' ) {
            return 'm';
        }
        else if ( l.getRoom( location ) == 'i' ) {
            return 'i';
        }
        else if ( l.getRoom( location ) == 'f' ) {
            return 'f';
        }
        else {
            return 0;
        }
    }
    
    /**
     * Returns the state of the room to the west of the hero
     * @param l     current dungeon level
     * @return      state of room to the west of the hero
     */
    public char goWest( Level l ) {
        if ( l.getRoom( location ) == 'm' ) {
            return 'm';
        }
        else if ( l.getRoom( location ) == 'i' ) {
            return 'i';
        }
        else if ( l.getRoom( location ) == 'f' ) {
            return 'f';
        }
        else {
            return 0;
        }
    }
    
    /**
     * Hero's attack method. Attack damage is based on hero level and the
     * amount of weapons in the hero's inventory
     * @param c         target of attack
     */
    @Override
    public void attack( Character c ) {
        
        Random attack = new Random();
        int attackModifier = 1;
        int attackDmg = attack.nextInt( 5*getLevel() );
        
        for ( int i = 0; i < getItems().size(); i++ ) {
            if ( getItems().get( i ).equals( "Mace" ) || 
             getItems().get( i ).equals( "Sword" ) ) {
                attackModifier += 0.5;
            }
        }
        attackDmg *= attackModifier;
        
        System.out.println(getName() + " hits " + c.getName() + " for "
         + attackDmg + " damage.");
        
        c.takeDamage( attackDmg );
    }
}
