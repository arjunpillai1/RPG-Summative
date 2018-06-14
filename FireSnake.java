/**
 * [FireSnake.java]
 * The fire snake enemy
 * @author Aiden, Albert
 * 05/30/2018
 */
class FireSnake extends Type{
  
  /**
   * Default constructor for the fire snakes
   * @param health, strength, intelligence, defence, level, accuracy, initial position x and y, initial spawn ground
   */
  FireSnake(int health, int strength, int intelligence, int defence, int level, int accuracy, 
           String name,  int posX, int posY, World initialGround){
    super(health, strength, intelligence, defence, level, accuracy,"fire", name, posX, posY, initialGround);
    
    setLvl( ((int)(Math.random()*5)+10));//between 10 and 14
  }
  

      
      

}