/* class Potion
 * Johann Muth
 * May 24 2018
 * Abstract class of all potions that boost your stats
*/  

class  DefensePermanentPotion extends PermanentPotion{
  private final int DEFENSEBOOST=5;
  DefensePermanentPotion(int cost){
  super("Defense Permanenet potion", cost);
}
  public int getDefenseBoost(){
    return DEFENSEBOOST;
  }
}