/* [Archer.java]
 * Enemy archer
 * @author Aiden
 * 06/14/2018
 */
class Archer extends Normal{
  
  private int newLvl;
  private int imageChoice;
  
  /**
   * Archer constructor
   * @param health, strength, intelligence, defence, level, accuracy, the name, intial position x and y, initial ground
   * of enemy spawns
   */
  Archer(int health, int strength, int intelligence, int defence, int level, int accuracy,
         String name, int posX, int posY , World initialGround){
    super(health, strength, intelligence, defence, level, accuracy, name, posX, posY, initialGround);
    newLvl = ((int)(Math.random()*2)+1);
    setLvl(newLvl);
    imageChoice=((int)(Math.random()*3))*4;//0,4,8
  }
  
  /**
   * Archer image
   * @return the image index for archer
   */
  public int getImageChoice(){
   return this.imageChoice; 
  }
  
}