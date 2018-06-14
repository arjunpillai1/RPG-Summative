/*
 * [FireBoss.java]
 * The Fire boss, related to enemies
 * @author Albert, Aiden
 * 05/28/2018
 */
class FireBoss extends Type{
  /**
   * Constructor for the fire boss
   * @param health, strength, intelligence, defense, level, accuracy, name, initial position x and y, the initial world object
   * spawned in on
   */
  FireBoss(int health, int strength, int intelligence, int defence, int level, int accuracy, 
           String name,  int posX, int posY, World initialGround){
    super(health, strength, intelligence, defence, level, accuracy,"fire", "The Volcano", posX, posY
            , initialGround);
  }
  
  
  
  /**
   * Interaction method for the enemy
   * @param the world array, enemy position 
   */
  @Override
  public void move(World[][] world, int coordX, int coordY){
    for (int i=coordX-2; i < coordX+3; i++) {
      for (int j=coordY-2; j < coordY+3; j++) {
        if (world[i][j] instanceof Player) {
          attack(world[i][j]);
          return;
        }
      }
    }
  }
}

