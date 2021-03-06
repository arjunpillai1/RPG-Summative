/*
 * [FrostSnake.java]
 * The frost snake enemy
 * @author Albert
 * 05/30/2018
 */
class FrostSnake extends Type{
  /**
   * Constructor for frost snake
   * @param health, strength, intelligence, defence, level, accuracy, name, initial position x and y, initial spawn ground
   */
  FrostSnake(int health, int strength, int intelligence, int defence, int level, int accuracy, 
             String name,  int posX, int posY, World initialGround){
    super(health, strength, intelligence, defence, level,
          accuracy,"frost", name, posX, posY, initialGround);
    
    setLvl(((int)(Math.random()*4)+6));//between 6 and 9
    
  }
  
  
  
  
  
}
