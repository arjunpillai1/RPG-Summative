/* class Potion
 * Johann Muth
 * May 24 2018
 * Abstract class of potions that boost your attack
 */  

abstract class AttackPotion extends TempPotion{
  
  private int attackBoost = (int)(Math.random()*5)+10;
  
  AttackPotion(String name, int cost, int timer){
    super(name, cost, timer);
  }
  
  public int getAttackBoost(){
    return attackBoost;
  }
  
}