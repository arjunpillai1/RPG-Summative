/* [Bandit.java]
 * Bandit enemy clas
 * @author Aiden
 * 06/10/2018
 */
class Bandit extends Normal{
  
  private int newLvl;
  private int imageChoice;
  /**
   * Bandit constructor
   * @param health, strength, intelligence, defence, level, accuracy of attacks, name, initial position of x
   * and y, initial ground spawned on
   */
  Bandit(int health, int strength, int intelligence, int defence, int level, int accuracy, 
         String name,  int posX, int posY, World initialGround){
    super(health, strength, intelligence, defence, level, accuracy, name, posX, posY, initialGround);
    newLvl = ((int)(Math.random()*2)+1);
    setLvl(newLvl);
    imageChoice=((int)(Math.random()*3))*4;//0,4,8
  }
  
  /**
   * Gets the image associated with the class
   * @return the int value of image 
   */
  public int getImageChoice(){
   return this.imageChoice; 
  }
  
}