import java.util.*;
import java.io.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Generates a list of enemies from a text file and provides a method to 
 * randomly generate one of those enemies
 * 
 * @author Nicholas Grant
 */
public class EnemyGenerator implements Serializable {
    /**List of enemies*/
    private ArrayList<Enemy> enemyList;
    
    /**
     * Adds enemies to enemyList using a text file with a list
     * of enemies. Adds a random item and gold amount to each enemy.
     */
    public EnemyGenerator() {
        Random randGold = new Random();
        int gold = randGold.nextInt( 9 ) + 1;
        ItemGenerator itemGen = new ItemGenerator();
        Item item = itemGen.generateItem();
        ArrayList<Enemy> enemies = new ArrayList<>();
        
        try {
            Scanner scan = new Scanner( new File( "EnemyList.txt" ) );
            scan.useDelimiter(",");
            do {
                String name = scan.next();
                String quip = scan.next();
                int hp = scan.nextInt();
                
                enemies.add( new Enemy( name, quip, hp, 1, gold, item ));
            } while ( scan.hasNext() );
            scan.close();
        } catch ( FileNotFoundException fnf ) {
            System.out.println( "File was not found." );
        }
        enemyList = enemies;
    }
    
    /**
     * Generates a random enemy from enemyList. Sets it's level and hp based
     * on the hero's level.
     * @param level     hero's level
     * @return          a random enemy from enemyList
     */
    public Enemy generateEnemy( int level ) {
        Random randEnemy = new Random();
        Enemy enemy = enemyList.get( randEnemy.nextInt( 7 ) );
        
        enemy.setLevel( level );
        enemy.setHp();
        
        return enemy;
    } 
}
