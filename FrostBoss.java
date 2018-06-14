/*
 * [FrostBoss.java]
 * The Frost boss enemy
 * @author Albert, Aiden 
 * 05/30/2018
 */


class FrostBoss extends Type{

  /**
   * Constructor for frost boss
   * @param health, strength, intelligence, defence, level, accuracy, name, initial x and y, intial spawn ground
   */
    FrostBoss(int health, int strength, int intelligence, int defence, int level, int accuracy, 
           String name,  int posX, int posY, World initialGround){
        super(health, strength, intelligence, defence, level, accuracy,"frost" , 
              "The King of Winter", posX, posY, initialGround);
    }
    
    /**
     * Interact method with the world
     * @param world array, current enemy position x and y
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
