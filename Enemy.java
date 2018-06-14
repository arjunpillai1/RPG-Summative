/*
 * 
 * 
 */
// imports
import java.util.Random;

abstract class Enemy extends CombatCharacter{
  
  private World futureStep;
  private World previousStep;
  private int direction=1;
  
  /*
   * 
   * 
   */
  Enemy(int health, int strength, int intelligence, int defence, int level, int accuracy,
        String name, int posX, int posY, World initialGround){
    super(health, strength, intelligence, defence, level, accuracy, name, posX, posY);
    this.previousStep = initialGround;
  }
  
  /*
   * 
   * 
   */
  void attack(World player) {
    int damage = getStr() + getInt() * 2;
    ((Character)player).setHealth(((Character)player).getHealth()-damage);
  }
  /*
   * 
   * 
   */
  void move(World[][] world, int coordX, int coordY) {
    Random rand = new Random();
    int decision;
    
    for (int i=coordX-1; i < coordX+2; i++) {
      for (int j=coordY-1; j < coordY+2; j++) {
        if (world[i][j] instanceof Player) {
          attack(world[i][j]);
          return;
        }
      }
    }
    
    // we can set a limit on their movement based on the original position that we save it as from initialization

    // this code is for random movement
    decision = rand.nextInt(5);
    if (decision == 1 && coordX >= getX() - 2) { // up
      
      if (world[coordX - 1][coordY] instanceof Grass) { //checks if spot is walkable
        futureStep = world[coordX - 1][coordY];
        world[coordX - 1][coordY] = world[coordX][coordY];
        world[coordX][coordY] = previousStep;
        previousStep = futureStep;
      }
    }
    else if (decision == 2 && coordX <= getX() + 2) { // down
      if (world[coordX + 1][coordY] instanceof Grass) { //checks if spot is walkable
        futureStep = world[coordX + 1][coordY];
        world[coordX + 1][coordY] = world[coordX][coordY];
        world[coordX][coordY] = previousStep;
        previousStep = futureStep;
      }
    }
    else if (decision == 3 && coordY <= getY() + 2) { // right
      if (world[coordX][coordY+1] instanceof Grass) { //checks if spot is walkable
        futureStep = world[coordX][coordY+1];
        world[coordX][coordY+1] = world[coordX][coordY];
        world[coordX][coordY] = previousStep;
        previousStep = futureStep;
        direction=1;
      }
    }
    else if (decision == 4 && coordY >= getY() - 2) { // left
      if (world[coordX][coordY-1] instanceof Grass) { //checks if spot is walkable
        futureStep = world[coordX][coordY-1];
        world[coordX][coordY-1] = world[coordX][coordY];
        world[coordX][coordY] = previousStep;
        previousStep = futureStep;
        direction=0;

      }
    }
  }
  /*
   * 
   * 
   */
  void death(Object[][] world, int coordX, int coordY) {
    world[coordX][coordY] = previousStep;
  }
  
  public int getDirection(){
    return direction;
  }
  
  public World getPreviousStep(){
    return previousStep;
  }
}
