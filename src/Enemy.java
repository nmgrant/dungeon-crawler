import java.util.*;
import java.io.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * A representation of Character that attacks the user and has gold and
 * an item
 * 
 * @author Nicholas Grant
 */
public class Enemy extends Character implements Serializable {
    /**Item found on enemy when killed by hero*/
    private Item item;
    
    /**
     * Enemy constructor
     * @param n     enemy's name
     * @param q     enemy's catchphrase
     * @param h     enemy's max hit points
     * @param l     enemy's current level
     * @param g     enemy's gold dropped on death
     * @param i     enemy's item dropped on death
     */
    public Enemy( String n, String q, int h, int l, int g, Item i ) {
        super( n, q, h, l, g );
        item = i;
    }
    
    /**
     * Gets the item dropped on enemy death
     * @return      enemy's item dropped on death
     */
    public Item getItem() {
        return item;
    }
    /**
     * Attacks target based on enemy's level
     * @param c     target of attack
     */
    @Override
    public void attack( Character c ) {
        Random attack = new Random();
        int attackDmg = attack.nextInt( 5*getLevel() );
        
        System.out.println(getName()+" hits "+c.getName()+" for "
         + attackDmg+" damage.");
        
        c.takeDamage( attackDmg );
    }
}
