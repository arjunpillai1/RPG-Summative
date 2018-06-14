/*
 * [PoisonBoss.java]
 * Poison Boss Enemy
 * @author Albert, Aiden
 * 05/30/2018
 */
class PoisonBoss extends Type{
  /*
   * 
   * Poison Boss Constructor
   * @param health, strength, intelligence, defence, level, accuracy, name, initial position x and y, initial spawn ground
   */
  PoisonBoss(int health, int strength, int intelligence, int defence, int level, int accuracy, 
             String name,  int posX, int posY, World initialGround){
    super(health, strength, intelligence, defence, level, accuracy,"poison"/*type*/, "Venom"/*name*/, posX, posY,
          initialGround);
  }
  
  /*
   * Interaction with the world, attacks player
   * @param world array, enemy current position
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