import java.io.*;
import java.util.*;
import java.awt.Point;

/**
 * Generates the dungeon levels based on text file input
 * 
 * @author Nicholas Grant
 */
public class Level implements Serializable  {
    /**Character array representing a dungeon level*/
    private char[][] level;
    
    /**
     * Level constructor
     */
    public Level(){
    }
    
    /**
     * Generates a dungeon level using a text file with a character array
     * @param levelNum
     */
    public void generateLevel( int levelNum ) {
        char[][] l = new char[ 4 ][ 4 ];
        if ( levelNum == 1 ) {
           try {
               Scanner scan = new Scanner( new File( "Level1.txt" ) );
               
               for ( int i = 0; i < 4; i++ ) {
                   l[ i ] = scan.nextLine().toCharArray();
               }
               scan.close();
            } catch ( FileNotFoundException fnf) {
               System.out.println("File not found.");
            }
        }
        if ( levelNum == 2 ) {
           try {
               Scanner scan = new Scanner( new File( "Level2.txt" ) );
               
               for ( int i = 0; i < 4; i++ ) {
                   l[ i ] = scan.nextLine().toCharArray();
               }
               scan.close();
            } catch ( FileNotFoundException fnf) {
               System.out.println("File not found.");
            }
        }
        if ( levelNum == 3 ) {
           try {
               Scanner scan = new Scanner( new File( "Level3.txt" ) );
               
               for ( int i = 0; i < 4; i++ ) {
                   l[ i ] = scan.nextLine().toCharArray();
               }
               scan.close();
            } catch ( FileNotFoundException fnf) {
               System.out.println("File not found.");
            }
        }
        level = l;
    }
    
    /**
     * Returns the character of a room
     * @param p     location of desired room 
     * @return      character at location p
     */
    public char getRoom( Point p ) {
        char room = level[ ( int ) p.getX() ][ ( int ) p.getY() ];
        return room;
    }
    
    /**
     * Displays the dungeon level to the user
     * @param p     the hero's location
     */
    public void displayMap( Point p ) {
        Point room = new Point( 0, 0 );
        
        System.out.println(" ---------");
        
        for ( int r = 0; r < 4; r++ ) {
            System.out.print("| ");
            for ( int c = 0; c < 4; c++ ) {
                room.setLocation( r, c );
                if ( ( p.getX() ) == r && ( p.getY() == c ) ) {
                    System.out.print("* ");
                }
                else {
                    System.out.print(getRoom( room )+ " ");
                }
            }
            System.out.println("|");
        }
        System.out.println(" ---------");
    }
    
    /**
     * Finds the start location in the dungeon level
     * @return      start location
     */
    public Point findStartLocation() {
        Point p = new Point( 0, 0 );
        for ( int r = 0; r < 4; r++ ) {
            for ( int c = 0; c < 4; c++ ) {
                p.setLocation( r, c );
                if ( getRoom( p ) == 's') {
                    return p;
                }
            }
        }
        return null;
    }
}
