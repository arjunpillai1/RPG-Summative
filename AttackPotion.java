/* class Potion
 * Johann Muth
 * May 24 2018
 * Abstract class of potions that boost your attack
*/  

abstract class AttackPotion extends Potion{
  
  double attackMultiplier;
  
  AttackPotion(int cost, int timer, double attackMultiplier){
  super(cost, timer);
  this.attackMultiplier=attackMultiplier;
  }
  
  public double getAttackMultiplier(){
    return attackMultiplier;
  }
  
  public void use(Player user){
    user.setStr((int)(Math.floor((user.getStr()) * (attackMultiplier))));
    used = true;
  }
}