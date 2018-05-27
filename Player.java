/**
 * Player Class
 * Guy and Arjun
 * May 24 2018
 */

class Player extends CombatCharacter {
  private int exp;
  private Object futureStep;
  private Object previousStep;
  
  Player(int health, int strength, int intelligence, int defence, int level, int accuracy, String name) {
    super(health, strength, intelligence, defence, level, accuracy, name);
  }
  
  public void attack(int strength, int intelligence) {
    
  }
  
  public void move(Object[][] world, int coordX, int coordY, int value) {
    
    if (value == 1) {
      if (world[coordX - 1][coordY] instanceof Grass || world[coordX - 1][coordY] instanceof Floor) { //checks if spot is walkable
        futureStep = world[coordX - 1][coordY];
        world[coordX - 1][coordY] = world[coordX][coordY];
        world[coordX][coordY] = previousStep;
        previousStep = futureStep;
      }
    } 
    else if (value == 2) {
      if (world[coordX][coordY + 1] instanceof Grass || world[coordX][coordY + 1] instanceof Floor) {
        futureStep = world[coordX][coordY + 1];
        world[coordX][coordY + 1] = world[coordX][coordY];
        world[coordX][coordY] = previousStep;
        previousStep = futureStep;
      }
    }  
    else if (value == 3) {
      if (world[coordX + 1][coordY] instanceof Grass || world[coordX + 1][coordY] instanceof Floor) {
        futureStep = world[coordX + 1][coordY];
        world[coordX + 1][coordY] = world[coordX][coordY];
        world[coordX][coordY] = previousStep;
        previousStep = futureStep;
      }
    }
    else if (value == 4) {
      if (world[coordX][coordY - 1] instanceof Grass || world[coordX][coordY - 1] instanceof Floor) {
        futureStep = world[coordX][coordY - 1];
        world[coordX][coordY - 1] = world[coordX][coordY];
        world[coordX][coordY] = previousStep;
        previousStep = futureStep;
      }
    }
  }
  
  public int getExp(){
    return exp;
  }
  
  public void setExp(int newExp){
    this.exp = newExp;
  }
  
  public void interact (Object interObj) {
//    if (interObj instanceof Chest) {
//    
//    } else if (interObj instanceof NPC) {
//      
//    }
  }
  
}



