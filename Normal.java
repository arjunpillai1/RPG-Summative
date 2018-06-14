/*
 * [Normal.java]
 * Normal enemy type
 * @author Aiden
 * 05/25/2018
 */

abstract class Normal extends Enemy{
  
  /**
   * Normal type constructor
   * @param health, strength, intelligence, defence, level, accuracy, name, initial enemy x and y, initial spawn ground
   */
    Normal(int health, int strength, int intelligence, int defence, int level, int accuracy, 
           String name,  int posX, int posY, World initialGround){
    super(health, strength, intelligence, defence, level, accuracy, name, posX, posY, initialGround);
  }
}
