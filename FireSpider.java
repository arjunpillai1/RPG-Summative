/*
 * [FireSpider.java]
 * The fire spider enemy
 * @author Aiden, Albert
 * 05/30/2018
 */
class FireSpider extends Type{
  /**
   * Constructor for fire spider
   * @param health, strength, intelligence, defence, level, accuracy, name, initial position x and y, initial spawn ground
   */
  FireSpider(int health, int strength, int intelligence, int defence, int level, int accuracy, 
           String name,  int posX, int posY, World initialGround){
    super(health, strength, intelligence, defence, level, accuracy,"fire", name, posX, posY, initialGround);
    
    setLvl(((int)(Math.random()*5)+10));//between 10 and 14
    
  }
  

  
  
}


