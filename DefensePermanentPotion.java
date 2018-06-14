/* class Potion
 * Johann Muth
 * May 24 2018
 * Abstract class of all potions that boost your stats
 */  

class  DefensePermanentPotion extends PermanentPotion{
  private final int DEFENSEBOOST=5;
  /**
   * Default constructor for the defense potions
   */
  DefensePermanentPotion(int cost){
    super("Defense Permanenet potion", cost);
  }
  /**
   * Method that returns the defense value associated with the potion
   * @return int defense value
   */
  public int getDefenseBoost(){
    return DEFENSEBOOST;
  }
}