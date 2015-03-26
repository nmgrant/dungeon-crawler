import java.util.*;
import java.awt.*;
import java.io.*;

/**
 * Test implementation of Character and all its subclasses
 * 
 * @author Nicholas Grant
 */
public class DungeonMasterTest {

    public static void main(String[] args) {
        
        Scanner scan = new Scanner( System.in );
        Point start = new Point( 3, 0 );
        Level currentLevel = new Level();
        Hero hero = null;
        File f = new File( "hero.dat" );
        
        boolean retry = true;
        boolean saveProgress = false;
        boolean gameFinished = false;
        
        while ( retry ) {
            if ( f.exists() ) {
                
                try {
                    ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream( f ));

                    hero = ( Hero ) in.readObject();
                    in.close();
                } catch ( IOException e) {
                    System.out.println("Error processing file.");
                } catch ( ClassNotFoundException c ) {
                    System.out.println("Class not found.");
                }
            
                currentLevel.generateLevel( hero.getLevel() );
                System.out.println("Level " + hero.getLevel());
                
                do {
                    int direction = directionMenu( hero, currentLevel );
                    gameFinished = moveHero( hero, direction, currentLevel, f );
                } while ( !saveProgress && !gameFinished );
            
            }
            else {
               
                System.out.println( "What is your name?" );
                String heroName = scan.nextLine();
                hero = new Hero( heroName, "Gotcha!", start );
                currentLevel.generateLevel( hero.getLevel() );
                System.out.println( hero.getName() + " enters the dungeon." );
        
                do {
                    int direction = directionMenu( hero, currentLevel );
                    gameFinished = moveHero( hero, direction, 
                     currentLevel, f );
                    hero.display();
                } while ( !saveProgress && !gameFinished );
            }
            
            if ( hero.getCurrentHp() != 0 ) {
                System.out.println("Congratulations! You beat the dungeon!");
                f.delete();
                retry = false;
            }
            else {
                System.out.println("Game over! Restart from last save point?");
                String restart;
                
                do {
                    System.out.println("(y for yes, n for no)");
                    restart = scan.nextLine();
                } while ( !( restart.equals( "y" ) || restart.equals( "n" ) ) );
                
                if ( restart.equals( "y" ) ) {
                    retry = true;
                }
                else {
                	retry = false;
                }
            }
        }
        scan.close();
    }
    
    /**
     * Displays the menu for user's desired direction
     * @param hero      the user's hero
     * @param level     the current dungeon level
     * @return          an integer indicating direction
     *                  (1 = north, 2 = south, 3 = east, 4 = west)
     */
    public static int directionMenu( Hero hero, Level level ) {
        level.displayMap( hero.getLocation() );
        System.out.println("Choose a direction: ");
	System.out.println("1. North");
	System.out.println("2. South");
        System.out.println("3. East");
        System.out.println("4. West");
	int direction = getValidInt( 1, 4 );
        return getValidDirection( direction, hero, level );
    }
    
    /**
     * Checks user input validity
     * @param low       lower bound of user input (based on menu)
     * @param high      upper bound of user input (based on menu)
     * @return          desired integer for the menu that called this method
     */
    public static int getValidInt( int low, int high ) {
	Scanner in = new Scanner( System.in );
	boolean invalid = true;
	int value = 0;
	while ( invalid ) {
            if( in.hasNextInt() ) {
		value = in.nextInt();
		if ( value >= low && value <= high ) {
                    invalid = false;
		}
		else {
                    System.out.println("Invalid- Retry: ");
		}
            }
            else {
                in.next();
                System.out.println("Invalid input- Retry: ");
            }
	}
	in.close();
	return value;
    }

    /**
     * Checks user direction input validity
     * @param direction     user's desired direction
     * @param hero          user's hero
     * @param level         current dungeon level
     * @return              integer indicating desired direction
     *                      (1 = north, 2 = south, 3 = east, 4 = west)
     */
    public static int getValidDirection( int direction, Hero hero,
     Level level ) {

        if ( direction == 1 ) {
            if ( ( int ) hero.getLocation().getX() - 1 == -1  ) {
                System.out.println("Cannot move that direction.");
                direction = directionMenu(hero,level);
            }
        }
        if ( direction == 2 ) {
            if ( ( int ) hero.getLocation().getX() + 1 == 4  ) {
                System.out.println("Cannot move that direction.");
                direction = directionMenu(hero,level);
            }
        }
        if ( direction == 3 ) {
            if ( ( int ) hero.getLocation().getY() + 1 == 4  ) {
                System.out.println("Cannot move that direction.");
                direction = directionMenu(hero,level);
            }
        }
        if ( direction == 4 ) {
            if ( ( int ) hero.getLocation().getY() - 1 == -1  ) {
                System.out.println("Cannot move that direction.");
                direction = directionMenu(hero,level);
            }
        }
        return direction;
    }

    /**
     * Moves the hero in the desired direction and interacts with the room
     * according to the current dungeon level map
     * @param hero          user's hero
     * @param direction     user's desired direction
     * @param level         current dungeon level
     * @param f             file used to save hero progress
     * @return              returns true if any "Game Over" conditions
     *                      are met (hero dies or finishes dungeon)
     */
    public static boolean moveHero( Hero hero, int direction, Level level,
         File f) {
        boolean gameFinished = false;

        if ( direction == 1 ) {
            
            Point p = new Point( ( int ) hero.getLocation().getX() - 1,
             ( int ) hero.getLocation().getY() );
            hero.setLocation( p );
            level.displayMap( hero.getLocation() );
            
            if ( hero.goNorth( level ) == 'm' ) {
                gameFinished = ( monsterEncounter( hero ) );
            }
            else if ( hero.goNorth( level ) == 'i' ) {
                itemEncounter( hero );
            }
            else if ( hero.goNorth( level ) == 'f' ) {
                gameFinished = nextLevel( hero, level, f );
            }
            else {
                int inventoryChoice = inventoryMenu( hero.getItems() );
                if ( inventoryChoice != 0 ) {
                    System.out.println("You sell your " 
                     + hero.getItems().get( inventoryChoice - 1 ).getName()
                     + " for " + hero.getItems().get( inventoryChoice - 1 )
                     .getValue() + " gold.");
                    hero.collectGold( inventoryChoice - 1 );
                    hero.removeItem( inventoryChoice - 1 );
                }
            }
        }
        if ( direction == 2 ) {
            
            Point p = new Point( ( int ) hero.getLocation().getX() + 1,
             ( int ) hero.getLocation().getY() );
            hero.setLocation( p );
            level.displayMap( hero.getLocation() );
            
            if ( hero.goSouth( level ) == 'm' ) {
                gameFinished = (monsterEncounter( hero ));
            }
            else if ( hero.goSouth( level ) == 'i' ) {
                itemEncounter( hero );
            }
            else if ( hero.goSouth( level ) == 'f' ) {
                gameFinished = nextLevel( hero, level, f );
            }
            else {
                int inventoryChoice = inventoryMenu( hero.getItems() );
                if ( inventoryChoice != 0 ) {
                    System.out.println("You sell your " 
                     + hero.getItems().get( inventoryChoice - 1 ).getName()
                     + " for " + hero.getItems().get( inventoryChoice - 1 )
                     .getValue() + " gold.");
                    hero.collectGold( inventoryChoice - 1 );
                    hero.removeItem( inventoryChoice - 1 );
                }
            }
        }
        if ( direction == 3 ) {
            
            Point p = new Point( ( int ) hero.getLocation().getX(),
             ( int ) hero.getLocation().getY() + 1 );
            hero.setLocation( p );
            level.displayMap( hero.getLocation() );
            
            if ( hero.goEast( level ) == 'm' ) {
                gameFinished = ( monsterEncounter( hero ) );
            }
            else if ( hero.goEast( level ) == 'i' ) {
                itemEncounter( hero );
            }
            else if ( hero.goEast( level ) == 'f' ) {
                gameFinished = nextLevel( hero, level, f );
            }
            else {
                int inventoryChoice = inventoryMenu( hero.getItems() );
                if ( inventoryChoice != 0 ) {
                    System.out.println("You sell your " 
                    + hero.getItems().get( inventoryChoice - 1 ).getName()
                    + " for " + hero.getItems().get( inventoryChoice - 1 )
                    .getValue() + " gold.");
                    hero.collectGold( inventoryChoice - 1 );
                    hero.removeItem( inventoryChoice - 1 );
                }
            }
        }
        if ( direction == 4 ) {
            
            Point p = new Point( ( int ) hero.getLocation().getX(),
             ( int ) hero.getLocation().getY() - 1 );
            hero.setLocation( p );
            level.displayMap( hero.getLocation() );
            
            if ( hero.goWest( level ) == 'm' ) {
                gameFinished = ( monsterEncounter( hero ) );
            }
            else if ( hero.goWest( level ) == 'i' ) {
                itemEncounter( hero );
            }
            else if ( hero.goWest( level ) == 'f' ) {
                gameFinished = nextLevel( hero, level, f );
            }
            else {
                int inventoryChoice = inventoryMenu( hero.getItems() );
                if ( inventoryChoice != 0 ) {
                    System.out.println("You sell your " 
                     + hero.getItems().get( inventoryChoice - 1 ).getName()
                     + " for " + hero.getItems().get( inventoryChoice - 1 )
                     .getValue() + " gold.");
                    hero.collectGold( inventoryChoice - 1 );
                    hero.removeItem( inventoryChoice - 1 );
                }
            }
        }
        return gameFinished;
    }

    /**
     * Handles hero encounters with monster rooms
     * @param hero      user's hero
     * @return          returns true if hero dies          
     */
    public static boolean monsterEncounter( Hero hero ) {
        
        EnemyGenerator enemyGen = new EnemyGenerator();
        Enemy enemy = enemyGen.generateEnemy( hero.getLevel() );
        boolean run = false;
        
        System.out.println(hero.getName() + " has encountered a " 
         + enemy.getName() + ".");
        
        do {
            
            System.out.println(hero.getName() + " has " + hero.getCurrentHp()
             + " health.");
            System.out.println(enemy.getName() + " has " + enemy.getCurrentHp()
             + " health.");
            
            int choice = monsterMenu( hero );
            if ( choice == 1) {
                
                hero.attack( enemy );
                enemy.attack( hero );
                
                if ( hero.getCurrentHp() == 0 ) {
                    System.out.println(enemy.getName() + " says " 
                     + "\"" + enemy.getQuip() + "\"");
                    System.out.println("You have been defeated!");
                    return true;
                }
                if ( enemy.getCurrentHp() == 0 ) {
                    
                    System.out.println(hero.getName() + " has killed a "
                     + enemy.getName() + ".");
                    System.out.println(hero.getName() + " says " 
                     + "\"" + hero.getQuip() + "\"");
                    
                    hero.collectGold( enemy.getGold() );
                    System.out.println(hero.getName() + " receives "
                     + enemy.getGold() + " gold.");
                    
                    System.out.println(hero.getName() + " receives a "
                     + enemy.getItem().getName() + ".");
                    
                    if ( hero.getItems().size() < 5 ) {
                        hero.pickUpItem( enemy.getItem() ); 
                    }
                    else {
                        int sellChoice = sellMenu();
                        if ( sellChoice == 1 ) {
                            hero.collectGold( enemy.getItem().getValue() );
                        }
                        if ( sellChoice == 2 ) {
                            int inventoryChoice = 
                             inventoryMenu( hero.getItems() );
                        System.out.println("You sell your " 
                         + hero.getItems().get(inventoryChoice - 1).getName()
                         + " for " + hero.getItems().get(inventoryChoice - 1)
                          .getValue() + " gold.");
                        hero.collectGold( inventoryChoice - 1 );
                        hero.removeItem( inventoryChoice - 1 );
                        hero.pickUpItem( enemy.getItem() );
                        }
                    }
                    break;
                }
            }
            if ( choice == 2 ) {
                
                enemy.attack(hero);
                
                if ( hero.getCurrentHp() == 0 ) {
                    System.out.println(enemy.getName() + " says " 
                     + "\"" + enemy.getQuip() + "\"");
                    System.out.println("You have been defeated!");
                    return true;
                }
                run = true;
            }
            if ( choice == 3 ) {
                
                hero.heal( hero.getHp() );
                
                for ( int i = 0; i < hero.getItems().size(); i++ ) {
                    if ( hero.getItems().get( i ).getName()
                     .equals( "Health Potion" ) ) {
                    hero.getItems().remove( i );
                    }
                }
            }
        } while ( !run );
        return false;
    }
    
    /**
     * Handles user decision regarding monster encounters
     * @param hero      user's hero
     * @return          user's monster encounter decision
     *                  (1 = attack, 2 = run away)
     */
    public static int monsterMenu( Hero hero ) {
        int high = 2;
        
        System.out.println("What do you do?");
        System.out.println("1. Attack");
        System.out.println("2. Run away");
        
        for ( int i = 0; i < hero.getItems().size(); i++ ) {
            if ( hero.getItems().get( i ).getName()
             .equals( "Health Potion" ) ) {
                System.out.println("3. Use a potion");
                high = 3;
            }
        }
        
        return getValidInt( 1, high );
    }

    /**
     * Handles hero encounters with item rooms
     * @param hero      user's hero
     */
    public static void itemEncounter( Hero hero ) {
        ItemGenerator itemGen = new ItemGenerator();
        Item item = itemGen.generateItem();
        
        System.out.println(hero.getName() + " has found a " 
         + item.getName() + ".");
        
        int itemChoice = itemMenu();
        if ( itemChoice == 1) {
            if ( hero.getItems().size() < 5 ) {
               hero.pickUpItem( item ); 
            }
            else {
                int sellChoice = sellMenu();
                if ( sellChoice == 1 ) {
                    hero.collectGold(item.getValue());
                }
                if ( sellChoice == 2 ) {
                    int inventoryChoice = inventoryMenu( hero.getItems() );
                    System.out.println("You sell your " 
                     + hero.getItems().get( inventoryChoice - 1 ).getName()+
                     " for " + hero.getItems().get( inventoryChoice - 1 ).
                     getValue() + " gold.");
                    hero.collectGold( inventoryChoice - 1 );
                    hero.removeItem( inventoryChoice - 1 );
                    hero.pickUpItem( item );
                }
            }
        }
        if ( itemChoice == 2) {
            System.out.println("You sell your " + item.getName() + " for "
            + item.getValue() + " gold.");
            hero.collectGold( item.getValue() );
        }
    }
    
    /**
     * Handles user's decision on what to do with item found in item room
     * @return          user's decision (1 = keep item, 2 = sell item)
     */
    public static int itemMenu() {
        System.out.println("What do you do?");
        System.out.println("1. Keep it");
        System.out.println("2. Sell it");
        return getValidInt( 1, 2 );
    }
    
    /**
     * Handles filled hero inventory when hero comes across an item
     * @return          user's decision (1 = sell found item, 2 = sell an
     *                  item in hero inventory)
     */
    public static int sellMenu() {
        System.out.println("Not enough inventory space. "
                 + " Would you like to sell this item or another item?");
        System.out.println("1. Sell this item");
        System.out.println("2. Sell an item in your inventory");
        return getValidInt( 1, 2 );
    }
    
    /**
     * Handles selling of a hero's item from inventory
     * @param items         hero's item list
     * @return              user's item to be sold
     */
    public static int inventoryMenu( ArrayList<Item> items ) {
        System.out.println("Choose an item to sell. Type 0 if you would not"
         + " like to sell an item.");
        
        for ( int i = 0; i < items.size(); i++ ) {
            System.out.println( ( i+1 ) +  "." + items.get( i ).getName() );
        }
        
        return getValidInt( 0, items.size() );
    }
    
    /**
     * Handles advancing the hero and dungeon level once the hero has
     * reached the current level's exit
     * @param hero          user's hero
     * @param level         current level
     * @param f             file to save user progress
     * @return              returns true if the dungeon is complete
     */
    public static boolean nextLevel(Hero hero, Level level, File f) {
        
        if ( hero.getLevel() == 3 ) {
            return true;
        }
        
        System.out.println("You have reached the next level!");
        
        hero.increaseLevel();
        hero.setHp();
        
        System.out.println("Level " + hero.getLevel());
        level.generateLevel( hero.getLevel() );
        hero.setLocation( level.findStartLocation() );
        
        saveProgress( hero, f );
        
        return false;
    }

    /**
     * Asks user if they'd like to use a potion after a battle
     * @return          user's choice (1 = use potion, 2 = don't use potion)
     */
    public static int potionMenu() {
        System.out.println("You have a health potion. Would you like"
         + " to use it?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        return getValidInt( 1, 2 );
    }

    /**
     * Saves hero object data to a file
     * @param hero      user's hero
     * @param f         file to save user progress
     */
    public static void saveProgress(Hero hero, File f) {
       try {
            ObjectOutputStream out = new ObjectOutputStream(
             new FileOutputStream ( f ));

            out.writeObject( hero );
            out.close();
            System.out.println("Progress saved.");
        } catch ( IOException e ) {
            System.out.println("Error processing file.");
        }
    }
}
