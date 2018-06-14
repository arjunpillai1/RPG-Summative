/* class Potion
 * Johann Muth
 * May 24 2018
 * Abstract class of potions that boost your attack
*/  

class DefensePotion extends TempPotion{
  
  private int defenseBoost =(int)(Math.random()*5)+10;
  
  /**
   * The constructor for defense potions
   * @param the value, the duration
   */
  DefensePotion(int cost, int timer){
  super("Defense Potion", cost, timer);
  }
  /**
   * Gets the defense boost provided by the potion
   * @return the int value of defense;
   */
  public int getDefenseBoost(){
    return defenseBoost;
  }
}