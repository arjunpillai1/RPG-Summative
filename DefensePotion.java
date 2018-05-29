/* class Potion
 * Johann Muth
 * May 24 2018
 * Abstract class of potions that boost your attack
*/  

abstract class DefensePotion extends Potion{
  
  double defenseMultiplier;
  
  DefensePotion(int cost, int timer, double defenseMultiplier){
  super(cost, timer);
  this.defenseMultiplier=defenseMultiplier;
  }
  
  public double getDefenseMultiplier(){
    return defenseMultiplier;
  }
  
  public void use(Player user){
    user.setDef((int)(Math.floor((user.getDef()) * (defenseMultiplier))));
    used = true;
  }
}