/*
 * [Troll.java]
 * The Troll enemy
 * @author Arjun
 * 06/14/2018
 */
class Troll extends Normal{
  
  private int newLvl;
  private int imageChoice;
  /**
   * Troll constructor
   * @param health, strength, intelligence, defence, level, accuracy, name, intial position x and y, initial spawn ground
   */
  Troll(int health, int strength, int intelligence, int defence, int level, int accuracy,
         String name, int posX, int posY , World initialGround){
    super(health, strength, intelligence, defence, level, accuracy, name, posX, posY, initialGround);
    newLvl = ((int)(Math.random()*2)+1);
    setLvl(newLvl);
    imageChoice=((int)(Math.random()*3))*4;//0,4,8
  }
  
  /**
   * Returns the associated image index
   * @return image index
   */
  public int getImageChoice(){
   return this.imageChoice; 
  }
  
}
