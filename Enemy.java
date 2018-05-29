  // imports
import java.util.Random;

abstract class Enemy extends CombatCharacter{
    
  
  Enemy(int health, int strength, int intelligence, int defence, int level, int accuracy, String name, int posX, int posY){
    super(health, strength, intelligence, defence, level, accuracy, name, posX, posY);
  }
  void attack(Object player) {
    int damage = getStr() + getInt();
    ((Character)player).setHealth(((Character)player).getHealth()-damage); 
  }
  void move(Object[][] map) {
    Random rand = new Random();
    int decision;
    map[1][1] = null;
    // for now enemies are stationary
    for (int i=0; i < map.length; i++) {
      for (int j=0; j < map.length; j++) {
        if (map[i][j] instanceof Player) {
          attack(map[i][j]); 
        }
      }
    }
    // we can set a limit on their movement based on the original position that we save it as from initialization
    
    // this code is for random movement
    decision = rand.nextInt(5);
    // the main method can be used to prevent stepping on important objects
//    if (decision == 1) { // up
//      return 1;
//    }
//    else if (decision == 2) { // down
//      return 2;
//    }
//    else if (decision == 3) { // left
//      return 3;
//    }
//    else if (decision == 4) { // right
//      return 4;
//    }
  }
}

