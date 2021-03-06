/*
 * [PoisonSpider.java]
 * Poison spider enemy
 * @author Aiden
 * 05/30/2018
 */

class PoisonSpider extends Type{
  /**
   * Poison spider constructor
   * @param health, strength, intelligence, defence, level, accuracy, name, initial position x and y, initial spawn ground
   */
  PoisonSpider(int health, int strength, int intelligence, int defence, int level, int accuracy, 
               String name,  int posX, int posY, World initialGround){
    super(health, strength, intelligence, defence, level, accuracy,"poison", name, posX, posY, initialGround);
    setLvl(((int)(Math.random()*3)+3));//between 3 and 5
    
  }
  
  
}
