/* class Potion
 * Johann Muth
 * May 24 2018
 * Abstract class of potions that boost your attack
*/  

class DefensePotion extends TempPotion{
  
  private double defenseBoost;
  
  DefensePotion(int cost, int timer, double defenseBoost){
  super("Defense Potion", cost, timer);
  this.defenseBoost=defenseBoost;
  }
  
  public double getDefenseBoost(){
    return defenseBoost;
  }
}