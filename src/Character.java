import java.io.*;

/**
 * Abstract representation of a generic type of character
 * 
 * @author Nicholas Grant
 */
public abstract class Character implements Serializable {
    /**Character's name*/
    private String name;
    /**Character's catchphrase*/
    private String quip;
    /**Character's level*/
    private int level;
    /**Character's max hit points*/
    private int hp;
    /**Character's current hit points*/ 
    private int currentHp;
    /**Character's gold amount*/
    private int gold;
    
    /**
     * Character constructor
     * @param n     Character name
     * @param q     Character catchphrase
     * @param l     Character level
     * @param h     Character max hit points
     * @param g     Character gold amount
     */
    public Character( String n, String q, int l, int h, int g ) {
        name = n;
        quip = q;
        level = l;
        hp = h*l;
        gold = g;
        currentHp = hp;
    }
    
    /**
     * Abstract method for character attacks
     * @param c
     */
    public abstract void attack(Character c);
    
    /**
     * Gets character's name
     * @return      character name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets character's catchphrase
     * @return      character catchphrase
     */
    public String getQuip() {
        return quip;
    }
    
    /**
     * Gets character's max hit points
     * @return      character max hit points
     */
    public int getHp() {
        return hp;
    }

    /**
     * Gets character's current hit points
     * @return      character current hit points
     */
    public int getCurrentHp() {
        return currentHp;
    }

    /**
     * Gets character's current level
     * @return      character level
     */
    public int getLevel() {
        return level;
    }
    
    /**
     * Gets character's current gold amount
     * @return      character gold amount
     */
    public int getGold() {
        return gold;
    }

    /**
     * Doubles the character's max and current hit points when they level up
     */
    public void setHp() {
        if ( level == 1 ) {
            currentHp = hp;
        }
        else {
            hp *= 2;
            currentHp *= 2;
        }
    }
    
    /**
     * Sets the character's current level to the parameter
     * @param l     Desired character level
     */
    public void setLevel( int l ) {
        level = l;
    }

    /**
     * Increases character level by 1
     */
    public void increaseLevel() {
        level++;
    }
    
    /**
     * Heals the character to max hit points
     * @param h
     */
    public void heal( int h ) {
        currentHp = hp;
    }
    
    /**
     * Reduces target character's current hit points by the amount of 
     * damage taken
     * @param h     Damage taken
     */
    public void takeDamage( int h ) {
        if ( currentHp < h ) {
            currentHp = 0;
        }
        else {
            currentHp -= h;
        }
    }
    
    /**
     * Adds picked up/sold gold to current amount
     * @param g     gold amount picked up/sold
     */
    public void collectGold( int g ) {
        gold += g;
    }
    
    /**
     * Displays the character's current level and hit points
     */
    public void display() {
        System.out.println(name + ": Level: " + level+ " HP: " + currentHp);
    }
}
