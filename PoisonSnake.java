/*
 * [PoisonSnake.java]
 * Poison Snake enemy
 * @author Aiden
 * 05/30/2018
 */

class PoisonSnake extends Type{
  /**
   * Poison snake constructor
   * @param health, strength, intelligence, defence, level, accuracy, name, initial position x and y, initial spawn ground
   */
  PoisonSnake(int health, int strength, int intelligence, int defence, int level, int accuracy, 
           String name,  int posX, int posY, World initialGround){
    super(health, strength, intelligence, defence, level, accuracy,"poison", name, posX, posY, initialGround);
    setLvl(((int)(Math.random()*3)+3));
  }
  
  

}