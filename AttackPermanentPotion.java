/* class Potion
 * Johann Muth
 * May 24 2018
 * Abstract class of all potions that boost your stats
*/  

class  AttackPermanentPotion extends PermanentPotion{
  private final int ATTACKBOOST=5;
  /**
   * Constructor for permanent potion
   * @param the value of the item
   */
  AttackPermanentPotion(int cost){
  super("Permanent Attack Potion", cost);
}
  /**
   * Returns the constant of attack boost
   * @return the int constant value of attack boost
   */
  public int getAttackBoost(){
    return ATTACKBOOST;
  }
}