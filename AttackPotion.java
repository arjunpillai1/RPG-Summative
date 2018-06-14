/* class Potion
 * Johann Muth
 * May 24 2018
 * Abstract class of potions that boost your attack
 */  

abstract class AttackPotion extends TempPotion{
  
  private int attackBoost = (int)(Math.random()*5)+10;
  
  /**
   * Constructor for attack potion
   * @param the name, value, duration
   */
  AttackPotion(String name, int cost, int timer){
    super(name, cost, timer);
  }
  /**
   * getter for the attack boost
   * @return int value of attack boost
   */
  public int getAttackBoost(){
    return attackBoost;
  }
  
}