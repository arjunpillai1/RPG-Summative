/* class Potion
 * Johann Muth
 * May 24 2018
 * Abstract class of all potions that boost your stats
*/  

class  AttackPermanentPotion extends Consumable{
  private final int ATTACKBOOST=5;
  AttackPermanentPotion(int cost){
  super("Permanent Attack Potion", cost);
}
  public int getAttackBoost(){
    return ATTACKBOOST;
  }
}