/* class Potion
 * Johann Muth
 * May 24 2018
 * Abstract class of potions that boost your attack
 */  

abstract class AttackPotion extends TempPotion{
  
  private int attackBoost;
  
  AttackPotion(String name, int cost, int timer, int attackBoost){
    super(name, cost, timer);
    this.attackBoost=attackBoost;
  }
  
  public int getAttackBoost(){
    return attackBoost;
  }
  
}